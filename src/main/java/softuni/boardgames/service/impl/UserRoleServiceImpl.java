package softuni.boardgames.service.impl;

import org.springframework.stereotype.Service;
import softuni.boardgames.constants.RolesDescription;
import softuni.boardgames.model.entity.UserRoleEntity;
import softuni.boardgames.model.enums.UserRoleEnum;
import softuni.boardgames.repository.UserRoleRepository;
import softuni.boardgames.service.UserRoleService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void initRoles() {
        if(userRoleRepository.count() == 0){
            List<UserRoleEntity> userRoleEntities = new ArrayList<>();
            UserRoleEnum[] roles = UserRoleEnum.values();
            Arrays.stream(roles)
                    .forEach(r -> userRoleEntities.add(new UserRoleEntity(r, setRoleDescription(r))));
            userRoleEntities.forEach(userRoleRepository::save);

        }
    }

    private static String setRoleDescription(UserRoleEnum userRoleEnum){
        String description = "";
        if(userRoleEnum == UserRoleEnum.ROLE_USER){
            description = RolesDescription.USER;
        } else if(userRoleEnum == UserRoleEnum.ROLE_EDITOR){
            description = RolesDescription.EDITOR;
        } else if(userRoleEnum == UserRoleEnum.ROLE_ADMIN){
            description = RolesDescription.ADMIN;
        }
        return description;
    }
}
