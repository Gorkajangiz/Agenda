package Contactos;

public class profesional extends contacto {

    private String empresa;

    public String getComentario() {
        return empresa;
    }

    public void setComentario(String comentario) {
        this.empresa = comentario;
    }

    public profesional(String name, Integer tlf, String comentario) {
        super(name, tlf);
        this.empresa = comentario;
    }

    @Override
    public String toString() {
        return super.toString() + " | Comentario: " + empresa + " | Relacion: PROFESIONAL" + "\n";
    }

}
