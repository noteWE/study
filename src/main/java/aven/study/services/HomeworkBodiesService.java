package aven.study.services;

import aven.study.models.HomeworkBody;

public interface HomeworkBodiesService {
    HomeworkBody getHomeworkBodyById(Integer id);

    HomeworkBody addHomeworkBody(HomeworkBody homeworkBody);

    HomeworkBody edit(HomeworkBody homeworkBody);

    void delete(HomeworkBody homeworkBody);
}
