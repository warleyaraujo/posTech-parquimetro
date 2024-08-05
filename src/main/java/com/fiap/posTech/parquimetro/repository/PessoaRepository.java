package com.fiap.posTech.parquimetro.repository;

import com.fiap.posTech.parquimetro.model.Pessoa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends MongoRepository<Pessoa, String> {
}
