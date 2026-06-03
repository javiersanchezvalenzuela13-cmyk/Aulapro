-- Cursor: Mostrar todas las matrículas de un alumno
-- Recorre una a una las matrículas y las muestra con SELECT

DELIMITER $$
CREATE PROCEDURE mostrarMatriculasAlumno(p_DNI VARCHAR(9))
BEGIN
    DECLARE v_idMat INT; -- Declara las variables para insertar los valores 
    DECLARE v_idCurso INT;
    DECLARE v_fecha DATE;
    DECLARE fin INT DEFAULT 0; -- Empieza en 0 y se usa para saber cuando se han acabado las filas

    -- Declarar el cursor que recorrerá una a una las filas del SELECT
    DECLARE cur_matriculas CURSOR FOR
        SELECT idMatricula, idCurso, fechaMatricula
        FROM MATRICULA
        WHERE DNI_alumno = p_DNI;

    -- Handler para detectar fin del cursor, cuando no haya más filas fin = 1
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET fin = 1;

    -- Abrir cursor
    OPEN cur_matriculas;

    -- Bucle para recorrer todas las filas
    bucle: LOOP
        FETCH cur_matriculas INTO v_idMat, v_idCurso, v_fecha; -- Lee la siguiente fila y mete los valores en las variables
        IF fin = 1 THEN --Cuando fin=1, no hay más filas y sale del bucle
            LEAVE bucle;
        END IF;

        -- Muestra la matrícula actual, se ejecuta una vez por fila
        SELECT v_idMat AS idMatricula,
               v_idCurso AS idCurso,
               v_fecha AS fechaMatricula;
    END LOOP;
    -- Cerrar cursor
    CLOSE cur_matriculas;
END$$

DELIMITER ;
