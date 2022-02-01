package aven.study.repositories;

import aven.study.models.Class;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassesRepository extends CrudRepository<Class, Integer> {

    List<Class> findByIdCourse(Integer id);

    void deleteByIdCourse(Integer id);
}
