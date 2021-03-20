package softuni.boardgames.service;

import softuni.boardgames.model.service.CommentServiceModel;

import java.util.List;

public interface CommentService {

    List<CommentServiceModel> getGameComments(Long id);

    void addComment(CommentServiceModel commentServiceModel);

    void deleteComment(Long id);
}
