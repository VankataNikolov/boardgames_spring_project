package softuni.boardgames.eventcustom;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class GameStatisticEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public GameStatisticEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEvent(String gameName){
        GameStatisticEvent event = new GameStatisticEvent(gameName);
        applicationEventPublisher.publishEvent(event);
    }
}
