package com.suriyaprakhash.gatling_java_sample.inventory;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Builder
@Document(collection = "inventory")
@Data
public class InventoryEntity {

    @Id
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID uuid;

    String name;
    InventoryType type;
    int availableCount;
    double price;
}
