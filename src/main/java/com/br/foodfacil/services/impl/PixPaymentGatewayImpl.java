package com.br.foodfacil.services.impl;


import com.br.foodfacil.dtos.MercadoPagoNotificacaoRequestDto;
import com.br.foodfacil.dtos.PagamentoResponseDto;
import com.br.foodfacil.records.PagamentoBody;
import com.br.foodfacil.records.QrCode;
import com.br.foodfacil.services.PixPaymentGateway;
import com.br.foodfacil.utils.AppUtils;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class PixPaymentGatewayImpl implements PixPaymentGateway {

    @Autowired
    PedidoServiceImpl pedidoServiceImpl;

    @Override
    public ResponseEntity<QrCode> generatePixKey(PagamentoBody pagamentoBody){
        var chavePix = this.tryGeneratePixKey(pagamentoBody);

       return  ResponseEntity.ok(chavePix);
    }

    public QrCode tryGeneratePixKey(PagamentoBody pagamentoBody) {
        MercadoPagoConfig.setAccessToken(AppUtils.PROD_ACCESS_TOKEN);

        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("x-idempotency-key", UUID.randomUUID().toString());

        var requestOptions = MPRequestOptions.builder()
                .customHeaders(customHeaders)
                .build();

        var client = new PaymentClient();

        var paymentCreateRequest = PaymentCreateRequest.builder()
                .transactionAmount(new BigDecimal(pagamentoBody.produtoData().valor()))
                .description(pagamentoBody.produtoData().descricao())
                .paymentMethodId("pix")
                .payer(
                        PaymentPayerRequest.builder()
                                .email(pagamentoBody.userData().email())
                                .firstName(pagamentoBody.userData().primeiroNome())
                                .build())
                .externalReference(pagamentoBody.produtoData().id())
                .build();

        try {
            var payment = client.create(paymentCreateRequest, requestOptions);
            System.out.println(payment);

            var qrcodeBase64 = payment.getPointOfInteraction().getTransactionData().getQrCodeBase64();
            var qrcode = payment.getPointOfInteraction().getTransactionData().getQrCode();

            var data = Map.of(
                    "message", "sucesso",
                    "qrcode", qrcode,
                    "qrcodeBase64", qrcodeBase64
            );
            System.out.println(data);
            return new QrCode(qrcode,qrcodeBase64);

        }catch (MPApiException e){
            var apiResponse = e.getApiResponse();
            var content = apiResponse.getContent();
            System.out.println(content);

            System.out.println("-----------falha ao gerar chave pix-----------");
            System.out.println("message: " + e.getMessage());
            System.out.println("cause: " + e.getCause());

            return null;
        }catch (MPException e){
            return  null;
        }
    }

    @Override
    public ResponseEntity<Object> checkPaymentStatus(MercadoPagoNotificacaoRequestDto mercadoPagoNotificacaoRequestDto) throws MPException, MPApiException {
        MercadoPagoConfig.setAccessToken(AppUtils.PROD_ACCESS_TOKEN);

        var pagamento = new PaymentClient();

        Long paymentId = Long.valueOf(mercadoPagoNotificacaoRequestDto.data().id());
        System.out.println("paymentID: " + paymentId);

        try{
            var pagamentoEncontrado = pagamento.get(paymentId);

            if(pagamentoEncontrado != null){
                var payer = pagamentoEncontrado.getPayer();
                var identification = payer.getIdentification();
                var transactionDetails = pagamentoEncontrado.getTransactionDetails();

                var pagamentoResposta = new PagamentoResponseDto(
                        pagamentoEncontrado.getId(),
                        pagamentoEncontrado.getDateCreated(),
                        pagamentoEncontrado.getDateApproved(),
                        pagamentoEncontrado.getDateLastUpdated(),
                        pagamentoEncontrado.getMoneyReleaseDate(),
                        pagamentoEncontrado.getPaymentMethodId(),
                        pagamentoEncontrado.getPaymentTypeId(),
                        pagamentoEncontrado.getStatus(),
                        pagamentoEncontrado.getStatusDetail(),
                        pagamentoEncontrado.getCurrencyId(),
                        pagamentoEncontrado.getDescription(),
                        pagamentoEncontrado.getCollectorId(),
                        new PagamentoResponseDto.PayerDto(
                                payer.getId(),
                                payer.getEmail(),
                                new PagamentoResponseDto.PayerDto.IdentificationDto(
                                        identification.getType(),
                                        identification.getNumber()
                                ),
                                payer.getType()),
                        pagamentoEncontrado.getMetadata(),
                        pagamentoEncontrado.getAdditionalInfo(),
                        pagamentoEncontrado.getExternalReference(),
                        pagamentoEncontrado.getTransactionAmount(),
                        pagamentoEncontrado.getTransactionAmountRefunded(),
                        pagamentoEncontrado.getCouponAmount(),
                        new PagamentoResponseDto.TransactionDetailsDto(
                                transactionDetails.getNetReceivedAmount(),
                                transactionDetails.getTotalPaidAmount(),
                                transactionDetails.getOverpaidAmount(),
                                transactionDetails.getInstallmentAmount()),
                        pagamentoEncontrado.getInstallments(),
                        pagamentoEncontrado.getCard()
                );

                System.out.println("status: "+pagamentoEncontrado.getStatus());

                String pedidoId = pagamentoEncontrado.getExternalReference();
                System.out.println("pedidoId: "+pedidoId);

                if(Objects.equals(pagamentoEncontrado.getStatus(), "approved")){
                    System.out.println("pagamento foi aprovado, agora vamos para implementação confirmaPagamento()");
                    pedidoServiceImpl.confirmaPagamento(pedidoId);
                }

                return ResponseEntity.ok().body(pagamentoResposta);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("não existe um pagamento com esse id");
        }catch (RuntimeException e){
            throw new RuntimeException("excessao ao buscar paymentId, devido a: " + e.getMessage());
        }

    }

}

