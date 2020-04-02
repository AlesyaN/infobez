package ru.itis.infobezroles.dto;

import lombok.Data;

@Data
public class GrantRoleDTO {
    private Long userId;
    private Long roleId;
}
