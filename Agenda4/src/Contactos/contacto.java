package Contactos;

import java.io.Serializable;
import java.util.Objects;

public abstract class contacto implements Comparable, Serializable {

    private String name;
    private Integer tlf;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTlf() {
        return tlf;
    }

    public void setTlf(Integer tlf) {
        this.tlf = tlf;
    }

    protected contacto(String name, Integer tlf) {
        this.name = name;
        this.tlf = tlf;
    }

    @Override
    public String toString() {
        String resul = "Nombre: " + name + " | Telefono: " + tlf;
        return resul;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final contacto other = (contacto) obj;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public int compareTo(Object obj) {
        int x;
        x = name.compareTo(((contacto) obj).name);
        return x;
    }
}
