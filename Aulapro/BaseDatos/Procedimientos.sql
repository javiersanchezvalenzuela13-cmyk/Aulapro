-- Procedimiento 1: Registrar matrícula
-- Registra una matrícula en un curso (anteriormente actualizaba las plazas pero lo deje solo como un trigger para no hacer dos veces lo mismo)

DELIMITER $$

CREATE PROCEDURE registrarMatricula(IN p_DNI_alumno VARCHAR (9), IN p_idCurso INT, IN p_DNI_profesor VARCHAR(9)) -- Valores de entrada que necesita el procedimiento
BEGIN
	DECLARE v_precio DOUBLE;   -- Variable para guardar el precio antes de insertarlo en la matrícula

	SELECT precio INTO v_precio -- Guarda en v_precio el precio del curso que se va a guardar en la matrícula
	FROM CURSO
	WHERE idCurso = p_idCurso;

	-- Insertar una nueva matricula en la tabla MATRICULA
	INSERT INTO MATRICULA(DNI_alumno, idCurso, DNI_profesor, fechaMatricula, importeAbonado, estadoMatricula)
	VALUES(p_DNI_alumno, p_idCurso, p_DNI_profesor, CURDATE(), v_precio, 'Activa');

END$$
DELIMITER ;


--Procedimiento 2: Mostrar historial de un alumno
--Muestra un listado de las matriculas en orden descendiente del alumno buscado

DELIMITER $$

CREATE PROCEDURE historialAlumno(IN p_DNI_alumno VARCHAR(9)) -- Valor de entrada que necesita el procedimiento
BEGIN
	SELECT	m.idMatricula, m.fechaMatricula, m.importeAbonado, m.estadoMatricula, c.nombreCurso, c.estado, p.nombreProfesor -- Datos a mostrar para cada matrícula del alumno
	FROM MATRICULA m 	-- De la tabla MATRICULA , uniendola con CURSO y PROFESOR
	INNER JOIN CURSO c ON m.idCurso = c.idCurso -- Es Inner join porque la matrícula siempre debe estar asociada a un profesor y un curso
  	INNER JOIN PROFESOR p ON m.DNI_profesor = p.DNI_profesor
    WHERE m.DNI_alumno = p_DNI_alumno -- De las matrículas que compartan el mismo DNI que el insertado
    ORDER BY m.fechaMatricula DESC; -- Orden descendiente, las matrículas más nuevas van antes
END$$

DELIMITER ;

-- Procedimiento 3: Resumen de un curso
-- Muestra información completa del curso, incluyendo plazas, matrículas y profesor

DELIMITER $$

CREATE PROCEDURE resumenCurso(IN p_idCurso INT)
BEGIN
   	DECLARE v_error INT DEFAULT 0;
    	-- Handler por si ocurre algún error SQL
    	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    	BEGIN
        		SELECT 'Error al obtener el resumen del curso' AS mensaje;
        		SET v_error = 1;
    	END;

    	IF v_error = 0 THEN -- Si no ha saltado error selecciona estos datos de la tabla curso, matrícula y profesor
			SELECT c.nombreCurso AS Curso, c.plazasMax AS PlazasRestantes, COUNT(m.idMatricula) AS TotalMatriculas, p.nombreProfesor AS Profesor
        	FROM CURSO c
        	LEFT JOIN MATRICULA m ON c.idCurso = m.idCurso -- Left join para que rellene con null si no existiera una matricula en el curso
       		LEFT JOIN PROFESOR p ON m.DNI_profesor = p.DNI_profesor -- Left join para que rellene con null si no existiera un profesor en la matricula
        	WHERE c.idCurso = p_idCurso -- Del curso con el mismo id que el insertado
        	GROUP BY c.idCurso, c.nombreCurso, c.plazasMax, p.nombreProfesor;
    	END IF;
END$$
DELIMITER ;
	
