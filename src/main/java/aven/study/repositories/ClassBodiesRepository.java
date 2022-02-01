package aven.study.repositories;

import aven.study.models.ClassBody;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassBodiesRepository extends CrudRepository<ClassBody, Integer> {
}
