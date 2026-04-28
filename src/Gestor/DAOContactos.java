/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestor;

import Contactos.amigo;
import Contactos.contacto;
import Contactos.profesional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Multi
 */
public class DAOContactos {

    public DAOContactos() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException ex) {
            System.out.println("Ha ocurrido el siguiente error: " + ex);
        }
    }

    private Connection con;

    /**
     * Crea la conexión con la base de datos
     *
     * @return Un objeto Connection para ser cerrado
     * @throws SQLException
     */
    public Connection contactar() throws SQLException {
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "scott";
        String pass = "tiger";
        con = DriverManager.getConnection(url, user, pass);
        return con;
    }

    /**
     * Rescata un contacto de nombre concreto en la base de datos, devuelve
     * "null" si no lo encuentra
     *
     * @param nombre
     * @return El usuario en tipo "Contacto"
     * @throws SQLException
     */
    public contacto getContacto(String nombre) throws SQLException {
        contacto c = null;
        this.contactar();
        try (PreparedStatement ps = con.prepareStatement("select nombre, telefono, fecha, tipo from contactos where nombre = ?")) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nombre2 = rs.getString(1);
                    int telefono = rs.getInt(2);
                    if (rs.getString(4) != null) {
                        c = new profesional(nombre2, telefono, rs.getString(4));
                    } else {
                        c = new amigo(nombre2, telefono, new java.util.Date(rs.getDate(3).getTime()));
                    }
                }
            }
        }
        con.close();
        return c;
    }

    /**
     * Rescata un ArrayList sin decorar de todos los contactos de la base de
     * datos (ArrayList<Contactos>)
     *
     * @return Un ArrayList de contactos
     * @throws SQLException
     */
    public ArrayListSort<contacto> getTodos() throws SQLException {
        ArrayListSort<contacto> lista = new ArrayListSort();
        this.contactar();
        try (PreparedStatement ps = con.prepareStatement("select * from contactos"); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String nombre = rs.getString(1);
                int telefono = rs.getInt(2);
                if (rs.getString(4) != null) {
                    profesional c = new profesional(nombre, telefono, rs.getString(4));
                    lista.add(c);
                } else {
                    amigo c = new amigo(nombre, telefono, new java.util.Date(rs.getDate(3).getTime()));
                    lista.add(c);
                }
            }
        }
        con.close();
        return lista;
    }

    /**
     * Rescata todos los NOMBRES de la base de datos, que funcionan como Primary
     * Keys en formato ArrayList<String>
     *
     * @return Un ArrayList de Strings
     * @throws SQLException
     */
    public ArrayListSort<String> getTodosClave() throws SQLException {
        ArrayListSort<String> lista = new ArrayListSort();
        this.contactar();
        try (PreparedStatement ps = con.prepareStatement("select nombre from contactos"); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String nombre = rs.getString(1);
                lista.add(nombre);
            }
        }
        con.close();
        return lista;
    }

    /**
     * Llama al metodo getContacto para saber si un contacto existe o no en la
     * base de datos
     *
     * @param nombre
     * @return Un boolean de existencia; existe = true, no existe = false
     * @throws SQLException
     */
    public boolean existe(String nombre) throws SQLException {
        Boolean existencia = false;
        if (this.getContacto(nombre) != null) {
            existencia = true;
        }
        return existencia;
    }

    /**
     * Comprueba si la base de datos esta vacía en caso de que sea necesario
     *
     * @return Un boolean; vacío = true; no vacío = false
     * @throws SQLException
     */
    public boolean vacio() throws SQLException {
        Boolean vacio = false;
        this.contactar();
        PreparedStatement ps = con.prepareStatement("select nombre from contactos"); 
        ResultSet rs = ps.executeQuery();
        vacio =! rs.next();
        con.close();
        return vacio;
    }

    /**
     * Inserta a una persona en la base de datos, requiere un objeto de tipo
     * contacto
     *
     * @param c
     * @throws SQLException
     */
    public void insertar(contacto c) throws SQLException {
        PreparedStatement ps = null;
        String q1 = "insert into Amigo values(?, ?, ?)";
        String q2 = "insert into Profesional values(?, ?, ?)";
        try {
            this.contactar();
            if (c instanceof amigo) {
                ps = con.prepareStatement(q1);
                ps.setDate(3, new java.sql.Date(((amigo) c).getFecha_nac().getTime()));
            } else if (c instanceof profesional) {
                ps = con.prepareStatement(q2);
                ps.setString(3, ((profesional) c).getComentario());
            }
            ps.setString(1, c.getName());
            ps.setInt(2, c.getTlf());
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 12899) {
                throw ex;
            } else {
                System.out.println("Ha ocurrido el siguiente error: " + ex);
            }
        }
    }

    /**
     * Elimina un contacto de la base de datos, la verificación esta en el
     * método de Borrar de la Agenda, pide un objeto tipo contacto
     *
     * @param c
     * @throws SQLException
     * @throws Exception
     */
    public void eliminar(contacto c) throws SQLException, Exception {
        PreparedStatement ps;
        String q1 = "delete Amigo where nombre = ?";
        String q2 = "delete Profesional where nombre = ?";
        this.contactar();
        if (c instanceof amigo) {
            ps = con.prepareStatement(q1);
        } else {
            ps = con.prepareStatement(q2);
        }
        ps.setString(1, c.getName());
        int r = ps.executeUpdate();
        if (r > 1) {
            throw new Exception("Hay más de un contacto con ese nombre");
        }
        ps.close();
        con.close();
    }

    /**
     * Edita un contacto ya existente en la agenda
     *
     * @param c
     * @param telefono
     * @param fecha
     * @param empresa
     * @param amigo
     * @throws SQLException
     * @throws Exception
     */
    public void editar(contacto c, Integer telefono, Date fecha, String empresa, Boolean amigo) throws SQLException, Exception {
        PreparedStatement ps;
        String q1 = "update Amigo set telefono = ?, fecha = ? where nombre = ?";
        String q2 = "update Profesional set telefono = ?, Empresa = ? where nombre = ?";
        this.contactar();
        if (amigo) {
            ps = con.prepareStatement(q1);
            ps.setDate(2, new java.sql.Date(fecha.getTime()));
        } else {
            ps = con.prepareStatement(q2);
            ps.setString(2, empresa);
        }
        ps.setInt(1, telefono);
        ps.setString(3, c.getName());
        int r = ps.executeUpdate();
        if (r > 1) {
            throw new Exception("Hay más de un contacto con ese nombre");
        }
        ps.close();
        con.close();
    }
}
