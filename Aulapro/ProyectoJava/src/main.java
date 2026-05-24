import java.sql.Connection;
public class main {
	public static void main(String[] args) {
	    try {
	        Connection con = conexion.getConnection();
	        System.out.println("Conexión OK");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
