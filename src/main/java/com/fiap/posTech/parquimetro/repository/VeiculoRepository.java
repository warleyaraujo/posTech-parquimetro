package com.fiap.posTech.parquimetro.repository;

import com.fiap.posTech.parquimetro.model.Veiculo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VeiculoRepository extends MongoRepository<Veiculo, String> {

    Optional<Veiculo> findByPlaca(String placa);

}
