package softuni.boardgames.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import softuni.boardgames.model.entity.GameStatisticEntity;
import softuni.boardgames.model.service.GameStatisticServiceModel;
import softuni.boardgames.repository.GameStatisticRepository;
import softuni.boardgames.service.GameStatisticService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameStatisticServiceImpl implements GameStatisticService {

    private final GameStatisticRepository gameStatisticRepository;
    private final ModelMapper modelMapper;

    public GameStatisticServiceImpl(GameStatisticRepository gameStatisticRepository,
                                    ModelMapper modelMapper) {
        this.gameStatisticRepository = gameStatisticRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addToStatistic(String gameName) {
        GameStatisticEntity statisticEntity = gameStatisticRepository.findByName(gameName);
        if(statisticEntity == null){
            GameStatisticEntity gameStatisticEntity = new GameStatisticEntity();
            gameStatisticEntity.setName(gameName);
            gameStatisticEntity.setVisits(1);
            gameStatisticRepository.save(gameStatisticEntity);
        } else {
            statisticEntity.setVisits(statisticEntity.getVisits() + 1);
            gameStatisticRepository.save(statisticEntity);
        }
    }

    @Override
    public List<GameStatisticServiceModel> getAll() {
        return gameStatisticRepository.findAll(Sort.by(Sort.Direction.DESC, "visits"))
                .stream()
                .map(gse -> modelMapper.map(gse, GameStatisticServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void clearStatistic() {
        gameStatisticRepository.deleteAll();
    }
}
