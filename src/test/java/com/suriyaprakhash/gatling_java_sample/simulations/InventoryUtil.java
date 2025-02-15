package com.suriyaprakhash.gatling_java_sample.simulations;

import com.suriyaprakhash.gatling_java_sample.inventory.InventorySortField;

import java.util.Random;

public class InventoryUtil {

    public static InventorySortField getRandomEnum(Class<InventorySortField> enumClass) {
        Random random = new Random();
        InventorySortField[] values = enumClass.getEnumConstants();
        return values[random.nextInt(values.length)];
    }

    public static String getRandomOrder() {
        Random random = new Random();
        int randomIndex = random.nextInt(2); // Generates either 0 or 1

        if (randomIndex == 0) {
            return "asc";
        } else {
            return "desc";
        }
    }

    public static int getRandomInt(int bound) {
        Random random = new Random();
        return random.nextInt(bound);
    }

}
