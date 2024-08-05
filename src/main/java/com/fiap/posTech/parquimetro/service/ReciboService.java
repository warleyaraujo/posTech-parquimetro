package com.fiap.posTech.parquimetro.service;

import com.fiap.posTech.parquimetro.model.Park;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReciboService {
    @Autowired
    private EmailService emailService;

    public void emitirRecibo(Park park) {

        String nomePessoa = park.getPessoa().getNome();
        String emailPessoa = park.getPessoa().getEmail();
        String dataEntrada = park.getEntrada().toString();
        String sdia = dataEntrada.substring(8, 10);
        String smes = dataEntrada.substring(5, 7);
        String sano = dataEntrada.substring(0, 4);
        String shora = dataEntrada.substring(11, 13);
        String sminuto = dataEntrada.substring(14, 16);

        dataEntrada = sdia + "-" + smes + "-" + sano + " " + shora + ":" + sminuto;
        String permanencia = park.getPermanencia().toString();
        String valorCobrado = String.valueOf(park.getValorCobrado());
        String veiculo = park.getVeiculo().getModelo() + " - Placa " + park.getVeiculo().getPlaca();

        String recibo = "RECIBO DE PAGAMENTO\n" +
                "Recebemos de " + nomePessoa + " o valor de " + valorCobrado + "\n" +
                "referente ao estacionamento do veículo " + veiculo + ".\n" +
                "Data de entrada " + dataEntrada + " com permanencia de " + permanencia + ". \n\n" +
                "Agradecemos a preferência.";

        emailService.enviar(emailPessoa, recibo, "Recibo de Pagamento");
    }
}
