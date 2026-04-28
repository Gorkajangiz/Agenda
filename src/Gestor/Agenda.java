package Gestor;

import Contactos.profesional;
import Contactos.amigo;
import Contactos.contacto;
import Exceptions.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class Agenda {

    DAOContactos dao = new DAOContactos();
    private final HashMap<String, contacto> Contactos;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public Agenda() {
        Contactos = new HashMap<>();
    }

    /**
     * Método para dar de alta nuevos contactos en la agenda HashMap
     *
     * @param name
     * @param num
     * @param fecha
     * @param empresa
     * @param amigo
     * @throws ParseException
     * @throws ExistenciaException
     * @throws NumberFormatException
     * @throws Exception
     */
    public void alta(String name, Integer num, String fecha, String empresa, Boolean amigo) throws ParseException, ExistenciaException, SQLException, Exception {
        if (dao.existe(name)) {
            throw new ExistenciaException(true, name);
        }
        if (name.isEmpty()) {
            throw new Exception("El nombre está vacío");
        }
        if (amigo) {
            Date fecdate = sdf.parse(fecha);
            contacto c = new amigo(name, num, fecdate);
            try {
                dao.insertar(c);
            } catch (SQLException ex) {
                System.out.println("Ha ocurrido el siguiente error: " + ex);
            } catch (Exception ex) {
                System.out.println("Ha ocurrido el siguiente error: " + ex);
            }
        } else {
            contacto c = new profesional(name, num, empresa);
            dao.insertar(c);
        }
    }

    /**
     * Método para buscar contactos en la agenda
     *
     * @param con
     * @return String del contacto
     * @throws ExistenciaException
     * @throws Exception
     */
    public String buscar(String con) throws ExistenciaException, Exception {
        contacto c = dao.getContacto(con);
        if (dao.vacio()) {
            throw new Exception("La agenda está vacía, pruebe a rellenarla");
        }
        if (c == null) {
            throw new ExistenciaException(false, con);
        }
        return "El contacto es " + c.toString() + "\n";
    }

    /**
     * Método para eliminar contactos definitivamente de la agenda HashMap
     *
     * @param con
     * @throws ExistenciaException
     * @throws Exception
     */
    public void borrar(String con) throws ExistenciaException, Exception {
        contacto c = dao.getContacto(con);
        if (dao.vacio()) {
            throw new Exception("La agenda está vacía, pruebe a rellenarla");
        }
        if (c == null) {
            throw new ExistenciaException(false, con);
        }
        try {
            dao.eliminar(c);
        } catch (Exception ex) {
            System.out.println("Ha ocurrido el siguiente error: " + ex);
        }
    }

    /**
     * Método para recorrer la agenda y listar por nombres alfabéticamente
     *
     * @return Un String listando todos los contactos
     * @throws Exception
     */
    public String Recorrer() throws Exception {
        if (dao.vacio()) {
            throw new Exception("La agenda está vacía, pruebe a rellenarla");
        }
        ArrayListSort<contacto> lista = dao.getTodos();
        Iterator it = (lista.iterator());
        String resul = "";
        while (it.hasNext()) {
            contacto obj = (contacto) it.next();
            resul += obj;
        }
        return resul;
    }

    /**
     * Método hermano de recorrer exclusivo para amigos
     *
     * @return Un String listando solo los amigos (Filtrados y ordenados por su
     * fecha)
     * @throws Exception
     */
    public String RecorrerPorFecha() throws Exception {
        if (dao.vacio()) {
            throw new Exception("La agenda está vacía, pruebe a rellenarla");
        }
        ArrayList<amigo> lista = new ArrayList<>();
        String fechanueva;
        for (contacto c : dao.getTodos()) {
            if (c instanceof amigo) {
                lista.add((amigo) c);
            }
        }
        if (lista.isEmpty()) {
            throw new Exception("La agenda está vacía, pruebe a rellenarla");
        }
        Collections.sort(lista, new comparatorFechas());
        long fyh = System.currentTimeMillis();
        SimpleDateFormat sdfmesdia = new SimpleDateFormat("MMdd");
        String fechaActual = sdfmesdia.format(new Date(fyh));
        Iterator it2 = (lista.iterator());
        String resul = "";
        while (it2.hasNext()) {
            amigo obj = (amigo) it2.next();
            fechanueva = sdfmesdia.format((obj).getFecha_nac());
            if (Integer.valueOf(fechaActual) <= Integer.valueOf(fechanueva)) {
                resul += obj;
            }
        }
        it2 = (lista.iterator());
        while (it2.hasNext()) {
            amigo obj = (amigo) it2.next();
            fechanueva = sdfmesdia.format((obj).getFecha_nac());
            if (Integer.valueOf(fechaActual) > Integer.valueOf(fechanueva)) {
                resul += obj;
            }
        }
        return resul;
    }

    /**
     * Método para editar contactos ya existentes
     *
     * @param name
     * @param num_nuevo
     * @param fecha_2
     * @param empresa_2
     * @param amigo
     * @throws ParseException
     * @throws Exception
     * @throws NumberFormatException
     * @throws ExistenciaException
     * @throws ClassCastException
     */
    public void editar(String name, String num_nuevo, String fecha_2, String empresa_2, Boolean amigo) throws ParseException, Exception, NumberFormatException, ExistenciaException, ClassCastException {
        if (dao.vacio()) {
            throw new Exception("La agenda está vacía, pruebe a rellenarla");
        }
        contacto c = dao.getContacto(name);
        if (!dao.existe(name)) {
            throw new ExistenciaException(false, name);
        }
        if (Integer.valueOf(num_nuevo) == null) {
            throw new Exception("El numero introducido es incorrecto");
        }
        Integer numero = Integer.valueOf(num_nuevo);
        Date fecha = null;
        String comentario = null;
        if (amigo) {
            fecha = sdf.parse(fecha_2);
        } else {
            comentario = empresa_2;
        }
        dao.editar(c, numero, fecha, comentario, amigo);
    }

    /**
     * Método para salvar la agenda en el archivo que se le introduzca en el
     * parámetro de entrada. Utiliza el método Fex / Fnex.
     *
     * @param ruta
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void guardarAgenda(String ruta) throws FileNotFoundException, IOException {
        File fex = new File(ruta);
        File fnex = new File(ruta + "_1");
        if (fex.exists()) {
            fex.renameTo(fnex);
        }
        try (FileOutputStream fos = new FileOutputStream(ruta)) {
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(Contactos);
            }
            fos.close();
            if (fnex.exists()) {
                fnex.delete();
            }
        } catch (IOException ex) {
            fnex.renameTo(fex);
        }
    }

    /**
     * Método para importar contactos nuevos, añadiendolos a la lista ya
     * existente de contactos. Saca un ArrayList de errores cuando los contactos
     * ya existan o tengan cualquier otro tipo de error
     *
     * @param ruta
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws Exception
     */
    public void importar(String ruta) throws FileNotFoundException, IOException, ClassNotFoundException, Exception {
        try (FileReader fr = new FileReader(ruta)) {
            try (BufferedReader br = new BufferedReader(fr)) {
                ArrayList<String> errores = new ArrayList();
                String linea;
                String nombre;
                String telefono;
                String extra;
                String amigo;
                while ((linea = br.readLine()) != null) {
                    try {
                        String[] datos = linea.split("\\s\\|\\s");
                        String[] datos2 = datos[0].split(": ");
                        nombre = datos2[1];
                        datos2 = datos[1].split(": ");
                        telefono = datos2[1];
                        datos2 = datos[2].split(": ");
                        extra = datos2[1];
                        datos2 = datos[3].split(": ");
                        amigo = datos2[1];
                        switch (amigo) {
                            case "PROFESIONAL":
                                this.alta(nombre, Integer.valueOf(telefono), null, extra, false);
                                break;
                            case "AMIGO":
                                this.alta(nombre, Integer.valueOf(telefono), extra, null, true);
                                break;
                            default:
                                throw new Exception("Error");
                        }
                    } catch (ExistenciaException ex) {
                        errores.add(linea + " Error: " + ex);
                    }
                }
                if (!errores.isEmpty()) {
                    throw new CargaErrores(errores);
                }
            }
            fr.close();
        }
    }

    /**
     * Método para crear una lista de contactos en un fichero externo. El
     * parámetro es la ruta del fichero.
     *
     * @param directorio2
     * @param dec1
     * @throws IOException
     * @throws Exception
     */
    public void listarExterno(String directorio2, String dec1) throws IOException, Exception {
        try (FileWriter fw = new FileWriter(directorio2)) {
            try (PrintWriter pw = new PrintWriter(fw)) {
                switch (dec1) {
                    case "F":
                        pw.print(this.RecorrerPorFecha());
                        break;
                    case "A":
                        pw.print(this.Recorrer());
                        break;
                    default:
                        throw new AssertionError();
                }
            }
            fw.close();
        }
    }

    /**
     * Método para conseguir la lista de nombres en formato ArrayList.
     *
     * @return El ArrayList de nombres.
     * @throws java.sql.SQLException
     */
    public ArrayList<String> getNombres() throws SQLException {
        ArrayList<String> nombres = new ArrayList(dao.getTodosClave());
        Collections.sort(nombres);
        return nombres;
    }

    public ArrayList<String> cogerDatos(String nombre) throws Exception {
        ArrayList<String> datos = new ArrayList();
        contacto c = dao.getContacto(nombre);
        String nombreUsuario = c.getName();
        String numero = c.getTlf().toString();
        datos.add(nombreUsuario);
        datos.add(numero);
        if (c instanceof amigo) {
            amigo c1 = (amigo) dao.getContacto(nombre);
            String fecha = sdf.format(c1.getFecha_nac());
            String AoP = "AMIGO";
            datos.add(fecha);
            datos.add(AoP);
        } else if (c instanceof profesional) {
            profesional c1 = (profesional) dao.getContacto(nombre);
            String comentario = c1.getComentario();
            String AoP = "PROFESIONAL";
            datos.add(comentario);
            datos.add(AoP);
        } else {
            throw new Exception("El elemento seleccionado no se puede procesar");
        }
        return datos;
    }
}
