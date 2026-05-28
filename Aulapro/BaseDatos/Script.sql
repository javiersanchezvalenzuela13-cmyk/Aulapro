DROP DATABASE IF EXISTS AULAPRO;
CREATE DATABASE AULAPRO;
USE AULAPRO;

-- Tabla alumno 
CREATE TABLE ALUMNO(
    DNI_alumno VARCHAR(9) PRIMARY KEY,
    nombreAlumno VARCHAR(50) NOT NULL,
    fechaAlta DATE NOT NULL,
    telefono VARCHAR (20) NOT NULL ,
    correo VARCHAR (100) NOT NULL ,
    direccion VARCHAR (100) NOT NULL
    );

-- Tabla profesor    
CREATE TABLE PROFESOR(
    DNI_profesor VARCHAR(9) PRIMARY KEY,
    nombreProfesor VARCHAR(50) NOT NULL,
    especialidad VARCHAR(50) NOT NULL,
    titulacion VARCHAR(50) NOT NULL,
    aniosExp INT DEFAULT 0,
    fechaIncorp DATE NOT NULL
    );

-- Tabla categoría   
CREATE TABLE CATEGORIA(
    idCategoria INT AUTO_INCREMENT PRIMARY KEY,
    nombreCategoria VARCHAR(50)NOT NULL UNIQUE,
    descripcion VARCHAR(200) NOT NULL
    );

-- Tabla curso
CREATE TABLE CURSO(
    idCurso INT AUTO_INCREMENT PRIMARY KEY,
    plazasMax INT NOT NULL, 
    precio DECIMAL(10,2) NOT NULL, 
    estado ENUM('Abierto' , 'Completo', 'Finalizado', 'Cancelado') NOT NULL,
    nombreCurso VARCHAR(50) NOT NULL,
    horario VARCHAR(50) NOT NULL,
    duracion VARCHAR(50),
    nivel VARCHAR(50),
    idCategoria INT,
    FOREIGN KEY (idCategoria) REFERENCES CATEGORIA(idCategoria)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);
-- Tabla matrícula
CREATE TABLE MATRICULA(
    idMatricula INT AUTO_INCREMENT PRIMARY KEY,
    importeAbonado DECIMAL (10,2) NOT NULL,
    fechaMatricula DATE NOT NULL,
    estadoMatricula VARCHAR(20) NOT NULL,
    DNI_alumno VARCHAR(9),
    DNI_profesor VARCHAR(9),
    idCurso INT,
    FOREIGN KEY (DNI_alumno) REFERENCES ALUMNO(DNI_alumno)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (DNI_profesor) REFERENCES PROFESOR(DNI_profesor)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
    FOREIGN KEY (idCurso) REFERENCES CURSO (idCurso)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- Insert alumno
INSERT INTO ALUMNO (DNI_alumno, nombreAlumno, fechaAlta, telefono, correo, direccion) VALUES
('12345678A', 'Héctor Suarez', '2024-01-10', '600123456', 'hsuare@gmail.com', 'Calle Hojaverde 12'),
('26785912D', 'Isaac García', '2025-04-16', '611987654', 'isaacgr@gmail.com', 'Av. Vetusta 45'),
('30384325L', 'Ángel Cruz', '2023-08-01', '622111222', 'lechuga@gmail.com', 'Calle Jubileo 8');

-- Insert profesor
INSERT INTO PROFESOR (DNI_profesor, nombreProfesor, especialidad, titulacion, aniosExp, fechaIncorp) VALUES
('67548734M', 'Jesús Rodríguez', 'Programación', 'Ingeniería Informática', 5, '2020-09-01'),
('14325839P', 'Alejandro Pilar ', 'Inglés', 'Traducción e interpretación (Inglés)', 8, '2018-01-15'),
('25623781C', 'Santiago Ledo', 'Matemáticas', 'Grado en Matemáticas', 6, '2022-05-20');

-- Insert categoria
INSERT INTO CATEGORIA (nombreCategoria, descripcion) VALUES
('Idiomas', 'Cursos orientados al aprendizaje de lenguas extranjeras'),
('Informática', 'Cursos relacionados con programación, redes y tecnologías'),
('Refuerzo escolar', 'Cursos de apoyo académico para estudiantes de diferentes niveles de estudios'),
('Preparación de oposiciones', 'Cursos para preparar oposiciones'),
('Formación profesional', 'Cursos prácticos orientados a la inserción laboral');

--Insert curso
INSERT INTO CURSO (plazasMax, precio, estado, nombreCurso, horario, duracion, nivel, idCategoria) VALUES
(20, 180.00, 'Abierto', 'Inglés B1', 'Lunes y Miércoles 17:00-19:00', '3 meses', 'Intermedio', 1),
(15, 220.00, 'Abierto', 'Java Básico', 'Martes y Jueves 18:00-20:00', '3 meses', 'Inicial', 2),
(12, 150.00, 'Cancelado', 'Refuerzo de Matemáticas', 'Viernes 16:00-18:00', '2 meses', 'Inicial', 3),
(25, 300.00, 'Completo', 'Preparación Oposiciones Auxiliar Administrativo', 'Sábados 09:00-13:00', '4 meses', 'Intermedio', 4),
(18, 350.00, 'Finalizado', 'Técnico en Redes', 'Lunes a Jueves 19:00-21:00', '5 meses', 'Avanzado', 5);

--Insert matrícula
INSERT INTO MATRICULA (importeAbonado, fechaMatricula, estadoMatricula, DNI_alumno, DNI_profesor, idCurso) VALUES
(180.00, '2024-01-15', 'Activa', '12345678A', '14325839P', 1),
(220.00, '2024-02-10', 'Activa', '26785912D', '67548734M', 2),
(150.00, '2024-03-05', 'Cancelada', '30384325L', '25623781C', 3),
(300.00, '2024-04-01', 'Activa', '12345678A', '25623781C', 4),
(350.00, '2024-05-20', 'Finalizada', '26785912D', '67548734M', 5);




