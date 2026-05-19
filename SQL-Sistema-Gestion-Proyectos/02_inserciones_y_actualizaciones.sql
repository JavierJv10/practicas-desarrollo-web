-- ===============================
-- Puestos de trabajo
-- ===============================
INSERT INTO PuestosTrabajo (NombrePuestoTrabajo) VALUES
('Project Manager'),
('Desarrollador/a'),
('Diseñador/a UX/UI'),
('QA Tester');


INSERT INTO PuestosTrabajo (NombrePuestoTrabajo) VALUES ('Project Manager');
INSERT INTO PuestosTrabajo (NombrePuestoTrabajo) VALUES ('Desarrollador/a');
INSERT INTO PuestosTrabajo (NombrePuestoTrabajo) VALUES ('Diseñador/a UX/UI');
INSERT INTO PuestosTrabajo (NombrePuestoTrabajo) VALUES ('QA Tester');

SELECT * FROM PuestosTrabajo;

SELECT NombrePuestoTrabajo FROM PuestosTrabajo
WHERE pk_PuestoTrabajoId = 2;


-- ===============================
-- Estados
-- ===============================
INSERT INTO Estados (NombreEstado) VALUES
('Pendiente'),
('En Proceso'),
('Bloqueada'),
('Hecha');

SELECT * from Estados;

-- ===============================
-- Empleados
-- ===============================
INSERT INTO Empleados (Nombre, Apellido1, Apellido2, Mail, fk_PuestoTrabajoId) VALUES
('Montse', 'García', 'López', 'montse@ucademy.test', 1),
('Marta', 'Soler', 'Ruiz', 'marta@ucademy.test', 3),
('Fernando', 'Pérez', 'Martín', 'fernando@ucademy.test', 2),
('Lucía Camila', 'Navarro', 'Díaz', 'lucia@ucademy.test', 4);

SELECT * from Empleados;

INSERT INTO Empleados (Nombre, Apellido1, Apellido2, Mail, fk_PuestoTrabajoId) VALUES
('Montse2', 'García2', 'López2', 'montse@ucademy.test', 5);

-- ===============================
-- Equipos
-- ===============================
INSERT INTO Equipos (NombreEquipo, DescEquipo) VALUES
('Equipo Alfa', 'Equipo principal de desarrollo'),
('Equipo Beta', 'Equipo de soporte y QA');

SELECT * from Equipos


INSERT INTO Equipos_Empleados (fk_EquipoId, fk_EmpleadoId) VALUES
(1,1),(1,2),(1,3),
(2,4),(2,3);

SELECT * from Equipos_Empleados;

INSERT INTO Equipos_Empleados (fk_EquipoId, fk_EmpleadoId) VALUES
(1,7);

-- ===============================
-- Proyectos
-- ===============================
INSERT INTO Proyectos (NombreProyecto, DescProyecto, FechaInicio, FechaFin) VALUES
('App Companion', 'Aplicación companion de serie', '2026-02-01', '2026-04-30'),
('Portal Interno', 'Web de gestión interna', '2026-01-15', '2026-03-15');

SELECT * from Proyectos;



INSERT INTO Tareas (NombreTarea, DescTarea, FechaInicio, FechaFin, fk_ProyectoId, fk_EmpleadoId, fk_EstadoId) VALUES
('Definir alcance', 'Documento inicial', '2026-02-01', '2026-02-05', 1, 1, 2),
('Diseño UX', 'Wireframes', '2026-02-03', '2026-02-14', 1, 2, 2),
('Backend API', 'Servicios principales', '2026-02-10', '2026-03-10', 1, 3, 1),
('Testing', 'Casos de prueba', '2026-02-12', '2026-02-20', 1, 4, 1),
('Maquetación web', 'Frontend portal', '2026-01-16', '2026-02-05', 2, 2, 4),
('Seguridad', 'Hardening', '2026-02-06', '2026-02-18', 2, 1, 3);

SELECT * from Tareas;

SELECT Tareas.*, Proyectos.* from Tareas
INNER JOIN Proyectos ON Proyectos.pk_ProyectoId = Tareas.fk_ProyectoId;

SELECT Tareas.pk_TareaId, Tareas.NombreTarea, Tareas.fk_ProyectoId, Proyectos.NombreProyecto from Tareas
INNER JOIN Proyectos ON Proyectos.pk_ProyectoId = Tareas.fk_ProyectoId;


SELECT * from Empleados;
SELECT * from PuestosTrabajo;

UPDATE Empleados
SET fk_PuestoTrabajoId = 1
WHERE pk_empleadoId = 3;

UPDATE Empleados
SET fk_PuestoTrabajoId = 4
WHERE Nombre = 'Fernando';
;
UPDATE Empleados
SET fk_PuestoTrabajoId = 3;

;

-- Canvi de domini de correu
UPDATE Empleados
SET Mail = CONCAT(SUBSTRING_INDEX(Mail,'@',1),'@ucademy.local')
WHERE Mail LIKE '%@ucademy.test';

SELECT * from Empleados;


-- Tasques acabades abans del 10 febrer → Hecha
UPDATE Tareas
SET fk_EstadoId = 4
WHERE FechaFin < '2026-02-10';

SELECT * from Tareas;



-- Reassignar tasques de seguretat al projecte 1
UPDATE Tareas
SET fk_ProyectoId = 1
WHERE NombreTarea LIKE '%Seguridad%';


SELECT * from Equipos;


-- Millorar descripció d’equip
UPDATE Equipos
SET DescEquipo = 'Equipo completo PM + Dev + UX + QA'
WHERE NombreEquipo = 'Equipo Alfa';


-- Esborrar tasques del projecte 2
DELETE FROM Tareas
WHERE fk_ProyectoId = 2;


-- Esborrar projecte 2
DELETE FROM Proyectos
WHERE pk_ProyectoId = 2;

SELECT * from Proyectos;

