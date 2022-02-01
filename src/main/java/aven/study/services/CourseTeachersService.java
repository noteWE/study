package aven.study.services;

import aven.study.models.Course;
import aven.study.models.CourseTeacher;

public interface CourseTeachersService {

    CourseTeacher getCourseTeacherByIdCourse(Integer id);

    CourseTeacher addTeacherCourse(CourseTeacher courseTeacher);


}
