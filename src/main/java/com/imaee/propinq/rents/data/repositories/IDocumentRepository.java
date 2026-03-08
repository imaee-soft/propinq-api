package com.imaee.propinq.rents.data.repositories;

import com.imaee.propinq.rents.data.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IDocumentRepository extends JpaRepository<Document, UUID> {}