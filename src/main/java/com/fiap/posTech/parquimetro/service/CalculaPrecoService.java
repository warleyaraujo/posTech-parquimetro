package com.fiap.posTech.parquimetro.service;

import com.fiap.posTech.parquimetro.model.EnumPark;
import com.fiap.posTech.parquimetro.model.Park;

import com.fiap.posTech.parquimetro.repository.ParkRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class CalculaPrecoService {

    private static final double PRECO_POR_HORA = 12.0;

    @Autowired
    private ParkRepository parkRepository;

    public static double getPrecoPorHora() {
        return PRECO_POR_HORA;
    }

    @Transactional
    public double calcularPreco(Park registro) {
        EnumPark tipoPark = registro.getTipoTempo();
        if (tipoPark == EnumPark.FIXO) {
            double valorCobrado = registro.getTempoFixo() * PRECO_POR_HORA;
            registro.setValorCobrado(arredondar(valorCobrado));
            return registro.getValorCobrado();
        } else {
            try {
                LocalDateTime entradaLocalDateTime = registro.getEntrada();
                LocalDateTime saidaLocalDateTime = registro.getSaida();

                if (entradaLocalDateTime == null || saidaLocalDateTime == null) {
                    // Tratar a situação de datas nulas de maneira adequada
                    throw new IllegalArgumentException("Datas de entrada ou saída nulas.");
                }

                long diferencaEmMilissegundos = Duration.between(entradaLocalDateTime, saidaLocalDateTime).toMillis();
                double horas = diferencaEmMilissegundos / (60.0 * 60.0 * 1000.0);
                double valorCobrado = arredondar(horas * getPrecoPorHora());

                if(valorCobrado < (getPrecoPorHora()/2)) {
                    registro.setValorCobrado(getPrecoPorHora()/2);
                } else {
                    registro.setValorHora(getPrecoPorHora());
                    registro.setValorCobrado(valorCobrado);
                }
                return registro.getValorCobrado();

            } catch (Exception e) {
                // Tratar a exceção de maneira adequada, se possível
                e.printStackTrace();
                throw new RuntimeException("Erro ao calcular o preço.", e);
            }
        }
    }

    private static double arredondar(double valorCobrado) {
        return Math.round(valorCobrado * 100.0) / 100.0;
    }
}
