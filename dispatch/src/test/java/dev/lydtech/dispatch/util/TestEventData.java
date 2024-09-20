package dev.lydtech.dispatch.util;

import dev.lydtech.dispatch.message.OrderCreated;

import java.util.UUID;

public class TestEventData {
    public static OrderCreated buildOrderCreated(UUID orderId, String item) {
        return OrderCreated.builder()
                .orderId(orderId)
                .item(item)
                .build();
    }
}
