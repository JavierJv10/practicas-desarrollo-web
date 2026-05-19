CREATE TABLE IF NOT EXISTS `Departamentos` (
	`pk_DepartamentoId` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`NombreDepartamento` VARCHAR(80) NOT NULL,
	`DescripcionDepartamento` VARCHAR(255) NOT NULL,
	PRIMARY KEY(`pk_DepartamentoId`)
);


CREATE TABLE IF NOT EXISTS `Especialidades` (
	`pk_EspecialidadId` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`NombreEspecialidad` VARCHAR(80) NOT NULL,
	`DescripcionEspecialidad` VARCHAR(255) NOT NULL,
	PRIMARY KEY(`pk_EspecialidadId`)
);


CREATE TABLE IF NOT EXISTS `Doctores` (
	`pk_DoctoriId` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`NombreDoctor` VARCHAR(80) NOT NULL,
	`ApellidoDoctor` VARCHAR(80) NOT NULL,
	`EmailDoctor` VARCHAR(80) NOT NULL,
	`TelefonoDoctor` VARCHAR(30) NOT NULL,
	`fk_DepartamentoId` INTEGER NOT NULL,
	PRIMARY KEY(`pk_DoctoriId`)
);


CREATE TABLE IF NOT EXISTS `DoctoresEspecialidades` (
	`fk_DoctorId` INTEGER NOT NULL,
	`fk_EspecialidadId` INTEGER NOT NULL,
	PRIMARY KEY(`fk_DoctorId`, `fk_EspecialidadId`)
);


CREATE TABLE IF NOT EXISTS `Pacientes` (
	`pk_PacienteiId` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`NombrePaciente` VARCHAR(80) NOT NULL,
	`ApellidoPaciente` VARCHAR(80) NOT NULL,
	`EmailPaciente` VARCHAR(80) NOT NULL,
	`TelefonoPaciente` VARCHAR(30) NOT NULL,
	`DireccionPaciente` VARCHAR(150) NOT NULL,
	`FechaNacimientoPaciente` DATE NOT NULL,
	PRIMARY KEY(`pk_PacienteiId`)
);


CREATE TABLE IF NOT EXISTS `HistoriaClinica` (
	`pk_HistoriaClinicaId` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`FechaAperturaHistoria` DATE NOT NULL,
	`Observaciones` TEXT NOT NULL,
	`fk_PacienteId` INTEGER NOT NULL,
	PRIMARY KEY(`pk_HistoriaClinicaId`)
);


CREATE TABLE IF NOT EXISTS `MotivosCita` (
	`pk_Motivo_Id` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`MotivoDescripcion` VARCHAR(80) NOT NULL,
	PRIMARY KEY(`pk_Motivo_Id`)
);


CREATE TABLE IF NOT EXISTS `EstadosCita` (
	`pk_EstadoCita_Id` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`EstadoCitaDescripcion` VARCHAR(80) NOT NULL,
	PRIMARY KEY(`pk_EstadoCita_Id`)
);


CREATE TABLE IF NOT EXISTS `TiposCita` (
	`pk_TipoCita_Id` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`TipoCitaDescripcion` VARCHAR(80) NOT NULL,
	PRIMARY KEY(`pk_TipoCita_Id`)
);


CREATE TABLE IF NOT EXISTS `Citas` (
	`pk_CitaId` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`FechaHoraCita` DATETIME NOT NULL,
	`fk_DoctorId` INTEGER NOT NULL,
	`fk_Pacienteid` INTEGER NOT NULL,
	`fk_MotivoCitaId` INTEGER NOT NULL,
	`fk_EstadoCitaId` INTEGER NOT NULL,
	`fk_TipoCitaId` INTEGER NOT NULL,
	`DiagnosticoCita` TEXT NOT NULL,
	PRIMARY KEY(`pk_CitaId`)
);


