-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 24, 2018 at 08:42 PM
-- Server version: 5.6.38
-- PHP Version: 7.2.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `gestionTouristique`
--

-- --------------------------------------------------------

--
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  `id` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `description` text NOT NULL,
  `address` varchar(50) NOT NULL,
  `latitude` float NOT NULL,
  `longitude` float NOT NULL,
  `url` varchar(100) NOT NULL,
  `image` varchar(25) NOT NULL,
  `tags` varchar(150) NOT NULL,
  `price` float NOT NULL,
  `schedule` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `activity`
--

INSERT INTO `activity` (`id`, `title`, `description`, `address`, `latitude`, `longitude`, `url`, `image`, `tags`, `price`, `schedule`) VALUES
(1, 'Stade Olympique', 'Le stade olympique de Montréal est un stade omnisports couvert d\'une capacité maximale de 65 000 places, conçu par l\'architecte français Roger Taillibert et construit pour les Jeux olympiques d\'été de 1976.', '4545, avenue Pierre-de-Coubertin', 45.5546, -73.5243, 'http://parcolympique.qc.ca', 'stade_olympique', 'sport, olympic,family,landmark', 0, 0),
(2, 'Insectarium', 'L\'Insectarium de Montréal est un musée d\'histoire naturelle éducatif, culturel et scientifique de Montréal, au Québec ayant pour objectif de sensibiliser la population au monde des insectes.', '4581, rue Sherbrooke Est.', 45.5576, -73.5305, 'http://espacepourlavie.ca/insectarium', 'insectarium', '', 10, 0),
(3, 'Parc du Mont-Royal', 'Avec ses trois sommets, ce lieu emblématique que nous surnommons affectueusement « la montagne » couvre 10 km2 au cœur de la ville.\r\nLes instances municipales et le Gouvernement du Québec ayant reconnu l\'importance de protéger ses qualités naturelles et culturelles,\r\nle mont Royal est maintenant site patrimonial.\r\nDécouvrez les trois sommets du mont Royal lors d’une randonnée guidée qui vous fera découvrir tous les secrets de la montagne!', '1260, chemin Remembrance Montréal (Québec) H3H 1A2', 45.5048, -73.5878, 'https://lemontroyal.qc.ca/fr', 'parc_mont_royal', '', 0, 0),
(4, 'Vieux Montréal', 'Le 8 janvier 1964, le gouvernement du Québec reconnaissait les qualités culturelles et patrimoniales exceptionnelles du Vieux-Montréal, \r\nen le déclarant «arrondissement historique de Montréal». Depuis, la Ville de Montréal et ses partenaires se sont employés activement à préserver\r\net à mettre en valeur le Vieux-Montréal, le lieu de fondation de la ville, aujourd’hui un quartier dynamique,une composante centrale du paysage\r\nurbain et principal attrait touristique de la ville, cher aux Montréalais et apprécié par ses visiteurs.', 'Montréal, Québec, Canada', 45.5075, -73.5544, 'http://www.vieuxmontreal.ca/fr/explorer/activites/', 'old_mtrl', '', 0, 0),
(5, 'Parc Jean-Drapeau', 'Le parc Jean-Drapeau (anciennement Parc des Îles) est un grand parc de Montréal situé en plein centre du fleuve Saint-Laurent. \r\nIl est composé de deux îles, l\'île Sainte-Hélène et l\'île Notre-Dame.\r\nL\'île Notre-Dame a accueilli chaque année, depuis 1978, le Grand Prix de Montréal sur le circuit Gilles-Villeneuve.\r\nElle abrite la plage du parc Jean-Drapeau,maintenant plage Jean-Doré, le Bassin olympique, \r\nle Casino de Montréal (pavillon de la France et pavillon du Québec lors de l\'Expo 67), \r\nles jardins des Floralies (Floralies Internationales de 1980) et le pavillon du Canada.\r\n\r\nL\'île Sainte-Hélène comprend le Complexe aquatique de l\'île Sainte-Hélène (piscines extérieures), \r\nLa Ronde, La Biosphère (pavillon des États-Unis lors de l\'expo 67), \r\nle Musée Stewart. Celle-ci est aussi le site de spectacles extérieurs et de festivals,\r\ndont la fête des Neiges de Montréal, La Fête des enfants de Montréal, le festival metal Heavy MTL, et dès 2014,\r\nun festival EDM nommé île Soniq.', '1 Circuit Gilles Villeneuve, Montréal, QC H3C 1A9', 45.5141, -73.5337, 'http://www.parcjeandrapeau.com/', 'jean_drap', '', 0, 0),
(6, 'Casino de Montréal', 'le Casino de Montréal est un casino situé sur l\'île Notre-Dame à Montréal au Québec ,Il compte neuf étages où l’on retrouve plus \r\nde 150 tables de jeu, 4 800 machines à sous et jeux électroniques ainsi que l’un des plus grands jeux de Keno en Amérique. \r\nIl abrite également cinq restaurants et une salle de 500 places, le Cabaret du Casino de Montréal, qui accueille des spectacles de\r\nvariétés et des revues musicales. Il s\'agit de l\'unique salle de spectacle à Montréal où l\'on peut prendre un repas.  \r\nIl est le plus grand casino du Canada.', '1 Avenue du Casino, Montréal, QC H3C 4W7', 45.5054, -73.5258, 'http://casinos.lotoquebec.com/fr/montreal/accueil', 'casino', '', 0, 0),
(7, 'La Ronde', 'La Ronde est un parc d\'attractions de 591 000 m2 (146 acres) situé sur l\'île Sainte-Hélène, à Montréal. \r\nLe parc est ouvert de la mi-mai à la fin octobre. Le parc, qui compte exactement 42 manèges et attractions, \r\nattire environ 1,2 million de visiteurs chaque année1.\r\nPlusieurs évènements se tiennent dans le parc à chaque année, dont l\'International des Feux Loto-Québec. Cette compétition d\'art pyrotechnique, \r\nqui bénéficie d\'une renommée internationale, s\'y tient pendant les mois de juin à août. La grande fête de l\'Halloween est un évènement qui,\r\npour sa part, se tient durant les fins de semaine du mois d\'octobre. ', '22 Chemin Macdonald, Montréal, QC H3C 6A3', 45.5215, -73.536, 'https://www.laronde.com/fr/larondefr', 'laronde', '', 0, 0),
(8, 'Vieux-Port de Montréal', 'Le Vieux-Port de Montréal est le port historique de la ville de Montréal. Situé sur la berge nord du fleuve\r\nSaint-Laurent, il s\'étend sur plus de deux kilomètres au sud du Vieux-Montréal.\r\nLe Vieux-Port offre aux Montréalais et aux touristes un accès à de nombreuses activités été comme hiver. \r\nLa Société du Vieux-Port, avec l\'aide de partenaires, veille à l\'animation du lieu via des évènements culturels tel\r\nque le festival Montréal en Lumière, l\'Igloofest, le Festival des Grands Voiliers et le Festival International Reggae. ', '333 Rue de la Commune O, Montréal, QC H2Y 2E2', 45.5125, -73.5485, 'http://www.vieuxportdemontreal.com/', 'old_mtrl', '', 0, 0);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
