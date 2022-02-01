package aven.study.repositories;

import aven.study.models.Person;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonsRepository extends CrudRepository<Person, Integer> {

    Person findPersonByEmail(String email);

    @Query("SELECT Persons.Id, firstname, lastname, gender, birthdate, email, phone, login, password " +
            "FROM Persons JOIN CourseTeachers ON Persons.Id = CourseTeachers.IdPerson " +
            "WHERE CourseTeachers.IdCourse = :id;")
    Person findTeacherByCourseId(Integer id);

    @Query("SELECT Persons.Id, firstname, lastname, gender, birthdate, email, phone, login, password " +
            "FROM Persons JOIN PersonCourses ON Persons.Id = PersonCourses.IdPerson " +
            "WHERE PersonCourses.IdCourse = :id")
    List<Person> findStudentsByCourseId(Integer id);

    Optional<Person> findPersonByLogin(String login);

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);
}
