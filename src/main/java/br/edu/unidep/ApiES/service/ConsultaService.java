package br.edu.unidep.ApiES.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unidep.ApiES.model.Consulta;
import br.edu.unidep.ApiES.repository.ConsultaRepository;


@Service
public class ConsultaService {
	
	@Autowired
	private ConsultaRepository repositorio;

	public Consulta atualizar(Long codigo, Consulta consulta) {		
		Consulta consultaSalva = repositorio.findOne(codigo);		
		BeanUtils.copyProperties(consulta, consultaSalva, "codigo");
		return repositorio.save(consultaSalva);
	}
}
