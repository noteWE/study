package aven.study.rest;

import aven.study.models.Addition;
import aven.study.services.AdditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AdditionController {
    private final AdditionService service;

    @GetMapping("addition/{id}")
    public Addition getAdditionById(@PathVariable(name = "id") Addition addition) {
        return addition;
    }

    @PutMapping("teacher/addition/{id}")
    public Addition editAddition(@AuthenticationPrincipal String login,
                                 @PathVariable(name = "id") Addition addition, @RequestBody Addition newAddition) {
        newAddition.setId(addition.getId());
        return service.edit(login, newAddition);
    }

    @DeleteMapping("teacher/addition/{id}")
    public void deleteAddition(@AuthenticationPrincipal String login, @PathVariable(name = "id") Addition addition) {
        service.delete(login, addition);
    }
}
