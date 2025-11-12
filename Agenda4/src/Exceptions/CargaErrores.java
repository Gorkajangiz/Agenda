/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

import java.util.ArrayList;

/**
 *
 * @author Multi
 */
public class CargaErrores extends Exception {

    ArrayList<String> errores;

    public CargaErrores(ArrayList<String> er) {
        errores = er;
    }

    @Override
    public String toString() {
        String salida = "";
        for (String problemas : errores) {
            salida += problemas + "\n";
        }
        return salida;
    }
}
