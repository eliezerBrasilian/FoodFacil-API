package com.br.foodfacil.ff.services;


import com.br.foodfacil.ff.dtos.PagamentoBody;
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

        var ENV_ACESS_TOKEN = "TEST-6242384253176670-042012-f47b1f59150e364b0788afa9cfdd23a5-618365626";

        MercadoPagoConfig.setAccessToken(ENV_ACESS_TOKEN);

        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("x-idempotency-key", UUID.randomUUID().toString());

        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .customHeaders(customHeaders)
                .build();

        PaymentClient client = new PaymentClient();

        PaymentCreateRequest paymentCreateRequest =
                PaymentCreateRequest.builder()
                        .transactionAmount(new BigDecimal(pagamentoBody.produtoData().valor()))
                        .description(pagamentoBody.produtoData().descricao())
                        .paymentMethodId("pix")
                        .dateOfExpiration(OffsetDateTime.of(2025, 1, 10, 10, 10, 10, 0, ZoneOffset.UTC))
                        .payer(
                                PaymentPayerRequest.builder()
                                        .email(pagamentoBody.userData().email())
                                        .firstName(pagamentoBody.userData().primeiroNome())
                                        .identification(
                                                IdentificationRequest.builder().type("CPF").number(pagamentoBody.userData().cpf()).build())
                                        .build())
                        .build();

        try {
            Payment payment = client.create(paymentCreateRequest, requestOptions);
            System.out.println();

            var qrcodeBase64 = payment.getPointOfInteraction().getTransactionData().getQrCodeBase64();
            var qrcode = payment.getPointOfInteraction().getTransactionData().getQrCode();

            var data = Map.of(
                    "message","sucesso",
                    "qrcode",qrcode,
                    "qrcodeBase64",qrcodeBase64
                );

            return ResponseEntity.ok().body(data);

        } catch (MPApiException ex) {
            System.out.printf(
                    "MercadoPago Error. Status: %s, Content: %s%n",
                    ex.getApiResponse().getStatusCode(), ex.getApiResponse().getContent());
            var data = Map.of(
                    "message","falha",
                    "statuscode",ex.getApiResponse().getStatusCode(),
                    "conteudo",ex.getApiResponse().getContent()
            );
            return ResponseEntity.badRequest().body(data);

        } catch (MPException e) {
            var data = Map.of(
                    "message",e.getMessage(),
                    "causa",e.getCause()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(data);
        }
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

