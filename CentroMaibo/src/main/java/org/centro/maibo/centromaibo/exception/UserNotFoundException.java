package org.centro.maibo.centromaibo.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(Long id) { super("El usuario no existe en el sistema: #" + id); }
}
