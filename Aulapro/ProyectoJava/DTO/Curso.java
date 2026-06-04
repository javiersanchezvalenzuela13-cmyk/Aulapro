package DTO;
import enumeracion.estadoCurso;

public class Curso {
	private int idCurso;
    private int plazasMax;
    private double precio;
    private estadoCurso estado;
    private String nombreCurso;
    private String horario;
    private String duracion;
    private String nivel;
    private int idCategoria;
    
public Curso(int idCurso, int plazasMax, double precio, estadoCurso estado, String nombreCurso, String horario,String duracion, String nivel, int idCategoria) {
		this.idCurso = idCurso;
		this.plazasMax = plazasMax;
		this.precio = precio;
		this.estado = estado;
		this.nombreCurso = nombreCurso;
		this.horario = horario;
		this.duracion = duracion;
		this.nivel = nivel;
		this.idCategoria = idCategoria;
	}


public Curso() {
}


public int getIdCurso() {
	return idCurso;
}

public void setIdCurso(int idCurso) {
	this.idCurso = idCurso;
}

public int getPlazasMax() {
	return plazasMax;
}

public void setPlazasMax(int plazasMax) {
	this.plazasMax = plazasMax;
}

public double getPrecio() {
	return precio;
}

public void setPrecio(double precio) {
	this.precio = precio;
}

public estadoCurso getEstado() {
	return estado;
}

public void setEstado(estadoCurso estado) {
	this.estado = estado;
}

public String getNombreCurso() {
	return nombreCurso;
}

public void setNombreCurso(String nombreCurso) {
	this.nombreCurso = nombreCurso;
}

public String getHorario() {
	return horario;
}

public void setHorario(String horario) {
	this.horario = horario;
}

public String getDuracion() {
	return duracion;
}

public void setDuracion(String duracion) {
	this.duracion = duracion;
}

public String getNivel() {
	return nivel;
}

public void setNivel(String nivel) {
	this.nivel = nivel;
}

public int getIdCategoria() {
	return idCategoria;
}

public void setIdCategoria(int idCategoria) {
	this.idCategoria = idCategoria;
}

@Override
public String toString() {
	return "Curso [idCurso=" + idCurso + ", plazasMax=" + plazasMax + ", precio=" + precio + ", estado=" + estado
			+ ", nombreCurso=" + nombreCurso + ", horario=" + horario + ", duracion=" + duracion + ", nivel=" + nivel
			+ ", idCategoria=" + idCategoria + "]";
}

}
