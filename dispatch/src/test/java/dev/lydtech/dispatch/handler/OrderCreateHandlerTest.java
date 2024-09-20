package dev.lydtech.dispatch.handler;

import dev.lydtech.dispatch.message.OrderCreated;
import dev.lydtech.dispatch.service.DispatchService;
import dev.lydtech.dispatch.util.TestEventData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderCreateHandlerTest {
    private OrderCreateHandler handler;
    private DispatchService dispatchServiceMock;

    @BeforeEach
    void setUp() {
        dispatchServiceMock = mock(DispatchService.class);
        handler = new OrderCreateHandler(dispatchServiceMock);
    }

    @Test
    void listen_Success() throws Exception {
        OrderCreated order = TestEventData.buildOrderCreated(UUID.randomUUID(), UUID.randomUUID().toString());
        handler.listen(order);
        verify(dispatchServiceMock, times(1)).process(order);
    }

    @Test
    void listen_ServiceThrowsException() throws Exception {
        OrderCreated order = TestEventData.buildOrderCreated(UUID.randomUUID(), UUID.randomUUID().toString());
        doThrow(new RuntimeException("Service Failure")).when(dispatchServiceMock).process(order);

        handler.listen(order);

        verify(dispatchServiceMock, times(1)).process(order);
    }
}