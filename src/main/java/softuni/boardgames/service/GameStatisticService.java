package softuni.boardgames.service;


import softuni.boardgames.model.service.GameStatisticServiceModel;

import java.util.List;

public interface GameStatisticService {
    void addToStatistic(String gameName);

    List<GameStatisticServiceModel> getAll();

    void clearStatistic();
}
