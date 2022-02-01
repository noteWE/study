package aven.study.services;

import aven.study.models.dto.RatedCourse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface RatedCoursesService {
    List<RatedCourse> getByStateInAndThemeInAndIdTeacher(Set<String> statuses,
                                                          Set<String> themes,
                                                          Integer idTeacher,
                                                          Pageable pageable);

    List<RatedCourse> getByStateInAndThemeIn(Set<String> statuses,
                                              Set<String> themes,
                                              Pageable pageable);
}
