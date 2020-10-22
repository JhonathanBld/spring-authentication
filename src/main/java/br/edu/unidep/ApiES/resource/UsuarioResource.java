package br.edu.unidep.ApiES.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unidep.ApiES.model.Usuario;
import br.edu.unidep.ApiES.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {
	
	@Autowired
	private UsuarioRepository repositorio;


	@GetMapping("/{email:.+}")
	public ResponseEntity<Usuario> buscarPeloEmail(@PathVariable String email) {
		
		Usuario usuario = repositorio.findByEmail(email);
		
		if ( usuario != null) { 
			return ResponseEntity.ok(usuario);
		} 
		
		return ResponseEntity.notFound().build();
	
	}

}
