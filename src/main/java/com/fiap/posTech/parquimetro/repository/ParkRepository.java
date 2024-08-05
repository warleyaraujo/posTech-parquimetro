package com.fiap.posTech.parquimetro.repository;

import com.fiap.posTech.parquimetro.model.Park;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkRepository extends MongoRepository<Park, String> {
    @Query(value = "{'valorCobrado': {$eq: ?0}}")
    public List<Park> obterListaEnviarEmail(Double valor);

    public Page<Park> findAllByAtivaIsTrue(Pageable lista);

    @Query("{ 'ativa' : true, 'tipoTempo' : 'FIXO' }")
    public List<Park> findAllByAtivaIsTrueAndTipoTempoFIXO();
}
