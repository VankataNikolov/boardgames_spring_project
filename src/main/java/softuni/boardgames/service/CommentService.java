package softuni.boardgames.service;

import javassist.NotFoundException;
import softuni.boardgames.model.service.CommentServiceModel;
import softuni.boardgames.model.view.CommentViewModel;

import java.util.List;

public interface CommentService {

    List<CommentViewModel> getGameComments(Long id);

    void addComment(CommentServiceModel commentServiceModel) throws NotFoundException;
}
