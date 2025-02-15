package com.suriyaprakhash.gatling_java_sample.simulations;

import com.suriyaprakhash.gatling_java_sample.inventory.InventorySortField;
import com.suriyaprakhash.gatling_java_sample.inventory.InventoryType;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import net.datafaker.Faker;

import java.util.*;
import java.util.stream.Stream;

import static io.gatling.javaapi.http.HttpDsl.http;

public class InventoryUtil {

    public static HttpProtocolBuilder httpProtocol =
            http.baseUrl("http://localhost:8080")
                    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .acceptLanguageHeader("en-US,en;q=0.5")
                    .acceptEncodingHeader("gzip, deflate")
                    .userAgentHeader(
                            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:109.0) Gecko/20100101 Firefox/119.0"
                    );

    public static Iterator<Map<String,Object>> feedData() {
        Faker faker = new Faker();
        Iterator<Map<String,Object>> iterator;
        iterator = Stream.generate(() -> {
                    var commerce = faker.commerce();
                    Map<String,Object> stringObjectMap = new HashMap<>();
                    stringObjectMap.put("name", commerce.productName());
                    stringObjectMap.put("type", getRandomEnumString(InventoryType.class));
                    stringObjectMap.put("availableCount", getRandomInt(100));
                    stringObjectMap.put("price", commerce.price());
                    return stringObjectMap;
                })
                .iterator();
        return iterator;
    }

    public static <T> String  getRandomEnumString(Class<T> enumClass) {
        Random random = new Random();
        T[] values = enumClass.getEnumConstants();
        return values[random.nextInt(values.length)].toString().toUpperCase(Locale.ROOT);
    }

    public static String getRandomSortOrder() {
        Random random = new Random();
        int randomIndex = random.nextInt(2); // Generates either 0 or 1

        if (randomIndex == 0) {
            return "ASC";
        } else {
            return "DESC";
        }
    }

    public static int getRandomInt(int bound) {
        Random random = new Random();
        return random.nextInt(bound);
    }

}
