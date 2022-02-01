package aven.study.services;

import aven.study.exceptions.BadRequestException;
import aven.study.exceptions.ForbiddenException;
import aven.study.exceptions.NotFoundException;
import aven.study.models.*;
import aven.study.models.Class;
import aven.study.models.dto.ClassDto;
import aven.study.models.dto.RatedCourse;
import aven.study.repositories.CoursesRepository;
import aven.study.util.MyExpressionToSqlConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoursesServiceImpl implements CoursesService {

    private final CoursesRepository courses;
    private final PersonService persons;
    private final ClassesService classes;
    private final PersonCoursesService personCourses;
    private final CourseTeachersService teachersService;
    private final RatedCoursesService ratedCourses;

    @Override
    public PersonCourse signPersonToCourse(Integer idPerson, Course course) {
        if (getStudentCourses(idPerson).stream().noneMatch(coursePers -> coursePers.getId().equals(course.getId()))) {
            if (course.getState() == CourseState.ANNOUNCED) {
                throw new BadRequestException("Вы не можете записать на курс, он ещё не начался");
            } else if (course.getState() == CourseState.FINISHED) {
                throw new BadRequestException("Вы не можете записать на курс, он уже закончился");
            }
            PersonCourse personCourse = new PersonCourse();
            personCourse.setIdCourse(course.getId());
            personCourse.setIdPerson(idPerson);
            personCourse.setStartDate(new Date(System.currentTimeMillis()));
            return personCourses.addPersonCourse(personCourse);
        } else {
            throw new BadRequestException("Вы уже подписанны на этот курс");
        }
    }

    @Override
    public Course addCourse(String login, Course course) {
        Person person = persons.getByLogin(login);
        course.setState(CourseState.ANNOUNCED);
        Course course1 = courses.save(course);
        CourseTeacher courseTeacher = new CourseTeacher();
        courseTeacher.setIdCourse(course1.getId());
        courseTeacher.setIdPerson(person.getId());
        teachersService.addTeacherCourse(courseTeacher);
        return course1;
    }

    @Override
    public Class addClass(String login, Course course, ClassDto classDto) {
        Person person = persons.getByLogin(login);
        CourseTeacher courseTeacher = teachersService.getCourseTeacherByIdCourse(course.getId());
        if (!person.getId().equals(courseTeacher.getIdPerson())) {
            throw new ForbiddenException("Нельзя добавлять занятия не к своим курсам!");
        }

        Class cl = new Class();
        cl.setIdCourse(course.getId());
        cl.setTitle(classDto.getTitle());
        cl.setDate(classDto.getDate());
        cl.setFileAnswer(classDto.isFileAnswer());
        cl.setWrittenAnswer(classDto.isWrittenAnswer());

        ClassBody classBody = new ClassBody();
        classBody.setClassDescription(classDto.getClassDescription());
        classBody.setHomeworkDescription(classDto.getHomeworkDescription());

        return classes.addClass(cl, classBody);
    }

    @Override
    @Transactional
    public void delete(String login, Course course) {
        if (courses.existsById(course.getId())) {
            if (course.getState() != CourseState.ANNOUNCED) {
                throw new BadRequestException("Вы не можите удалить уже начатый курс");
            }
            Person person = persons.getByLogin(login);
            CourseTeacher courseTeacher = teachersService.getCourseTeacherByIdCourse(course.getId());
            if (courseTeacher.getIdPerson().equals(person.getId())) {
                classes.deleteByIdCourse(course.getId());
                courses.delete(course);
            } else {
                throw new ForbiddenException("Вы не можите удалить не свой курс");
            }
        }
        else {
            throw new NotFoundException();
        }
    }

    @Override
    public Iterable<Course> getAllCourses() {
        return courses.findAll();
    }

    @Override
    public Course getById(Integer id) {
        return courses.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Course> getStudentCourses(Integer idPerson) {
        return courses.findCoursesByStudent(idPerson);
    }

    @Override
    public List<Course> getTeacherCourses(Integer idPerson) {
        return courses.findCoursesByTeacher(idPerson);
    }

    @Override
    public List<Course> getCoursesByTeacher(Integer idPerson) {
        return courses.findCoursesByTeacher(idPerson);
    }

    @Override
    public List<Course> getCoursesByTheme(CourseTheme theme) {
        return courses.findCoursesByTheme(theme.name());
    }

    @Override
    @Transactional
    public Course edit(String login, Course course) {
        if (courses.existsById(course.getId())) {
            if (course.getState() != CourseState.ANNOUNCED) {
                throw new BadRequestException("Вы не можите изменить уже начатый курс");
            }
            Person person = persons.getByLogin(login);
            CourseTeacher courseTeacher = teachersService.getCourseTeacherByIdCourse(course.getId());
            if (courseTeacher.getIdPerson().equals(person.getId())) {
                return courses.save(course);
            } else {
                throw new ForbiddenException("Вы не можите изменить не свой курс");
            }
        }
        else {
            throw new NotFoundException();
        }
    }

    @Override
    public Person getCourseTeacher(Integer id) {
        return persons.getTeacherByCourseId(id);
    }

    @Override
    public List<Person> getCourseStudents(Integer id) {
        return persons.getStudentsByCourseId(id);
    }

    @Override
    public List<Class> getCourseClasses(Integer id) {
        return classes.getClassesByIdCourse(id);
    }

    @Override
    @Transactional
    public PersonCourse rateCourse(String login, Course course, Integer mark) {
        Person person = persons.getByLogin(login);
        PersonCourse personCourse = personCourses.getByIdPersonAndIdCourse(person.getId(), course.getId())
                .orElseThrow(() -> new BadRequestException("Невозможно поставить оценку курсу"));
        personCourse.setRate(mark);
        return personCourses.edit(personCourse);
    }

    private CourseState nextStateCourse(CourseState state) {
        switch (state.name()) {
            case "ANNOUNCED":
                return CourseState.STARTED;
            case "STARTED":
                return CourseState.FINISHED;
            default:
                return CourseState.ANNOUNCED;
        }
    }

    @Override
    @Transactional
    public Course changeStateCourse(String login, Course course) {
        if (courses.existsById(course.getId())) {
            Person person = persons.getByLogin(login);
            if (teachersService.getCourseTeacherByIdCourse(course.getId()).getIdPerson().equals(person.getId())) {
                if (course.getState() == CourseState.FINISHED) {
                    throw new BadRequestException("Нельзя изменить состояние курса, курс закрыт");
                }
                course.setState(nextStateCourse(course.getState()));
                return courses.save(course);
            } else {
                throw new ForbiddenException("Вы не можете менять состояние не вашего курса");
            }
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public Iterator<RatedCourse> getCoursesBySortingAndGroupingParams(String field,
                                                                      String order,
                                                                      String filter,
                                                                      Integer pageNumber,
                                                                      Integer pageSize) {
        var map = MyExpressionToSqlConvertor.parseFilteringExpression(filter);
        Sort sort = MyExpressionToSqlConvertor.parseSortingExpression(field, order);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        if (map.containsKey(MyExpressionToSqlConvertor.FilterFields.TEACHER)) {
            String login = map.get(MyExpressionToSqlConvertor.FilterFields.TEACHER).iterator().next();
            Person person = persons.getByLogin(login);
            return ratedCourses.getByStateInAndThemeInAndIdTeacher(
                    map.get(MyExpressionToSqlConvertor.FilterFields.STATUS),
                    map.get(MyExpressionToSqlConvertor.FilterFields.THEME),
                    person.getId(),
                    pageable).iterator();
        } else {
            return ratedCourses.getByStateInAndThemeIn(
                    map.get(MyExpressionToSqlConvertor.FilterFields.STATUS),
                    map.get(MyExpressionToSqlConvertor.FilterFields.THEME),
                    pageable).iterator();
        }
    }
}
