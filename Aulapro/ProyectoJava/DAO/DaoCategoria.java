package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DTO.Categoria;
import excepciones.DatosException;

public class DaoCategoria implements CRUD<Categoria> {

    private Connection con;

    public DaoCategoria(Connection con) {
        this.con = con;
    }

    // Método que valida que los campos de la categoría tengan un formato correcto
    private void validar(Categoria c) throws DatosException {
        if (c.getIdCategoria() <= 0)
            throw new DatosException("ID de categoría inválido");
        if (c.getNombreCategoria() == null || c.getNombreCategoria().isEmpty())
            throw new DatosException("Nombre vacío");
        if (c.getDescripcion() == null || c.getDescripcion().isEmpty())
            throw new DatosException("Descripción vacía");
    }

    // Método que comprueba si la categoría ya existe en la base de datos mediante su ID
    private boolean existe(int id) throws Exception {
        String sql = "SELECT idCategoria FROM CATEGORIA WHERE idCategoria=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    // Método que elimina una categoría de la base de datos filtrando por su ID
    @Override
    public boolean eliminar(int id) throws Exception {
        if (!existe(id))
            throw new DatosException("La categoría no existe");

        String sql = "DELETE FROM CATEGORIA WHERE idCategoria=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    }

    // Método que obtiene una categoría específica filtrando por su ID
    @Override
    public Categoria obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM CATEGORIA WHERE idCategoria=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Categoria c = new Categoria();
            c.setIdCategoria(rs.getInt("idCategoria"));
            c.setNombreCategoria(rs.getString("nombreCategoria"));
            c.setDescripcion(rs.getString("descripcion"));
            return c;
        }
        return null;
    }

    // Método que inserta una nueva categoría, antes comprueba si sus datos son válidos y si ya existe la categoría
    @Override
    public boolean insertar(Categoria c) throws Exception {
        validar(c);

        if (existe(c.getIdCategoria()))
            throw new DatosException("La categoría ya existe");

        String sql = "INSERT INTO CATEGORIA (idCategoria, nombreCategoria, descripcion) VALUES (?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, c.getIdCategoria());
        ps.setString(2, c.getNombreCategoria());
        ps.setString(3, c.getDescripcion());

        return ps.executeUpdate() > 0;
    }

    // Método que modifica los datos de una categoría, valida los datos y si existe en la base de datos 
    @Override
    public boolean modificar(Categoria c) throws Exception {
        validar(c);

        if (!existe(c.getIdCategoria()))
            throw new DatosException("La categoría no existe");

        String sql = "UPDATE CATEGORIA SET nombreCategoria=?, descripcion=? WHERE idCategoria=?";
        PreparedStatement ps = con.prepareStatement(sql);
        
        ps.setString(1, c.getNombreCategoria());
        ps.setString(2, c.getDescripcion());
        ps.setInt(3, c.getIdCategoria());

        return ps.executeUpdate() > 0;
    }

    // Método que devuelve una lista con todas las categorías registradas en la base de datos
    @Override
    public List<Categoria> obtenerTodos() throws Exception {
        List<Categoria> lista = new ArrayList<>();

        String sql = "SELECT * FROM CATEGORIA";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Categoria c = new Categoria();
            c.setIdCategoria(rs.getInt("idCategoria"));
            c.setNombreCategoria(rs.getString("nombreCategoria"));
            c.setDescripcion(rs.getString("descripcion"));
            lista.add(c);
        }

        return lista;
    }
}