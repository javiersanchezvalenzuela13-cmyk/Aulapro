package DTO;
import java.sql.Date;
public class Alumno {
	 	private String DNI_alumno;
	    private String nombreAlumno;
	    private Date fechaAlta;
	    private String direccion;
	    private String correo;
	    private String telefono;
	    
public Alumno(String DNI_alumno, String nombreAlumno, Date fechaAlta, String direccion, String correo,String telefono) {
			this.DNI_alumno = DNI_alumno;
			this.nombreAlumno = nombreAlumno;
			this.fechaAlta = fechaAlta;
			this.direccion = direccion;
			this.correo = correo;
			this.telefono = telefono;
		}

public Alumno() {
}


public String getDNI_alumno() {
	return DNI_alumno;
}

public void setDNI_alumno(String DNI_alumno) {
	this.DNI_alumno = DNI_alumno;
}

public String getNombreAlumno() {
	return nombreAlumno;
}

public void setNombreAlumno(String nombreAlumno) {
	this.nombreAlumno = nombreAlumno;
}

public Date getFechaAlta() {
	return fechaAlta;
}

public void setFechaAlta(Date fechaAlta) {
	this.fechaAlta = fechaAlta;
}

public String getDireccion() {
	return direccion;
}

public void setDireccion(String direccion) {
	this.direccion = direccion;
}

public String getCorreo() {
	return correo;
}

public void setCorreo(String correo) {
	this.correo = correo;
}

public String getTelefono() {
	return telefono;
}

public void setTelefono(String telefono) {
	this.telefono = telefono;
}

@Override
public String toString() {
	return "Alumno [DNI_alumno=" + DNI_alumno + ", nombreAlumno=" + nombreAlumno + ", fechaAlta=" + fechaAlta
			+ ", direccion=" + direccion + ", correo=" + correo + ", telefono=" + telefono + "]";
}
	    

}
