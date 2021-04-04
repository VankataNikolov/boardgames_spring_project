package softuni.boardgames.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import softuni.boardgames.UnitTestLinkInit;
import softuni.boardgames.UnitTestUserInit;
import softuni.boardgames.model.entity.LinkEntity;
import softuni.boardgames.model.entity.UserEntity;
import softuni.boardgames.model.service.LinkServiceModel;
import softuni.boardgames.repository.LinkRepository;
import softuni.boardgames.repository.UserRepository;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class LinkServiceImplTest {

    private LinkEntity firstTestEntity, secondTestEntity;
    private UserEntity testUserEntity;
    private UnitTestUserInit unitTestUserInit;
    private UnitTestLinkInit unitTestLinkInit;
    private LinkServiceImpl linkServiceTest;
    private ModelMapper modelMapper;

    @Mock
    LinkRepository mockedLinkRepository;
    @Mock
    UserRepository mockedUserRepository;

    public LinkServiceImplTest() {
        this.unitTestLinkInit = new UnitTestLinkInit();
        this.unitTestUserInit = new UnitTestUserInit();
        this.modelMapper = new ModelMapper();
    }

    @BeforeEach
    public void setUp(){
        testUserEntity = unitTestUserInit.userEntityInit();
        firstTestEntity = unitTestLinkInit.linkEntityInit();
        secondTestEntity = unitTestLinkInit.secondLinkEntityInit();

        linkServiceTest = new LinkServiceImpl(
                mockedLinkRepository,
                mockedUserRepository,
                modelMapper
        );

    }

    @Test
    void gatAllShouldReturnCorrectListOfLinks(){
        Mockito.when(mockedLinkRepository.findAll())
                .thenReturn(List.of(firstTestEntity, secondTestEntity));
        List<LinkServiceModel> allLinks = linkServiceTest.getAllLinks();

        Assertions.assertEquals(2, allLinks.size());
        Assertions.assertEquals(firstTestEntity.getTitle(), allLinks.get(0).getTitle());
        Assertions.assertEquals(secondTestEntity.getTitle(), allLinks.get(1).getTitle());
    }

    @Test
    void addLinkShouldSaveNewLink(){
        LinkServiceModel linkServiceModel = new LinkServiceModel();
        linkServiceModel.setAddedBy("UserTest");
        linkServiceModel.setDescription("TestDescription");
        linkServiceModel.setTitle("TestTitle");
        linkServiceModel.setUrl("TestURL");

        Mockito.when(mockedUserRepository.findByUsername("UserTest"))
                .thenReturn(java.util.Optional.ofNullable(testUserEntity));
        Mockito.when(mockedLinkRepository.save(any()))
                .thenReturn(any());

        linkServiceTest.addLink(linkServiceModel);

        Mockito.verify(mockedLinkRepository, Mockito.times(1)).save(any());

    }
}
