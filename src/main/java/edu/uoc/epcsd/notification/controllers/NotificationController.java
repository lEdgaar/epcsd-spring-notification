package edu.uoc.epcsd.notification.controllers;

import edu.uoc.epcsd.notification.pojos.Show;
import edu.uoc.epcsd.notification.services.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    @Operation(operationId = "sendShowCreated",
            summary = "Notificar usuarios cuando se crea un Show.",
            description = "Esta acción la puede realizar el usuario administrador",
            tags = {"Notification"},
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Se ha notificado correctamente a todos los usuarios."
                            )
            }
    )
    @KafkaListener(topics = "show-created", autoStartup = "true", groupId = "gwegewg")
    public void sendShowCreated(Show show) {
        this.notificationService.notifyShowCreation(show);
    }

}
