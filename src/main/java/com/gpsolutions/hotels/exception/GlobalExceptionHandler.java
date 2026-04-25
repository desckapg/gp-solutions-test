package com.gpsolutions.hotels.exception;

import com.gpsolutions.hotels.domain.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
@NullMarked
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {ResourceNotFoundException.class})
  public ResponseEntity<ErrorDto> handleNotFoundException(ResourceNotFoundException ex,
      WebRequest request) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ErrorDto.notFound(ex.getMessage(), request.getContextPath()));
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status,
      WebRequest request) {
    return ResponseEntity.unprocessableContent().body(
        ErrorDto.validation(
            ex.getBindingResult().getFieldErrors().get(0).getField(),
            ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage()
        )
    );
  }

  @ExceptionHandler(value = {RuntimeException.class})
  public ResponseEntity<ErrorDto> handleRuntimeException(RuntimeException ex, WebRequest request) {
    log.error(ex.getMessage(), ex);
    return ResponseEntity.internalServerError()
        .body(ErrorDto.internal(request.getContextPath()));
  }
}
