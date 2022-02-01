package aven.study.services;

import aven.study.models.ClassAddition;

public interface ClassAdditionsService {

    ClassAddition getById(Integer id);

    ClassAddition getByIdClass(Integer idClass);

    ClassAddition getByIdAddition(Integer idAddition);

    ClassAddition addClassAddition(ClassAddition classAddition);

    ClassAddition edit(ClassAddition classAddition);

    void delete(ClassAddition classAddition);
}
