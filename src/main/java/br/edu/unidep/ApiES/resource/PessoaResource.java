package br.edu.unidep.ApiES.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import br.edu.unidep.ApiES.event.ObjetoCriadoEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.unidep.ApiES.model.Pessoa;
import br.edu.unidep.ApiES.repository.PessoaRepository;
import br.edu.unidep.ApiES.service.ProdutoService;
import error.ResourceNotFoundExeption;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository repositorio;
	
	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ApplicationEventPublisher publisher;

	
	@GetMapping
	public ResponseEntity<?> listar() {
		List<Pessoa> pessoas = repositorio.findAll();
		if (pessoas == null) {
			throw new ResourceNotFoundExeption("Not Found");
		}
		return !pessoas.isEmpty() ? ResponseEntity.ok(pessoas) :
			ResponseEntity.noContent().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Pessoa> salvar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa produtoSalvo = repositorio.save(pessoa);

		publisher.publishEvent(new ObjetoCriadoEvent(this, response, produtoSalvo.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
	}
	
	@GetMapping("/{codigo_pessoa}")
	public ResponseEntity<Pessoa> buscaPorId(@PathVariable Long codigo_pessoa){
		Pessoa pessoa = repositorio.findOne(codigo_pessoa);
		if (pessoa != null) {
			return ResponseEntity.ok(pessoa);
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo_pessoa}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo_pessoa) {
		repositorio.delete(codigo_pessoa);
	}
	
	@PutMapping("/{codigo_pessoa}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo_pessoa, @Valid @RequestBody Pessoa pessoa) {
		Pessoa produtoSalvo = produtoService.atualizar(codigo_pessoa, pessoa);
		return ResponseEntity.ok(produtoSalvo);
	}
	
}
