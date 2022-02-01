package aven.study.repositories;

import aven.study.models.PersonCourse;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonCoursesRepository extends CrudRepository<PersonCourse, Integer> {
    Optional<PersonCourse> findPersonCourseByIdPersonAndIdCourse(Integer idPerson, Integer idCourse);
}
