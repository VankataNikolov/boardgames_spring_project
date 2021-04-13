package softuni.boardgames.model.service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class GameStatisticServiceModel {
    private String name;
    private Integer visits;

    public GameStatisticServiceModel() {
    }

    @Size(min = 2, max = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public Integer getVisits() {
        return visits;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }
}
