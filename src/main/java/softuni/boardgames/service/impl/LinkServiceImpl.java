package softuni.boardgames.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.boardgames.model.entity.LinkEntity;
import softuni.boardgames.model.entity.UserEntity;
import softuni.boardgames.model.service.LinkServiceModel;
import softuni.boardgames.repository.LinkRepository;
import softuni.boardgames.repository.UserRepository;
import softuni.boardgames.service.LinkService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LinkServiceImpl implements LinkService {

    private final LinkRepository linkRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public LinkServiceImpl(LinkRepository linkRepository,
                           UserRepository userRepository,
                           ModelMapper modelMapper) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedLinks() {
        if(linkRepository.count() == 0){
            UserEntity editorEntity = userRepository.findByUsername("editor")
                    .orElseThrow(() -> new IllegalStateException("user with username editor not found"));

            List<LinkEntity> linkEntities = List.of(
                    new LinkEntity(
                            "Time2Play",
                            "https://time2play.bg/",
                            "boardgame store",
                            editorEntity
                    ),
                    new LinkEntity(
                            "PikkoGames",
                            "https://nastolniigri.com/?gclid=EAIaIQobChMItvPIlu_e7wIVVLLVCh0EzADpEAAYASAAEgL2_fD_BwE",
                            "boardgame store",
                            editorEntity
                    ),
                    new LinkEntity(
                            "BoardGamesBG",
                            "https://boardgames-bg.com/",
                            "boardgame store",
                            editorEntity)
                    );

            linkRepository.saveAll(linkEntities);
        }
    }

    @Override
    public List<LinkServiceModel> getAllLinks() {
        return linkRepository.findAll()
                .stream()
                .map(le -> {
                    LinkServiceModel mappedLink = modelMapper.map(le, LinkServiceModel.class);
                    mappedLink.setAddedBy(le.getAddedBy().getUsername());
                    return mappedLink;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void addLink(LinkServiceModel linkServiceModel) {
        String userName = linkServiceModel.getAddedBy();
        UserEntity userEntity = userRepository.findByUsername(userName)
                .orElseThrow(() -> new IllegalStateException("user with username " + userName +" not found"));
        LinkEntity linkEntity = modelMapper.map(linkServiceModel, LinkEntity.class);
        linkEntity.setAddedBy(userEntity);

        linkRepository.save(linkEntity);
    }
}
