package aven.study.repositories;

import aven.study.models.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CoursesRepository extends CrudRepository<Course, Integer> {

    @Query("SELECT Courses.Id, Courses.Title, Courses.Theme, Courses.Difficult, Courses.State " +
            "FROM Courses JOIN CourseTeachers ON Courses.Id = CourseTeachers.IdCourse " +
            "WHERE CourseTeachers.IdPerson = :idPerson;")
    List<Course> findCoursesByTeacher(Integer idPerson);

    @Query("SELECT * FROM Courses WHERE Courses.Theme = :theme;")
    List<Course> findCoursesByTheme(String theme);

    @Query("SELECT Courses.Id, Courses.Title, Courses.Theme, Courses.Difficult, Courses.State " +
            "FROM Courses JOIN PersonCourses ON Courses.Id = PersonCourses.IdCourse " +
            "WHERE PersonCourses.IdPerson = :idPerson;")
    List<Course> findCoursesByStudent(Integer idPerson);
}
