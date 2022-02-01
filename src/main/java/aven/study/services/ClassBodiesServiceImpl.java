package aven.study.services;

import aven.study.exceptions.NotFoundException;
import aven.study.models.ClassBody;
import aven.study.repositories.ClassBodiesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassBodiesServiceImpl implements ClassBodiesService {

    private final ClassBodiesRepository bodies;

    @Override
    public ClassBody getClassBodyById(Integer id) {
        return bodies.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public ClassBody addClassBody(ClassBody classBody) {
        return bodies.save(classBody);
    }

    @Override
    public ClassBody edit(ClassBody classBody) {
        if (bodies.existsById(classBody.getId())) {
            return bodies.save(classBody);
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public void delete(ClassBody classBody) {
        if (bodies.existsById(classBody.getId())) {
            bodies.delete(classBody);
        } else {
            throw new NotFoundException();
        }
    }
}