CREATE TABLE IF NOT EXISTS `Recetas` (
	`pk_RecetaId` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`fk_CitaId` INTEGER NOT NULL,
	`FechaReceta` DATE NOT NULL,
	`ObsReceta` TEXT NOT NULL,
	PRIMARY KEY(`pk_RecetaId`)
);


CREATE TABLE IF NOT EXISTS `Medicamentos` (
	`pk_MedicamentoId` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`NombreMedicamento` VARCHAR(80) NOT NULL,
	`DescripcionMedicamento` VARCHAR(255) NOT NULL,
	PRIMARY KEY(`pk_MedicamentoId`)
);


CREATE TABLE IF NOT EXISTS `RecetasMedicamentos` (
	`fk_RecetaId` INTEGER NOT NULL,
	`fk_MedicamentoId` INTEGER NOT NULL,
	`Dosis` VARCHAR(100) NOT NULL,
	`Duracion` VARCHAR(100) NOT NULL,
	PRIMARY KEY(`fk_RecetaId`, `fk_MedicamentoId`)
);


CREATE TABLE IF NOT EXISTS `PruebasDiagnosticas` (
	`pk_PruebaDiagnosticaId` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`fk_CitaId` INTEGER NOT NULL,
	`FechaPrueba` DATE NOT NULL,
	`Resultado` TEXT NOT NULL,
	`fk_TipoPruebaId` INTEGER NOT NULL,
	PRIMARY KEY(`pk_PruebaDiagnosticaId`)
);


CREATE TABLE IF NOT EXISTS `TiposPrueba` (
	`pk_TipoPruebaId` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`DescripcionTipoPrueba` VARCHAR(100) NOT NULL,
	PRIMARY KEY(`pk_TipoPruebaId`)
);


ALTER TABLE `Doctores`
ADD FOREIGN KEY(`fk_DepartamentoId`) REFERENCES `Departamentos`(`pk_DepartamentoId`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `DoctoresEspecialidades`
ADD FOREIGN KEY(`fk_DoctorId`) REFERENCES `Doctores`(`pk_DoctoriId`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `DoctoresEspecialidades`
ADD FOREIGN KEY(`fk_EspecialidadId`) REFERENCES `Especialidades`(`pk_EspecialidadId`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `HistoriaClinica`
ADD FOREIGN KEY(`fk_PacienteId`) REFERENCES `Pacientes`(`pk_PacienteiId`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `Citas`
ADD FOREIGN KEY(`fk_DoctorId`) REFERENCES `Doctores`(`pk_DoctoriId`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `Citas`
ADD FOREIGN KEY(`fk_Pacienteid`) REFERENCES `Pacientes`(`pk_PacienteiId`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `Citas`
ADD FOREIGN KEY(`fk_MotivoCitaId`) REFERENCES `MotivosCita`(`pk_Motivo_Id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `Citas`
ADD FOREIGN KEY(`fk_EstadoCitaId`) REFERENCES `EstadosCita`(`pk_EstadoCita_Id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `Citas`
ADD FOREIGN KEY(`fk_TipoCitaId`) REFERENCES `TiposCita`(`pk_TipoCita_Id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `Recetas`
ADD FOREIGN KEY(`fk_CitaId`) REFERENCES `Citas`(`pk_CitaId`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `RecetasMedicamentos`
ADD FOREIGN KEY(`fk_RecetaId`) REFERENCES `Recetas`(`pk_RecetaId`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `RecetasMedicamentos`
ADD FOREIGN KEY(`fk_MedicamentoId`) REFERENCES `Medicamentos`(`pk_MedicamentoId`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `PruebasDiagnosticas`
ADD FOREIGN KEY(`fk_CitaId`) REFERENCES `Citas`(`pk_CitaId`)
ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE `PruebasDiagnosticas`
ADD FOREIGN KEY(`fk_TipoPruebaId`) REFERENCES `TiposPrueba`(`pk_TipoPruebaId`)
ON UPDATE NO ACTION ON DELETE NO ACTION;