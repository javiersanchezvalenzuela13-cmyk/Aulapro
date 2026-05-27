CREATE DATABASE AULAPRO;
USE AULAPRO;

CREATE TABLE ALUMNO(
    DNI_alumno VARCHAR(9) PRIMARY KEY,
    nombreAlumno VARCHAR(50),
    fechaAlta DATE,
    telefono VARCHAR (20),
    correo VARCHAR (100),
    direccion VARCHAR (100)
    );
    
CREATE TABLE PROFESOR(
    DNI_profesor VARCHAR(9) PRIMARY KEY,
    nombreProfesor VARCHAR(50),
    especialidad VARCHAR(50),
    titulacion VARCHAR(50),
    aniosExp INT,
    fechaIncorp DATE
    );
   
CREATE TABLE CATEGORIA(
    idCategoria INT AUTO_INCREMENT PRIMARY KEY,
    nombreCategoria VARCHAR(50),
    descripcion VARCHAR(200)
    );

CREATE TABLE CURSO(
    idCurso INT AUTO_INCREMENT PRIMARY KEY,
    plazasMax INT,
    precio DECIMAL(10,2),
    estado VARCHAR(20),
    nombreCurso VARCHAR(50),
    horario VARCHAR(50),
    duracion VARCHAR(50),
    nivel VARCHAR(50),
    idCategoria INT,
    FOREIGN KEY (idCategoria) REFERENCES CATEGORIA(idCategoria)
);

CREATE TABLE MATRICULA(
    idMatricula INT AUTO_INCREMENT PRIMARY KEY,
    importeAbonado DECIMAL (10,2),
    fechaMatricula DATE,
    estadoMatricula VARCHAR(20),
    DNI_alumno VARCHAR(9),
    DNI_profesor VARCHAR(9),
    idCurso INT,
    FOREIGN KEY (DNI_alumno) REFERENCES ALUMNO(DNI_alumno),
    FOREIGN KEY (DNI_profesor) REFERENCES PROFESOR(DNI_profesor),
    FOREIGN KEY (idCurso) REFERENCES CURSO (idCurso)
);

+INSERT
