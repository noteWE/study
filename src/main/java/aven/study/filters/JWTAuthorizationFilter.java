package aven.study.filters;

import aven.study.exceptions.ForbiddenException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static aven.study.util.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);

        if (header == null) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            try {
                DecodedJWT jwt = JWT.require(Algorithm.HMAC512(SECRET_KEY.getBytes()))
                        .build()
                        .verify(token);
                String personLogin = jwt.getSubject();
                String personRole = jwt.getClaim("role").asString();
                List<GrantedAuthority> list = new ArrayList<>();
                list.add(new SimpleGrantedAuthority("ROLE_" + personRole));
                if (personLogin != null) {
                    return new UsernamePasswordAuthenticationToken(personLogin, null, list);
                }
            } catch (JWTVerificationException exception) {
                throw new ForbiddenException(exception.getMessage(), exception.getCause());
            }
            return null;
        }
        return null;
    }
}
