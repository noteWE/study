package aven.study.services;

import aven.study.models.*;
import aven.study.models.Class;
import aven.study.models.dto.ClassDto;
import aven.study.models.dto.RatedCourse;

import java.util.Iterator;
import java.util.List;

public interface CoursesService {

    PersonCourse signPersonToCourse(Integer idPerson, Course course);

    Course addCourse(String login, Course course);

    Class addClass(String login, Course course, ClassDto classDto);

    void delete(String login, Course course);

    Iterable<Course> getAllCourses();

    Course getById(Integer id);

    List<Course> getStudentCourses(Integer idPerson);

    List<Course> getTeacherCourses(Integer idPerson);

    List<Course> getCoursesByTeacher(Integer idPerson);

    List<Course> getCoursesByTheme(CourseTheme theme);

    Course edit(String login, Course course);

    Person getCourseTeacher(Integer id);

    List<Person> getCourseStudents(Integer id);

    List<Class> getCourseClasses(Integer id);

    PersonCourse rateCourse(String login, Course course, Integer mark);

    Course changeStateCourse(String login, Course course);

    Iterator<RatedCourse> getCoursesBySortingAndGroupingParams(String field, String order, String filter, Integer pageNumber, Integer pageSize);
}
