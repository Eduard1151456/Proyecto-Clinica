-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 22, 2023 at 06:05 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `registro_covid`
--

-- --------------------------------------------------------

--
-- Table structure for table `clinica`
--

CREATE TABLE `clinica` (
  `nombre` varchar(50) NOT NULL,
  `direccion` varchar(100) NOT NULL,
  `documento_paciente` varchar(20) NOT NULL,
  `documento_personal` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `paciente`
--

CREATE TABLE `paciente` (
  `documento` varchar(20) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `direccion` varchar(100) NOT NULL,
  `telefono` varchar(20) NOT NULL,
  `genero` varchar(20) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `estado` varchar(50) NOT NULL,
  `lugar_procedencia` int(100) NOT NULL,
  `fecha_deteccion` date NOT NULL,
  `tratado` varchar(50) NOT NULL,
  `personas_posible_contacto` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `personal_salud`
--

CREATE TABLE `personal_salud` (
  `documento` varchar(20) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `direccion` varchar(100) NOT NULL,
  `telefono` varchar(20) NOT NULL,
  `genero` varchar(20) NOT NULL,
  `fechaNacimiento` date NOT NULL,
  `estado` varchar(20) NOT NULL,
  `especialidad` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `clinica`
--
ALTER TABLE `clinica`
  ADD KEY `documento_paciente` (`documento_paciente`,`documento_personal`),
  ADD KEY `documento_personal` (`documento_personal`);

--
-- Indexes for table `paciente`
--
ALTER TABLE `paciente`
  ADD PRIMARY KEY (`documento`);

--
-- Indexes for table `personal_salud`
--
ALTER TABLE `personal_salud`
  ADD PRIMARY KEY (`documento`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `clinica`
--
ALTER TABLE `clinica`
  ADD CONSTRAINT `clinica_ibfk_1` FOREIGN KEY (`documento_paciente`) REFERENCES `paciente` (`documento`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `clinica_ibfk_2` FOREIGN KEY (`documento_personal`) REFERENCES `personal_salud` (`documento`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
