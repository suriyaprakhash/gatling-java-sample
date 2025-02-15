package com.suriyaprakhash.gatling_java_sample.simulations;


import com.suriyaprakhash.gatling_java_sample.inventory.InventorySortField;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static com.suriyaprakhash.gatling_java_sample.simulations.InventoryUtil.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;


public class InventorySimulation extends Simulation {

    public InventorySimulation() {
        setUp(
                users.injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol);
    }


    static HttpProtocolBuilder httpProtocol =
            http.baseUrl("http://localhost:8080")
                    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .acceptLanguageHeader("en-US,en;q=0.5")
                    .acceptEncodingHeader("gzip, deflate")
                    .userAgentHeader(
                            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:109.0) Gecko/20100101 Firefox/119.0"
                    );

    static ChainBuilder addInventory = exec(
            http("Add inventory item").get("/inventory")
    );

    static ChainBuilder getInventory = exec(
            http("Get inventory item list")
                    .get("/inventory")
                    .multivaluedQueryParam("page", String.valueOf(getRandomInt(10)))
                    .multivaluedQueryParam("sizePerPage", String.valueOf(getRandomInt(10)))
                    .multivaluedQueryParam("inventorySortField", getRandomEnum(InventorySortField.class).toString())
                    .multivaluedQueryParam("sortDirection", getRandomOrder())
                    .check(status().is(200))
    );

    static ScenarioBuilder users = scenario("Users").exec(getInventory);

}
