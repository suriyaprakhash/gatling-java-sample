package com.suriyaprakhash.gatling_java_sample.simulations;


import com.suriyaprakhash.gatling_java_sample.inventory.InventorySortField;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;

import static com.suriyaprakhash.gatling_java_sample.simulations.InventoryUtil.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class InventorySimulation extends Simulation {

    public InventorySimulation() {

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

        ChainBuilder printUuidFromSession = exec(session -> {
            System.out.println("Printing session UUID : " + session.getString("uuid"));
            return session;
        });

        ChainBuilder getInventoryById = exec(
                doIf(session -> session.contains("uuid")).then(
                        http("Get inventory item by UUID")
                                .get("/inventory/#{uuid}")
                                .check(status().is(200))

                )
        );

        ScenarioBuilder admin = scenario("Admin").exec(addInventory);
        ScenarioBuilder users = scenario("Users").exec(
                getAllInventory,
                pause(1),
                printUuidFromSession,
                getInventoryById);


        setUp(
                users.injectOpen(atOnceUsers(1)),
                admin.injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol);
    }

}
