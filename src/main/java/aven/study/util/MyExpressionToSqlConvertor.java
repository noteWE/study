package aven.study.util;

import aven.study.exceptions.BadRequestException;
import aven.study.models.CourseState;
import aven.study.models.CourseTheme;
import org.springframework.data.domain.Sort;

import java.util.*;
import java.util.function.Supplier;

public class MyExpressionToSqlConvertor {

    private static Map<Integer, String> themes;

    static {
        themes = new HashMap<>();
        themes.put(CourseTheme.ANALYTIC.ordinal(), CourseTheme.ANALYTIC.name());
        themes.put(CourseTheme.TESTING.ordinal(), CourseTheme.TESTING.name());
        themes.put(CourseTheme.BACKEND.ordinal(), CourseTheme.BACKEND.name());
        themes.put(CourseTheme.DESIGN.ordinal(), CourseTheme.DESIGN.name());
        themes.put(CourseTheme.FRONTEND.ordinal(), CourseTheme.FRONTEND.name());
    }

    private static Map<Integer, String> statuses;

    static {
        statuses = new HashMap<>();
        statuses.put(CourseState.ANNOUNCED.ordinal(), CourseState.ANNOUNCED.name());
        statuses.put(CourseState.STARTED.ordinal(), CourseState.STARTED.name());
    }

    public static final int ALL_THEMES = 0x1F;
    public static final int ALL_STATUSES = 0x3;

    public enum FilterFields {
        THEME,
        STATUS,
        TEACHER
    }

    public static Sort parseSortingExpression(String field, String order) {
        return Sort.by(
                Sort.Direction.valueOf(
                        Optional.ofNullable(order)
                                .orElse("ASC")
                                .toUpperCase(Locale.ROOT)
                ),
                Optional.ofNullable(field)
                .orElse("id"));
    }

    public static EnumMap<FilterFields, Set<String>> parseFilteringExpression(String filtering) {
        return Arrays.stream(Optional.ofNullable(filtering)
                .orElse(String.format("THEME(%d)|STATUS(%d)", ALL_THEMES, ALL_STATUSES))
                .split("\\|"))
                .filter((x) -> !x.isEmpty())
                .collect(
                        (Supplier<HashMap<String, String>>) HashMap::new,
                        (x, y) -> {
                            String[] terms = y.split("[\\(\\)]");
                            if (terms.length == 2) {
                                x.put(terms[0], terms[1]);
                            } else {
                                throw new BadRequestException("Не верный формат данных");
                            }
                        },
                        Map::putAll
                )
                .entrySet()
                .stream()
                .collect(
                        () -> {
                            var enumMap = new EnumMap<FilterFields, Set<String>>(FilterFields.class);
                            enumMap.put(FilterFields.THEME, themeDecoder(ALL_THEMES));
                            enumMap.put(FilterFields.STATUS, statusDecoder(ALL_STATUSES));
                            return enumMap;
                        },
                        (x, y) -> {
                            if (y.getKey().intern() == "THEME") {
                                try {
                                    int num = Integer.parseInt(y.getValue());
                                    x.put(FilterFields.THEME, themeDecoder(num));
                                } catch (NumberFormatException ex) {
                                    throw new BadRequestException("Не верный формат данных");
                                }
                            } else if (y.getKey().intern() == "STATUS") {
                                try {
                                    int num = Integer.parseInt(y.getValue());
                                    x.put(FilterFields.STATUS, statusDecoder(num));
                                } catch (NumberFormatException ex) {
                                    throw new BadRequestException("Не верный формат данных");
                                }
                            } else if (y.getKey().intern() == "TEACHER") {
                                x.put(FilterFields.TEACHER, Set.of(y.getValue()));
                            }
                        },
                        EnumMap::putAll

                );
    }

    private static Set<String> themeDecoder(int flag) {
        Set<String> list = new HashSet<>();
        flag = flag & 0x1F;
        for (int i = 0; i < 5; i++) {
            if ((flag & 0x1) == 1) {
                list.add(themes.get(i));
            }
            flag >>= 1;
        }
        return list;
    }

    private static Set<String> statusDecoder(int flag) {
        Set<String> list = new HashSet<>();
        flag = flag & 0x3;
        for (int i = 0; i < 2; i++) {
            if ((flag & 0x1) == 1) {
                list.add(statuses.get(i));
            }
            flag >>= 1;
        }
        return list;
    }
}
