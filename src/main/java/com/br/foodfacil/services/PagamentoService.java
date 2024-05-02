package com.br.foodfacil.services;


import com.br.foodfacil.dtos.MercadoPagoNotificacaoRequestDto;
import com.br.foodfacil.dtos.PagamentoResponseDto;
import com.br.foodfacil.records.PagamentoBody;
import com.br.foodfacil.utils.AppUtils;
import com.br.foodfacil.utils.GeraChavePix;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    public ResponseEntity<Object> geraPix(PagamentoBody pagamentoBody){
        var chavePix = new GeraChavePix().generate(pagamentoBody);

       return chavePix!= null? ResponseEntity.ok(chavePix) :
               ResponseEntity.badRequest().body("falha ao gerar chave pix");
    }

    public ResponseEntity<Object> checaPagamento(MercadoPagoNotificacaoRequestDto mercadoPagoNotificacaoRequestDto) throws MPException, MPApiException {
        MercadoPagoConfig.setAccessToken(AppUtils.TESTE_ACCESS_TOKEN);

        var pagamento = new PaymentClient();

        Long paymentId = 123456789L;
         var pagamentoEncontrado = pagamento.get(paymentId);

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

         return ResponseEntity.ok().body(pagamentoResposta);
    }


}

