package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import DTO.Matricula;
import excepciones.DatosException;
import excepciones.MatriculaException;

public class DaoMatricula implements CRUD<Matricula> {

    private Connection con;

    public DaoMatricula(Connection con) {
        this.con = con;
    }

    // Método que valida que los campos de la matrícula tengan un formato correcto
    private void validar(Matricula m) throws DatosException {
        if (m.getDNI_alumno() == null || m.getDNI_alumno().isEmpty())
            throw new DatosException("DNI alumno vacío");
        if (m.getDNI_profesor() == null || m.getDNI_profesor().isEmpty())
            throw new DatosException("DNI profesor vacío");
        if (!m.getDNI_alumno().matches("\\d{8}[A-Z]"))
            throw new DatosException("Formato de DNI de alumno inválido (8 números + letra mayúscula)");
        if (!m.getDNI_profesor().matches("\\d{8}[A-Z]"))
            throw new DatosException("Formato de DNI de profesor inválido (8 números + letra mayúscula)");
        if (m.getIdCurso() <= 0)
            throw new DatosException("ID curso inválido");
        if (m.getEstadoMatricula() == null || m.getEstadoMatricula().isEmpty())
            throw new DatosException("Estado vacío");
        if (m.getFechaMatricula() == null)
            throw new DatosException("Fecha matrícula vacía");
        if (m.getImporteAbonado() < 0)
            throw new DatosException("Importe inválido");
    }

    // Método que comprueba si la matrícula ya existe en la base de datos mediante su ID
    private boolean existe(int id) throws Exception {
        String sql = "SELECT idMatricula FROM MATRICULA WHERE idMatricula=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    // Método que comprueba si un alumno ya tiene una matrícula activa en un curso específico
    private boolean alumnoYaMatriculado(String dni, int idCurso) throws Exception {
        String sql = "SELECT * FROM MATRICULA WHERE DNI_alumno=? AND idCurso=? AND estadoMatricula='Activa'";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, dni);
        ps.setInt(2, idCurso);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    // Método que comprueba si quedan plazas libres disponibles en un curso específico
    private boolean hayPlazas(int idCurso) throws Exception {
        String sql = """
            SELECT c.plazasMax - COUNT(m.idMatricula) AS plazasLibres
            FROM CURSO c
            LEFT JOIN MATRICULA m ON c.idCurso = m.idCurso AND m.estadoMatricula='Activa'
            WHERE c.idCurso=?
            GROUP BY c.idCurso
        """;

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idCurso);
        ResultSet rs = ps.executeQuery();

        if (rs.next())
            return rs.getInt("plazasLibres") > 0;

        return false;
    }

