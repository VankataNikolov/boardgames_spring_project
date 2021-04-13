package softuni.boardgames.eventcustom;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import softuni.boardgames.service.GameStatisticService;

@Component
public class GameStatisticEventListener {

    private final GameStatisticService gameStatisticService;

    public GameStatisticEventListener(GameStatisticService gameStatisticService) {
        this.gameStatisticService = gameStatisticService;
    }

    @EventListener(GameStatisticEvent.class)
    public void onGameStatisticEvent(GameStatisticEvent event){
        gameStatisticService.addToStatistic(event.getGameName());
    }
}
