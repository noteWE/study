package aven.study.services;

import aven.study.models.Homework;
import aven.study.models.HomeworkBody;

import java.util.List;

public interface HomeworksService {
    List<Homework> getAllHomeworksByClassId(Integer id);

    Homework getHomeworkById(Integer id);

    HomeworkBody addHomeworkBody(Homework homework, HomeworkBody homeworkBody);

    Homework edit(String login, Homework homework);

    void delete(Homework homework);

    Homework setMarkHomework(String login, Homework homework, Integer rate);

    Homework getHomeworkByIdPersonAndIdClass(Integer idClass, Integer idPerson);

    List<Homework> getHomeworksByIdClass(Integer id);

    Homework addHomework(Homework homework);

    HomeworkBody editHomeworkBody(String login, Homework homework, HomeworkBody homeworkBody);

    HomeworkBody getTeacherHomeworkBody(Integer idBody);

    HomeworkBody getStudentHomeworkBody(String login, Homework homework);
}
