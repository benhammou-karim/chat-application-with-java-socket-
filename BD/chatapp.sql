-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jan 12, 2023 at 06:00 PM
-- Server version: 5.7.36
-- PHP Version: 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `chatapp`
--

-- --------------------------------------------------------

--
-- Table structure for table `amis`
--

DROP DATABASE IF EXISTS `chatapp`;
CREATE Database chatapp;

use `chatapp`;

DROP TABLE IF EXISTS `amis`;
CREATE TABLE IF NOT EXISTS `amis` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_client` int(11) NOT NULL,
  `amis` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_client` (`id_client`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `amis`
--

INSERT INTO `amis` (`id`, `id_client`, `amis`) VALUES
(57, 3, 'karim'),
(58, 3, 'hamza'),
(60, 5, 'hamza'),
(61, 4, 'taha'),
(62, 4, 'oussama'),
(63, 4, 'karim'),
(64, 1, 'hamza'),
(65, 1, 'oussama'),
(67, 5, 'karim'),
(68, 1, 'taha');

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`id`, `login`, `password`) VALUES
(1, 'karim', '123'),
(3, 'oussama', '123'),
(4, 'hamza', '123'),
(5, 'taha', '123'),
(6, 'said', '123'),
(7, 'zz', '123');

-- --------------------------------------------------------

--
-- Table structure for table `groupe`
--

DROP TABLE IF EXISTS `groupe`;
CREATE TABLE IF NOT EXISTS `groupe` (
  `id_group` int(11) NOT NULL AUTO_INCREMENT,
  `nom_groupe` varchar(50) NOT NULL,
  `admin_id` int(11) NOT NULL,
  PRIMARY KEY (`id_group`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `groupe`
--

INSERT INTO `groupe` (`id_group`, `nom_groupe`, `admin_id`) VALUES
(40, 'GROUP1', 1),
(41, 'GROUP2', 4);

-- --------------------------------------------------------

--
-- Table structure for table `groupe_users`
--

DROP TABLE IF EXISTS `groupe_users`;
CREATE TABLE IF NOT EXISTS `groupe_users` (
  `id_groupe` int(11) NOT NULL,
  `id_client` int(11) NOT NULL,
  PRIMARY KEY (`id_groupe`,`id_client`),
  KEY `fk_clients` (`id_client`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `groupe_users`
--

INSERT INTO `groupe_users` (`id_groupe`, `id_client`) VALUES
(41, 1),
(40, 3),
(40, 4),
(41, 5);

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
CREATE TABLE IF NOT EXISTS `notification` (
  `id_notifi` int(11) NOT NULL AUTO_INCREMENT,
  `admin` varchar(25) NOT NULL,
  `amis` varchar(25) NOT NULL,
  PRIMARY KEY (`id_notifi`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=latin1;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `amis`
--
ALTER TABLE `amis`
  ADD CONSTRAINT `fk_client` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `groupe_users`
--
ALTER TABLE `groupe_users`
  ADD CONSTRAINT `fk_clients` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_groupe` FOREIGN KEY (`id_groupe`) REFERENCES `groupe` (`id_group`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;