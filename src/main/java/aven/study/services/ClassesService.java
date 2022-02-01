package aven.study.services;

import aven.study.models.*;
import aven.study.models.Class;
import aven.study.models.dto.ClassDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClassesService {

    Class getClassById(Integer id);

    ClassBody getClassBody(Integer id);

    List<Addition> getClassAdditions(Integer id);

    List<Homework> getAllClassHomeworks(Integer id);

    Homework getClassHomeworkByIdPerson(Class cl, Integer idPerson);

    Homework getPersonClassHomework(Class id, String idPerson);

    ClassBody addClassBody(Class cl, ClassBody clBody);

    Addition addAddition(Class cl, Addition add);

    void delete(String login, Class cl);

    Class edit(String login, Class cl, ClassDto classNew);

    ClassBody editBody(ClassBody classBody);

    Class addClass(Class cl, ClassBody classBody);

    List<Class> getClassesByIdCourse(Integer id);

    Homework addHomework(String id, Class id1, HomeworkBody homeworkBody);

    void deleteByIdCourse(Integer idCourse);
}
