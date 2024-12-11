package org.centro.maibo.centromaibo.exception;

public class BoxNotFoundException extends Exception {

    public BoxNotFoundException(Long id) { super("El box no existe en el sistema: #" + id); }
}
