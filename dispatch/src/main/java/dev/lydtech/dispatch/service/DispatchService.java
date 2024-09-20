package dev.lydtech.dispatch.service;

import dev.lydtech.dispatch.message.OrderCreated;
import dev.lydtech.dispatch.message.OrderDispatched;
import dev.lydtech.dispatch.message.OrderTracking;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DispatchService {
    private static final String ORDER_DISPATCHED_TOPIC = "order.dispatched";
    private static final String ORDER_DISPATCH_TRACKING_TOPIC = "dispatch.tracking";
    private final KafkaTemplate<String, Object> kafkaProducer;

    public void process(OrderCreated orderCreated) throws Exception {
        OrderTracking orderTracking = OrderTracking.builder()
                .orderId(orderCreated.getOrderId())
                .build();

        kafkaProducer.send(ORDER_DISPATCH_TRACKING_TOPIC, orderTracking).get();

        OrderDispatched orderDispatched = OrderDispatched.builder()
                .orderId(orderCreated.getOrderId())
                .build();

        kafkaProducer.send(ORDER_DISPATCHED_TOPIC, orderDispatched).get();
    }
}
