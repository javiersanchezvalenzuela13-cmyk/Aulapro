import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import DAO.*;
import DTO.*;
import enumeracion.estadoCurso;

public class main {

    private static Connection con;

    public static void main(String[] args) {

        try {
            con = conexion.getConnection();
            System.out.println("Conexión establecida correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al conectar con la BD: " + e.getMessage());
            return;
        }

        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MENÚ PRINCIPAL AULAPRO ---");
            System.out.println("1. Gestión Alumnos");
            System.out.println("2. Gestión Profesores");
            System.out.println("3. Gestión Categorías");
            System.out.println("4. Gestión Cursos");
            System.out.println("5. Gestión Matrículas");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = sc.nextInt(); 
            sc.nextLine();

            switch (opcion) {
                case 1 -> menuAlumnos();
                case 2 -> menuProfesores();
                case 3 -> menuCategorias();
                case 4 -> menuCursos();
                case 5 -> menuMatriculas();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    // ------------------ ALUMNOS ------------------
    private static void menuAlumnos() {
        Scanner sc = new Scanner(System.in);
        DaoAlumnos dao = new DaoAlumnos(con);
        DaoMatricula daoM = new DaoMatricula(con);
        int op;

        do {
            System.out.println("\n--- GESTIÓN ALUMNOS ---");
            System.out.println("1. Insertar");
            System.out.println("2. Modificar");
            System.out.println("3. Eliminar");
            System.out.println("4. Buscar por DNI");
            System.out.println("5. Listar todos");
            System.out.println("6. Historial matrículas");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            op = sc.nextInt(); 
            sc.nextLine();

            try {
                switch (op) {
                    case 1 -> {
                        Alumno a = new Alumno();
                        System.out.print("DNI: "); 
                        a.setDNI_alumno(sc.nextLine());
                        System.out.print("Nombre: "); 
                        a.setNombreAlumno(sc.nextLine());
                        System.out.print("Fecha alta (YYYY-MM-DD): "); 
                        a.setFechaAlta(java.sql.Date.valueOf(sc.nextLine()));
                        System.out.print("Dirección: "); 
                        a.setDireccion(sc.nextLine());
                        System.out.print("Correo: "); 
                        a.setCorreo(sc.nextLine());
                        System.out.print("Teléfono: "); 
                        a.setTelefono(sc.nextLine());
                        dao.insertar(a);
                    }
                    case 2 -> {
                        System.out.print("DNI a modificar: ");
                        Alumno mod = dao.obtenerPorDNI(sc.nextLine());
                        if (mod != null) {
                            System.out.print("Nuevo nombre: "); 
                            mod.setNombreAlumno(sc.nextLine());
                            System.out.print("Nueva dirección: "); 
                            mod.setDireccion(sc.nextLine());
                            System.out.print("Nuevo correo: "); 
                            mod.setCorreo(sc.nextLine());
                            System.out.print("Nuevo teléfono: "); 
                            mod.setTelefono(sc.nextLine());
                            dao.modificar(mod);
                        } else {
                            System.out.println("Alumno no encontrado.");
                        }
                    }
                    case 3 -> {
                        System.out.print("DNI a eliminar: ");
                        dao.eliminarPorDNI(sc.nextLine());
                    }
                    case 4 -> {
                        System.out.print("DNI: ");
                        Alumno a = dao.obtenerPorDNI(sc.nextLine());
                        System.out.println(a != null ? a : "No encontrado.");
                    }
                    case 5 -> {
                        List<Alumno> lista = dao.obtenerTodos();
                        Iterator<Alumno> it = lista.iterator();
                        while (it.hasNext()) {
                            System.out.println(it.next());
                        }
                    }
                    case 6 -> {
                        System.out.print("DNI del alumno: ");
                        List<Matricula> lista = daoM.obtenerPorAlumno(sc.nextLine());
                        if (lista.isEmpty()) {
                            System.out.println("Sin matrículas.");
                        } else {
                            Iterator<Matricula> it = lista.iterator();
                            while (it.hasNext()) {
                                System.out.println(it.next());
                            }
                        }
                    }
                }
            } catch (Exception e) { 
                System.out.println("Error: " + e.getMessage()); 
            }

        } while (op != 0);
    }

    // ------------------ PROFESORES ------------------
    private static void menuProfesores() {
        Scanner sc = new Scanner(System.in);
        DaoProfesores dao = new DaoProfesores(con);
        DaoMatricula daoM = new DaoMatricula(con);
        DaoCursos daoC = new DaoCursos(con);
        int op;

        do {
            System.out.println("\n--- GESTIÓN PROFESORES ---");
            System.out.println("1. Insertar");
            System.out.println("2. Modificar");
            System.out.println("3. Eliminar");
            System.out.println("4. Buscar por DNI");
            System.out.println("5. Listar todos");
            System.out.println("6. Cursos del profesor");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            op = sc.nextInt(); 
            sc.nextLine();

            try {
                switch (op) {
                    case 1 -> {
                        Profesor p = new Profesor();
                        System.out.print("DNI: "); 
                        p.setDNI_profesor(sc.nextLine());
                        System.out.print("Nombre: "); 
                        p.setNombreProfesor(sc.nextLine());
                        System.out.print("Especialidad: "); 
                        p.setEspecialidad(sc.nextLine());
                        System.out.print("Titulación: "); 
                        p.setTitulacion(sc.nextLine());
                        System.out.print("Años exp: "); 
                        p.setAniosExp(sc.nextInt()); 
                        sc.nextLine();
                        System.out.print("Fecha incorporación: "); 
                        p.setFechaIncorp(java.sql.Date.valueOf(sc.nextLine()));
                        dao.insertar(p);
                    }
                    case 2 -> {
                        System.out.print("DNI a modificar: ");
                        Profesor p = dao.obtenerPorDNI(sc.nextLine());
                        if (p != null) {
                            System.out.print("Nuevo nombre: "); 
                            p.setNombreProfesor(sc.nextLine());
                            System.out.print("Nueva especialidad: "); 
                            p.setEspecialidad(sc.nextLine());
                            System.out.print("Nueva titulación: "); 
                            p.setTitulacion(sc.nextLine());
                            System.out.print("Años exp: "); 
                            p.setAniosExp(sc.nextInt()); 
                            sc.nextLine();
                            System.out.print("Nueva fecha incorporación: "); 
                            p.setFechaIncorp(java.sql.Date.valueOf(sc.nextLine()));
                            dao.modificar(p);
                        } else {
                            System.out.println("Profesor no encontrado.");
                        }
                    }
                    case 3 -> {
                        System.out.print("DNI a eliminar: ");
                        dao.eliminarPorDNI(sc.nextLine());
                    }
                    case 4 -> {
                        System.out.print("DNI: ");
                        Profesor p = dao.obtenerPorDNI(sc.nextLine());
                        System.out.println(p != null ? p : "No encontrado.");
                    }
                    case 5 -> {
                        List<Profesor> lista = dao.obtenerTodos();
                        Iterator<Profesor> it = lista.iterator();
                        while (it.hasNext()) {
                            System.out.println(it.next());
                        }
                    }
                    case 6 -> {
                        System.out.print("DNI profesor: ");
                        List<Integer> ids = daoM.obtenerCursosPorProfesor(sc.nextLine());
                        if (ids.isEmpty()) {
                            System.out.println("Sin cursos.");
                        } else {
                            Iterator<Integer> it = ids.iterator();
                            while (it.hasNext()) {
                                System.out.println(daoC.obtenerPorId(it.next()));
                            }
                        }
                    }
                }
            } catch (Exception e) { 
                System.out.println("Error: " + e.getMessage()); 
            }

        } while (op != 0);
    }

    // ------------------ CATEGORÍAS ------------------
    private static void menuCategorias() {
        Scanner sc = new Scanner(System.in);
        DaoCategoria dao = new DaoCategoria(con);
        int op;

        do {
            System.out.println("\n--- GESTIÓN CATEGORÍAS ---");
            System.out.println("1. Insertar");
            System.out.println("2. Modificar");
            System.out.println("3. Eliminar");
            System.out.println("4. Buscar por ID");
            System.out.println("5. Listar todas");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            op = sc.nextInt(); 
            sc.nextLine();

            try {
                switch (op) {
                    case 1 -> {
                        Categoria c = new Categoria();
                        System.out.print("ID: "); 
                        c.setIdCategoria(sc.nextInt()); 
                        sc.nextLine();
                        System.out.print("Nombre: "); 
                        c.setNombreCategoria(sc.nextLine());
                        System.out.print("Descripción: "); 
                        c.setDescripcion(sc.nextLine());
                        dao.insertar(c);
                    }
                    case 2 -> {
                        System.out.print("ID a modificar: ");
                        Categoria c = dao.obtenerPorId(sc.nextInt()); 
                        sc.nextLine();
                        if (c != null) {
                            System.out.print("Nuevo nombre: "); 
                            c.setNombreCategoria(sc.nextLine());
                            System.out.print("Nueva descripción: "); 
                            c.setDescripcion(sc.nextLine());
                            dao.modificar(c);
                        } else {
                            System.out.println("No encontrada.");
                        }
                    }
                    case 3 -> {
                        System.out.print("ID a eliminar: ");
                        dao.eliminar(sc.nextInt());
                    }
                    case 4 -> {
                        System.out.print("ID: ");
                        Categoria c = dao.obtenerPorId(sc.nextInt());
                        System.out.println(c != null ? c : "No encontrada.");
                    }
                    case 5 -> {
                        List<Categoria> lista = dao.obtenerTodos();
                        Iterator<Categoria> it = lista.iterator();
                        while (it.hasNext()) {
                            System.out.println(it.next());
                        }
                    }
                }
            } catch (Exception e) { 
                System.out.println("Error: " + e.getMessage()); 
            }

        } while (op != 0);
    }

    // ------------------ CURSOS ------------------
    private static void menuCursos() {
        Scanner sc = new Scanner(System.in);
        DaoCursos dao = new DaoCursos(con);
        int op;

        do {
            System.out.println("\n--- GESTIÓN CURSOS ---");
            System.out.println("1. Insertar");
            System.out.println("2. Modificar");
            System.out.println("3. Eliminar");
            System.out.println("4. Buscar por ID");
            System.out.println("5. Listar todos");
            System.out.println("6. Listar por categoría");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            op = sc.nextInt(); 
            sc.nextLine();

            try {
                switch (op) {
                    case 1 -> {
                        Curso c = new Curso();
                        System.out.print("ID: "); 
                        c.setIdCurso(sc.nextInt()); 
                        sc.nextLine();
                        System.out.print("Plazas: "); 
                        c.setPlazasMax(sc.nextInt()); 
                        sc.nextLine();
                        System.out.print("Precio: "); 
                        c.setPrecio(sc.nextDouble()); 
                        sc.nextLine();
                        System.out.print("Estado (Abierto/Completo/Finalizado/Cancelado): ");
                        c.setEstado(estadoCurso.valueOf(sc.nextLine()));
                        System.out.print("Nombre: "); 
                        c.setNombreCurso(sc.nextLine());
                        System.out.print("Horario: "); 
                        c.setHorario(sc.nextLine());
                        System.out.print("Duración: "); 
                        c.setDuracion(sc.nextLine());
                        System.out.print("Nivel: "); 
                        c.setNivel(sc.nextLine());
                        System.out.print("ID Categoría: "); 
                        c.setIdCategoria(sc.nextInt());
                        dao.insertar(c);
                    }
                    case 2 -> {
                        System.out.print("ID a modificar: ");
                        Curso c = dao.obtenerPorId(sc.nextInt()); 
                        sc.nextLine();
                        if (c != null) {
                            System.out.print("Nuevo nombre: "); 
                            c.setNombreCurso(sc.nextLine());
                            System.out.print("Nuevo horario: "); 
                            c.setHorario(sc.nextLine());
                            System.out.print("Nueva duración: "); 
                            c.setDuracion(sc.nextLine());
                            System.out.print("Nuevo nivel: "); 
                            c.setNivel(sc.nextLine());
                            dao.modificar(c);
                        } else {
                            System.out.println("No encontrado.");
                        }
                    }
                    case 3 -> {
                        System.out.print("ID a eliminar: ");
                        dao.eliminar(sc.nextInt());
                    }
                    case 4 -> {
                        System.out.print("ID: ");
                        Curso c = dao.obtenerPorId(sc.nextInt());
                        System.out.println(c != null ? c : "No encontrado.");
                    }
                    case 5 -> {
                        List<Curso> lista = dao.obtenerTodos();
                        Iterator<Curso> it = lista.iterator();
                        while (it.hasNext()) {
                            System.out.println(it.next());
                        }
                    }
                    case 6 -> {
                        System.out.print("ID categoría: ");
                        List<Curso> lista = dao.obtenerPorCategoria(sc.nextInt());
                        if (lista.isEmpty()) {
                            System.out.println("Sin cursos.");
                        } else {
                            Iterator<Curso> it = lista.iterator();
                            while (it.hasNext()) {
                                System.out.println(it.next());
                            }
                        }
                    }
                }
            } catch (Exception e) { 
                System.out.println("Error: " + e.getMessage()); 
            }

        } while (op != 0);
    }

    // ------------------ MATRÍCULAS ------------------
    private static void menuMatriculas() {
        Scanner sc = new Scanner(System.in);
        DaoMatricula dao = new DaoMatricula(con);
        int op;

        do {
            System.out.println("\n--- GESTIÓN MATRÍCULAS ---");
            System.out.println("1. Insertar");
            System.out.println("2. Modificar");
            System.out.println("3. Eliminar");
            System.out.println("4. Buscar por ID");
            System.out.println("5. Listar todas");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            op = sc.nextInt(); 
            sc.nextLine();

            try {
                switch (op) {
                    case 1 -> {
                        Matricula m = new Matricula();
                        System.out.print("Importe: "); 
                        m.setImporteAbonado(sc.nextDouble()); 
                        sc.nextLine();
                        System.out.print("Fecha: "); 
                        m.setFechaMatricula(java.sql.Date.valueOf(sc.nextLine()));
                        System.out.print("Estado: "); 
                        m.setEstadoMatricula(sc.nextLine());
                        System.out.print("DNI alumno: "); 
                        m.setDNI_alumno(sc.nextLine());
                        System.out.print("DNI profesor: "); 
                        m.setDNI_profesor(sc.nextLine());
                        System.out.print("ID curso: "); 
                        m.setIdCurso(sc.nextInt());
                        dao.insertar(m);
                    }
                    case 2 -> {
                        System.out.print("ID matrícula: ");
                        Matricula m = dao.obtenerPorId(sc.nextInt()); 
                        sc.nextLine();
                        if (m != null) {
                            System.out.print("Nuevo estado: "); 
                            m.setEstadoMatricula(sc.nextLine());
                            dao.modificar(m);
                        } else {
                            System.out.println("No encontrada.");
                        }
                    }
                    case 3 -> {
                        System.out.print("ID a eliminar: ");
                        dao.eliminar(sc.nextInt());
                    }
                    case 4 -> {
                        System.out.print("ID: ");
                        Matricula m = dao.obtenerPorId(sc.nextInt());
                        System.out.println(m != null ? m : "No encontrada.");
                    }
                    case 5 -> {
                        List<Matricula> lista = dao.obtenerTodos();
                        // ORDENAR POR FECHA DE MATRÍCULA (más antigua → más reciente) COMPARABLE
                        lista.sort((m1, m2) -> m1.getFechaMatricula().compareTo(m2.getFechaMatricula()));
                        Iterator<Matricula> it = lista.iterator();
                        while (it.hasNext()) {
                            System.out.println(it.next());
                        }
                    }
                }
            } catch (Exception e) { 
                System.out.println("Error: " + e.getMessage()); 
            }

        } while (op != 0);
    }
}