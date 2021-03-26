package softuni.boardgames.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.boardgames.constants.RolesDescription;
import softuni.boardgames.model.entity.UserRoleEntity;
import softuni.boardgames.model.enums.UserRoleEnum;
import softuni.boardgames.model.service.UserRoleServiceModel;
import softuni.boardgames.repository.UserRoleRepository;
import softuni.boardgames.service.UserRoleService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository,
                               ModelMapper modelMapper) {
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
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

    @Override
    public List<UserRoleServiceModel> getAllRoles() {

        return userRoleRepository.findAll()
                .stream()
                .map(ure -> modelMapper.map(ure, UserRoleServiceModel.class))
                .collect(Collectors.toList());
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
