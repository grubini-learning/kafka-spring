package dev.lydtech.tracking.service;

import dev.lydtech.tracking.message.OrderStatus;
import dev.lydtech.tracking.message.OrderTracking;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderTrackingService {
    private static final String ORDER_TRACKING_TOPIC = "tracking.status";
    private final KafkaTemplate<String, Object> kafkaProducer;

    public void process(OrderTracking orderDispatched) throws Exception {
        log.info("Starting tracking for order with id: " + orderDispatched.toString());

        OrderStatus orderStatus = OrderStatus.builder()
                .orderId(orderDispatched.getOrderId())
                .status("open")
                .build();

        kafkaProducer.send(ORDER_TRACKING_TOPIC, orderStatus);
    }
}
