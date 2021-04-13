package softuni.boardgames.eventcustom;

import org.springframework.context.ApplicationEvent;

public class GameStatisticEvent extends ApplicationEvent {

    private String gameName;

    public GameStatisticEvent(String gameName) {
        super(gameName);
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }


}
