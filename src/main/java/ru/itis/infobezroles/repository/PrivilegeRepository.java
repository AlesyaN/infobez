package ru.itis.infobezroles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.infobezroles.models.Privilege;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
}
