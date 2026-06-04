package DTO;

import java.sql.Date;

public class Matricula {
		private int idMatricula;
	    private double importeAbonado;
	    private Date fechaMatricula;
	    private String estadoMatricula;       
	    private String DNI_alumno;
	    private String DNI_profesor;
	    private int idCurso;
		
public Matricula(int idMatricula, double importeAbonado, Date fechaMatricula, String estadoMatricula,String DNI_alumno, String DNI_profesor, int idCurso) {
			this.idMatricula = idMatricula;
			this.importeAbonado = importeAbonado;
			this.fechaMatricula = fechaMatricula;
			this.estadoMatricula = estadoMatricula;
			this.DNI_alumno = DNI_alumno;
			this.DNI_profesor = DNI_profesor;
			this.idCurso = idCurso;
		}


public Matricula() {
}


public int getIdMatricula() {
	return idMatricula;
}

public void setIdMatricula(int idMatricula) {
	this.idMatricula = idMatricula;
}

public double getImporteAbonado() {
	return importeAbonado;
}

public void setImporteAbonado(double importeAbonado) {
	this.importeAbonado = importeAbonado;
}

public Date getFechaMatricula() {
	return fechaMatricula;
}

public void setFechaMatricula(Date fechaMatricula) {
	this.fechaMatricula = fechaMatricula;
}

public String getEstadoMatricula() {
	return estadoMatricula;
}

public void setEstadoMatricula(String estadoMatricula) {
	this.estadoMatricula = estadoMatricula;
}

public String getDNI_alumno() {
	return DNI_alumno;
}

public void setDNI_alumno(String DNI_alumno) {
	this.DNI_alumno = DNI_alumno;
}

public String getDNI_profesor() {
	return DNI_profesor;
}

public void setDNI_profesor(String DNI_profesor) {
	this.DNI_profesor = DNI_profesor;
}

public int getIdCurso() {
	return idCurso;
}

public void setIdCurso(int idCurso) {
	this.idCurso = idCurso;
}

@Override
public String toString() {
	return "Matricula [idMatricula=" + idMatricula + ", importeAbonado=" + importeAbonado + ", fechaMatricula="
			+ fechaMatricula + ", estadoMatricula=" + estadoMatricula + ", DNI_alumno=" + DNI_alumno + ", DNI_profesor="
			+ DNI_profesor + ", idCurso=" + idCurso + "]";
}
	    
}
