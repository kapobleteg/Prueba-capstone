package org.centro.maibo.centromaibo.exception;

public class ProfileNotFoundException extends Exception {

    public ProfileNotFoundException(Long id) {
        super("El perfil no existe en el sistema: #" + id);
    }
}
