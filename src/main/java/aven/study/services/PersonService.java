package aven.study.services;

import aven.study.models.Course;
import aven.study.models.Person;

import java.util.List;

public interface PersonService {

    Person addPerson(Person person);

    void delete(Person person);

    Person getById(Integer id);

    Person getByLogin(String login);

    Person getByEmail(String email);

    Person edit(Person person);

    Person getTeacherByCourseId(Integer id);

    List<Person> getStudentsByCourseId(Integer id);
}
