package com.suriyaprakhash.gatling_java_sample.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InventorySortField {
    NAME("name"),
    TYPE("type"),
    AVAILABLE_COUNT("availableCount"),
    PRICE("price");

    private final String databaseFieldName;
}