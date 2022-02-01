package aven.study.rest;

import aven.study.models.Homework;
import aven.study.models.HomeworkBody;
import aven.study.services.HomeworkBodiesService;
import aven.study.services.HomeworksService;
import aven.study.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HomeworksController {

    private final HomeworksService service;
    private final PersonService persons;

    @GetMapping("/teacher/homework/{id}")
    public Homework getHomework(@PathVariable(name = "id") Homework homework) {
        return homework;
    }

    @GetMapping("/teacher/homework/{id}/body")
    public HomeworkBody getTeacherHomeworkBody(@PathVariable(name = "id") Homework homework) {
        return service.getTeacherHomeworkBody(homework.getIdHomeworkBody());
    }

    @GetMapping("/student/homework/{id}/body")
    public HomeworkBody getStudentHomeworkBody(@AuthenticationPrincipal String login, @PathVariable(name = "id") Homework homework) {
        return service.getStudentHomeworkBody(login, homework);
    }

    @PatchMapping("/teacher/homework/{id}")
    public Homework setMarkHomework(@AuthenticationPrincipal String login, @PathVariable(name = "id") Homework homework, @RequestParam(name = "mark") Integer rate) {
        return service.setMarkHomework(login, homework, rate);
    }

    @PutMapping("/student/homework/{id}/body")
    public HomeworkBody editHomeworkBody(@AuthenticationPrincipal String login,
                                         @PathVariable(name = "id") Homework homework,
                                         @RequestBody HomeworkBody homeworkBody) {
        return service.editHomeworkBody(login, homework, homeworkBody);
    }
}
