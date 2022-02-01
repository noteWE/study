package aven.study.rest;

import aven.study.models.Course;
import aven.study.models.Person;
import aven.study.models.PersonCourse;
import aven.study.models.Views;
import aven.study.services.CoursesService;
import aven.study.services.PersonService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonController {

    private final PersonService persons;
    private final CoursesService courses;

    @PostMapping(value = "person/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @JsonView(Views.PublicData.class) Person register(@RequestBody Person person) {
        return persons.addPerson(person);
    }

    @GetMapping("student/person/courses")
    public List<Course> getCurrentStudentCourses(@AuthenticationPrincipal String login) {
        return courses.getStudentCourses(persons.getByLogin(login).getId());
    }

    @GetMapping("/teacher/person/courses")
    public List<Course> getCurrentTeacherCourses(@AuthenticationPrincipal String login) {
        return courses.getTeacherCourses(persons.getByLogin(login).getId());
    }

    @GetMapping("/person/:login")
    public @JsonView(Views.PublicData.class) Person getPersonByLogin(@PathVariable String login) {
        return persons.getByLogin(login);
    }
    
    @GetMapping("person/current")
    public @JsonView(Views.PublicData.class) Person getCurrentPerson(@AuthenticationPrincipal String login) {
        return persons.getByLogin(login);
    }

    @PostMapping("student/person/course/{id}/sign")
    public PersonCourse signPersonToCourse(@AuthenticationPrincipal String login,
                                           @PathVariable(name = "id") Course course) {
        return courses.signPersonToCourse(persons.getByLogin(login).getId(), course);
    }
}
