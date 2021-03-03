package softuni.boardgames.service.impl;

import org.springframework.stereotype.Service;
import softuni.boardgames.repository.GameRepository;
import softuni.boardgames.service.GameService;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public void seedGames() {

    }
}
