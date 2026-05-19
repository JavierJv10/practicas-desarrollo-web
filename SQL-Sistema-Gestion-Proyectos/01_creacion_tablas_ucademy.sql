DROP DATABASE IF EXISTS ucademy_2026_01_28;

CREATE DATABASE IF NOT EXISTS ucademy_2026_01_28;
USE ucademy_2026_01_28;

CREATE TABLE IF NOT EXISTS `Empleados` (
	`pk_EmpleadoId` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`Nombre` VARCHAR(80) NOT NULL,
	`Apellido1` VARCHAR(80) NOT NULL,
	`Apellido2` VARCHAR(80) NOT NULL,
	`Mail` VARCHAR(120) NOT NULL,
	`fk_PuestoTrabajoId` INTEGER NOT NULL,
	PRIMARY KEY(`pk_EmpleadoId`)
);


CREATE TABLE IF NOT EXISTS `Equipos` (
	`pk_EquipoId` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`NombreEquipo` VARCHAR(80) NOT NULL,
	`DescEquipo` VARCHAR(255) NOT NULL,
	PRIMARY KEY(`pk_EquipoId`)
);


CREATE TABLE IF NOT EXISTS `Equipos_Empleados` (
	`fk_EquipoId` INTEGER NOT NULL,
	`fk_EmpleadoId` INTEGER NOT NULL,
	PRIMARY KEY(`fk_EquipoId`, `fk_EmpleadoId`)
);


CREATE TABLE IF NOT EXISTS `Proyectos` (
	`pk_ProyectoId` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`NombreProyecto` VARCHAR(80) NOT NULL,
	`DescProyecto` VARCHAR(255) NOT NULL,
	`FechaInicio` DATE NOT NULL,
	`FechaFin` DATE NOT NULL,
	PRIMARY KEY(`pk_ProyectoId`)
);


CREATE TABLE IF NOT EXISTS `Tareas` (
	`pk_TareaId` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`NombreTarea` VARCHAR(80) NOT NULL,
	`DescTarea` VARCHAR(255) NOT NULL,
	`FechaInicio` DATE NOT NULL,
	`FechaFin` DATE NOT NULL,
	`fk_ProyectoId` INTEGER NOT NULL,
	`fk_EmpleadoId` INTEGER NOT NULL,
	`fk_EstadoId` INTEGER,
	PRIMARY KEY(`pk_TareaId`)
);


CREATE TABLE IF NOT EXISTS `Estados` (
	`pk_EstadoId` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`NombreEstado` VARCHAR(80) NOT NULL,
	PRIMARY KEY(`pk_EstadoId`)
);


CREATE TABLE IF NOT EXISTS `PuestosTrabajo` (
	`pk_PuestoTrabajoId` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`NombrePuestoTrabajo` VARCHAR(80) NOT NULL,
	PRIMARY KEY(`pk_PuestoTrabajoId`)
);


ALTER TABLE `Equipos_Empleados`
ADD FOREIGN KEY(`fk_EquipoId`) REFERENCES `Equipos`(`pk_EquipoId`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `Equipos_Empleados`
ADD FOREIGN KEY(`fk_EmpleadoId`) REFERENCES `Empleados`(`pk_EmpleadoId`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `Tareas`
ADD FOREIGN KEY(`fk_ProyectoId`) REFERENCES `Proyectos`(`pk_ProyectoId`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `Tareas`
ADD FOREIGN KEY(`fk_EmpleadoId`) REFERENCES `Empleados`(`pk_EmpleadoId`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `Tareas`
ADD FOREIGN KEY(`fk_EstadoId`) REFERENCES `Estados`(`pk_EstadoId`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `Empleados`
ADD FOREIGN KEY(`fk_PuestoTrabajoId`) REFERENCES `PuestosTrabajo`(`pk_PuestoTrabajoId`)
ON UPDATE NO ACTION ON DELETE NO ACTION;