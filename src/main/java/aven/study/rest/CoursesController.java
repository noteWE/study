package aven.study.rest;

import aven.study.models.*;
import aven.study.models.Class;
import aven.study.models.dto.ClassDto;
import aven.study.models.dto.RatedCourse;
import aven.study.services.CoursesService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CoursesController {

    private final CoursesService service;

    @GetMapping("/courses")
    public Iterable<Course> getAllCourses() {
        return service.getAllCourses();
    }

    @GetMapping("/courses/sorting_filtering_paging")
    public Iterator<RatedCourse> getCourseSorted(@RequestParam(name = "by", required = false) String field,
                                                 @RequestParam(name = "order", required = false) String order,
                                                 @RequestParam(name = "filter", required = false) String filter,
                                                 @RequestParam(name = "pageSize") Integer pageSize,
                                                 @RequestParam(name = "pageNumber") Integer pageNumber) {
        return service.getCoursesBySortingAndGroupingParams(field, order, filter, pageNumber, pageSize);
    }

    @GetMapping("/course/{id}")
    public Course getCourseById(@PathVariable(name = "id") Course course) {
        return course;
    }

    @GetMapping("course/{id}/classes")
    public List<Class> getCourseClasses(@PathVariable Integer id) {
        return service.getCourseClasses(id);
    }

    @GetMapping("course/{id}/teacher")
    public Person getCourseTeachers(@PathVariable Integer id) {
        return service.getCourseTeacher(id);
    }

    @GetMapping("teacher/course/{id}/students")
    public List<Person> getCourseStudents(@PathVariable Integer id) {
        return service.getCourseStudents(id);
    }

    @PostMapping(value = "teacher/course/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course createCourse(@AuthenticationPrincipal String login,
                               @JsonView(Views.DataWithoutKeys.class) @RequestBody Course course) {
        return service.addCourse(login, course);
    }

    @PutMapping("teacher/course/{id}")
    public Course editCourse(@AuthenticationPrincipal String login,
                             @PathVariable(name = "id") Course courseOld,
                             @JsonView(Views.DataWithoutKeys.class) @RequestBody Course courseNew) {
        courseNew.setId(courseOld.getId());
        return service.edit(login, courseNew);
    }

    @PatchMapping("/student/course/{id}/rate")
    public PersonCourse rateCourse(@AuthenticationPrincipal String login, @PathVariable(name = "id") Course course,
                                   @RequestParam(name = "mark") Integer mark) {
        return service.rateCourse(login, course, mark);
    }

    @PatchMapping("/teacher/course/{id}/change_state")
    public Course changeStateCourse(@AuthenticationPrincipal String login, @PathVariable(name = "id") Course course) {
        return service.changeStateCourse(login, course);
    }

    @PostMapping("teacher/course/{id}/class/add")
    public Class addClass(@AuthenticationPrincipal String login, @PathVariable(name = "id") Course course,
                          @RequestBody ClassDto classDto) {
        return service.addClass(login, course, classDto);
    }

    @DeleteMapping("/teacher/course/{id}")
    public void deleteCourse(@AuthenticationPrincipal String login, @PathVariable(name = "id") Course course) {
        service.delete(login, course);
    }
}
