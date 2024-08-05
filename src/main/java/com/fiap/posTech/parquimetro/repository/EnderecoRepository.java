package com.fiap.posTech.parquimetro.repository;

import com.fiap.posTech.parquimetro.model.Endereco;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends MongoRepository<Endereco, String>{
}
