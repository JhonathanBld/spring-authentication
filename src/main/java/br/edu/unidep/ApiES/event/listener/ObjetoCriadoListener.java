package br.edu.unidep.ApiES.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.unidep.ApiES.event.ObjetoCriadoEvent;

@Component
public class ObjetoCriadoListener implements ApplicationListener<ObjetoCriadoEvent>  {

	@Override
	public void onApplicationEvent(ObjetoCriadoEvent event) {
		
		HttpServletResponse response = event.getResponse();
		Long codigo = event.getCodigo();
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}").buildAndExpand(codigo).toUri();
		response.setHeader("Location", uri.toASCIIString());
	}

}
