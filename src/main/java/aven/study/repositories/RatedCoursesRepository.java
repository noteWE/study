package aven.study.repositories;

import aven.study.models.dto.RatedCourse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RatedCoursesRepository extends PagingAndSortingRepository<RatedCourse, Integer> {

    List<RatedCourse> findByStateInAndThemeInAndIdTeacher(Set<String> statuses,
                                                          Set<String> themes,
                                                          Integer idTeacher,
                                                          Pageable pageable);

    List<RatedCourse> findByStateInAndThemeIn(Set<String> statuses,
                                              Set<String> themes,
                                              Pageable pageable);
}
