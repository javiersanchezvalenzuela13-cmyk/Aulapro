package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DTO.Curso;
import excepciones.CursoException;
import excepciones.DatosException;
import enumeracion.estadoCurso;

public class DaoCursos implements CRUD<Curso> {

    private Connection con;

    public DaoCursos(Connection con) {
        this.con = con;
    }

    // Método que valida que los campos del curso tengan un formato correcto
    private void validar(Curso c) throws DatosException {
        if (c.getIdCurso() <= 0)
            throw new DatosException("ID de curso inválido");
        if (c.getPlazasMax() <= 0)
            throw new DatosException("Plazas máximas inválidas");
        if (c.getPrecio() < 0)
            throw new DatosException("Precio inválido");
        if (c.getNombreCurso() == null || c.getNombreCurso().isEmpty())
            throw new DatosException("Nombre vacío");
        if (c.getHorario() == null || c.getHorario().isEmpty())
            throw new DatosException("Horario vacío");
        if (c.getEstado() == null)
            throw new DatosException("Estado vacío");
        if (c.getNivel() == null || c.getNivel().isEmpty())
            throw new DatosException("Nivel vacío");
        if (c.getIdCategoria() <= 0)
            throw new DatosException("ID de categoría inválido");
    }

    // Método que comprueba si el curso ya existe en la base de datos mediante su ID
    private boolean existe(int id) throws Exception {
        String sql = "SELECT idCurso FROM CURSO WHERE idCurso=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    // Método que elimina un curso de la base de datos filtrando por su ID
    @Override
    public boolean eliminar(int id) throws Exception {
        if (!existe(id))
            throw new CursoException("El curso no existe");

        String sql = "DELETE FROM CURSO WHERE idCurso=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    }

    // Método que obtiene un curso específico filtrando por su ID
    @Override
    public Curso obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM CURSO WHERE idCurso=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Curso c = new Curso();
            c.setIdCurso(rs.getInt("idCurso"));
            c.setPlazasMax(rs.getInt("plazasMax"));
            c.setPrecio(rs.getDouble("precio"));
            c.setEstado(estadoCurso.valueOf(rs.getString("estado")));
            c.setNombreCurso(rs.getString("nombreCurso"));
            c.setHorario(rs.getString("horario"));
            c.setDuracion(rs.getString("duracion"));
            c.setNivel(rs.getString("nivel"));
            c.setIdCategoria(rs.getInt("idCategoria"));
            return c;
        }
        return null;
    }

    // Método que inserta un nuevo curso, antes comprueba si sus datos son válidos y si ya existe el curso
    @Override
    public boolean insertar(Curso c) throws Exception {
        validar(c);

        if (existe(c.getIdCurso()))
            throw new CursoException("El curso ya existe");

        if (c.getEstado() == estadoCurso.Completo)
            throw new CursoException("No se puede crear un curso ya completo");

        String sql = "INSERT INTO CURSO (idCurso, plazasMax, precio, estado, nombreCurso, horario, duracion, nivel, idCategoria) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, c.getIdCurso());
        ps.setInt(2, c.getPlazasMax());
        ps.setDouble(3, c.getPrecio());
        ps.setString(4, c.getEstado().name());
        ps.setString(5, c.getNombreCurso());
        ps.setString(6, c.getHorario());
        ps.setString(7, c.getDuracion());
        ps.setString(8, c.getNivel());
        ps.setInt(9, c.getIdCategoria());

        return ps.executeUpdate() > 0;
    }

    // Método que modifies los datos de un curso, valida los datos y si existe en la base de datos
    @Override
    public boolean modificar(Curso c) throws Exception {
        validar(c);

        if (!existe(c.getIdCurso()))
            throw new CursoException("El curso no existe");

        String sql = "UPDATE CURSO SET plazasMax=?, precio=?, estado=?, nombreCurso=?, horario=?, duracion=?, nivel=?, idCategoria=? WHERE idCurso=?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, c.getPlazasMax());
        ps.setDouble(2, c.getPrecio());
        ps.setString(3, c.getEstado().name());
        ps.setString(4, c.getNombreCurso());
        ps.setString(5, c.getHorario());
        ps.setString(6, c.getDuracion());
        ps.setString(7, c.getNivel());
        ps.setInt(8, c.getIdCategoria());
        ps.setInt(9, c.getIdCurso());

        return ps.executeUpdate() > 0;
    }

    // Método que devuelve una lista con todos los cursos registrados en la base de datos
    @Override
    public List<Curso> obtenerTodos() throws Exception {
        List<Curso> lista = new ArrayList<>();

        String sql = "SELECT * FROM CURSO";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Curso c = new Curso();
            c.setIdCurso(rs.getInt("idCurso"));
            c.setPlazasMax(rs.getInt("plazasMax"));
            c.setPrecio(rs.getDouble("precio"));
            c.setEstado(estadoCurso.valueOf(rs.getString("estado")));
            c.setNombreCurso(rs.getString("nombreCurso"));
            c.setHorario(rs.getString("horario"));
            c.setDuracion(rs.getString("duracion"));
            c.setNivel(rs.getString("nivel"));
            c.setIdCategoria(rs.getInt("idCategoria"));
            lista.add(c);
        }

        return lista;
    }

    // Método que devuelve una lista con todos los cursos pertenecientes a una categoría específica
    public List<Curso> obtenerPorCategoria(int idCategoria) throws Exception {
        List<Curso> lista = new ArrayList<>();

        String sql = "SELECT * FROM CURSO WHERE idCategoria=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idCategoria);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Curso c = new Curso();
            c.setIdCurso(rs.getInt("idCurso"));
            c.setPlazasMax(rs.getInt("plazasMax"));
            c.setPrecio(rs.getDouble("precio"));
            c.setEstado(estadoCurso.valueOf(rs.getString("estado")));
            c.setNombreCurso(rs.getString("nombreCurso"));
            c.setHorario(rs.getString("horario"));
            c.setDuracion(rs.getString("duracion"));
            c.setNivel(rs.getString("nivel"));
            c.setIdCategoria(rs.getInt("idCategoria"));
            lista.add(c);
        }

        return lista;
    }
}