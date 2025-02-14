package com.suriyaprakhash.gatling_java_sample.inventory;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface InventoryRepository extends
        MongoRepository<InventoryEntity, UUID>,
        PagingAndSortingRepository<InventoryEntity, UUID> {
}