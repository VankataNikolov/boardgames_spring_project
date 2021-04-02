package softuni.boardgames.service;

import softuni.boardgames.model.service.LinkServiceModel;

import java.util.List;

public interface LinkService {

    void seedLinks();

    List<LinkServiceModel> getAllLinks();

    void addLink(LinkServiceModel linkServiceModel);
}
