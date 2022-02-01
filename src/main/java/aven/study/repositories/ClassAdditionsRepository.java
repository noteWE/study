package aven.study.repositories;

import aven.study.models.ClassAddition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassAdditionsRepository extends CrudRepository<ClassAddition, Integer> {
    ClassAddition findClassAdditionByIdClass(Integer idClass);
    ClassAddition findClassAdditionByIdAddition(Integer idAddition);
}
