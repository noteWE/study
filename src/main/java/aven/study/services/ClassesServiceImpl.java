package aven.study.services;

import aven.study.exceptions.BadRequestException;
import aven.study.exceptions.ForbiddenException;
import aven.study.exceptions.NotFoundException;
import aven.study.models.*;
import aven.study.models.Class;
import aven.study.models.dto.ClassDto;
import aven.study.models.dto.PersonSecurity;
import aven.study.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassesServiceImpl implements ClassesService {

    private final ClassesRepository classes;
    private final AdditionService additions;
    private final ClassBodiesService bodies;
    private HomeworksService homeworks;
    private final ClassAdditionsService classAdditions;
    private final HomeworkBodiesService homeworkBodies;
    private final CourseTeachersService courseTeachers;
    private final PersonSecurityService persons;
    private final PersonCoursesService personCourses;
    private CoursesService courses;

    @Autowired
    public void setHomeworks(HomeworksService homeworks) {
        this.homeworks = homeworks;
    }

    @Autowired
    public void setCourses(CoursesService courses) {
        this.courses = courses;
    }

    @Override
    public Class getClassById(Integer id) {
        return classes.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public ClassBody getClassBody(Integer id) {
        return bodies.getClassBodyById(id);
    }

    @Override
    public List<Addition> getClassAdditions(Integer id) {
        return additions.getAdditionsByIdClass(id);
    }

    @Override
    public List<Homework> getAllClassHomeworks(Integer id) {
        return homeworks.getHomeworksByIdClass(id);
    }

    @Override
    public Homework getClassHomeworkByIdPerson(Class cl, Integer idPerson) {
        return homeworks.getHomeworkByIdPersonAndIdClass(cl.getId(), idPerson);
    }

    @Override
    public Homework getPersonClassHomework(Class cl, String login) {
        PersonSecurity person = (PersonSecurity) persons.loadUserByUsername(login);
        return homeworks.getHomeworkByIdPersonAndIdClass(cl.getId(), person.getId());
    }

    @Override
    public ClassBody addClassBody(Class cl, ClassBody clBody) {
        ClassBody body = bodies.addClassBody(clBody);
        cl.setIdClassBody(body.getId());
        classes.save(cl);
        return body;
    }

    @Override
    public Homework addHomework(String login, Class cl, HomeworkBody homeworkBody) {
        Homework homework = null;
        PersonSecurity person = (PersonSecurity) persons.loadUserByUsername(login);
        Course course = courses.getById(cl.getIdCourse());
        PersonCourse personCourse = personCourses.getByIdPersonAndIdCourse(person.getId(), course.getId())
                .orElseThrow(() -> new BadRequestException(
                        "Вы ещё не записанны на этот курс, вы не можите добавить к нему домашнюю работу"));
        if (course.getState() == CourseState.FINISHED) {
            throw new BadRequestException("Вы не можите добавить домашнюю работу к курсу, который закончился");
        }
        try {
            homework = homeworks.getHomeworkByIdPersonAndIdClass(cl.getId(), person.getId());
        } catch (NotFoundException exception) {
            homework = new Homework();
        }
        if (homework.getIdHomeworkBody() == null) {
            HomeworkBody bodyFromDb = homeworkBodies.addHomeworkBody(homeworkBody);
            homework.setIdPerson(person.getId());
            homework.setIdClass(cl.getId());
            homework.setIdHomeworkBody(bodyFromDb.getId());
            homework.setDateOfCompletion(new Date(System.currentTimeMillis()));
            return homeworks.addHomework(homework);
        } else {
            throw new BadRequestException("Домашняя работа уже есть");
        }
    }

    @Override
    public Addition addAddition(Class cl, Addition add) {
        Addition addition = additions.addAddition(add);
        ClassAddition classAdd = new ClassAddition();
        classAdd.setIdAddition(addition.getId());
        classAdd.setIdClass(cl.getId());
        classAdditions.addClassAddition(classAdd);
        return addition;
    }

    @Override
    @Transactional
    public void delete(String login, Class cl) {
        if (classes.existsById(cl.getId())) {
            PersonSecurity person = (PersonSecurity) persons.loadUserByUsername(login);
            CourseTeacher courseTeacher = courseTeachers.getCourseTeacherByIdCourse(cl.getIdCourse());
            Course course = courses.getById(cl.getIdCourse());
            if (courseTeacher.getIdPerson().equals(person.getId())) {
                if (course.getState() == CourseState.ANNOUNCED || cl.getDate().compareTo(new Date(System.currentTimeMillis())) > 0) {
                    classes.delete(cl);
                    bodies.delete(bodies.getClassBodyById(cl.getIdClassBody()));
                } else {
                    throw new BadRequestException("Нельзя удалять прошедшие(текущие) курсы");
                }
            } else {
                throw new ForbiddenException("Нельзя удалять не свои занятия");
            }
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public Class edit(String login, Class cl, ClassDto classNew) {
        if (classes.existsById(cl.getId())) {
            Course course = courses.getById(cl.getIdCourse());
            if (course.getState() == CourseState.ANNOUNCED || cl.getDate().compareTo(new Date(System.currentTimeMillis())) > 0) {
                PersonSecurity person = (PersonSecurity) persons.loadUserByUsername(login);
                CourseTeacher courseTeacher = courseTeachers.getCourseTeacherByIdCourse(cl.getIdCourse());
                if (courseTeacher.getIdPerson().equals(person.getId())) {
                    cl.setDate(classNew.getDate());
                    cl.setWrittenAnswer(classNew.isWrittenAnswer());
                    cl.setFileAnswer(classNew.isFileAnswer());
                    ClassBody body = bodies.getClassBodyById(cl.getIdClassBody());
                    body.setClassDescription(classNew.getClassDescription());
                    body.setHomeworkDescription(classNew.getHomeworkDescription());
                    bodies.edit(body);
                    return classes.save(cl);
                } else {
                    throw new ForbiddenException("Нельзя редактировать не свои занятия");
                }
            } else {
                throw new BadRequestException("Нельзя изменять прошедшие(текущие) занятия");
            }
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public ClassBody editBody(ClassBody classBody) {
        return bodies.edit(classBody);
    }

    @Override
    public Class addClass(Class cl, ClassBody classBody) {
        ClassBody classBody1 = bodies.addClassBody(classBody);
        cl.setIdClassBody(classBody1.getId());
        return classes.save(cl);
    }

    @Override
    public List<Class> getClassesByIdCourse(Integer id) {
        return classes.findByIdCourse(id);
    }

    @Override
    @Transactional
    public void deleteByIdCourse(Integer idCourse) {
        classes.deleteByIdCourse(idCourse);
    }
}
