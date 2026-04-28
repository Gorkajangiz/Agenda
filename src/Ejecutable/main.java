package Ejecutable;

import Exceptions.CargaErrores;
import Gestor.Agenda;
import Exceptions.ExistenciaException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Agenda Contactos = new Agenda();
        String directorio = null;
        Boolean aceptado = false;
        Boolean ejecutando = true;
        Boolean ejecutandoEspecial = true;
        while (ejecutandoEspecial) {
            System.out.println("¡Hola Gorka! ¿Qué deseas hacer hoy?");
            System.out.println("1. Iniciar la agenda");
            System.out.println("X. Salir del programa");
            String str_original = sc.nextLine().toUpperCase();
            while (ejecutando) {
                switch (str_original) {
                    case "1":
                        System.out.println("¿Qué te gustaría hacer hoy?");
                        System.out.println("1. Crear un nuevo contacto");
                        System.out.println("2. Listar contactos");
                        System.out.println("3. Buscar un contacto");
                        System.out.println("4. Eliminar un contacto");
                        System.out.println("5. Modificar un contacto");
                        System.out.println("6. Listar en un fichero");
                        System.out.println("7. Importar agenda desde fichero");
                        System.out.println("X. Salir del programa");
                        String str_ordenes = sc.nextLine().toUpperCase();

                        switch (str_ordenes) {
                            case "1":
                                Boolean fun = true;
                                Boolean amigo;
                                String extra;
                                while (fun) {
                                    System.out.println("¿(A)migo o (P)rofesional?");
                                    String strAmigo = sc.nextLine().toUpperCase();
                                    if (strAmigo.equals("P") || strAmigo.equals("A")) {
                                        System.out.println("Introduce su nombre:");
                                        String nombre = sc.nextLine();
                                        System.out.println("Ahora su numero:");
                                        String numero = sc.nextLine();
                                        try {
                                            Integer.valueOf(numero);
                                        } catch (NumberFormatException ex) {
                                            System.out.println("El numero no se ha aceptado por el siguiente motivo: " + ex);
                                            break;
                                        }
                                        switch (strAmigo) {
                                            case "A":
                                                amigo = true;
                                                System.out.println("Introduce su fecha de nacimiento (dd/mm/yyyy):");
                                                extra = sc.nextLine();
                                                try {
                                                    Contactos.alta(nombre, Integer.valueOf(numero), extra, null, amigo);
                                                    System.out.println("Contacto creado correctamente");
                                                } catch (ParseException ex) {
                                                    System.out.println("La fecha introducida no es correcta: " + ex);
                                                } catch (ExistenciaException ex) {
                                                    System.out.println(ex);
                                                } catch (NullPointerException ex) {
                                                    System.out.println("La lista no ha sido inicializada: " + ex);
                                                } catch (Exception ex) {
                                                    System.out.println("Error: " + ex);
                                                }
                                                break;
                                            case "P":
                                                amigo = false;
                                                System.out.println("Introduce el comentario o nombre de la empresa:");
                                                extra = sc.nextLine();
                                                 {
                                                    try {
                                                        Contactos.alta(nombre, Integer.valueOf(numero), null, extra, amigo);
                                                        System.out.println("Contacto creado correctamente");
                                                    } catch (ExistenciaException ex) {
                                                        System.out.println(ex);
                                                    } catch (ParseException ex) {
                                                        System.out.println("La fecha introducida no es correcta: " + ex);
                                                    } catch (Exception ex) {
                                                        System.out.println("Error: "+ex);
                                                    }
                                                }
                                                break;
                                            default:
                                                System.out.println("Introduce un dato valido por favor");
                                        }
                                        System.out.println("-------------------------------------------------------");
                                        fun = false;
                                    } else {
                                        System.out.println("Introduce una letra válida por favor");
                                        break;
                                    }
                                }
                                break;
                            case "2":
                                Boolean recorrer = true;
                                while (recorrer) {
                                    System.out.println("¿Desea ver todos o ordenar por fechas (Solo amigos)?");
                                    System.out.println("1. Todos");
                                    System.out.println("2. Fechas");
                                    String strEleccion1 = sc.nextLine();
                                    switch (strEleccion1) {
                                        case "1":
                                            System.out.println("Esta es tu lista de contactos:");
                                             {
                                                try {
                                                    System.out.println(Contactos.Recorrer());
                                                } catch (Exception ex) {
                                                    System.out.println(ex.getMessage());
                                                }
                                            }
                                            break;
                                        case "2":
                                            System.out.println("Esta es tu lista de amigos ordenados por fecha:");
                                             {
                                                try {
                                                    System.out.println(Contactos.RecorrerPorFecha());
                                                } catch (Exception ex) {
                                                    System.out.println(ex.getMessage());
                                                }
                                            }
                                            break;
                                        default:
                                            System.out.println("Datos incorrectos, pruebe otra vez");
                                    }
                                    System.out.println("-------------------------------------------------------");
                                    recorrer = false;
                                }
                                break;
                            case "3":
                                System.out.println("Introduce el nombre de tu contacto:");
                                String str_buscar = sc.nextLine();
                                 {
                                    try {
                                        System.out.println(Contactos.buscar(str_buscar));
                                    } catch (ExistenciaException ex) {
                                        System.out.println(ex);
                                    } catch (Exception ex) {
                                        System.out.println("Error: "+ex);
                                    }
                                }
                                break;
                            case "4":
                                System.out.println("Introduce el nombre de tu contacto:");
                                String str_borrar = sc.nextLine();
                                 {
                                    try {
                                        Contactos.borrar(str_borrar);
                                        System.out.println("Se ha borrado el contacto " + str_borrar);
                                    } catch (ExistenciaException ex) {
                                        System.out.println(ex);
                                    } catch (Exception ex) {
                                        System.out.println("Error: "+ex);
                                    }
                                }
                                break;
                            case "5":
                                Boolean fun2 = true;
                                Boolean amigo2;
                                while (fun2) {
                                    System.out.println("¿(A)migo o (P)rofesional?");
                                    String strAmigo = sc.nextLine().toUpperCase();
                                    if (strAmigo.equals("P") || strAmigo.equals("A")) {
                                        System.out.println("Introduce su nombre:");
                                        String nombre = sc.nextLine();
                                        System.out.println("Ahora su nuevo numero (si no quieres cambiar esto, dale al intro):");
                                        String numero = sc.nextLine();
                                        switch (strAmigo) {
                                            case "A":
                                                amigo2 = true;
                                                System.out.println("Introduce su nueva fecha de nacimiento (dd/mm/yyyy)");
                                                extra = sc.nextLine();
                                                try {
                                                    Contactos.editar(nombre, numero, extra, null, amigo2);
                                                    System.out.println("Contacto modificado correctamente");
                                                } catch (ParseException ex) {
                                                    System.out.println("La fecha introducida no es válida: " + ex);
                                                } catch (NumberFormatException ex) {
                                                    System.out.println("El numero introducido no es válido: " + ex);
                                                } catch (Exception ex) {
                                                    System.out.println(ex);
                                                }
                                                break;
                                            case "P":
                                                amigo2 = false;
                                                System.out.println("Introduce el nuevo comentario o nombre de la empresa:");
                                                extra = sc.nextLine();
                                                 {
                                                    try {
                                                        Contactos.editar(nombre, numero, null, extra, amigo2);
                                                    } catch (ExistenciaException ex) {
                                                        System.out.println(ex);
                                                    } catch (ClassCastException ex) {
                                                        System.out.println("El usuario que has buscado no es del tipo indicado, el error es el siguiente: " + ex);
                                                    } catch (Exception ex) {
                                                        System.out.println(ex.getMessage());
                                                    }
                                                }
                                                break;
                                            default:
                                                System.out.println("Introduce un dato válido por favor");
                                                break;
                                        }
                                        System.out.println("-------------------------------------------------------");
                                        fun2 = false;
                                    } else {
                                        System.out.println("Introduce un dato válido por favor");
                                    }
                                }
                                break;
                            case "6":
                                System.out.println("¿Quieres listar por (F)echa o por órden (A)lfabético?");
                                String dec1 = sc.nextLine().toUpperCase();
                                System.out.println("¿Que nombre quieres que tenga?");
                                String nombreFichero = sc.nextLine();
                                String directorio2 = "C:\\Programacion\\netbeans\\proyectos\\Agenda3\\ficherosAgenda\\" + nombreFichero;
                                try {
                                    Contactos.listarExterno(directorio2, dec1);
                                } catch (IOException ex) {
                                    System.out.println("El archivo no se ha encontrado: " + ex);
                                } catch (Exception ex) {
                                    System.out.println("Error: " + ex.getMessage());
                                }
                                System.out.println("Contactos listados correctamente en el directorio " + directorio2);
                                break;
                            case "7":
                                System.out.println("¿Que nombre tiene el fichero a importar?");
                                nombreFichero = sc.nextLine();
                                directorio2 = "C:\\Programacion\\netbeans\\proyectos\\Agenda3\\ficherosAgenda\\" + nombreFichero;
                                try {
                                    Contactos.importar(directorio2);
                                } catch (CargaErrores ex) {
                                    System.err.println("Los siguientes contactos no se han podido cargar:");
                                    System.err.println(ex);
                                } catch (IOException ex) {
                                    System.err.println("El archivo no se ha encontrado: " + ex);
                                } catch (ExistenciaException ex) {
                                    System.err.println("El contacto ya existe: " + ex);
                                } catch (Exception ex) {
                                    System.err.println("Error: " + ex);
                                }
                                break;
                            case "X": {
                                try {
                                    Contactos.guardarAgenda(directorio);
                                } catch (IOException ex) {
                                    System.out.println("Ha surgido el siguiente error: " + ex);
                                }
                            }
                            System.out.println("Cerrando agenda...");
                            ejecutando = false;
                            ejecutandoEspecial = false;
                            break;
                            default:
                                System.out.println("Opción incorrecta, prueba con un número del 1 al 7, por favor");
                                break;
                        }
                        break;
                    case "X":
                        System.out.println("Cerrando agenda...");
                        ejecutando = false;
                        ejecutandoEspecial = false;
                        break;
                    default:
                        System.out.println("Opción incorrecta, escribe 1 o X, por favor");
                        ejecutando = false;
                        ejecutandoEspecial = false;
                        break;
                }
            }
        }
    }
}
