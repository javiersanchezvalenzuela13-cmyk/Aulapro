package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DTO.Alumno;
import excepciones.DatosException;

public class DaoAlumnos implements CRUD<Alumno> {

    private Connection con;

    public DaoAlumnos(Connection con) {
        this.con = con;
    }

    // Método que valida que los campos del alumno tengan un formato correcto
    private void validarAlumno(Alumno a) throws DatosException {
        if (a.getDNI_alumno() == null || a.getDNI_alumno().isEmpty())
            throw new DatosException("DNI vacío");
        if (!a.getDNI_alumno().matches("\\d{8}[A-Z]"))
            throw new DatosException("Formato de DNI inválido (debe ser 8 números + letra mayúscula)");
        if (a.getNombreAlumno() == null || a.getNombreAlumno().isEmpty())
            throw new DatosException("Nombre vacío");
        if (a.getFechaAlta() == null)
            throw new DatosException("Fecha alta nula");
    }

    // Método que comprueba si el alumno ya existe en la base de datos mediante su DNI
    private boolean existe(String dni) throws Exception {
        String sql = "SELECT DNI_alumno FROM ALUMNO WHERE DNI_alumno=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, dni);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    // Método que elimina un alumno de la base de datos filtrando por su DNI
    public boolean eliminarPorDNI(String dni) throws Exception {
        if (dni == null || dni.isEmpty())
            throw new DatosException("DNI vacío");

        String sql = "DELETE FROM ALUMNO WHERE DNI_alumno=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, dni);
        return ps.executeUpdate() > 0;
    }

    // Método que obtiene un alumno específico filtrando por su DNI
    public Alumno obtenerPorDNI(String dni) throws Exception {
        if (dni == null || dni.isEmpty())
            throw new DatosException("DNI vacío");
        
        String sql = "SELECT * FROM ALUMNO WHERE DNI_alumno=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, dni);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Alumno a = new Alumno();
            a.setDNI_alumno(rs.getString("DNI_alumno"));
            a.setNombreAlumno(rs.getString("nombreAlumno"));
            a.setFechaAlta(rs.getDate("fechaAlta"));
            a.setDireccion(rs.getString("direccion"));
            a.setCorreo(rs.getString("correo"));
            a.setTelefono(rs.getString("telefono"));
            return a;
        }
        return null;
    }

    // Método que inserta un nuevo alumno, antes comprueba si sus datos son válidos y si ya existe el alumno
    @Override
    public boolean insertar(Alumno a) throws Exception {
        validarAlumno(a);

        if (existe(a.getDNI_alumno()))
            throw new DatosException("El alumno ya existe");

        String sql = "INSERT INTO ALUMNO (DNI_alumno, nombreAlumno, fechaAlta, direccion, correo, telefono) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, a.getDNI_alumno());
        ps.setString(2, a.getNombreAlumno());
        ps.setDate(3, a.getFechaAlta());
        ps.setString(4, a.getDireccion());
        ps.setString(5, a.getCorreo());
        ps.setString(6, a.getTelefono());

        return ps.executeUpdate() > 0;
    }

    // Método que modifica los datos de un alumno, valida los datos y si existe en la base de datos 
    @Override
    public boolean modificar(Alumno a) throws Exception {
        validarAlumno(a);

        if (!existe(a.getDNI_alumno()))
            throw new DatosException("El alumno no existe");

        String sql = "UPDATE ALUMNO SET nombreAlumno=?, fechaAlta=?, direccion=?, correo=?, telefono=? WHERE DNI_alumno=?";
        PreparedStatement ps = con.prepareStatement(sql);
        
        ps.setString(1, a.getNombreAlumno());
        ps.setDate(2, a.getFechaAlta());
        ps.setString(3, a.getDireccion());
        ps.setString(4, a.getCorreo());
        ps.setString(5, a.getTelefono());
        ps.setString(6, a.getDNI_alumno());

        return ps.executeUpdate() > 0;
    }

    // No se usa porque Alumno no tiene id, se gestiona mediante DNI
    @Override
    public boolean eliminar(int id) throws Exception {
        throw new UnsupportedOperationException("Usa eliminarPorDNI()");
    }

    // No se usa porque la entidad Alumno no tiene id, se gestiona mediante DNI
    @Override
    public Alumno obtenerPorId(int id) throws Exception {
        throw new UnsupportedOperationException("Usa obtenerPorDNI()");
    }

    // Método que devuelve una lista con todos los alumnos registrados en la base de datos
    @Override
    public List<Alumno> obtenerTodos() throws Exception {
        List<Alumno> lista = new ArrayList<>();

        String sql = "SELECT * FROM ALUMNO";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Alumno a = new Alumno();
            a.setDNI_alumno(rs.getString("DNI_alumno"));
            a.setNombreAlumno(rs.getString("nombreAlumno"));
            a.setFechaAlta(rs.getDate("fechaAlta"));
            a.setDireccion(rs.getString("direccion"));
            a.setCorreo(rs.getString("correo"));
            a.setTelefono(rs.getString("telefono"));
            lista.add(a);
        }

        return lista;
    }
}