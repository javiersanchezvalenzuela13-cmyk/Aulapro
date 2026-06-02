-- Función 1 : Calcular importe del curso
-- Devuelve el precio del curso insertado

DELIMITER $$

CREATE FUNCTION calcularImporteCurso(p_idCurso INT)
RETURNS DOUBLE
DETERMINISTIC -- Siempre devuelve lo mismo para el mismo valor insertado
BEGIN 
    DECLARE v_precio DOUBLE; -- Declara una variable para introducir el precio
    SELECT precio INTO v_precio --Introduce el precio del curso con el mismo id que p_idCurso
    FROM CURSO
    WHERE idCurso = p_idCurso;
    RETURN v_precio; -- Devuelve la variable
END$$

DELIMITER ;

-- Función 2: Contar las matrículas de un alumno
-- Devuelve el número de matrículas del alumno

DELIMITER $$

CREATE FUNCTION contarMatriculasAlumno(p_DNI_alumno VARCHAR(9))
RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE v_total INT; --Declara una variable para ir contando las matriculas 
    SELECT COUNT(*) INTO v_total --Si el DNI de la matrícula coincide con el parámetro insertado cuenta 1
    FROM MATRICULA
    WHERE DNI_alumno = p_DNI_alumno;
    RETURN v_total; -- Devuelve la variable
END$$

DELIMITER ;

-- Función 3: Plazas disponibles de un curso
-- Devuelve cuántas plazas quedan en un curso (el atributo se llama plazasMax pero actúa como plazas restantes)

DELIMITER $$

CREATE FUNCTION plazasDisponibles(p_idCurso INT)
RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE v_plazas INT;
    SELECT plazasMax INTO v_plazas -- Inserta el número de plazas en la variable
    FROM CURSO
    WHERE idCurso = p_idCurso; --Si el id del curso coincide con el parámetro insertado
    RETURN v_plazas; -- Devuelve la variable
END$$

DELIMITER ;
