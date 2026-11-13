import javax.swing.*;
import java.util.ArrayList;

public class ProfesoresEstudiantes {

    ArrayList<String> listaProfesores;
    ArrayList<String> listEstudiantes;
    ArrayList<ArrayList<String>> listaGeneralEstudiantes;

    public ProfesoresEstudiantes(){

        listaProfesores = new ArrayList<>();
        listaGeneralEstudiantes = new ArrayList<>();

    }

    public void iniciar (){

        String menu = "===== MENÚ PRINCIPAL =====\n"
                + "1. Registrar profesores\n"
                + "2. Registrar estudiantes asociados a un profesor\n"
                + "3. Consultar lista total de profesores y sus estudiantes\n"
                + "4. Consultar un profesor e imprimir sus estudiantes\n"
                + "5. Consultar un profesor e indicar la cantidad de estudiantes\n"
                + "6. Consultar un estudiante e indicar su director de grupo\n"
                + "0. Salir\n\n"
                + "Elija una opción:";


        int opcion = -1;
        while (opcion != 0) {
            String entrada = JOptionPane.showInputDialog(menu);



            if (entrada == null) {
                opcion = 0;
                continue;
            }

            try {
                opcion = Integer.parseInt(entrada.trim());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    registrarProfesores();
                    break;
                case 2:
                    if (validarProfesoresRegistrados()) {
                        registrarEstudiantes();
                    }
                    break;
                case 3:
                    if (validarProfesoresRegistrados() && validarEstudiantesRegistrados()) {
                        consultarListaProfesoresYEstudiantes();
                    }
                    break;
                case 4:
                    if (validarProfesoresRegistrados() && validarEstudiantesRegistrados()) {
                        consultaProfesorPorNombre();
                    }
                    break;
                case 5:
                    if (validarProfesoresRegistrados() && validarEstudiantesRegistrados()) {
                        consultaCantidadEstudiantesPorProfesor();
                    }
                    break;
                case 6:
                    if (validarProfesoresRegistrados() && validarEstudiantesRegistrados()) {
                        consultarEstudiante();
                    }
                    break;
                case 0:
                    System.out.println("\n  Hasta pronto! ");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida, intente de nuevo.");
            }
        }
    }


    private boolean validarProfesoresRegistrados() {
        if (listaProfesores.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Primero debe registrar profesores (opción 1).");
            return false;
        }
        return true;
    }

    private boolean validarEstudiantesRegistrados() {
        for (int i = 0; i < listaGeneralEstudiantes.size(); i++) {
            if (!listaGeneralEstudiantes.get(i).isEmpty()) {
                return true;
            }
        }
        JOptionPane.showMessageDialog(null, "Primero debe registrar estudiantes (opción 2).");
        return false;
    }


    private int pedirEntero(String mensaje) {
        String entrada = JOptionPane.showInputDialog(mensaje);
        if (entrada == null) {
            return -1;
        }
        try {
            int valor = Integer.parseInt(entrada.trim());
            return valor >= 0 ? valor : -1;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un número entero válido.");
            return -1;
        }
    }


    private void registrarProfesores() {
        System.out.println("\n<<<< Registro de Profesores >>>>");
        int cant = pedirEntero("Ingrese la cantidad de profesores a registrar");

        for (int i = 0; i < cant; i++) {
            String nombreProfesor = JOptionPane.showInputDialog("Nombre profesor " + (i + 1));
            if (nombreProfesor == null) {
                break;
            }
            nombreProfesor = nombreProfesor.trim();

            if (nombreProfesor.isEmpty()) {
                JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío.");
                i--;

            } else if (listaProfesores.contains(nombreProfesor)) {
                JOptionPane.showMessageDialog(null, "El profesor " + nombreProfesor + " ya está registrado. Ingrese otro nombre.");
                i--;
            } else {
                listaProfesores.add(nombreProfesor);

                listaGeneralEstudiantes.add(new ArrayList<String>());
            }
        }
        System.out.println("Registro de profesores Exitoso!\n");
    }


