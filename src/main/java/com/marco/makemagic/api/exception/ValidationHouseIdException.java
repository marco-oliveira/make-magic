package com.marco.makemagic.api.exception;

/**
 * Exceção a ser lançada na ocorrência de falhas no fluxo da validação.
 *
 * @author Marco Antônio
 */
public class ValidationHouseIdException extends RuntimeException {

    public ValidationHouseIdException() {}

    public ValidationHouseIdException(String message) {
        super(message);
    }
}
