package br.edu.unidep.ApiES.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import br.edu.unidep.ApiES.event.ObjetoCriadoEvent;
import br.edu.unidep.ApiES.repository.ClinicaRepository;
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

import br.edu.unidep.ApiES.model.Clinica;
import br.edu.unidep.ApiES.service.ClinicaService;
import error.ResourceNotFoundExeption;



@RestController
@RequestMapping("/clinicas")
public class ClinicaResource {

	@Autowired
	private ClinicaRepository repositorio;
	
	@Autowired
	private ClinicaService clinicaService;

	@Autowired
	private ApplicationEventPublisher publisher;
	

	@GetMapping
	public ResponseEntity<?> listar() {
		List<Clinica> clinicas = repositorio.findAll();
		if (clinicas == null) {
			throw new ResourceNotFoundExeption("Not Found");
		}
		return !clinicas.isEmpty() ? ResponseEntity.ok(clinicas) :
			ResponseEntity.noContent().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Clinica> salvar(@Valid @RequestBody Clinica clinica, HttpServletResponse response) {
		Clinica clinicaSalva = repositorio.save(clinica);

		publisher.publishEvent(new ObjetoCriadoEvent(this, response, clinicaSalva.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(clinicaSalva);

	}

	@GetMapping("/{codigo_clinica}")
	public ResponseEntity<Clinica> buscarPeloCodigo(@PathVariable Long codigo_clinica) {
		Clinica clinica = repositorio.findOne(codigo_clinica);
		if ( clinica != null) { 
			return ResponseEntity.ok(clinica);
		}
		return ResponseEntity.notFound().build();	
	}
	
	@DeleteMapping("/{codigo_clinica}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo_clinica) {
		repositorio.delete(codigo_clinica);
	}
	
	@PutMapping("/{codigo_clinica}")
	public ResponseEntity<Clinica> atualizar(@PathVariable Long codigo_clinica, @Valid @RequestBody Clinica clinica) {		
		Clinica clinicaSalva = clinicaService.atualizar(codigo_clinica, clinica);		
		return ResponseEntity.ok(clinicaSalva);
	}
	
	
}
