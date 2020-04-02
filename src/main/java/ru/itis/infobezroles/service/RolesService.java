package ru.itis.infobezroles.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.infobezroles.dto.GrantRoleDTO;
import ru.itis.infobezroles.models.Role;
import ru.itis.infobezroles.models.User;
import ru.itis.infobezroles.repository.RoleRepository;
import ru.itis.infobezroles.repository.UserRepository;

@Service
public class RolesService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void grantRole(GrantRoleDTO grantRoleDTO) {
        User user = userRepository.findById(grantRoleDTO.getUserId()).orElseThrow(IllegalArgumentException::new);
        Role role = roleRepository.findById(grantRoleDTO.getRoleId()).orElseThrow(IllegalArgumentException::new);
        user.setRole(role);
        userRepository.save(user);
    }
}
