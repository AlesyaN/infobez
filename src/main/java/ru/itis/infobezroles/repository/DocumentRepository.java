package ru.itis.infobezroles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.infobezroles.models.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
}
