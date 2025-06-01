package com.imaee.propinq.log.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface LogRepository extends MongoRepository<Log, UUID> {}