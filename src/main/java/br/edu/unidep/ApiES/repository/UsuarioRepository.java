package br.edu.unidep.ApiES.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;

import br.edu.unidep.ApiES.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	public Usuario findByEmail(String email);
	
//	@Query("select u from User u where u.emailAddress = ?1")
//	Optional<Usuario> buscarPeloEmail(String email);

}
