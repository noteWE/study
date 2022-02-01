package aven.study.services;

import aven.study.models.dto.RatedCourse;
import aven.study.repositories.RatedCoursesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RatedCoursesServiceImpl implements RatedCoursesService {

    private final RatedCoursesRepository ratedCourses;

    @Override
    public List<RatedCourse> getByStateInAndThemeInAndIdTeacher(Set<String> statuses, Set<String> themes, Integer idTeacher, Pageable pageable) {
        return ratedCourses.findByStateInAndThemeInAndIdTeacher(statuses, themes, idTeacher, pageable);
    }

    @Override
    public List<RatedCourse> getByStateInAndThemeIn(Set<String> statuses, Set<String> themes, Pageable pageable) {
        return ratedCourses.findByStateInAndThemeIn(statuses, themes, pageable);
    }
}
