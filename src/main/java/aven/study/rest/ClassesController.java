package aven.study.rest;

import aven.study.models.*;
import aven.study.models.Class;
import aven.study.models.dto.ClassDto;
import aven.study.services.ClassesService;
import aven.study.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClassesController {

    private final ClassesService service;

    @GetMapping("class/{id}")
    public Class getClassById(@PathVariable(name = "id") Class myClass) {
        return myClass;
    }

    @GetMapping("class/{id}/additions")
    public List<Addition> getClassAdditions(@PathVariable Integer id) {
        return service.getClassAdditions(id);
    }

    @GetMapping("class/{id}/body")
    public ClassBody getClassBody(@PathVariable(name = "id") Class cl) {
        if (cl.getIdClassBody() != null) {
            return service.getClassBody(cl.getIdClassBody());
        } else {
            throw new RuntimeException("тело класса незадано");
        }
    }

    @GetMapping("teacher/class/{id}/homeworks")
    public List<Homework> getAllClassHomeworks(@PathVariable Integer id) {
        return service.getAllClassHomeworks(id);
    }

    @GetMapping("teacher/class/{id}/homework")
    public Homework getPersonClassHomeworks(@PathVariable(name = "id") Class cl,
                                            @RequestParam(name = "idPerson") Integer idPerson) {
        return service.getClassHomeworkByIdPerson(cl, idPerson);
    }

    @GetMapping("student/class/{id}/my_homework")
    public Homework getMyClassHomework(@PathVariable(name = "id") Class cl, @AuthenticationPrincipal String login) {
        return service.getPersonClassHomework(cl, login);
    }

    /*@PostMapping("teacher/class/{id}/body/add")
    public ClassBody addClassBody(@PathVariable(name = "id") Class cl, @RequestBody ClassBody classBody) {
        return service.addClassBody(cl, classBody);
    }*/

    @PostMapping("student/class/{id}/homework/add")
    public Homework addClassHomework(@AuthenticationPrincipal String login,
                                     @PathVariable(name = "id") Class cl, @RequestBody HomeworkBody homeworkBody) {
        return service.addHomework(login, cl, homeworkBody);
    }

    @PostMapping("teacher/class/{id}/addition/add")
    public Addition addClassAddition(@PathVariable(name = "id") Class cl, @RequestBody Addition addition) {
        return service.addAddition(cl, addition);
    }

    @PutMapping("teacher/class/{id}")
    public Class editClass(@AuthenticationPrincipal String login, @PathVariable(name = "id") Class classOld, @RequestBody ClassDto classNew) {
        return service.edit(login, classOld, classNew);
    }

    @DeleteMapping("teacher/class/{id}")
    public void deleteClass(@AuthenticationPrincipal String login, @PathVariable(name = "id") Class cl) {
        service.delete(login, cl);
    }

}
