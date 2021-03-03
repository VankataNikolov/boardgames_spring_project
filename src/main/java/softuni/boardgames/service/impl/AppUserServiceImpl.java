package softuni.boardgames.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import softuni.boardgames.model.entity.UserEntity;
import softuni.boardgames.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppUserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public AppUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + username + "was not found"));

        return mapToUserDetails(userEntity);
    }

    private UserDetails mapToUserDetails(UserEntity userEntity){
        List<GrantedAuthority> authorities =
                userEntity
                        .getRoles()
                        .stream()
                        .map(r -> new SimpleGrantedAuthority(r.getRole().name()))
                        .collect(Collectors.toList());
        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                authorities
        );
    }
}
