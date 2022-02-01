package aven.study.services;

import aven.study.exceptions.NotFoundException;
import aven.study.models.HomeworkBody;
import aven.study.repositories.HomeworkBodiesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeworkBodiesServiceImpl implements HomeworkBodiesService {

    private final HomeworkBodiesRepository bodies;

    @Override
    public HomeworkBody getHomeworkBodyById(Integer id) {
        return bodies.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public HomeworkBody addHomeworkBody(HomeworkBody homeworkBody) {
        return bodies.save(homeworkBody);
    }

    @Override
    public HomeworkBody edit(HomeworkBody homeworkBody) {
        if (bodies.existsById(homeworkBody.getId())) {
            return bodies.save(homeworkBody);
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public void delete(HomeworkBody homeworkBody) {
        if (bodies.existsById(homeworkBody.getId())) {
            bodies.delete(homeworkBody);
        } else {
            throw new NotFoundException();
        }
    }
}
