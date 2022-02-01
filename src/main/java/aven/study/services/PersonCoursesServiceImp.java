package aven.study.services;

import aven.study.exceptions.NotFoundException;
import aven.study.models.PersonCourse;
import aven.study.repositories.PersonCoursesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonCoursesServiceImp implements PersonCoursesService {

    private final PersonCoursesRepository personCourses;

    @Override
    public PersonCourse getById(Integer id) {
        return personCourses.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Optional<PersonCourse> getByIdPersonAndIdCourse(Integer idPerson, Integer idCourse) {
        return personCourses.findPersonCourseByIdPersonAndIdCourse(idPerson, idCourse);
    }

    @Override
    public PersonCourse addPersonCourse(PersonCourse personCourse) {
        return personCourses.save(personCourse);
    }

    @Override
    public void delete(PersonCourse personCourse) {
        if (personCourses.existsById(personCourse.getId())) {
            personCourses.delete(personCourse);
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public PersonCourse edit(PersonCourse personCourse) {
        if (personCourses.existsById(personCourse.getId())) {
            return personCourses.save(personCourse);
        } else {
            throw new NotFoundException();
        }
    }
}
