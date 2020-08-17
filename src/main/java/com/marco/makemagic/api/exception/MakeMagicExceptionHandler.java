package com.marco.makemagic.api.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe handler responsável por interceptar e tratar as exceções de forma amigavel para o usuário.
 *
 * @author Marco Antônio
 */
@RestControllerAdvice
public class MakeMagicExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public MakeMagicExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Método handle referente a exceção {@link HttpMessageNotReadableException}.
     * @param ex -
     * @param headers -
     * @param status -
     * @param request -
     * @return -
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        String userMessage = messageSource.getMessage("invalid.message", null, LocaleContextHolder.getLocale());
        String devMessage = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
        List<MakeMagicError> errors = Collections.singletonList(new MakeMagicError(userMessage, devMessage));
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Método handle referente a exceção {@link MethodArgumentNotValidException}.
     *
     * @param ex -
     * @param headers -
     * @param status -
     * @param request -
     * @return -
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<MakeMagicError> errors = createErrorsList(ex.getBindingResult());
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Método handle referente a exceção {@link EmptyResultDataAccessException}.
     *
     * @param ex -
     * @param request -
     * @return -
     */
    @ExceptionHandler({ EmptyResultDataAccessException.class })
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
        String userMessage = messageSource.getMessage("not.found", null, LocaleContextHolder.getLocale());
        String devMessage = ex.toString();
        List<MakeMagicError> errors = Collections.singletonList(new MakeMagicError(userMessage, devMessage));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    /**
     * Método handle referente a exceção {@link DataIntegrityViolationException}.
     *
     * @param ex -
     * @param request -
     * @return -
     */
    @ExceptionHandler({ DataIntegrityViolationException.class } )
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        String userMessage = messageSource.getMessage("operation.not.permission", null, LocaleContextHolder.getLocale());
        String devMessage = ExceptionUtils.getRootCauseMessage(ex);
        List<MakeMagicError> errors = Collections.singletonList(new MakeMagicError(userMessage, devMessage));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Método handle referente a exceção {@link ValidationHouseIdException}.
     *
     * @param ex -
     * @param request -
     * @return -
     */
    @ExceptionHandler({ ValidationHouseIdException.class } )
    public ResponseEntity<Object> handleValidationHouseIdException(ValidationHouseIdException ex, WebRequest request) {
        String userMessage = messageSource.getMessage("house.id.not.exiting", null, LocaleContextHolder.getLocale());
        String devMessage = ExceptionUtils.getRootCauseMessage(ex);
        List<MakeMagicError> errors = Collections.singletonList(new MakeMagicError(userMessage, devMessage));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Método handle referente a exceção {@link WebClientResponseException}.
     *
     * @param ex -
     * @param request -
     * @return -
     */
    @ExceptionHandler({ WebClientResponseException.class } )
    public ResponseEntity<Object> handleWebClientResponseException(WebClientResponseException ex, WebRequest request) {
        String userMessage = messageSource.getMessage("webclient.error", null, LocaleContextHolder.getLocale());
        String devMessage = ExceptionUtils.getRootCauseMessage(ex);
        List<MakeMagicError> errors = Collections.singletonList(new MakeMagicError(userMessage, devMessage));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Responsável por criar uma lista de erros para apresentação.
     *
     * @param bindingResult -
     * @return -
     */
    private List<MakeMagicError> createErrorsList(final BindingResult bindingResult) {
        List<MakeMagicError> errors = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String userMessage = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String devMessage = fieldError.toString();
            errors.add(new MakeMagicError(userMessage, devMessage));
        }

        return errors;
    }

}
