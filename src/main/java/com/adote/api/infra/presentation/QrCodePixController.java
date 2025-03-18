package com.adote.api.infra.presentation;

import com.adote.api.core.entities.ChavePix;
import com.adote.api.infra.dtos.qrCodePix.request.QrCodeRequestDTO;
import com.adote.api.infra.dtos.qrCodePix.response.QrCodeResponseDTO;
import com.adote.api.infra.service.QrCodePixService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/qrcodepix")
@RequiredArgsConstructor
@Tag(name = "QrCodePix", description = "Responsável por gerar o QR Code do Pix.")
public class QrCodePixController {

    @Autowired
    QrCodePixService qrCodePixService;

    @PostMapping
    public ResponseEntity<QrCodeResponseDTO> gerarQrCodePix(@RequestBody QrCodeRequestDTO qrCodeRequestDTO) {
        ResponseEntity<QrCodeResponseDTO> response = qrCodePixService.gerarQrCode(qrCodeRequestDTO);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {

            return ResponseEntity.status(response.getStatusCode()).body(
                    new QrCodeResponseDTO(response.getBody().qrCode()));

        } else {
            return ResponseEntity.status(response.getStatusCode()).body(null);
        }
    }
}
