package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DTO.Profesor;
import excepciones.DatosException;

public class DaoProfesores implements CRUD<Profesor> {

    private Connection con;

    public DaoProfesores(Connection con) {
        this.con = con;
    }

    // Método que valida que los campos del profesor tengan un formato correcto
    private void validar(Profesor p) throws DatosException {
        if (p.getDNI_profesor() == null || p.getDNI_profesor().isEmpty())
            throw new DatosException("DNI vacío");
        if (!p.getDNI_profesor().matches("\\d{8}[A-Z]"))
            throw new DatosException("Formato de DNI inválido (debe ser 8 números + letra mayúscula)");
        if (p.getNombreProfesor() == null || p.getNombreProfesor().isEmpty())
            throw new DatosException("Nombre vacío");
        if (p.getEspecialidad() == null || p.getEspecialidad().isEmpty())
            throw new DatosException("Especialidad vacía");
        if (p.getTitulacion() == null || p.getTitulacion().isEmpty())
            throw new DatosException("Titulación vacía");
        if (p.getAniosExp() < 0)
            throw new DatosException("Años de experiencia inválidos");
        if (p.getFechaIncorp() == null)
            throw new DatosException("Fecha de incorporación nula");
    }

    // Método que comprueba si el profesor ya existe en la base de datos mediante su DNI
    private boolean existe(String dni) throws Exception {
        String sql = "SELECT DNI_profesor FROM PROFESOR WHERE DNI_profesor=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, dni);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    // Método que elimina un profesor de la base de datos filtrando por su DNI
    public boolean eliminarPorDNI(String dni) throws Exception {
        if (!existe(dni))
            throw new DatosException("El profesor no existe");

        String sql = "DELETE FROM PROFESOR WHERE DNI_profesor=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, dni);
        return ps.executeUpdate() > 0;
    }

    // Método que obtiene un profesor específico filtrando por su DNI
    public Profesor obtenerPorDNI(String dni) throws Exception {
        String sql = "SELECT * FROM PROFESOR WHERE DNI_profesor=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, dni);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Profesor p = new Profesor();
            p.setDNI_profesor(rs.getString("DNI_profesor"));
            p.setNombreProfesor(rs.getString("nombreProfesor"));
            p.setEspecialidad(rs.getString("especialidad"));
            p.setTitulacion(rs.getString("titulacion"));
            p.setAniosExp(rs.getInt("aniosExp"));
            p.setFechaIncorp(rs.getDate("fechaIncorp"));
            return p;
        }
        return null;
    }

    // Método que inserta un nuevo alumno, antes comprueba si sus datos son válidos y si ya existe el alumno
    @Override
    public boolean insertar(Profesor p) throws Exception {
        validar(p);

        if (existe(p.getDNI_profesor()))
            throw new DatosException("El profesor ya existe");

        String sql = "INSERT INTO PROFESOR (DNI_profesor, nombreProfesor, especialidad, titulacion, aniosExp, fechaIncorp) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, p.getDNI_profesor());
        ps.setString(2, p.getNombreProfesor());
        ps.setString(3, p.getEspecialidad());
        ps.setString(4, p.getTitulacion());
        ps.setInt(5, p.getAniosExp());
        ps.setDate(6, p.getFechaIncorp());

        return ps.executeUpdate() > 0;
    }

    // Método que modifica los datos de un alumno, valida los datos y si existe en la base de datos 
    @Override
    public boolean modificar(Profesor p) throws Exception {
        validar(p);

        if (!existe(p.getDNI_profesor()))
            throw new DatosException("El profesor no existe");

        String sql = "UPDATE PROFESOR SET nombreProfesor=?, especialidad=?, titulacion=?, aniosExp=?, fechaIncorp=? WHERE DNI_profesor=?";
        PreparedStatement ps = con.prepareStatement(sql);
        
        ps.setString(1, p.getNombreProfesor());
        ps.setString(2, p.getEspecialidad());
        ps.setString(3, p.getTitulacion());
        ps.setInt(4, p.getAniosExp());
        ps.setDate(5, p.getFechaIncorp());
        ps.setString(6, p.getDNI_profesor());

        return ps.executeUpdate() > 0;
    }

    // No se usa porque Alumno no tiene id, se gestiona mediante DNI
    @Override
    public boolean eliminar(int id) throws Exception {
        throw new UnsupportedOperationException("Usa eliminarPorDNI()");
    }

    // No se usa porque la entidad Alumno no tiene id, se gestiona mediante DNI
    @Override
    public Profesor obtenerPorId(int id) throws Exception {
        throw new UnsupportedOperationException("Usa obtenerPorDNI()");
    }

    // Método que devuelve una lista con todos los alumnos registrados en la base de datos
    @Override
    public List<Profesor> obtenerTodos() throws Exception {
        List<Profesor> lista = new ArrayList<>();

        String sql = "SELECT * FROM PROFESOR";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Profesor p = new Profesor();
            p.setDNI_profesor(rs.getString("DNI_profesor"));
            p.setNombreProfesor(rs.getString("nombreProfesor"));
            p.setEspecialidad(rs.getString("especialidad"));
            p.setTitulacion(rs.getString("titulacion"));
            p.setAniosExp(rs.getInt("aniosExp"));
            p.setFechaIncorp(rs.getDate("fechaIncorp"));
            lista.add(p);
        }

        return lista;
    }
}