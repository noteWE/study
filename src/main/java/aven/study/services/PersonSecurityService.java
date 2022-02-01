package aven.study.services;

import aven.study.models.Person;
import aven.study.models.dto.PersonSecurity;
import aven.study.repositories.PersonsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonSecurityService implements UserDetailsService {

    private final String PERSON_NOT_FOUND_MSG = "Person with login: %s not found!";
    private final PersonsRepository repository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Person person = repository.findPersonByLogin(login).orElseThrow(() -> new UsernameNotFoundException(
                String.format(PERSON_NOT_FOUND_MSG, login)));
        return PersonSecurity.builder()
                .id(person.getId())
                .email(person.getEmail())
                .password(person.getPassword())
                .login(person.getLogin())
                .role(person.getRole())
                .build();
    }
}
