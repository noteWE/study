package aven.study.services;

import aven.study.exceptions.NotFoundException;
import aven.study.models.ClassAddition;
import aven.study.repositories.ClassAdditionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassAdditionsServiceImpl implements ClassAdditionsService {

    private final ClassAdditionsRepository classAdditions;

    @Override
    public ClassAddition getById(Integer id) {
        return classAdditions.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public ClassAddition getByIdClass(Integer idClass) {
        return classAdditions.findClassAdditionByIdClass(idClass);
    }

    @Override
    public ClassAddition getByIdAddition(Integer idAddition) {
        return classAdditions.findClassAdditionByIdAddition(idAddition);
    }

    @Override
    public ClassAddition addClassAddition(ClassAddition classAddition) {
        return classAdditions.save(classAddition);
    }

    @Override
    public ClassAddition edit(ClassAddition classAddition) {
        if (classAdditions.existsById(classAddition.getId())) {
            return classAdditions.save(classAddition);
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public void delete(ClassAddition classAddition) {
        if (classAdditions.existsById(classAddition.getId())) {
            classAdditions.delete(classAddition);
        } else {
            throw new NotFoundException();
        }
    }
}
