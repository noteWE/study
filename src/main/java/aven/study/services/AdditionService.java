package aven.study.services;

import aven.study.models.Addition;

import java.util.List;

public interface AdditionService {
    Addition getAdditionById(Integer id);

    List<Addition> getAdditionsByIdClass(Integer idClass);

    Addition addAddition(Addition addition);

    Addition edit(String login, Addition addition);

    void delete(String login, Addition addition);
}
