-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 01-Fev-2016 às 14:35
-- Versão do servidor: 5.6.21
-- PHP Version: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `2ainf`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `endereco`
--

CREATE TABLE IF NOT EXISTS `endereco` (
`id_endereco` int(11) NOT NULL,
  `rua` varchar(120) NOT NULL,
  `bairro` varchar(100) NOT NULL,
  `cidade` varchar(100) NOT NULL,
  `uf` varchar(2) NOT NULL,
  `id_pessoafk` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `endereco`
--

INSERT INTO `endereco` (`id_endereco`, `rua`, `bairro`, `cidade`, `uf`, `id_pessoafk`) VALUES
(1, 'Av Brasil', 'Jardim Eldorado', 'Vilhena', 'RO', 1),
(3, 'Av Brasil', 'Jardim Eldorado', 'Vilhena', 'RO', 7),
(4, 'Av Brasil', 'Jardim Eldorado', 'Vilhena', 'RO', 8);

-- --------------------------------------------------------

--
-- Estrutura da tabela `pessoa`
--

CREATE TABLE IF NOT EXISTS `pessoa` (
`id_pessoa` int(11) NOT NULL,
  `nome_pessoa` varchar(100) NOT NULL,
  `cpf` varchar(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `pessoa`
--

INSERT INTO `pessoa` (`id_pessoa`, `nome_pessoa`, `cpf`) VALUES
(1, 'Clayton Ferraz', '12345678911'),
(2, 'Jose das Couves', '12345678911'),
(3, 'Igor', '12345678911'),
(4, 'Joice', '12345678911'),
(5, 'Tiago', '12345678911'),
(7, 'Clayton Ferraz Andrade', '12345678911'),
(8, 'Clayton Ferraz Andrade', '12345678911');

-- --------------------------------------------------------

--
-- Estrutura da tabela `telefone`
--

CREATE TABLE IF NOT EXISTS `telefone` (
`id_telefone` int(11) NOT NULL,
  `numero` varchar(100) NOT NULL,
  `id_pessoafk` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `telefone`
--

INSERT INTO `telefone` (`id_telefone`, `numero`, `id_pessoafk`) VALUES
(1, '6992259283', 8);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `endereco`
--
ALTER TABLE `endereco`
 ADD PRIMARY KEY (`id_endereco`), ADD KEY `uf` (`uf`), ADD KEY `id_pessoafk` (`id_pessoafk`);

--
-- Indexes for table `pessoa`
--
ALTER TABLE `pessoa`
 ADD PRIMARY KEY (`id_pessoa`);

--
-- Indexes for table `telefone`
--
ALTER TABLE `telefone`
 ADD PRIMARY KEY (`id_telefone`), ADD KEY `id_pessoafk` (`id_pessoafk`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `endereco`
--
ALTER TABLE `endereco`
MODIFY `id_endereco` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `pessoa`
--
ALTER TABLE `pessoa`
MODIFY `id_pessoa` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `telefone`
--
ALTER TABLE `telefone`
MODIFY `id_telefone` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `endereco`
--
ALTER TABLE `endereco`
ADD CONSTRAINT `endereco_ibfk_1` FOREIGN KEY (`id_pessoafk`) REFERENCES `pessoa` (`id_pessoa`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limitadores para a tabela `telefone`
--
ALTER TABLE `telefone`
ADD CONSTRAINT `telefone_ibfk_1` FOREIGN KEY (`id_pessoafk`) REFERENCES `pessoa` (`id_pessoa`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
