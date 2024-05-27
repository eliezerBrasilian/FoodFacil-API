package com.br.foodfacil.services;


import com.br.foodfacil.dtos.MercadoPagoNotificacaoRequestDto;
import com.br.foodfacil.dtos.PagamentoResponseDto;
import com.br.foodfacil.records.PagamentoBody;
import com.br.foodfacil.services.impl.PedidoServiceImpl;
import com.br.foodfacil.utils.AppUtils;
import com.br.foodfacil.utils.GeraChavePix;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PagamentoService {

    @Autowired
    PedidoServiceImpl pedidoServiceImpl;

    public ResponseEntity<Object> geraPix(PagamentoBody pagamentoBody){
        var chavePix = new GeraChavePix().generate(pagamentoBody);

       return chavePix!= null? ResponseEntity.ok(chavePix) :
               ResponseEntity.badRequest().body("falha ao gerar chave pix");
    }

    public ResponseEntity<Object> checaPagamento(MercadoPagoNotificacaoRequestDto mercadoPagoNotificacaoRequestDto) throws MPException, MPApiException {
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

