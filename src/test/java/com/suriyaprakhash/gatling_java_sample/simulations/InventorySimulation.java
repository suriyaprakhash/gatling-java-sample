package com.suriyaprakhash.gatling_java_sample.simulations;


import com.suriyaprakhash.gatling_java_sample.inventory.InventorySortField;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import org.springframework.data.domain.Sort;

import static com.suriyaprakhash.gatling_java_sample.simulations.InventoryUtil.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class InventorySimulation extends Simulation {

    public InventorySimulation() {

        ChainBuilder addInventory = exec(
                http("Add inventory item")
                        .post("/inventory")
                        .header("Content-Type", "application/json")
                        .body(StringBody("""
                              {
                                "name": "%s",
                                "type":  "%s",
                                "availableCount": %s,
                                "price": %s
                              }
                              """.formatted("#{name}","#{type}", "#{availableCount}", "#{price}"))),
                feed(feedData())
        );

        ChainBuilder getInventory = exec(
                http("Get inventory item list")
                        .get("/inventory")
                        .queryParam("page", getRandomInt(10) + "")
                        .queryParam("sizePerPage", getRandomInt(10) + 1  + "")
                        .queryParam("inventorySortField", getRandomEnumString(InventorySortField.class))
                        .queryParam("sortDirection", getRandomEnumString(SortDirection.class))
                        .check(status().is(200))
        );

        ScenarioBuilder users = scenario("Users").exec(addInventory);

        setUp(
                users.injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol);
    }

}
