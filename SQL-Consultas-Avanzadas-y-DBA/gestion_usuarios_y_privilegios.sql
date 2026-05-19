-- ==========================================
-- 1. CREACIÓN DE USUARIOS
-- ==========================================

-- Creamos un usuario administrador que tendrá control total sobre la base de datos
-- El formato es: CREATE USER 'nombre'@'host' IDENTIFIED BY 'contraseña'
-- 'localhost' significa que solo puede conectarse desde el servidor local
CREATE USER 'admin_ucademy1'@'localhost' IDENTIFIED BY 'Admin123!';

-- Creamos un usuario desarrollador que podrá realizar operaciones CRUD
-- Este usuario tendrá permisos para modificar datos pero no la estructura
CREATE USER 'dev_ucademy1'@'localhost' IDENTIFIED BY 'Dev456!';

-- Creamos un usuario de solo lectura para consultas y reportes
-- Este usuario no podrá modificar ningún dato
CREATE USER 'analista_ucademy1'@'localhost' IDENTIFIED BY 'Analista789!';

-- Creamos un usuario específico para el equipo de RRHH
-- Solo tendrá acceso a las tablas relacionadas con empleados
CREATE USER 'rrhh_user_ucademy1'@'localhost' IDENTIFIED BY 'RRHH2024!';

SELECT user, host FROM mysql.user;

-- ==========================================
-- 2. ASIGNACIÓN DE PERMISOS A NIVEL DE BASE DE DATOS
-- ==========================================

-- Otorgamos TODOS los privilegios al administrador sobre la base de datos completa
-- Esto incluye: SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, ALTER, etc.
GRANT ALL PRIVILEGES ON m0484_bdd_gestionproyectos.* TO 'admin_ucademy1'@'localhost';

-- Para poder otorgar permisos a otros usuarios:
GRANT ALL PRIVILEGES ON `m0484_bdd_gestionproyectos`.* TO `admin_ucademy1`@`localhost` WITH GRANT OPTION; 


-- Otorgamos permisos de lectura, escritura, actualización y eliminación al desarrollador
-- No puede modificar la estructura (CREATE, DROP, ALTER)
GRANT SELECT, INSERT, UPDATE, DELETE ON m0484_bdd_gestionproyectos.* TO 'dev_ucademy1'@'localhost';

-- Otorgamos solo permiso de lectura al analista en toda la base de datos
GRANT SELECT ON m0484_bdd_gestionproyectos.* TO 'analista_ucademy1'@'localhost';



-- ==========================================
-- 3. ASIGNACIÓN DE PERMISOS A NIVEL DE TABLA
-- ==========================================

-- El usuario de RRHH solo puede leer y modificar la tabla Empleados
GRANT SELECT, INSERT, UPDATE ON m0484_bdd_gestionproyectos.Empleados TO 'rrhh_user'@'localhost';

-- Le damos también acceso de lectura a PuestosTrabajo para consultar los puestos disponibles
GRANT SELECT ON m0484_bdd_gestionproyectos.PuestosTrabajo TO 'rrhh_user'@'localhost';

-- Le permitimos ver los equipos y la relación empleados-equipos
GRANT SELECT ON m0484_bdd_gestionproyectos.Equipos TO 'rrhh_user'@'localhost';
GRANT SELECT ON m0484_bdd_gestionproyectos.Equipos_Empleados TO 'rrhh_user'@'localhost';

-- ==========================================
-- 4. ASIGNACIÓN DE PERMISOS ESPECÍFICOS POR OPERACIÓN
-- ==========================================

-- Permitimos al usuario de RRHH insertar registros en la tabla Equipos_Empleados
-- para poder asignar empleados a equipos
GRANT INSERT ON m0484_bdd_gestionproyectos.Equipos_Empleados TO 'rrhh_user'@'localhost';

-- Le damos permiso para eliminar asignaciones de empleados a equipos
GRANT DELETE ON m0484_bdd_gestionproyectos.Equipos_Empleados TO 'rrhh_user'@'localhost';

-- Otorgamos al desarrollador permiso para crear y eliminar tablas temporales
-- Útil para procesamiento de datos sin afectar las tablas principales
GRANT CREATE TEMPORARY TABLES ON m0484_bdd_gestionproyectos.* TO 'dev_ucademy1'@'localhost';


-- ==========================================
-- 5. APLICAR LOS CAMBIOS
-- ==========================================

