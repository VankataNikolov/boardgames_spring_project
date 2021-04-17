package softuni.boardgames.eventcustom;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import softuni.boardgames.service.GameStatisticService;

@Component
public class GameStatisticScheduler {

    private final GameStatisticService gameStatisticService;

    public GameStatisticScheduler(GameStatisticService gameStatisticService) {
        this.gameStatisticService = gameStatisticService;
    }

    @Scheduled(cron = "@weekly") //weekly "0 0 1 * * MON" | every minute "0 * * * * *" | every week "@weekly"
    public void onWeek() {
        gameStatisticService.clearStatistic();
    }
}
