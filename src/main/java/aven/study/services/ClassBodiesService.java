package aven.study.services;

import aven.study.models.ClassBody;

public interface ClassBodiesService {
    ClassBody getClassBodyById(Integer id);

    ClassBody addClassBody(ClassBody classBody);

    ClassBody edit(ClassBody classBody);

    void delete(ClassBody classBody);
}
