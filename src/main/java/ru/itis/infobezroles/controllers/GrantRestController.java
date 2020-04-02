package ru.itis.infobezroles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.infobezroles.dto.GrantRoleDTO;
import ru.itis.infobezroles.service.RolesService;

@RestController
public class GrantRestController {

    @Autowired
    private RolesService rolesService;

    @PutMapping("/grant")
    public ResponseEntity grantRole(GrantRoleDTO grantRoleDTO) {
        rolesService.grantRole(grantRoleDTO);
        return ResponseEntity.ok().build();
    }
}
