-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 27, 2018 at 02:10 PM
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
  `address` varchar(100) NOT NULL,
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
(2, 'Insectarium', 'L\'Insectarium de Montréal est un musée d\'histoire naturelle éducatif, culturel et scientifique de Montréal, au Québec ayant pour objectif de sensibiliser la population au monde des insectes.', '4581, rue Sherbrooke Est.', 45.5576, -73.5305, 'http://espacepourlavie.ca/insectarium', 'insectarium', 'family, museum,science', 10, 0),
(3, 'Parc du Mont-Royal', 'Avec ses trois sommets, ce lieu emblématique que nous surnommons affectueusement « la montagne » couvre 10 km2 au cœur de la ville.\r\nLes instances municipales et le Gouvernement du Québec ayant reconnu l\\’importance de protéger ses qualités naturelles et culturelles,\r\nle mont Royal est maintenant site patrimonial.\r\nDécouvrez les trois sommets du mont Royal lors d\\’une randonnée guidée qui vous fera découvrir tous les secrets de la montagne!\r\n\r\nNote: Pour de plus amples renseignements sur les visites guidées, les horaires, les tarifs ou d\\\'autres activités dans le parc,\r\nveuillez appeler au 514 843-8240, poste 0 ou visitez notre site web: lemontroyal.qc.ca\r\nNos bureaux administratifs, tout comme le service d\\’information par courriel, sont ouverts du lundi au vendredi, de 9 h 30 à 16 h 30.', '1260, chemin Remembrance Montréal (Québec) H3H 1A2', 45.5048, -73.5878, 'https://lemontroyal.qc.ca/fr', 'parc_mont_royal', 'plus_populaire,plein_air,familier,physique,culturelle,recreative', 0, 0),
(4, 'Vieux Montréal', 'Le 8 janvier 1964, le gouvernement du Québec reconnaissait les qualités culturelles et patrimoniales exceptionnelles du Vieux-Montréal, \r\nen le déclarant «arrondissement historique de Montréal». Depuis, la Ville de Montréal et ses partenaires se sont employés activement à préserver\r\net à mettre en valeur le Vieux-Montréal, le lieu de fondation de la ville, aujourd’hui un quartier dynamique,une composante centrale du paysage\r\nurbain et principal attrait touristique de la ville, cher aux Montréalais et apprécié par ses visiteurs.', 'Montréal, Québec, Canada', 45.5075, -73.5544, 'http://www.vieuxmontreal.ca/fr/explorer/activites/', 'old_mtrl', 'plus_populaire,familier,plein_air,culturelle,recreative,gastronomique', 0, 0),
(5, 'Parc Jean-Drapeau', 'Le parc Jean-Drapeau (anciennement Parc des Îles) est un grand parc de Montréal situé en plein centre du fleuve Saint-Laurent. \r\nIl est composé de deux îles, l\'île Sainte-Hélène et l\'île Notre-Dame.\r\nL\'île Notre-Dame a accueilli chaque année, depuis 1978, le Grand Prix de Montréal sur le circuit Gilles-Villeneuve.\r\nElle abrite la plage du parc Jean-Drapeau,maintenant plage Jean-Doré, le Bassin olympique, \r\nle Casino de Montréal (pavillon de la France et pavillon du Québec lors de l\'Expo 67), \r\nles jardins des Floralies (Floralies Internationales de 1980) et le pavillon du Canada.\r\n\r\nL\'île Sainte-Hélène comprend le Complexe aquatique de l\'île Sainte-Hélène (piscines extérieures), \r\nLa Ronde, La Biosphère (pavillon des États-Unis lors de l\'expo 67), \r\nle Musée Stewart. Celle-ci est aussi le site de spectacles extérieurs et de festivals,\r\ndont la fête des Neiges de Montréal, La Fête des enfants de Montréal, le festival metal Heavy MTL, et dès 2014,\r\nun festival EDM nommé île Soniq.', '1 Circuit Gilles Villeneuve, Montréal, QC H3C 1A9', 45.5141, -73.5337, 'http://www.parcjeandrapeau.com/', 'jean_drap', 'plus_populaire,familier,plein_air,culturelle,recreative', 0, 0),
(6, 'Casino de Montréal', 'le Casino de Montréal est un casino situé sur l\'île Notre-Dame à Montréal au Québec ,Il compte neuf étages où l’on retrouve plus \r\nde 150 tables de jeu, 4 800 machines à sous et jeux électroniques ainsi que l’un des plus grands jeux de Keno en Amérique. \r\nIl abrite également cinq restaurants et une salle de 500 places, le Cabaret du Casino de Montréal, qui accueille des spectacles de\r\nvariétés et des revues musicales. Il s\'agit de l\'unique salle de spectacle à Montréal où l\'on peut prendre un repas.  \r\nIl est le plus grand casino du Canada.', '1 Avenue du Casino, Montréal, QC H3C 4W7', 45.5054, -73.5258, 'http://casinos.lotoquebec.com/fr/montreal/accueil', 'casino', 'plus_populaire,recreative,gastronomique,seulement_pour_adultes', 0, 0),
(7, 'La Ronde', 'La Ronde est un parc d\'attractions de 591 000 m2 (146 acres) situé sur l\'île Sainte-Hélène, à Montréal. \r\nLe parc est ouvert de la mi-mai à la fin octobre. Le parc, qui compte exactement 42 manèges et attractions, \r\nattire environ 1,2 million de visiteurs chaque année1.\r\nPlusieurs évènements se tiennent dans le parc à chaque année, dont l\'International des Feux Loto-Québec. Cette compétition d\'art pyrotechnique, \r\nqui bénéficie d\'une renommée internationale, s\'y tient pendant les mois de juin à août. La grande fête de l\'Halloween est un évènement qui,\r\npour sa part, se tient durant les fins de semaine du mois d\'octobre. ', '22 Chemin Macdonald, Montréal, QC H3C 6A3', 45.5215, -73.536, 'https://www.laronde.com/fr/larondefr', 'laronde', 'plus_populaire,familier,plein_air,culturelle,recreative,gastronomique', 0, 0),
(8, 'Vieux-Port de Montréal', 'Le Vieux-Port de Montréal est le port historique de la ville de Montréal. Situé sur la berge nord du fleuve\r\nSaint-Laurent, il s\'étend sur plus de deux kilomètres au sud du Vieux-Montréal.\r\nLe Vieux-Port offre aux Montréalais et aux touristes un accès à de nombreuses activités été comme hiver. \r\nLa Société du Vieux-Port, avec l\'aide de partenaires, veille à l\'animation du lieu via des évènements culturels tel\r\nque le festival Montréal en Lumière, l\'Igloofest, le Festival des Grands Voiliers et le Festival International Reggae. ', '333 Rue de la Commune O, Montréal, QC H2Y 2E2', 45.5125, -73.5485, 'http://www.vieuxportdemontreal.com/', 'old_mtrl', 'plus_populaire,familier,plein_air,culturelle,recreative,gastronomique', 0, 0),
(13, 'Espace pour la vie', 'Espace pour la vie, un lieu exceptionnel pour explorer les mille et une facettes de la vie et de la nature. \r\nRéunissant le Biodôme, l’Insectarium, le Jardin botanique et le Planétarium de Montréal, Espace pour la vie est un laboratoire vivant\r\n où l’infiniment petit prend des proportions gigantesques et l’infiniment grand est à portée de la main.\r\nPar ses actions de diffusion, de conservation, de recherche et d’éducation, Espace pour la vie accompagne l’humain pour \r\nmieux vivre la nature. Cette mission prend forme dans ses quatre institutions, lieux de découvertes par excellence, \r\nqui accueillent chaque année plus de 2 millions de visiteurs et offrent plus de 160 activités culturelles et scientifiques.', '4101, rue Sherbrooke Est, Montréal, QC. H1X 2B2', 45.5569, -73.5564, 'http://espacepourlavie.ca/en', 'espace_vie', 'plus_populaire,familier,culturelle,recreative', 0, 0),
(14, 'La Tour du stade olympique', 'Culminant à 165 mètres et inclinée suivant un angle de 45 degrés, la tour de Montréal est la plus haute tour inclinée au monde, \r\nce qui est certifié par le Livre Guinness des records. Un funiculaire vitré à deux étages transporte en un clin d’œil 76 passagers\r\n en une fois à l’observatoire de la tour. Les visiteurs y trouvent une vue panoramique qui s’étend jusqu’à 80 km dans chaque direction et le guide \r\nMichelin lui a décerné trois étoiles.  C\'est le seul au monde qui fonctionne sur une structure courbée ; un système hydraulique permet en effet à la\r\n cabine de demeurer horizontale pendant les deux minutes que dure l\'ascension. ', 'Parc olympique de Montréal 4141, av. Pierre-de-Cou', 45.5598, -73.5515, 'https://parcolympique.qc.ca/quoi-faire/la-tour/', 'tour_olymp', 'plus_populaire,familier,culturelle,recreative', 0, 0),
(15, 'Montréal, visite gourmande Mile End', 'Une visite qui tente d\'expliquer comment le Mile End a acquis sa réputation d\'un des quartiers les plus hip de la métropole, sinon du pays.\r\nLe Mile End est un quartier hipster, chic avec un petit air bum, qui donne l’impression d’un petit séjour à New York.\r\n Au nord-ouest du Plateau Mont-Royal, c’est le «dernier mile» avant les voies ferrées du Canadien Pacifique près du viaduc Van Horne. \r\n Sur l’avenue Fairmount, les rues Saint-Viateur et Bernard, l’avenue du Parc et le boulevard Saint-Laurent, on a repéré pour vous plusieurs\r\n restaurants et des boutiques sympathiques.', 'Mile End, Montreal, QC, Canada', 45.5236, -73.5985, 'https://localfoodtours.com/fr/montreal/tours/mile-end-montreal-visite-guidee/', 'mileend', 'plus_populaire,familier,recreative,gastronomique', 0, 0),
(16, 'Rafting Montréal', 'Même pas 12 minutes hors du centre-ville de Montréal, les Rapides de Lachine sont une escapade idéale pour des aventuriers\r\n qui aimeront apprendre comment faire du rafting dans un environnement amusant et sécurisé. La Club Raft offre des aventures\r\n de rafting d’un demi-jour sur les Rapides de Lachine qui sont idéales pour des débutants et aussi pour ceux avec un peu d’expérience. \r\n Chasser les vagues avec vos amis et camarades plus aventureux. On vous promet une poussée d’adrénaline de cette expérience comme rien\r\n d’autre! C’est l’endroit idéal pour trouver de l’enthousiasme avec une vue spectaculaire sur l’horizon de Montréal, au cœur de l’histoire \r\n et de la culture de la ville.', '8912 Boulevard LaSalle, LaSalle, QC H8P 1Z9, Canad', 45.4151, -73.6288, 'http://raftingmontreal.com/fr/coordonnees-adresse-rafting-montreal/', 'rafting', 'sportive,familier,recreative,plein_air', 0, 0),
(17, 'Montréal croisiere sur le Saint-Laurent', 'Optez pour une croisière sur le majestueux Saint-Laurent pour en découvrir toutes ses promesses. Montréal fait partie des neuf \r\n escales du Saint-Laurent et est l’un des ports d’embarquement et de débarquement. Montréal est sans contredit le point de départ\r\n idéal pour l’itinéraire Canada/ New England, pour des voyages vers l’Europe de même que pour des voyages sur la portion québécoise\r\n de Montréal aux Iles de la Madeleine.', 'Vieux-Port de Montréal Montreal, QC, Canada', 45.5075, -73.5544, 'https://www.croisieresaml.com/planifiez-votre-croisiere/montreal/tout/', 'croisiere_sl', 'plus_populaire,familier,recreative,gastronomique', 0, 0),
(18, 'Croisiere Souper sur le Saint-Laurent', 'Du Vieux-Port de Montréal, découvrez la métropole à partir du fleuve avec nos croisières guidées et nos croisières-brunchs.\r\n En souper-croisière, admirez des performances de cirque de calibre international ou profitez d’une vue panoramique sur les feux d’artifice.\r\n Ce voyage permet aux amoureux de se retrouver, loin du quotidien près du cœur, et aux proches de célébrer ensemble la belle saison.', 'Jacques-Cartier Pier Rue de la Commune E, Montréal', 45.5075, -73.5484, 'https://www.bateaumouche.ca/horaire-et-tarifs.html', 'eat_sl', 'gastronomique,familier,recreative', 0, 0),
(19, 'Montréal visite guide', 'Optez pour ce tour de ville classique et voyez les lieux incontournables de Montréal en quelques heures seulement.\r\n Votre guide vous fera \r\nrevivre l’histoire et ressentir l’ambiance unique de la deuxième ville francophone au monde. Découvrez un aperçu de Montréal avec sa culture\r\n diversifiée et des sites historiques.\r\n \r\n Parmi les tours guides, vous avez ces options: tour à pied, tour en bus, tour à vélo et tour en bateau.\r\n Vous pouvez aussi visiter les sites suivants: montreal-amphibus-tour.com, grayline.com ainsi que montrealinfo.com pour en connaître plus sur les options.', '', 0, 0, 'http://guidatour.qc.ca/visites-pour-groupes/en-autobus-visites-privees/montreal-classique/', 'visite_guide', 'plus_populaire,familier,culturelle,recreative', 0, 0),
(20, 'Basilique Notre-Dame', 'La basilique Notre-Dame de Montréal est la première église de style néo-gothique au Canada. Son histoire, \r\nmarquée par les sulpiciens depuis sa fondation, est indissociable de celle de la Ville de Montréal.\r\nElle témoigne de ses fondations catholiques et des liens constants entre les arts et la religion. \r\nSon style architectural a marqué un tournant dans la tradition religieuse et a été imité par plusieurs paroisses.\r\nLe décor intérieur de Notre-Dame suscite l’émerveillement. Son sanctuaire, ses couleurs, ses vitraux sont photographiés\r\npar près d’un million de visiteurs annuellement.', '110 Rue Notre-Dame Ouest, Montréal, QC H2Y 1T2', 45.5045, -73.5561, 'https://www.basiliquenotredame.ca/fr/activites-touristiques', 'basilique', 'plus_populaire,familier,culturelle', 0, 0),
(21, 'Oratoire Saint-Joseph du Mont-Royal', 'Fondé en 1904, l’Oratoire Saint-Joseph du Mont-Royal est le sanctuaire le plus important au monde dédié à saint Joseph et un lieu\r\nde pèlerinage de renommée mondiale associé à saint frère André, son fondateur.\r\nLa nature, la musique, les arts et l’histoire font de l’Oratoire Saint-Joseph l\'une des principales attractions touristiques de Montréal. \r\nL’architecture distinctive du bâtiment, et surtout son dôme imposant, conjuguée à son emplacement à flanc de montagne, \r\nlui confèrent une présence physique et symbolique remarquable à Montréal. Laissez-vous inspirer pas les concerts d\'orgue, \r\nles récitals de carillon et par les voix mélodieuses des Petits Chanteurs du Mont-Royal. En plus de l’exposition permanente de 200 crèches \r\nde plus de 100 pays, le Musée de l’Oratoire présente plusieurs expositions temporaires qui permettent aux visiteurs de découvrir \r\nle patrimoine religieux et artistique du Québec et d’ailleurs.Un trésor caché dévoile ses couleurs\r\nOffrant l’un des plus beaux points de vue de Montréal, l’Oratoire permet durant la belle saison d’harmoniser détente, \r\nrecueillement et nature. Une visite des jardins du chemin de la croix s’impose! Situé dans un boisé tout près de la basilique, \r\nce lieu calme et reposant est propice à la méditation, dans un décor horticole unique où l’on peut découvrir de majestueuses sculptures \r\nsignées Louis Parent.Venez découvrir les nombreuses facettes de ce site exceptionnel et de son patrimoine artistique et historique.\r\nL\'Oratoire Saint-Joseph du Mont-Royal, un lieu incontournable durant votre séjour à Montréal.', 'Chemin Queen-Mary, Montreal, QC H3V 1H6', 45.4933, -73.6204, 'www.saint-joseph.org/fr/nature/', 'orat_sj', 'plus_populaire,familier,plein_air,culturelle,physique', 0, 0),
(22, 'Musée des beaux-arts de Montréal', 'Le Musée des beaux-arts de Montréal, est le musée d’art le plus fréquenté au Canada. Sa collection encyclopédique, unique au pays, compte 41 000\r\nœuvres de l’Antiquité à nos jours.Le Musée présente tout au long de l’année d’importantes expositions temporaires. Des conférences, \r\nfilms et concerts variés en lien avec les expositions ont lieu dans l’auditorium. Des visites guidées sont offertes au public. En 2011, \r\nla musique fait son entrée comme partie intégrante de la programmation du Musée, ouvrant une nouvelle perspective sur les arts visuels, \r\ngrâce aux audioguides musicaux et à d’autres activités novatrices, organisées en collaboration avec la nouvelle fondation Arte Musica.\r\nLe Musée des beaux-arts de Montréal (Montreal Museum of Fine Arts), l’une des institutions artistiques les plus importantes du Canada,\r\net la plus ancienne, résulte d’un engagement à attirer une clientèle issue de toutes les couches de la société. ', '1380 Rue Sherbrooke O, Montréal, QC H3G 1J5, Canada', 45.4985, -73.5794, 'https://www.mbam.qc.ca/', 'mus_barts', 'plus_populaire,familier,culturelle,recreative', 0, 0),
(23, 'Musée Pointe-à-Callière', 'Pointe-à-Callière propose aux visiteurs de plonger dans l’histoire par le biais d’un parcours souterrain inédit au cœur de vestiges \r\narchéologiques. En début de visite, le spectacle multimédia immersif présente l’histoire de la ville, les ruines s’animent et le compte \r\nà rebours commence!. Pour le 375e  anniversaire de Montréal,Pointe-à-Callière a inauguré un nouveau pavillon mettant en valeur le Fort de \r\nVille-Marie - Pavillon Québecor ainsi que le collecteur de mémoires. \r\nEn plus des expositions permanentes qui mettent en valeur l’histoire et l’archéologie de Montréal, Pointe-à-Callière présente également \r\njusqu\'à quatre expositions temporaires à chaque année. Depuis son inauguration en 1992, le Musée a présenté une soixantaine d\'expositions \r\ntemporaires dont les thèmes touchent les grandes civilisations, l’archéologie d’ici et d’ailleurs, l’histoire et le patrimoine.\r\nDécouvrez cette riche programmation.', '350 Place Royale, Montreal, QC H2Y 3Y5', 45.5027, -73.5542, 'http://museesmontreal.org/fr/musees/pointe-a-calliere-cite-d-archeologie-et-d-histoire-de-montreal', 'pointe_a_caillere', 'plus_populaire,familier,culturelle,recreative', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `liked`
--

DROP TABLE IF EXISTS `liked`;
CREATE TABLE `liked` (
  `user_id` int(11) NOT NULL,
  `activity_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `liked`
--

INSERT INTO `liked` (`user_id`, `activity_id`) VALUES
(1, 6),
(1, 7);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `preferences` varchar(100) NOT NULL,
  `liked` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `name`, `email`, `password`, `preferences`, `liked`) VALUES
(1, 'lspoulin', 'lspoulin@gmail.com', 'ce92c299bb5d344bdac1f9c03396b34f', 'sport', 0),
(4, 'yest', 'lspoulin123@gmail.com', '098f6bcd4621d373cade4e832627b4f6', '', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `activity`
--
ALTER TABLE `activity`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `liked`
--
ALTER TABLE `liked`
  ADD KEY `FK_USER_ID` (`user_id`),
  ADD KEY `FK_ACTIVITY_ID` (`activity_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`,`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `activity`
--
ALTER TABLE `activity`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `liked`
--
ALTER TABLE `liked`
  ADD CONSTRAINT `LINK_ACTIVITY_ID` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`),
  ADD CONSTRAINT `LINK_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
