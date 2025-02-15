package com.suriyaprakhash.gatling_java_sample.simulations;


import com.suriyaprakhash.gatling_java_sample.inventory.InventorySortField;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;

import java.time.Duration;

import static com.suriyaprakhash.gatling_java_sample.simulations.InventoryUtil.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

/**
 * The actual simulation for Inventory
 */
public class InventorySimulation extends Simulation {

    public InventorySimulation() {

        /// CHAIN BUILDER ///////

        // POST - add inventory
        ChainBuilder addInventory = exec(
                feed(feedData()),
                http("Add inventory item")
                        .post("/inventory")
                        .header("Content-Type", "application/json")
                        .body(StringBody("""
                              {
                                "name": "#{name}",
                                "type": "#{type}",
                                "availableCount": #{availableCount},
                                "price": #{price}
                              }
                              """))

        );

        // GET - all inventory items - PAGED
        ChainBuilder getAllInventory = exec(
                http("Get inventory item list")
                        .get("/inventory")
                        .queryParam("page", 0)
                        .queryParam("sizePerPage", getRandomInt(10) + 1  + "")
                        .queryParam("inventorySortField", getRandomEnumString(InventorySortField.class))
                        .queryParam("sortDirection", getRandomEnumString(SortDirection.class))
                        .check(status().is(200))
                        .check(jsonPath("$..content[0].uuid").saveAs("uuid"))
//                        .check(jsonPath("$..content[0].uuid").is("7b672608-3dbe-4d9a-821c-50bd5fbc1c83"))
        );

        // GET - the inventory by ID - uses Session
        ChainBuilder getInventoryById = exec(
                doIf(session -> session.contains("uuid")).then(
                        http("Get inventory item by UUID")
                                .get("/inventory/#{uuid}")
                                .check(status().is(200))

                )
        );

        // Just for printing the value
        ChainBuilder printUuidFromSession = exec(session -> {
            System.out.println("Printing session UUID : " + session.getString("uuid"));
            return session;
        });


        /// SCENARIO BUILDER ///////

        ScenarioBuilder admin = scenario("Admin").exec(getAllInventory);
        ScenarioBuilder manager = scenario("InventoryManager").exec(addInventory);
        ScenarioBuilder user = scenario("User").exec(
                getAllInventory,
                pause(1),
                printUuidFromSession,
                getInventoryById);


        /// MAIN  ///////

        if ("rampup".equalsIgnoreCase(System.getProperty("ingestionType"))) {
            // for more complex rampUp realtime use case - all these will execute in parallel
            setUp(
                    user.injectOpen(rampUsers(10).during(60)),
                    manager.injectOpen(rampUsersPerSec(5).to(60).during(Duration.ofSeconds(60))),
                    admin.injectClosed(rampConcurrentUsers(1).to(2).during(Duration.ofSeconds(10)))
            ).protocols(httpProtocol);
        } else {
            // base setup - both these will execute in parallel
            setUp(
                    user.injectOpen(atOnceUsers(1)),
                    admin.injectOpen(atOnceUsers(1))
            ).protocols(httpProtocol);
        }

    }

}
