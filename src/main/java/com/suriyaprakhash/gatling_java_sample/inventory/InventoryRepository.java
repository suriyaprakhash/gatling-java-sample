package com.suriyaprakhash.gatling_java_sample.inventory;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface InventoryRepository extends
        MongoRepository<InventoryEntity, UUID> {

}