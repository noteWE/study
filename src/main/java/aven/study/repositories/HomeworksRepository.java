package aven.study.repositories;

import aven.study.models.Homework;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HomeworksRepository extends CrudRepository<Homework, Integer> {

    @Query("SELECT * FROM Homeworks WHERE IdClass = :idClass")
    List<Homework> findHomeworksByIdClass(Integer idClass);

    @Query("SELECT * FROM Homeworks WHERE IdPerson = :idPerson AND IdClass = :idClass")
    Optional<Homework> findHomeworkByIdPersonAndIdClass(Integer idClass, Integer idPerson);
}
