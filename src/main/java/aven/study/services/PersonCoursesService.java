package aven.study.services;


import aven.study.models.PersonCourse;

import java.util.Optional;

public interface PersonCoursesService {
    PersonCourse getById(Integer id);

    Optional<PersonCourse> getByIdPersonAndIdCourse(Integer idPerson, Integer idCourse);

    PersonCourse addPersonCourse(PersonCourse personCourse);

    void delete(PersonCourse personCourse);

    PersonCourse edit(PersonCourse personCourse);
}