-- Es necesario ejecutar FLUSH PRIVILEGES para que los cambios surtan efecto inmediatamente
-- Sin este comando, los permisos se aplicarían hasta el próximo reinicio del servidor
FLUSH PRIVILEGES;

-- ==========================================
-- 6. VERIFICACIÓN DE PERMISOS
-- ==========================================

-- Consultamos los permisos otorgados al administrador
SHOW GRANTS FOR 'admin_ucademy1'@'localhost';

-- Consultamos los permisos del desarrollador
SHOW GRANTS FOR 'dev_ucademy1'@'localhost';

-- Consultamos los permisos del analista
SHOW GRANTS FOR 'analista_ucademy1'@'localhost';

-- Consultamos los permisos del usuario de RRHH
SHOW GRANTS FOR 'rrhh_user'@'localhost';

-- ==========================================
-- 7. MODIFICACIÓN DE PERMISOS (OTORGAR ADICIONALES)
-- ==========================================

-- Decidimos que el analista también necesita acceso a crear vistas
-- para facilitar sus reportes
GRANT CREATE VIEW ON m0484_bdd_gestionproyectos.* TO 'analista_ucademy1'@'localhost';

-- Permitimos al desarrollador ejecutar procedimientos almacenados
GRANT EXECUTE ON m0484_bdd_gestionproyectos.* TO 'dev_ucademy1'@'localhost';

-- Aplicamos los cambios
FLUSH PRIVILEGES;

-- ==========================================
-- 8. REVOCACIÓN DE PERMISOS (DESASIGNACIÓN)
-- ==========================================

-- Removemos el permiso de DELETE al usuario de RRHH en la tabla Empleados
-- Ya no podrá eliminar empleados, solo consultarlos y modificarlos
REVOKE DELETE ON m0484_bdd_gestionproyectos.Empleados FROM 'rrhh_user'@'localhost';

-- Quitamos el permiso de INSERT en Equipos_Empleados temporalmente
REVOKE INSERT ON m0484_bdd_gestionproyectos.Equipos_Empleados FROM 'rrhh_user'@'localhost';

-- Aplicamos los cambios
FLUSH PRIVILEGES;

-- ==========================================
-- 9. REVOCACIÓN COMPLETA DE PERMISOS
-- ==========================================

-- Removemos TODOS los privilegios del desarrollador
-- Útil cuando alguien cambia de rol o deja la empresa
REVOKE ALL PRIVILEGES ON m0484_bdd_gestionproyectos.* FROM 'dev_ucademy1'@'localhost';

-- Aplicamos los cambios
FLUSH PRIVILEGES;

-- ==========================================
-- 10. ELIMINACIÓN DE USUARIOS
-- ==========================================

-- Verificamos que el usuario no tenga sesiones activas antes de eliminarlo
-- Esta consulta muestra las conexiones actuales (requiere privilegios de administrador)
SELECT user, host FROM mysql.user WHERE user = 'dev_ucademy1';

-- Eliminamos el usuario desarrollador completamente del sistema
-- Esto también revoca automáticamente todos sus permisos
DROP USER IF EXISTS 'dev_ucademy1'@'localhost';

-- Verificamos que el usuario fue eliminado
SELECT user, host FROM mysql.user WHERE user = 'dev_ucademy1';

-- ==========================================
-- 11. CREACIÓN DE USUARIO CON PERMISOS CON GRANT OPTION
-- ==========================================

-- Creamos un usuario supervisor que puede otorgar sus permisos a otros
-- WITH GRANT OPTION permite que este usuario delegue sus privilegios
CREATE USER 'supervisor_ucademy1'@'localhost' IDENTIFIED BY 'Super2024!';

GRANT SELECT, INSERT, UPDATE ON m0484_bdd_gestionproyectos.* 
TO 'supervisor_ucademy1'@'localhost' 
WITH GRANT OPTION;

-- Este usuario ahora puede otorgar permisos SELECT, INSERT y UPDATE a otros usuarios

- ==========================================
-- 12. CONSULTAS ÚTILES PARA ADMINISTRACIÓN
-- ==========================================

-- Listar todos los usuarios del sistema MySQL
SELECT user, host FROM mysql.user;
SELECT * FROM mysql.user;

-- Ver todos los permisos de todos los usuarios en la base de datos específica
SELECT * FROM mysql.db 
WHERE Db = 'm0484_bdd_gestionproyectos';

-- Ver permisos a nivel de tabla
SELECT * FROM mysql.tables_priv 
-- WHERE Db = 'm0484_bdd_gestionproyectos';

SHOW DATABASES;


SHOW TABLES;
