package softuni.boardgames.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.boardgames.exception.RecordNotFoundException;
import softuni.boardgames.model.entity.CommentEntity;
import softuni.boardgames.model.entity.GameEntity;
import softuni.boardgames.model.entity.UserEntity;
import softuni.boardgames.model.service.CommentServiceModel;
import softuni.boardgames.repository.CommentRepository;
import softuni.boardgames.repository.GameRepository;
import softuni.boardgames.repository.UserRepository;
import softuni.boardgames.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public CommentServiceImpl(CommentRepository commentRepository,
                              ModelMapper modelMapper,
                              UserRepository userRepository,
                              GameRepository gameRepository) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public List<CommentServiceModel> getGameComments(Long id) {
        return commentRepository.findAllByGameId(id)
                .stream()
                .map(ce -> {
                    CommentServiceModel commentServiceModel = modelMapper.map(ce, CommentServiceModel.class);
                    commentServiceModel.setAuthorName(ce.getAuthor().getUsername());
                    commentServiceModel.setGameId(ce.getGame().getId());
                    return commentServiceModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void addComment(CommentServiceModel commentServiceModel) {
        CommentEntity commentEntity = modelMapper.map(commentServiceModel, CommentEntity.class);
        UserEntity creator = userRepository
                .findByUsername(commentServiceModel.getAuthorName())
                .orElseThrow(() -> new RecordNotFoundException("Creator " + commentServiceModel.getAuthorName() + " could not be found"));
        commentEntity.setAuthor(creator);

        GameEntity gameEntity = gameRepository.findById(commentServiceModel.getGameId())
                .orElseThrow(() -> new RecordNotFoundException("Game with id " + commentServiceModel.getGameId()+ " could not be found"));
        commentEntity.setGame(gameEntity);

        commentRepository.save(commentEntity);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
