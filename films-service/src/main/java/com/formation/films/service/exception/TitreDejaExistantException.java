package com.formation.films.service.exception;

public class TitreDejaExistantException extends RuntimeException {
    public TitreDejaExistantException(String titre) {
        super("Un film avec le titre '" + titre + "' existe déjà.");
    }
}