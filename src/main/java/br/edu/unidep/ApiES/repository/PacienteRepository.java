package br.edu.unidep.ApiES.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.unidep.ApiES.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long>{

}