    // Método que comprueba si el alumno existe en la base de datos mediante su DNI
    private boolean existeAlumno(String dni) throws Exception {
        String sql = "SELECT DNI_alumno FROM ALUMNO WHERE DNI_alumno=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, dni);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    // Método que comprueba si el profesor existe en la base de datos mediante su DNI
    private boolean existeProfesor(String dni) throws Exception {
        String sql = "SELECT DNI_profesor FROM PROFESOR WHERE DNI_profesor=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, dni);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    // Método que comprueba si el curso existe en la base de datos mediante su ID
    private boolean existeCurso(int idCurso) throws Exception {
        String sql = "SELECT idCurso FROM CURSO WHERE idCurso=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idCurso);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    // Método que elimina una matrícula de la base de datos filtrando por su ID
    @Override
    public boolean eliminar(int id) throws Exception {
        if (!existe(id))
            throw new MatriculaException("La matrícula no existe");

        String sql = "DELETE FROM MATRICULA WHERE idMatricula=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);

        return ps.executeUpdate() > 0;
    }

    // Método que obtiene una matrícula específica filtrando por su ID
    @Override
    public Matricula obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM MATRICULA WHERE idMatricula=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Matricula m = new Matricula();
            m.setIdMatricula(rs.getInt("idMatricula"));
            m.setImporteAbonado(rs.getDouble("importeAbonado"));
            m.setFechaMatricula(rs.getDate("fechaMatricula"));
            m.setEstadoMatricula(rs.getString("estadoMatricula"));
            m.setDNI_alumno(rs.getString("DNI_alumno"));
            m.setDNI_profesor(rs.getString("DNI_profesor"));
            m.setIdCurso(rs.getInt("idCurso"));
            return m;
        }
        return null;
    }

    // Método que inserta una nueva matrícula, antes comprueba si sus datos son válidos y si ya existe la matrícula
    @Override
    public boolean insertar(Matricula m) throws Exception {
        validar(m);
        
        if (!existeAlumno(m.getDNI_alumno()))
            throw new DatosException("El alumno no existe");

        if (!existeProfesor(m.getDNI_profesor()))
            throw new DatosException("El profesor no existe");

        if (!existeCurso(m.getIdCurso()))
            throw new DatosException("El curso no existe");

        if (alumnoYaMatriculado(m.getDNI_alumno(), m.getIdCurso()))
            throw new MatriculaException("El alumno ya está matriculado en este curso");

        if (!hayPlazas(m.getIdCurso()))
            throw new MatriculaException("No hay plazas disponibles en este curso");

        String sql = """
            INSERT INTO MATRICULA (importeAbonado, fechaMatricula, estadoMatricula, DNI_alumno, DNI_profesor, idCurso)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setDouble(1, m.getImporteAbonado());
        ps.setDate(2, m.getFechaMatricula());
        ps.setString(3, m.getEstadoMatricula());
        ps.setString(4, m.getDNI_alumno());
        ps.setString(5, m.getDNI_profesor());
        ps.setInt(6, m.getIdCurso());

        return ps.executeUpdate() > 0;
    }

    // Método que modifica los datos de una matrícula, valida los datos y si existe en la base de datos
    @Override
    public boolean modificar(Matricula m) throws Exception {
        validar(m);

        if (!existe(m.getIdMatricula()))
            throw new MatriculaException("La matrícula no existe");

        String sql = """
            UPDATE MATRICULA SET importeAbonado=?, fechaMatricula=?, estadoMatricula=?, 
            DNI_alumno=?, DNI_profesor=?, idCurso=? WHERE idMatricula=?
        """;

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setDouble(1, m.getImporteAbonado());
        ps.setDate(2, m.getFechaMatricula());
        ps.setString(3, m.getEstadoMatricula());
        ps.setString(4, m.getDNI_alumno());
        ps.setString(5, m.getDNI_profesor());
        ps.setInt(6, m.getIdCurso());
        ps.setInt(7, m.getIdMatricula());

        return ps.executeUpdate() > 0;
    }

    // Método que devuelve una lista con todas las matrículas registradas en la base de datos
    @Override
    public List<Matricula> obtenerTodos() throws Exception {
        List<Matricula> lista = new ArrayList<>();

        String sql = "SELECT * FROM MATRICULA";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Matricula m = new Matricula();
            m.setIdMatricula(rs.getInt("idMatricula"));
            m.setImporteAbonado(rs.getDouble("importeAbonado"));
            m.setFechaMatricula(rs.getDate("fechaMatricula"));
            m.setEstadoMatricula(rs.getString("estadoMatricula"));
            m.setDNI_alumno(rs.getString("DNI_alumno"));
            m.setDNI_profesor(rs.getString("DNI_profesor"));
            m.setIdCurso(rs.getInt("idCurso"));
            lista.add(m);
        }

        return lista;
    }

    // Método que devuelve una lista con todas las matrículas asociadas a un alumno específico
    public List<Matricula> obtenerPorAlumno(String dniAlumno) throws Exception {
        List<Matricula> lista = new ArrayList<>();

        String sql = "SELECT * FROM MATRICULA WHERE DNI_alumno=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, dniAlumno);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Matricula m = new Matricula();
            m.setIdMatricula(rs.getInt("idMatricula"));
            m.setImporteAbonado(rs.getDouble("importeAbonado"));
            m.setFechaMatricula(rs.getDate("fechaMatricula"));
            m.setEstadoMatricula(rs.getString("estadoMatricula"));
            m.setDNI_alumno(rs.getString("DNI_alumno"));
            m.setDNI_profesor(rs.getString("DNI_profesor"));
            m.setIdCurso(rs.getInt("idCurso"));
            lista.add(m);
        }

        return lista;
    }

    // Método que devuelve una lista con los identificadores de los cursos impartidos por un profesor específico
    public List<Integer> obtenerCursosPorProfesor(String dniProfesor) throws Exception {
        Set<Integer> idsCursos = new HashSet<>();

        String sql = "SELECT idCurso FROM MATRICULA WHERE DNI_profesor=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, dniProfesor);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            idsCursos.add(rs.getInt("idCurso"));
        }

        return new ArrayList<>(idsCursos);
    }
}