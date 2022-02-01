package aven.study.services;

import aven.study.exceptions.ForbiddenException;
import aven.study.exceptions.NotFoundException;
import aven.study.models.Addition;
import aven.study.models.CourseTeacher;
import aven.study.models.dto.PersonSecurity;
import aven.study.repositories.AdditionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdditionsServiceImpl implements AdditionService {

    private final AdditionsRepository additions;
    private final PersonSecurityService persons;
    private final ClassAdditionsService classAdditions;
    private ClassesService classes;
    private final CourseTeachersService courseTeachers;

    @Autowired
    public void setClasses(ClassesService classes) {
        this.classes = classes;
    }

    @Override
    public Addition getAdditionById(Integer id) {
        return additions.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Addition> getAdditionsByIdClass(Integer idClass) {
        return additions.findAdditionsByIdClass(idClass);
    }

    @Override
    public Addition addAddition(Addition addition) {
        return additions.save(addition);
    }

    @Override
    public Addition edit(String login, Addition addition) {
        if (additions.existsById(addition.getId())) {
            PersonSecurity person = (PersonSecurity) persons.loadUserByUsername(login);
            CourseTeacher courseTeacher = courseTeachers.getCourseTeacherByIdCourse(
                    classes
                            .getClassById(
                                    classAdditions
                                            .getByIdAddition(addition.getId())
                                            .getIdClass())
                            .getIdCourse());
            if (courseTeacher.getIdPerson().equals(person.getId())) {
                return additions.save(addition);
            } else {
                throw new ForbiddenException("Нельзя изменять дополнительный материал, который вам не принадлежит");
            }
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public void delete(String login, Addition addition) {
        if (additions.existsById(addition.getId())) {
            PersonSecurity person = (PersonSecurity) persons.loadUserByUsername(login);
            CourseTeacher courseTeacher = courseTeachers.getCourseTeacherByIdCourse(
                    classes
                            .getClassById(
                                    classAdditions
                                            .getByIdAddition(addition.getId())
                                            .getIdClass())
                            .getIdCourse());
            if (courseTeacher.getIdPerson().equals(person.getId())) {
                additions.delete(addition);
            } else {
                throw new ForbiddenException("Нельзя удалять дополнительный материал, который вам не принадлежит");
            }
        } else {
            throw new NotFoundException();
        }
    }
}
