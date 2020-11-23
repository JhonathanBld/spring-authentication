package br.edu.unidep.ApiES.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.unidep.ApiES.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
