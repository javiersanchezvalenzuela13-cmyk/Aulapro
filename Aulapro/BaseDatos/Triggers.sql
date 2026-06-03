-- Trigger 1: Actualizar plazas y estado del curso al registrar una nueva matrícula
-- Cada vez que se inserta una matrícula se resta 1 a plazasMax (plazas restantes)

DELIMITER $$

CREATE TRIGGER actualizarPlazas
AFTER INSERT ON MATRICULA
FOR EACH ROW
BEGIN
    -- Restar una plaza al curso matriculado
    UPDATE CURSO
    SET plazasMax = plazasMax - 1
    WHERE idCurso = NEW.idCurso;

    -- Si ya no quedan plazas, cambia el estado del curso a completo
    UPDATE CURSO
    SET estado = 'Completo'
    WHERE idCurso = NEW.idCurso
      AND plazasMax = 0;

    -- Si aún quedan plazas, dejar el estado como disponible
    UPDATE CURSO
    SET estado = 'Abierto'
    WHERE idCurso = NEW.idCurso
      AND plazasMax > 0;
END$$

DELIMITER ;


-- Trigger 2: Añadir una plaza si se elimina una matrícula del curso
-- Cada vez que se borra una matrícula se suma 1 a plazasMax (plazas restantes)

DELIMITER $$

CREATE TRIGGER devolverPlaza
BEFORE DELETE ON MATRICULA
FOR EACH ROW
BEGIN
    -- Sumar una plaza al curso
    UPDATE CURSO
    SET plazasMax = plazasMax + 1
    WHERE idCurso = OLD.idCurso;

    -- Si el curso estaba completo y ahora tiene al menos 1 plaza, ponerlo como disponible
    UPDATE CURSO
    SET estado = 'Abierto'
    WHERE idCurso = OLD.idCurso
      AND plazasMax = 1;
END$$

DELIMITER ;

