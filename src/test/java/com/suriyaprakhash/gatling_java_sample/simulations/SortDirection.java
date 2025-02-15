package com.suriyaprakhash.gatling_java_sample.simulations;

/**
 * This is the custom sort direction - since org.springframework.data.domain.Sort$Direction will not be
 * available to the simulation during runtime
 *
 */
public enum SortDirection {
    ASC,
    DESC;
}
