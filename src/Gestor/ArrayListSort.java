package Gestor;


import java.util.ArrayList;
import java.util.Collections;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gorka
 * @param <E>
 */
public class ArrayListSort<E extends Comparable> extends ArrayList<E> {
    public boolean add(E objeto) {
        super.add(objeto);
        Collections.sort(this);
        return true;
    }
}
