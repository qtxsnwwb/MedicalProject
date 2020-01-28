-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: 2020-01-28 08:19:48
-- 服务器版本： 5.6.47
-- PHP Version: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `medical`
--
CREATE DATABASE IF NOT EXISTS `medical` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `medical`;

-- --------------------------------------------------------

--
-- 表的结构 `doctor`
--

DROP TABLE IF EXISTS `doctor`;
CREATE TABLE IF NOT EXISTS `doctor` (
  `DoctorID` varchar(50) NOT NULL,
  `DoctorName` varchar(30) NOT NULL,
  `DoctorIntro` varchar(255) DEFAULT NULL,
  `DoctorOffice` varchar(30) NOT NULL,
  PRIMARY KEY (`DoctorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `doctor`
--

INSERT INTO `doctor` (`DoctorID`, `DoctorName`, `DoctorIntro`, `DoctorOffice`) VALUES
('123', 'zhangsan', 'famous professional 125832u178923778234eu7832ye27843ryedfgu2k3r7fedh3i4u2ojd34ukewf', '1'),
('234', 'lisi', 'famous professional', '1'),
('279ea56f8d1d9966027025cd0357f721', 'liuyang', 'f23d34f43', '0'),
('wedwe231es', 'hanliu', 'WHUT professional...', '2');

-- --------------------------------------------------------

--
-- 表的结构 `report`
--

DROP TABLE IF EXISTS `report`;
CREATE TABLE IF NOT EXISTS `report` (
  `ReportID` varchar(50) NOT NULL,
  `PatientID` varchar(50) NOT NULL,
  `PatientName` varchar(30) NOT NULL,
  `PatientSex` varchar(30) NOT NULL,
  `PatientAge` varchar(30) NOT NULL,
  `Office` varchar(30) NOT NULL,
  `Doctor` varchar(30) NOT NULL,
  `Result` varchar(255) NOT NULL,
  `Medicine` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ReportID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `report`
--

INSERT INTO `report` (`ReportID`, `PatientID`, `PatientName`, `PatientSex`, `PatientAge`, `Office`, `Doctor`, `Result`, `Medicine`) VALUES
('123', '234', 'wangwu', 'man', '23', '1', 'zhangsan', '12ewsgqyjhe32bhebj23edbhjqdx', 'penicillin');

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `UserID` varchar(50) NOT NULL,
  `UserName` varchar(30) NOT NULL,
  `UserPass` varchar(30) NOT NULL,
  `UserPurview` int(30) NOT NULL,
  PRIMARY KEY (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`UserID`, `UserName`, `UserPass`, `UserPurview`) VALUES
('234', 'wangwu', '123', 1),
('af937edfeb124417966fe4bc1e50e880', 'suncai', '1234567', 1),
('d31swd3423r32', 'zhangsan', '123', 2),
('e23ehio23', 'haiping', '1234567', 3);

-- --------------------------------------------------------

--
-- 表的结构 `wait`
--

DROP TABLE IF EXISTS `wait`;
CREATE TABLE IF NOT EXISTS `wait` (
  `WaitID` varchar(50) NOT NULL COMMENT '挂号ID',
  `PatientID` varchar(50) NOT NULL COMMENT '患者ID',
  `DoctorName` varchar(50) NOT NULL COMMENT '医生姓名',
  PRIMARY KEY (`WaitID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `wait`
--

INSERT INTO `wait` (`WaitID`, `PatientID`, `DoctorName`) VALUES
('ceecd1ba2ebb4ad39d980ad5b65e3906', '234', 'zhangsan');
--
-- Database: `test`
--
CREATE DATABASE IF NOT EXISTS `test` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `test`;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
