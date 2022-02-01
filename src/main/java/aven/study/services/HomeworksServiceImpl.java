package aven.study.services;

import aven.study.exceptions.BadRequestException;
import aven.study.exceptions.ForbiddenException;
import aven.study.exceptions.NotFoundException;
import aven.study.models.CourseState;
import aven.study.models.Homework;
import aven.study.models.HomeworkBody;
import aven.study.models.dto.PersonSecurity;
import aven.study.repositories.HomeworksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeworksServiceImpl implements HomeworksService {

    private final HomeworksRepository homeworks;
    private final HomeworkBodiesService bodies;
    private final PersonSecurityService persons;
    private ClassesService classes;
    private CoursesService courses;
    private final CourseTeachersService courseTeachers;

    @Autowired
    public void setClasses(ClassesService classes) {
        this.classes = classes;
    }

    @Autowired
    public void setCourses(CoursesService courses) {
        this.courses = courses;
    }

    @Override
    public List<Homework> getAllHomeworksByClassId(Integer id) {
        return homeworks.findHomeworksByIdClass(id);
    }

    @Override
    public Homework getHomeworkById(Integer id) {
        return homeworks.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public HomeworkBody addHomeworkBody(Homework homework, HomeworkBody homeworkBody) {
        HomeworkBody homeworkBody1 = bodies.addHomeworkBody(homeworkBody);
        homework.setIdHomeworkBody(homeworkBody1.getId());
        homeworks.save(homework);
        return homeworkBody1;
    }

    @Override
    public Homework edit(String login, Homework homework) {
        if (homeworks.existsById(homework.getId())) {
            return homeworks.save(homework);
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public void delete(Homework homework) {
        if (homeworks.existsById(homework.getId())) {
            homeworks.delete(homework);
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public HomeworkBody getTeacherHomeworkBody(Integer idBody) {
        return bodies.getHomeworkBodyById(idBody);
    }

    @Override
    public HomeworkBody getStudentHomeworkBody(String login, Homework homework) {
        PersonSecurity person = (PersonSecurity) persons.loadUserByUsername(login);
        if (homework.getIdPerson().equals(person.getId())) {
            return bodies.getHomeworkBodyById(homework.getIdHomeworkBody());
        } else {
            throw new ForbiddenException("Запрешенно просматривать чужие домашние работы");
        }
    }

    @Override
    @Transactional
    public Homework setMarkHomework(String login, Homework homework, Integer rate) {
        PersonSecurity person = (PersonSecurity) persons.loadUserByUsername(login);
        if (courseTeachers.getCourseTeacherByIdCourse(
                courses.getById(
                        classes.getClassById(homework.getIdClass()).getIdCourse()).getId())
                .getIdPerson().equals(person.getId())) {
            if (homework.getRate() == null) {
                homework.setRate(rate);
                return homeworks.save(homework);
            } else {
                throw new BadRequestException("Домашней работе уже выставленна оценка");
            }
        } else {
            throw new ForbiddenException("Вы не можите установить оценку домашней работы не своего курса!");
        }
    }

    @Override
    public Homework getHomeworkByIdPersonAndIdClass(Integer idClass, Integer idPerson) {
        return homeworks.findHomeworkByIdPersonAndIdClass(idClass, idPerson).orElseThrow(() -> new NotFoundException("Вы ещё не сдали работу"));
    }

    @Override
    public List<Homework> getHomeworksByIdClass(Integer id) {
        return homeworks.findHomeworksByIdClass(id);
    }

    @Override
    public Homework addHomework(Homework homework) {
        return homeworks.save(homework);
    }

    @Override
    @Transactional
    public HomeworkBody editHomeworkBody(String login, Homework homework, HomeworkBody homeworkBody) {
        PersonSecurity person = (PersonSecurity) persons.loadUserByUsername(login);
        if (homework.getIdPerson().equals(person.getId())) {
            if (courses.getById(
                    classes.getClassById(
                            homework.getIdClass()).getIdCourse())
                    .getState() == CourseState.STARTED) {
                return bodies.edit(homeworkBody);
            } else {
                throw new BadRequestException("Вы не можите изменить домашнюю работу курса, который уже закончился!");
            }
        } else {
            throw new ForbiddenException("Запрещенно изменять чужие домашние работы");
        }
    }
}
