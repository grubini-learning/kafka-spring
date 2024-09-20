package dev.lydtech.tracking.handler;

import dev.lydtech.tracking.message.OrderTracking;
import dev.lydtech.tracking.service.OrderTrackingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderTrackingHandler {
    @Autowired
    private final OrderTrackingService trackingService;

    @KafkaListener(
            id = "trackingConsumerList",
            topics = "dispatch.tracking",
            groupId = "tracking.dispatch.tracking",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(OrderTracking order) {
        try {
            trackingService.process(order);
        } catch (Exception e) {
            log.error("Processing tracking failure", e);
        }
    }
}
