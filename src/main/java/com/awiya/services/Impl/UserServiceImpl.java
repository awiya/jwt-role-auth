package com.awiya.services.Impl;

import com.awiya.entities.Role;
import com.awiya.entities.User;
import com.awiya.repositories.RoleRepository;
import com.awiya.repositories.UserRepository;
import com.awiya.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void initUsersAndRoles(){
        Role admin_role = Role.builder()
                .name("ROLE_ADMIN")
                .description("ADMIN ROLE FOR THE APPLICATION")
                .build();
        roleRepository.save(admin_role);

        Role user_role = Role.builder()
                .name("ROLE_USER")
                .description("DEFAULT ROLE FOR NEWLY RECORD")
                .build();
        roleRepository.save(user_role);

        Set<Role> admin_roles=new HashSet<>();
        admin_roles.add(admin_role);
        admin_roles.add(user_role);

        User admin_user = User.builder()
                .username("admin")
                .firstName("adminFirstName")
                .lastName("adminLastName")
                .password(encoder.encode("adminPassword"))
                .roles(admin_roles)
                .build();
        userRepository.save(admin_user);

        Set<Role> user_roles=new HashSet<>();
        user_roles.add(user_role);
        User default_user = User.builder()
                .username("awiya")
                .firstName("awiya")
                .lastName("ayiwa")
                .password(encoder.encode("awiyayiwa"))
                .roles(user_roles)
                .build();

        userRepository.save(default_user);

    }
}
