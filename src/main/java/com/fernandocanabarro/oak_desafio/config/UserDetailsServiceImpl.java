package com.fernandocanabarro.oak_desafio.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fernandocanabarro.oak_desafio.models.Role;
import com.fernandocanabarro.oak_desafio.models.User;
import com.fernandocanabarro.oak_desafio.projections.UserDetailsProjection;
import com.fernandocanabarro.oak_desafio.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> list = userRepository.searchUserAndRolesByEmail(username);
        if (list.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        if (!list.get(0).getActivated()) {
            throw new DisabledException("User account not activated");
        }
        User user = new User();
        user.setEmail(list.get(0).getUsername());
        user.setPassword(list.get(0).getPassword());
        list.stream().forEach(userDetails -> user.addRole(new Role(userDetails.getRoleId(), userDetails.getAuthority())));
        return user;
    }

}
