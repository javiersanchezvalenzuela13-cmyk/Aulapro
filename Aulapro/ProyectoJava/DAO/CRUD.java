package DAO;

public interface CRUD<T> {// Interfaz generica

    boolean insertar(T obj) throws Exception; //Operacion CREATE, devuelve true si se inserta correctamente
    boolean modificar(T obj) throws Exception; //Operacion UPDATE
    boolean eliminar(int id) throws Exception; //Operacion DELETE
    T obtenerPorId(int id) throws Exception; //Operacion READ de un solo registro, devuelve objeto T
    java.util.List<T> obtenerTodos() throws Exception; //Operacion READ de todos los registros, devuelve lista de objeto T
}
