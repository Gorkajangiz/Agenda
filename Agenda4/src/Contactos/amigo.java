package Contactos;

import java.text.SimpleDateFormat;
import java.util.Date;

public class amigo extends contacto implements Comparable {

    private Date fecha_nac;

    public Date getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(Date fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public amigo(String name, Integer tlf, Date fecha_nac) {
        super(name, tlf);
        this.fecha_nac = fecha_nac;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatoCastellano = new SimpleDateFormat("dd/MM/yyyy");
        return super.toString() + " | Fecha de nacimiento: " + formatoCastellano.format(fecha_nac) + " | Relación: AMIGO" + "\n";
    }
}
