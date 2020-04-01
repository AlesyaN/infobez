package ru.itis.infobezroles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.infobezroles.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
