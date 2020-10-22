package br.edu.unidep.ApiES.service;

import java.util.Optional;

import br.edu.unidep.ApiES.repository.ClinicaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.edu.unidep.ApiES.model.Clinica;


@Service
public class ClinicaService {
	
	private final ClinicaRepository repositorio;

	public ClinicaService(ClinicaRepository repositorio) {
		this.repositorio = repositorio;
	}

	public Clinica atualizar(Long id, Clinica clinica) {

		Clinica clinicaSalva = repositorio.findOne(id);
		
		if (clinicaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(clinica, clinicaSalva, "codigo");
		return repositorio.save(clinicaSalva);
	}
}
