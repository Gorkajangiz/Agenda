/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 *
 * @author Multi
 */
public class ExistenciaException extends Exception {

    boolean exist;
    String nombre;

    public ExistenciaException(boolean exist, String nombre) {
        this.exist = exist;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "El contacto " + nombre + ((exist) ? " ya" : " no") + " existe";
    }
}
