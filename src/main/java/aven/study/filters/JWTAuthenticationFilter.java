package aven.study.filters;

import aven.study.models.Person;
import aven.study.models.dto.PersonSecurity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static aven.study.util.SecurityConstants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        setFilterProcessesUrl("/person/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            Person creds = new ObjectMapper()
                    .readValue(request.getInputStream(), Person.class);
            /*List<GrantedAuthority> list = new ArrayList<>();
            list.add(new SimpleGrantedAuthority(creds.getRole().name()));*/

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    creds.getLogin(),
                    creds.getPassword(),
                    new ArrayList<>()
            );

            return authenticationManager.authenticate(token);
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        PersonSecurity person = (PersonSecurity) authResult.getPrincipal();
        String token = JWT.create()
                .withSubject(person.getLogin())
                .withClaim("role", person.getRole().name())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET_KEY.getBytes()));

        String body = person.getLogin() + " " + token;

        response.getWriter().write(body);
        response.getWriter().flush();
    }
}
