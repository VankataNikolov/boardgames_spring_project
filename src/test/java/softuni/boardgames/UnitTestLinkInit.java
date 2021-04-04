package softuni.boardgames;

import softuni.boardgames.model.entity.LinkEntity;

public class UnitTestLinkInit {

    private UnitTestUserInit unitTestUserInit = new UnitTestUserInit();

    public LinkEntity linkEntityInit(){
        return new LinkEntity(
                "TestTitle",
                "TestURL",
                "TestDescription",
                unitTestUserInit.userEntityInit()
        );
    }

    public LinkEntity secondLinkEntityInit(){
        return new LinkEntity(
                "TestTitle2",
                "TestURL2",
                "TestDescription2",
                unitTestUserInit.secondUserEntityInit()
        );
    }
}
