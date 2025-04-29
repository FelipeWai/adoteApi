package com.adote.api.infra.presentation;

import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import static org.mockito.Mockito.*;

class NotificationControllerTest {

    @Test
    void deveEnviarMensagemParaOngCorreta() {
        // Arrange
        SimpMessagingTemplate messagingTemplate = mock(SimpMessagingTemplate.class);
        NotificationController controller = new NotificationController(messagingTemplate);

        Long organizationId = 123L;
        String expectedDestination = "/topic/ong/123/formulario";
        String expectedMessage = "Formulário novo recebido";

        // Act
        controller.notifyOrganization(organizationId);

        // Assert
        verify(messagingTemplate, times(1)).convertAndSend(expectedDestination, expectedMessage);
    }

    @Test
    void notifyOrganization() {
    }
}