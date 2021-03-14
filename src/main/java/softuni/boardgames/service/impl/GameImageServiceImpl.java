package softuni.boardgames.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import softuni.boardgames.model.entity.GameEntity;
import softuni.boardgames.model.entity.GameImagesEntity;
import softuni.boardgames.repository.GameImageRepository;
import softuni.boardgames.service.CloudinaryService;
import softuni.boardgames.service.GameImageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameImageServiceImpl implements GameImageService {

    private final CloudinaryService cloudinaryService;
    private final GameImageRepository gameImageRepository;

    public GameImageServiceImpl(CloudinaryService cloudinaryService,
                                GameImageRepository gameImageRepository) {
        this.cloudinaryService = cloudinaryService;
        this.gameImageRepository = gameImageRepository;
    }

    @Override
    public void addPictures(List<MultipartFile> pictures, GameEntity gameEntity) throws IOException {
        for (MultipartFile picture : pictures) {
            if(!picture.isEmpty()){
                String url = cloudinaryService.uploadImage(picture);
                GameImagesEntity newGameImage = new GameImagesEntity(url, gameEntity);
                gameImageRepository.save(newGameImage);
            }

        }

    }
}