    private void registrarEstudiantes() {
        System.out.println("\n<<<< Registro de Estudiantes >>>>");
        String nombreProfesor = JOptionPane.showInputDialog("Ingrese el nombre del profesor al que le registrará estudiantes");
        if (nombreProfesor == null) {
            return;
        }
        nombreProfesor = nombreProfesor.trim();

        if (!listaProfesores.contains(nombreProfesor)) {
            JOptionPane.showMessageDialog(null, "No se encuentra el profesor " + nombreProfesor);
            return;
        }

        int posicion = listaProfesores.indexOf(nombreProfesor);
        listEstudiantes = listaGeneralEstudiantes.get(posicion);

        int cant = pedirEntero("Cantidad de estudiantes para el profesor " + nombreProfesor);

        for (int j = 0; j < cant; j++) {
            String nombreEst = JOptionPane.showInputDialog("Nombre estudiante " + (j + 1));
            if (nombreEst == null) {
                break; // el usuario canceló
            }
            nombreEst = nombreEst.trim();

            if (nombreEst.isEmpty()) {
                JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío.");
                j--;
            } else if (estudianteYaRegistrado(nombreEst)) {
                JOptionPane.showMessageDialog(null,
                        "El estudiante " + nombreEst + " ya está registrado. Ingrese otro nombre.");
                j--;
            } else {
                listEstudiantes.add(nombreEst);
            }
        }
        System.out.println("Registro de estudiantes Exitoso!\n");
    }

    private boolean estudianteYaRegistrado(String nombre) {
        for (int i = 0; i < listaGeneralEstudiantes.size(); i++) {
            if (listaGeneralEstudiantes.get(i).contains(nombre)) {
                return true;
            }
        }
        return false;
    }

    private void consultarListaProfesoresYEstudiantes() {
        System.out.println("\n<<<< Lista de Profesores y Estudiantes >>>>");
        ArrayList<String> listaTemporal;

        for (int i = 0; i < listaGeneralEstudiantes.size(); i++) {
            listaTemporal = listaGeneralEstudiantes.get(i);
            System.out.print("Profesor: " + listaProfesores.get(i) + " = ");
            imprimirLista(listaTemporal);
        }
    }


    private void imprimirLista(ArrayList<String> lista) {
        System.out.print("[");
        for (int j = 0; j < lista.size(); j++) {
            System.out.print(lista.get(j));
            if (j < lista.size() - 1) {
                System.out.print(",");
            }
        }
        System.out.print("]\n");
    }

    private void consultaProfesorPorNombre() {
        System.out.println("\n<<<< Consulta de Profesor >>>> \n");
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del profesor");
        if (nombre == null) {
            return;
        }
        nombre = nombre.trim();

        if (listaProfesores.contains(nombre)) {
            int posicion = listaProfesores.indexOf(nombre);
            ArrayList<String> listaEstudiantesTemporal = listaGeneralEstudiantes.get(posicion);

            System.out.print("Profesor: " + nombre + " = ");
            imprimirLista(listaEstudiantesTemporal);
        } else {
            System.out.println("No se encuentra el profesor " + nombre + "\n");
        }
    }


    private void consultaCantidadEstudiantesPorProfesor() {
        System.out.println("\n<<<< Consulta cantidad de estudiantes de Profesores >>>> \n");
        String nombre = JOptionPane.showInputDialog(
                "Ingrese el nombre del profesor para saber la cantidad de estudiantes asociados");
        if (nombre == null) {
            return;
        }
        nombre = nombre.trim();

        if (listaProfesores.contains(nombre)) {
            int posicion = listaProfesores.indexOf(nombre);
            ArrayList<String> listaEstudiantesTemporal = listaGeneralEstudiantes.get(posicion);
            System.out.println("La cantidad de estudiantes asociados al profesor " + nombre
                    + " es " + listaEstudiantesTemporal.size());
        } else {
            System.out.println("No se encuentra el profesor " + nombre + "\n");
        }
    }

    private void consultarEstudiante() {
        System.out.println("\n<<<< Consulta de Estudiante >>>> \n");
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del estudiante a consultar");
        if (nombre == null) {
            return;
        }
        nombre = nombre.trim();

        boolean encontrado = false;
        ArrayList<String> listTemporal;

        for (int i = 0; i < listaGeneralEstudiantes.size(); i++) {
            listTemporal = listaGeneralEstudiantes.get(i);
            for (int j = 0; j < listTemporal.size(); j++) {
                if (listTemporal.get(j).equalsIgnoreCase(nombre)) {
                    System.out.println("Se encuentra al estudiante: " + listTemporal.get(j)
                            + " en el grupo de " + listaProfesores.get(i));
                    encontrado = true;
                }
            }
        }

        if (!encontrado) {
            System.out.println("No se encuentra el estudiante " + nombre + " en ningún grupo.");
        }
    }

}