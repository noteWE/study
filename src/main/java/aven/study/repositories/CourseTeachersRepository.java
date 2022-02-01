package aven.study.repositories;

import aven.study.models.CourseTeacher;
import org.springframework.data.repository.CrudRepository;

public interface CourseTeachersRepository extends CrudRepository<CourseTeacher, Integer> {

    CourseTeacher findCourseTeacherByIdCourse(Integer id);
}
