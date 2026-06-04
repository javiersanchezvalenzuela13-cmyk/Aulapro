package DTO;
import java.sql.Date;
public class Profesor {
	 private String DNI_profesor;
	    private String nombreProfesor;
	    private String especialidad;
	    private String titulacion;
	    private int aniosExp;
	    private Date fechaIncorp;

public Profesor(String DNI_profesor, String nombreProfesor, String especialidad, String titulacion,int aniosExp, Date fechaIncorp) {
			this.DNI_profesor = DNI_profesor;
			this.nombreProfesor = nombreProfesor;
			this.especialidad = especialidad;
			this.titulacion = titulacion;
			this.aniosExp = aniosExp;
			this.fechaIncorp = fechaIncorp;
		}


public Profesor() {
}


public String getDNI_profesor() {
	return DNI_profesor;
}

public void setDNI_profesor(String DNI_profesor) {
	this.DNI_profesor = DNI_profesor;
}

public String getNombreProfesor() {
	return nombreProfesor;
}

public void setNombreProfesor(String nombreProfesor) {
	this.nombreProfesor = nombreProfesor;
}

public String getEspecialidad() {
	return especialidad;
}

public void setEspecialidad(String especialidad) {
	this.especialidad = especialidad;
}

public String getTitulacion() {
	return titulacion;
}

public void setTitulacion(String titulacion) {
	this.titulacion = titulacion;
}

public int getAniosExp() {
	return aniosExp;
}

public void setAniosExp(int aniosExp) {
	this.aniosExp = aniosExp;
}

public Date getFechaIncorp() {
	return fechaIncorp;
}

public void setFechaIncorp(Date fechaIncorp) {
	this.fechaIncorp = fechaIncorp;
}

@Override
public String toString() {
	return "Profesor [DNI_profesor=" + DNI_profesor + ", nombreProfesor=" + nombreProfesor + ", especialidad="
			+ especialidad + ", titulacion=" + titulacion + ", aniosExp=" + aniosExp + ", fechaIncorp=" + fechaIncorp
			+ "]";
}
	    
}
