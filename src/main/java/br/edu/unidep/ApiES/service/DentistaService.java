package br.edu.unidep.ApiES.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unidep.ApiES.model.Dentista;
import br.edu.unidep.ApiES.repository.DentistaRepository;

@Service
public class DentistaService {

	@Autowired
	private DentistaRepository repositorio;
	
	public Dentista atualizar(Long codigo, Dentista dentista) {
		Dentista dentistaSalva = repositorio.findOne(codigo);
		BeanUtils.copyProperties(dentista, dentistaSalva, "codigo");
		return repositorio.save(dentistaSalva);
	}
}
