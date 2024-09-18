package br.com.app.product.exception.handle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import br.com.app.product.exception.BusinessException;
import br.com.app.product.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	public static final String DEFAULT_MESSAGE_ERROR = 
			"Ocorreu um erro interno inesperado no sistema. Tente novamente e se "
			+ "o problema persistir, entre em contato com o administrador do sistema.";
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status).headers(headers).build();
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleValidationInternal(ex, ex.getBindingResult(), status, request);
	}

	private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult, HttpStatus status,
			WebRequest request) {
		ProblemType problemType = ProblemType.INVALID_DATA;
		String message = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
		List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream()
				.map(objectError -> {
					String messageObject = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
					
					String name = objectError.getObjectName();
					
					if(objectError instanceof FieldError) {
						name = ((FieldError) objectError).getField();
					}
					return Problem.Object.builder()
						.name(name)
						.message(messageObject)
						.build();
				})
				.collect(Collectors.toList());
		
		Problem problem = createProblemBuilder(status, problemType, message)
						.objects(problemObjects)
						.build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleUncaught(Exception ex,
			WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ProblemType problemType = ProblemType.SYSTEM_ERROR;
		
		log.error(ex.getMessage(), ex);
		
		Problem problem = createProblemBuilder(status, problemType, DEFAULT_MESSAGE_ERROR).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
		String message = String.format(
				"O recurso '%s', que você tentou acessar, é inexistente", ex.getRequestURL());
		
		Problem problem = createProblemBuilder(status, problemType, message).build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException) ex, headers, status, request);
		}
		return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, 
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ProblemType problemType = ProblemType.INVALID_PARAMETER;
		String message = String.format(
				"O parâmetro '%s' recebeu o valor '%s' que é de um tipo inválido. "
				+ "Corrija e informe um valor compatível com o tipo %s.",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
		
		Problem problem = createProblemBuilder(status, problemType, message).build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);

		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		}
		if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
		}

		ProblemType problemType = ProblemType.INCOMPREHENSIVE_MESSAGE;
		String message = "O corpo da requisição está inválida. Verifique erro de sintaxe.";

		Problem problem = createProblemBuilder(status, problemType, message).build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ProblemType problemType = ProblemType.INCOMPREHENSIVE_MESSAGE;
		String path = joinPath(ex.getPath());
		String message = String.format(
				"A propriedade '%s' recebeu o valor '%s' que é de um tipo inválido. "
				+ "Corrija e informe um valor compatível com o tipo %s.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());
		
		Problem problem = createProblemBuilder(status, problemType, message).build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String path = joinPath(ex.getPath());
		ProblemType problemType = ProblemType.INCOMPREHENSIVE_MESSAGE;
		String message = String.format(
				"A propriedade '%s' não existe."
				+ " Corrija ou remova essa propriedade e tente novamente.", path);
		
		Problem problem = createProblemBuilder(status, problemType, message).build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex,
			WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
		String message = ex.getMessage();

		Problem problem = createProblemBuilder(status, problemType, message).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.BUSINESS_ERROR;
		String message = ex.getMessage();

		Problem problem = createProblemBuilder(status, problemType, message).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (body == null) {
			body = Problem.builder()
					.status(status.value())
					.datetime(LocalDateTime.now())
					.title(status.getReasonPhrase())
					.message(DEFAULT_MESSAGE_ERROR).build();
		} else if (body instanceof String) {
			body = Problem.builder()
					.status(status.value())
					.datetime(LocalDateTime.now())
					.title((String) body)
					.message(DEFAULT_MESSAGE_ERROR).build();
		}
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	

	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String message) {
		return Problem.builder()
				.status(status.value())
				.datetime(LocalDateTime.now())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.message(message);
	}

	private String joinPath(List<Reference> path) {
		return path.stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
	}
}
