-- Procedimiento 1: Registrar matrícula
-- Registra una matrícula en un curso (anteriormente actualizaba las plazas pero lo deje solo como un trigger para no hacer dos veces lo mismo)

DELIMITER $$

CREATE PROCEDURE registrarMatricula(IN p_DNI_alumno VARCHAR (9), IN p_idCurso INT, IN p_DNI_profesor VARCHAR(9)) -- valores de entrada que necesita el procedimiento
BEGIN
	DECLARE v_precio DOUBLE;   -- variable para guardar el precio antes de insertarlo en la matrícula

	SELECT precio INTO v_precio -- Guarda en v_precio el precio del curso que se va a guardar en la matrícula
	FROM CURSO
	WHERE idCurso = p_idCurso;

	-- Insertar una nueva matricula en la tabla MATRICULA
	INSERT INTO MATRICULA(DNI_alumno, idCurso, DNI_profesor, fechaMatricula, importeAbonado, estadoMatricula)
	VALUES(p_DNI_alumno, p_idCurso, p_DNI_profesor, NOW(), v_precio, 'Activa');

END$$
DELIMITER ;


--Procedimiento 2: Mostrar historial de un alumno
--Muestra un listado de las matriculas en orden descendiente del alumno buscado
DELIMITER $$

CREATE PROCEDURE historialAlumno(IN p_DNI_alumno VARCHAR(9)) -- valor de entrada que necesita el procedimiento
BEGIN
	SELECT	m.idMatricula, m.fechaMatricula, m.importeAbonado, m.estadoMatricula, c.nombreCurso, c.estado, p.nombreProfesor -- Datos a mostrar para cada matricula del alumno
	FROM MATRICULA m 	-- De la tabla MATRICULA , uniendola con CURSO y PROFESOR
	INNER JOIN CURSO c ON m.idCurso = c.idCurso -- Es Inner join porque la mátricula siempre debe estar asociada a un profesor y un curso
  	INNER JOIN PROFESOR p ON m.DNI_profesor = p.DNI_profesor
    WHERE m.DNI_alumno = p_DNI_alumno -- De las matriculas que compartan el mismo DNI que el insertado
    ORDER BY m.fechaMatricula DESC; -- Orden descendiente, las matrículas más nuevas van antes
END$$

DELIMITER ;

	
