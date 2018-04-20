-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 20, 2018 at 08:13 PM
-- Server version: 5.6.38
-- PHP Version: 7.2.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `gestionTouristique`
--
CREATE DATABASE IF NOT EXISTS `gestionTouristique` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `gestionTouristique`;

-- --------------------------------------------------------

--
-- Table structure for table `activity`
--

CREATE TABLE `activity` (
  `id` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `description` text NOT NULL,
  `address` varchar(50) NOT NULL,
  `longitude` float NOT NULL,
  `latitude` float NOT NULL,
  `url` varchar(50) NOT NULL,
  `image` varchar(25) NOT NULL,
  `price` float NOT NULL,
  `schedule` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `activity`
--

INSERT INTO `activity` (`id`, `title`, `description`, `address`, `longitude`, `latitude`, `url`, `image`, `price`, `schedule`) VALUES
(1, 'Stade Olympique', 'Le stade olympique de Montréal est un stade omnisports couvert d\'une capacité maximale de 65 000 places, conçu par l\'architecte français Roger Taillibert et construit pour les Jeux olympiques d\'été de 1976.', '4545, avenue Pierre-de-Coubertin', -73.5243, 45.5546, 'http://parcolympique.qc.ca', 'stadeolympique', 0, 0),
(2, 'Insectarium', 'L\'Insectarium de Montréal est un musée d\'histoire naturelle éducatif, culturel et scientifique de Montréal, au Québec ayant pour objectif de sensibiliser la population au monde des insectes.', '4581, rue Sherbrooke Est.', -73.5305, 45.5576, 'http://espacepourlavie.ca/insectarium', '', 10, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `activity`
--
ALTER TABLE `activity`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `activity`
--
ALTER TABLE `activity`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
