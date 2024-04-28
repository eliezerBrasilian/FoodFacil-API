package com.br.foodfacil.ff.services;


import com.br.foodfacil.ff.dtos.PagamentoBody;
import com.br.foodfacil.ff.utils.GeraChavePix;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PagamentoService {


    private final ObjectMapper objectMapper;

    public PagamentoService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public ResponseEntity<Object> geraPix(PagamentoBody pagamentoBody){
        var chavePix = new GeraChavePix().generate(pagamentoBody);

       return chavePix!= null? ResponseEntity.ok(chavePix) :
               ResponseEntity.badRequest().body("falha ao gerar chave pix");
    }


    public ResponseEntity<Object> notificacao(Map<String, Object> payload){
        try {
            JsonNode rootNode = objectMapper.readTree((JsonParser) payload);

            JsonNode dataNode = rootNode.get("data");
            System.out.println(Map.of("rootnode",rootNode));

            if (dataNode != null) {
                JsonNode statusNode = dataNode.get("status");
                if (statusNode != null) {
                    String status = statusNode.asText();
                    System.out.println("Status do pagamento: " + status);
                    // Aqui você pode fazer algo com o status do pagamento, como processar o pagamento ou atualizar o estado do pedido
                }
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erro ao processar a notificação");
        }
    }
}

