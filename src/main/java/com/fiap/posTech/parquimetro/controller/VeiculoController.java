package com.fiap.posTech.parquimetro.controller;

import com.fiap.posTech.parquimetro.controller.exception.ControllerNotFoundException;
import com.fiap.posTech.parquimetro.controller.exception.ErrorResponse;
import com.fiap.posTech.parquimetro.controller.exception.ParkingException;
import com.fiap.posTech.parquimetro.model.Pessoa;
import com.fiap.posTech.parquimetro.model.Veiculo;
import com.fiap.posTech.parquimetro.service.PessoaService;
import com.fiap.posTech.parquimetro.service.VeiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/veiculos", produces = {"application/json"})
@Tag(name = "Cadastro de Veículos", description = "CRUD de Veículos")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private PessoaService pessoaService;


    @GetMapping
    @Operation(summary = "Obtem todos os veículos cadastradas", method = "GET")
    public ResponseEntity<Page<Veiculo>> findAll(@PageableDefault(size = 10, page = 0, sort= "modelo")
                                                   Pageable pageable) {
        Page<Veiculo> veiculos = veiculoService.findAll(pageable);
        return ResponseEntity.ok(veiculos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Procurar Veículo por Id", method = "GET")
    public ResponseEntity<Veiculo> findById(@PathVariable String id) {
        var veiculo = veiculoService.findById(id);
        return ResponseEntity.ok(veiculo);
    }

    @GetMapping("/placa/{placa}")
    @Operation(summary = "Procurar Veículo por Placa", method = "GET")
    public ResponseEntity<Veiculo> findByPlaca(@PathVariable String placa) {
        var veiculo = veiculoService.findByPlaca(placa);
        return ResponseEntity.ok(veiculo);
    }

    @GetMapping("/pessoa/{pessoaId}")
    @Operation(summary = "Procurar Veículo por Placa", method = "GET")
    public ResponseEntity<List<Veiculo>> findByPessoaId(@PathVariable String pessoaId) {
        var pessoa = pessoaService.findById(pessoaId);
        var veiculos = veiculoService.findByPessoa(pessoa);
        return ResponseEntity.ok(veiculos);
    }

    @PostMapping
    @Operation(summary = "Cadastrar um novo veículo", method = "POST")
    public ResponseEntity<?> save(@Valid @RequestBody Veiculo veiculo,
                                  @RequestParam String pessoaId) {
        try {
            Pessoa pessoa = pessoaService.findById(pessoaId);
            veiculo.setPessoa(pessoa);
            Veiculo savedVeiculo = veiculoService.save(veiculo);
            pessoa.adicionarVeiculo(savedVeiculo);
            pessoaService.save(pessoa);
            return new ResponseEntity<>(savedVeiculo, HttpStatus.CREATED);
        } catch (ControllerNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getTimestamp(), e.getStatus(), e.getError(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        catch (ParkingException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getTimestamp(), e.getStatus(), e.getError(), e.getMessage());
            return ResponseEntity.status(e.getStatus()).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Alterar um Veículo já cadastrada por Id", method = "PUT")
    public ResponseEntity<Veiculo> update(@PathVariable String id,
                                            @Valid @RequestBody Veiculo veiculo) {
        Veiculo veiculoUpdate = veiculoService.update(id, veiculo);
        return ResponseEntity.ok().body(veiculoUpdate);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um veículo já cadastrada por Id", method = "DELETE")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        veiculoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
