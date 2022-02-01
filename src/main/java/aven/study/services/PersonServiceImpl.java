package aven.study.services;

import aven.study.exceptions.BadRequestException;
import aven.study.exceptions.NotFoundException;
import aven.study.models.Course;
import aven.study.models.Person;
import aven.study.repositories.PersonsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonsRepository persons;
    private final BCryptPasswordEncoder encoder;

    @Override
    public Person addPerson(Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        String login = person.getLogin();
        String email = person.getEmail();
        if (persons.existsByLogin(login)) {
            throw new BadRequestException(String.format("Пользователь с логин: %s существует", login));
        } else if (persons.existsByEmail(email)) {
            throw new BadRequestException(String.format("Пользователь с адресом электронной почты: %s существует", email));
        } else {
            return persons.save(person);
        }
    }

    @Override
    public void delete(Person person) {
        persons.delete(person);
    }

    @Override
    public Person getById(Integer id) {
        return persons.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Person getByLogin(String login) {
        return persons.findPersonByLogin(login).orElseThrow(NotFoundException::new);
    }

    @Override
    public Person getByEmail(String email) {
        Person person = persons.findPersonByEmail(email);
        if (person != null) {
            return persons.findPersonByEmail(email);
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public Person edit(Person person) {
        if (persons.existsById(person.getId()))
            return persons.save(person);
        else
            throw new NotFoundException();
    }

    @Override
    public Person getTeacherByCourseId(Integer id) {
        return persons.findTeacherByCourseId(id);
    }

    @Override
    public List<Person> getStudentsByCourseId(Integer id) {
        return persons.findStudentsByCourseId(id);
    }
}
