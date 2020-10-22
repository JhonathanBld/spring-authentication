package br.edu.unidep.ApiES.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unidep.ApiES.model.Pessoa;
import br.edu.unidep.ApiES.repository.PessoaRepository;

@Service
public class ProdutoService {

	@Autowired
	private PessoaRepository repositorio;
	
	public Pessoa atualizar(Long codigo, Pessoa produto) {
		Pessoa produtoSalvo = repositorio.findOne(codigo);
		BeanUtils.copyProperties(produto, produtoSalvo, "codigo");
		return repositorio.save(produtoSalvo);
	}
}
