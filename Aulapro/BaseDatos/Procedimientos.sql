-- Procedimiento 1: Registrar matrícula
-- Registra una matrícula en un curso (anteriormente actualizaba las plazas pero lo deje solo como un trigger para no hacer dos veces lo mismo)

DELIMITER $$

CREATE PROCEDURE registrarMatricula( -- valores de entrada que necesita el procedimiento
	IN p_DNI_alumno VARCHAR (9),
	IN p_idCurso INT,
	IN p_DNI_profesor VARCHAR(9)
)
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
	
