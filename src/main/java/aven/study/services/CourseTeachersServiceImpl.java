package aven.study.services;

import aven.study.models.CourseTeacher;
import aven.study.repositories.CourseTeachersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseTeachersServiceImpl implements CourseTeachersService {
    private final CourseTeachersRepository courseTeachers;

    @Override
    public CourseTeacher getCourseTeacherByIdCourse(Integer id) {
        return courseTeachers.findCourseTeacherByIdCourse(id);
    }

    @Override
    public CourseTeacher addTeacherCourse(CourseTeacher courseTeacher) {
        return courseTeachers.save(courseTeacher);
    }
}
