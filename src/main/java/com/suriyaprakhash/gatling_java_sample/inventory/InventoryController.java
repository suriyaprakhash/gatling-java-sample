package com.suriyaprakhash.gatling_java_sample.inventory;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("inventory")
public class InventoryController {


    private final InventoryRepository inventoryRepository;

    public InventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Schema(example = "1b86de0d-7e40-452c-ab1c-1c0b6b094047", description = "Get the inventory item by id")
    @GetMapping("{id}")
    public ResponseEntity<InventoryEntity> getInventoryItemById(@PathVariable("id") String id) {
        UUID inventoryId = UUID.fromString(id);
        var inventoryEntity = inventoryRepository.findById(inventoryId);
        return inventoryEntity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Schema(example = "1b86de0d-7e40-452c-ab1c-1c0b6b094047", description = "Get the inventory items by pagable")
    @GetMapping
    public ResponseEntity<Page<InventoryEntity>> getInventoryItems(Pageable pageable) {
        var inventoryEntities = inventoryRepository.findAll(pageable);
        return ResponseEntity.ok(inventoryEntities);
    }

    @Schema(description = "Save an new inventory item")
    @PostMapping
    public ResponseEntity<String> addInventory(@RequestBody InventoryEntity inventoryEntity) {
        inventoryEntity.setUuid(UUID.randomUUID());
        if (inventoryEntity.getAvailableCount() == 0) {
            inventoryEntity.setType(InventoryType.BACKORDER);
        }
        inventoryRepository.save(inventoryEntity);
        return ResponseEntity.ok("Successfully added inventoryEntity");
    }

    @Schema(description = "Update existing an inventory item")
    @PutMapping("{id}")
    public ResponseEntity<String> updateInventory(@PathVariable("id") String id, @RequestBody InventoryEntity inventoryEntity) {
        inventoryEntity.setUuid(UUID.fromString(id));
        if (inventoryEntity.getAvailableCount() == 0) {
            inventoryEntity.setType(InventoryType.BACKORDER);
        }
        inventoryRepository.save(inventoryEntity);
        return ResponseEntity.ok("Successfully added inventoryEntity");
    }
}
