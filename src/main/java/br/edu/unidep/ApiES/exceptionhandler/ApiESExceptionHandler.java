package br.edu.unidep.ApiES.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.springframework.dao.EmptyResultDataAccessException;

@ControllerAdvice
public class ApiESExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	MessageSource messageSource;

	/* Esse método é capturado quando a aplicação cliente envia um atributo não conhecido ao
	   modelo (Exemplo: Marca) no servidor. Pois não pode ser lido. 
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String mensagemUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());

		String mensagemDesenvolvedor = ex.getCause().toString();
		
		return super.handleExceptionInternal(ex, new Erro(mensagemUsuario, mensagemDesenvolvedor), headers, status,
				request);

	}
	
	/* Esse método é capturado quando a aplicação cliente envia as informações de um atributo
	 * inválida (Exemplo: O atributo nome de uma marca aceita entre 5 e 50 caracteres e é enviado
	 * um valor que está fora dessa validação). É um argumento não válido.
	 * 
	 * */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<Erro> erros = listaDeErros(ex.getBindingResult());
		
		return super.handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	/* Esse método é capturado quando a classe 'EmptyResultDataAccessException' é lançada, seja ela
	 * por qualquer motivo. (Exemplo: Quando tenta deletar um registro da tabela que não existe).
	 */
	@ExceptionHandler({ EmptyResultDataAccessException.class})
	public ResponseEntity<Object> EmptyResultDataAccessException (
			EmptyResultDataAccessException ex,
			WebRequest request) {
		
		String mensagemUsuario = messageSource.getMessage("recurso.nao.encontrado", null, 
										LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	private List<Erro> listaDeErros(BindingResult bindingResult) {
		List<Erro> erros = new ArrayList<>();
		
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String mensagemUsuario = 
						messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String mensagemDesenvolvedor = fieldError.toString();
			erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		}
		return erros;
	}
	
	public static class Erro {

		private String mensagemUsuario;
		private String mensagemDesenvolvedor;

		public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
			super();
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}

		public String getMensagemUsuario() {
			return mensagemUsuario;
		}

		public String getMensagemDesenvolvedor() {
			return mensagemDesenvolvedor;
		}

	}
	

	
}
