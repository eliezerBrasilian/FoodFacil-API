package com.br.foodfacil.services;


import com.br.foodfacil.dtos.MercadoPagoNotificacaoRequestDto;
import com.br.foodfacil.records.PagamentoBody;
import com.br.foodfacil.records.QrCode;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.springframework.http.ResponseEntity;

public interface PixPaymentGateway {

    ResponseEntity<QrCode> generatePixKey(PagamentoBody pagamentoBody);

    ResponseEntity<Object> checkPaymentStatus(MercadoPagoNotificacaoRequestDto mercadoPagoNotificacaoRequestDto) throws MPException, MPApiException;

}

