package aven.study.repositories;

import aven.study.models.Addition;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdditionsRepository extends CrudRepository<Addition, Integer> {

    @Query("SELECT Additions.Id, Description, Link FROM Additions " +
            "JOIN ClassAdditions on Additions.Id = ClassAdditions.IdAddition " +
            "WHERE ClassAdditions.IdClass = :idClass")
    List<Addition> findAdditionsByIdClass(int idClass);
}
