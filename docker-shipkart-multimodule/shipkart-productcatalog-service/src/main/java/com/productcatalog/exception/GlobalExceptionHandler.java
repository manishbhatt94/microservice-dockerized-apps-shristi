package com.productcatalog.exception;

import java.time.LocalDateTime;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.productcatalog.model.dtos.ApiErrors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected @Nullable ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		ApiErrors apiErrors = ApiErrors.builder().errorMessage(HttpStatus.METHOD_NOT_ALLOWED.name())
				.exception(ex.getMessage()).timestamp(LocalDateTime.now()).status(status.value()).build();

		return ResponseEntity.status(status).body(apiErrors);
	}

	@Override
	protected @Nullable ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		ApiErrors apiErrors = ApiErrors.builder().errorMessage(HttpStatus.UNSUPPORTED_MEDIA_TYPE.name())
				.exception(ex.getMessage()).timestamp(LocalDateTime.now()).status(status.value()).build();

		return ResponseEntity.status(status).body(apiErrors);
	}

	@Override
	protected @Nullable ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		ApiErrors apiErrors = ApiErrors.builder().errorMessage(HttpStatus.valueOf(ex.getStatusCode().value()).name())
				.exception(ex.getMessage()).timestamp(LocalDateTime.now()).status(status.value()).build();

		return ResponseEntity.status(status).body(apiErrors);
	}

	@Override
	protected @Nullable ResponseEntity<Object> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status,
			WebRequest request) {

		ApiErrors apiErrors = ApiErrors.builder().errorMessage(HttpStatus.BAD_REQUEST.name()).exception(ex.getMessage())
				.timestamp(LocalDateTime.now()).status(status.value()).build();

		return ResponseEntity.status(status).body(apiErrors);
	}

	@Override
	protected @Nullable ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {

		ApiErrors apiErrors = ApiErrors.builder().errorMessage(ex.getErrorCode()).exception(ex.getMessage())
				.timestamp(LocalDateTime.now()).status(status.value()).build();

		return ResponseEntity.status(status).body(apiErrors);
	}

	@ExceptionHandler(value = ProductNotFoundException.class)
	public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException ex) {

		int statusCode = HttpStatus.NOT_FOUND.value();
		ApiErrors apiErrors = ApiErrors.builder().errorMessage("Product(s) with selected filter(s) not found")
				.exception(ex.getMessage()).timestamp(LocalDateTime.now()).status(statusCode).build();

		return ResponseEntity.status(statusCode).body(apiErrors);
	}

}
