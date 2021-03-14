package softuni.boardgames.service;

import org.springframework.web.multipart.MultipartFile;
import softuni.boardgames.model.entity.GameEntity;

import java.io.IOException;
import java.util.List;

public interface GameImageService {

    void addPictures(List<MultipartFile> pictures, GameEntity gameEntity) throws IOException;
}
