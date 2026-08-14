package com.testvito.backendtestvito.exception;

public class ParticipantNotFoundException extends RuntimeException {

    public ParticipantNotFoundException(Long participantId) {
        super("Participant with id " + participantId + " not found");
    }

    public ParticipantNotFoundException(String message) {
        super(message);
    }
}