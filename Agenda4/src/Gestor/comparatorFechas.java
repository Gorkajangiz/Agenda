/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestor;

import Contactos.amigo;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author Multi
 */
public class comparatorFechas implements Comparator {

    @Override
    public int compare(Object obj1, Object obj2) {
        SimpleDateFormat formctm = new SimpleDateFormat("MMdd");
        Date f1 = ((amigo) obj1).getFecha_nac();
        Date f2 = ((amigo) obj2).getFecha_nac();
        String str1 = formctm.format(f1);
        String str2 = formctm.format(f2);
        int x = str1.compareTo(str2);
        return x;
    }
}
