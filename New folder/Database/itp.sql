-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Oct 01, 2019 at 01:16 PM
-- Server version: 5.7.21
-- PHP Version: 5.6.35

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `itp`
--

-- --------------------------------------------------------

--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
CREATE TABLE IF NOT EXISTS `bill` (
  `billId` int(11) NOT NULL AUTO_INCREMENT,
  `cashierId` int(11) NOT NULL,
  `billDateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `nettAmt` double NOT NULL,
  `payType` varchar(20) NOT NULL,
  `customerId` int(11) NOT NULL,
  `refundamt` double NOT NULL,
  PRIMARY KEY (`billId`),
  KEY `cashierId` (`cashierId`),
  KEY `customerId` (`customerId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bill`
--

INSERT INTO `bill` (`billId`, `cashierId`, `billDateTime`, `nettAmt`, `payType`, `customerId`, `refundamt`) VALUES
(1, 5, '2019-08-26 10:26:09', 175, 'Cash', 1, 25),
(2, 5, '2019-08-26 10:28:58', 99, 'Cash', 1, 1),
(3, 5, '2019-08-26 13:31:58', 55, 'Cash', 1, 45),
(4, 5, '2019-08-27 14:49:28', 1200, 'Cash', 1, 100),
(5, 5, '2019-08-28 08:44:10', 99, 'Cash', 1, 1),
(6, 5, '2019-08-28 09:29:55', 2000, 'Cash', 2, 0),
(7, 5, '2019-08-28 10:28:32', 200, 'Cash', 2, 0),
(8, 5, '2019-09-11 09:54:39', 100, 'Cash', 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `billongoing`
--

DROP TABLE IF EXISTS `billongoing`;
CREATE TABLE IF NOT EXISTS `billongoing` (
  `billongoingid` int(11) NOT NULL AUTO_INCREMENT,
  `itemid` int(11) NOT NULL,
  `itemname` varchar(100) NOT NULL,
  `qty` int(11) NOT NULL,
  `amt` double NOT NULL,
  PRIMARY KEY (`billongoingid`)
) ENGINE=MyISAM AUTO_INCREMENT=164 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `billpayment`
--

DROP TABLE IF EXISTS `billpayment`;
CREATE TABLE IF NOT EXISTS `billpayment` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL,
  `month` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `eid` int(11) NOT NULL,
  PRIMARY KEY (`pid`),
  KEY `billPayments_fk` (`eid`),
  KEY `bill_type_fk` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `billpayment`
--

INSERT INTO `billpayment` (`pid`, `type`, `month`, `amount`, `eid`) VALUES
(1, 2, 999, 1000, 2),
(2, 2, 10, 1000, 4),
(3, 1, 10, 1000, 4),
(4, 2, 1, 1000, 2);

-- --------------------------------------------------------

--
-- Table structure for table `billtype`
--

DROP TABLE IF EXISTS `billtype`;
CREATE TABLE IF NOT EXISTS `billtype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `billtype`
--

INSERT INTO `billtype` (`id`, `description`) VALUES
(1, 'Electricty'),
(2, 'Water');

-- --------------------------------------------------------

--
-- Table structure for table `consumers`
--

DROP TABLE IF EXISTS `consumers`;
CREATE TABLE IF NOT EXISTS `consumers` (
  `username` varchar(200) NOT NULL,
  `password` varchar(200) DEFAULT NULL,
  `roles` varchar(200) DEFAULT NULL,
  `sec_q` varchar(200) DEFAULT NULL,
  `answer` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `consumers`
--

INSERT INTO `consumers` (`username`, `password`, `roles`, `sec_q`, `answer`) VALUES
('qwe', '123', NULL, 'What is your mother toungue?', 'sinhl'),
('sliit', '123', 'Cashier', 'What is your mother toungue?', 'sinhala'),
('thanuja', '123', 'Cashier', 'What is your mother toungue?', 'sinhala');

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
CREATE TABLE IF NOT EXISTS `customers` (
  `custId` int(11) NOT NULL AUTO_INCREMENT,
  `custName` varchar(100) NOT NULL,
  `custPhone` int(11) NOT NULL,
  `custEmail` varchar(100) NOT NULL,
  `custAddress` varchar(100) NOT NULL,
  PRIMARY KEY (`custId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`custId`, `custName`, `custPhone`, `custEmail`, `custAddress`) VALUES
(1, 'Anil', 5, 'test@g.com', 'rd test'),
(2, 'Nuwanga', 773265977, 'nw@gmail.com', 'Malabe');

-- --------------------------------------------------------

--
-- Table structure for table `deliveries`
--

DROP TABLE IF EXISTS `deliveries`;
CREATE TABLE IF NOT EXISTS `deliveries` (
  `deliveryID` varchar(50) NOT NULL,
  `billId` int(11) NOT NULL,
  `custName` varchar(100) NOT NULL,
  `dispatchDate` varchar(20) NOT NULL,
  `dispatchTime` varchar(10) NOT NULL,
  `Address` varchar(100) NOT NULL,
  `Town` varchar(30) NOT NULL,
  `vehicleId` varchar(50) NOT NULL,
  `driverId` int(11) NOT NULL,
  `status` varchar(50) NOT NULL,
  PRIMARY KEY (`deliveryID`),
  KEY `billId` (`billId`),
  KEY `driverId` (`driverId`),
  KEY `vehicleId` (`vehicleId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `deliveries`
--

INSERT INTO `deliveries` (`deliveryID`, `billId`, `custName`, `dispatchDate`, `dispatchTime`, `Address`, `Town`, `vehicleId`, `driverId`, `status`) VALUES
('DI-001', 3, 'ssa', '2019/09/04', '11:11 2', 'saa', 'asa', 'VD-001', 4, 'Pending...');

-- --------------------------------------------------------

--
-- Table structure for table `delivery_vehicle`
--

DROP TABLE IF EXISTS `delivery_vehicle`;
CREATE TABLE IF NOT EXISTS `delivery_vehicle` (
  `vehicleID` varchar(50) NOT NULL,
  `registrationNumber` varchar(20) NOT NULL,
  `make` varchar(20) NOT NULL,
  `category` varchar(20) NOT NULL,
  `fuelType` varchar(10) NOT NULL,
  `capacity` int(10) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `date` varchar(50) NOT NULL,
  `path` text NOT NULL,
  PRIMARY KEY (`vehicleID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `delivery_vehicle`
--

INSERT INTO `delivery_vehicle` (`vehicleID`, `registrationNumber`, `make`, `category`, `fuelType`, `capacity`, `description`, `date`, `path`) VALUES
('VD-001', 'as', 'Toyota', 'Car', 'Petrol', 11, 'ads', '2019/09/04', 'C:\\Users\\thanu\\OneDrive\\Pictures\\Saved Pictures\\colorful_hot_air_balloon_ride-wallpaper-5120x2880.jpg'),
('VD-002', 'sds', 'Nissan', 'Van', 'Petrol', 11, 'aaa', '2019/09/04', 'C:\\Users\\thanu\\OneDrive\\Pictures\\Saved Pictures\\colorful_hot_air_balloon_ride-wallpaper-5120x2880.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `empattendance`
--

DROP TABLE IF EXISTS `empattendance`;
CREATE TABLE IF NOT EXISTS `empattendance` (
  `attendanceId` int(11) NOT NULL AUTO_INCREMENT,
  `empId` int(11) NOT NULL,
  `signInStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`attendanceId`),
  KEY `empId` (`empId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `empattendance`
--

INSERT INTO `empattendance` (`attendanceId`, `empId`, `signInStamp`) VALUES
(1, 5, '2019-08-26 09:55:12'),
(2, 5, '2019-08-26 09:55:27'),
(3, 5, '2019-08-26 09:59:26'),
(4, 5, '2019-08-26 09:59:29'),
(5, 5, '2019-08-26 09:59:37'),
(6, 5, '2019-08-28 09:17:39'),
(7, 5, '2019-08-28 09:33:11'),
(8, 5, '2019-08-28 10:17:09'),
(9, 5, '2019-10-01 11:30:38'),
(10, 5, '2019-10-01 11:30:45');

-- --------------------------------------------------------

--
-- Table structure for table `empleave`
--

DROP TABLE IF EXISTS `empleave`;
CREATE TABLE IF NOT EXISTS `empleave` (
  `leaveId` int(11) NOT NULL AUTO_INCREMENT,
  `empId` int(11) NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `noOfDays` int(11) NOT NULL,
  `approved` tinyint(1) NOT NULL DEFAULT '0',
  `rejected` tinyint(1) NOT NULL DEFAULT '0',
  `reason` varchar(200) NOT NULL,
  PRIMARY KEY (`leaveId`),
  KEY `empId` (`empId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `empleave`
--

INSERT INTO `empleave` (`leaveId`, `empId`, `startDate`, `endDate`, `noOfDays`, `approved`, `rejected`, `reason`) VALUES
(1, 5, '2019-08-27', '2019-08-28', 2, 0, 0, 'Medical'),
(2, 5, '2019-08-29', '2019-08-29', 1, 1, 0, 'Medical'),
(3, 5, '2019-08-30', '2019-08-30', 1, 1, 0, 'Medical');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
CREATE TABLE IF NOT EXISTS `employee` (
  `empId` int(11) NOT NULL AUTO_INCREMENT,
  `empName` varchar(50) NOT NULL,
  `nic` varchar(10) NOT NULL,
  `empAddress` varchar(100) NOT NULL,
  `empPhone` int(11) NOT NULL,
  `empEmail` varchar(50) NOT NULL,
  `empType` int(20) NOT NULL,
  PRIMARY KEY (`empId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`empId`, `empName`, `nic`, `empAddress`, `empPhone`, `empEmail`, `empType`) VALUES
(2, 'df', 'df', 'sdf', 213, 'qwe', 0),
(4, 'dfdf', 'xzc', 'dsfsdf', 34324, 'sdfdf', 0),
(5, 'qwer', '1234567890', 'asd', 1234567890, 'thanuja@gmail.com', 2),
(7, 'Update', '963200153V', '12', 1234567891, 'as@gmail.com', 0),
(9, 'Imal Kumarage', '203200153V', '12', 1234567891, 'as@gmail.com', 5);

-- --------------------------------------------------------

--
-- Table structure for table `employeetype`
--

DROP TABLE IF EXISTS `employeetype`;
CREATE TABLE IF NOT EXISTS `employeetype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(200) NOT NULL,
  `basicSalary` double NOT NULL,
  `vacationDays` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employeetype`
--

INSERT INTO `employeetype` (`id`, `description`, `basicSalary`, `vacationDays`) VALUES
(2, 'Cashier', 15000, 21),
(4, 'Driver', 25000, 36),
(5, 'Manager', 55000, 21),
(8, 'Added', 50000, 21);

-- --------------------------------------------------------

--
-- Table structure for table `empsalary`
--

DROP TABLE IF EXISTS `empsalary`;
CREATE TABLE IF NOT EXISTS `empsalary` (
  `salaryId` int(11) NOT NULL AUTO_INCREMENT,
  `salMonth` varchar(100) NOT NULL,
  `salYear` int(11) NOT NULL,
  `empId` int(11) NOT NULL,
  PRIMARY KEY (`salaryId`),
  KEY `empId` (`empId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
CREATE TABLE IF NOT EXISTS `feedback` (
  `feedbackId` int(11) NOT NULL AUTO_INCREMENT,
  `custId` int(11) NOT NULL,
  `category` varchar(250) NOT NULL,
  `rating` int(11) NOT NULL,
  `options` varchar(250) NOT NULL,
  PRIMARY KEY (`feedbackId`),
  KEY `feedback_fk` (`custId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `feedback`
--

INSERT INTO `feedback` (`feedbackId`, `custId`, `category`, `rating`, `options`) VALUES
(2, 1, 'Suggestion', 5, 'bad');

-- --------------------------------------------------------

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
CREATE TABLE IF NOT EXISTS `items` (
  `itemId` int(11) NOT NULL AUTO_INCREMENT,
  `itemName` varchar(100) NOT NULL,
  `itemType` varchar(100) NOT NULL,
  `itemQty` int(11) NOT NULL,
  `buyingPrice` double NOT NULL,
  `sellingPrice` double NOT NULL,
  PRIMARY KEY (`itemId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `items`
--

INSERT INTO `items` (`itemId`, `itemName`, `itemType`, `itemQty`, `buyingPrice`, `sellingPrice`) VALUES
(1, 'Oil', 'Bajaj', 5, 50, 100),
(2, 'Oil', 'Bajaj', 500, 50, 100),
(3, 'light', 'bmw', 5, 1000, 1100);

-- --------------------------------------------------------

--
-- Table structure for table `orderpayment`
--

DROP TABLE IF EXISTS `orderpayment`;
CREATE TABLE IF NOT EXISTS `orderpayment` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `oid` int(11) NOT NULL,
  `amount` double NOT NULL,
  PRIMARY KEY (`pid`),
  KEY `oid` (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders` (
  `orderId` int(11) NOT NULL AUTO_INCREMENT,
  `itemId` int(11) NOT NULL,
  `orderQty` int(11) NOT NULL,
  `nettAmt` double NOT NULL,
  `supplierId` int(11) NOT NULL,
  `orderDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `orderApproved` tinyint(1) NOT NULL DEFAULT '0',
  `orderRejected` tinyint(1) NOT NULL DEFAULT '0',
  `orderReceived` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`orderId`),
  KEY `itemId` (`itemId`),
  KEY `supplierId` (`supplierId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`orderId`, `itemId`, `orderQty`, `nettAmt`, `supplierId`, `orderDate`, `orderApproved`, `orderRejected`, `orderReceived`) VALUES
(2, 3, 120000, 20, 2, '2019-08-28 09:15:30', 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `salarypayment`
--

DROP TABLE IF EXISTS `salarypayment`;
CREATE TABLE IF NOT EXISTS `salarypayment` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `eid` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `month` varchar(200) NOT NULL,
  `amount` double NOT NULL,
  PRIMARY KEY (`pid`),
  KEY `eid` (`eid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sales`
--

DROP TABLE IF EXISTS `sales`;
CREATE TABLE IF NOT EXISTS `sales` (
  `salesId` int(11) NOT NULL AUTO_INCREMENT,
  `billId` int(11) NOT NULL,
  `custId` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `salesDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `itemId` int(11) NOT NULL,
  PRIMARY KEY (`salesId`),
  KEY `billId` (`billId`),
  KEY `custId` (`custId`),
  KEY `itemId` (`itemId`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sales`
--

INSERT INTO `sales` (`salesId`, `billId`, `custId`, `qty`, `salesDate`, `itemId`) VALUES
(5, 4, 1, 1, '2019-08-27 14:49:28', 3),
(7, 6, 2, 10, '2019-08-28 09:29:55', 1),
(9, 7, 2, 1, '2019-08-28 10:28:32', 1),
(10, 7, 2, 1, '2019-08-28 10:28:32', 2),
(11, 8, 1, 1, '2019-09-11 09:54:39', 1);

-- --------------------------------------------------------

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
CREATE TABLE IF NOT EXISTS `suppliers` (
  `supplierId` int(11) NOT NULL AUTO_INCREMENT,
  `supplierName` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `supplierPhone` int(11) NOT NULL,
  `supplierAddress` varchar(100) NOT NULL,
  PRIMARY KEY (`supplierId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `suppliers`
--

INSERT INTO `suppliers` (`supplierId`, `supplierName`, `email`, `supplierPhone`, `supplierAddress`) VALUES
(2, 'David Peiris', 'g@gmail.com', 711539851, 'czxc');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bill`
--
ALTER TABLE `bill`
  ADD CONSTRAINT `bill_ibfk_1` FOREIGN KEY (`cashierId`) REFERENCES `employee` (`empId`),
  ADD CONSTRAINT `bill_ibfk_2` FOREIGN KEY (`customerId`) REFERENCES `customers` (`custId`);

--
-- Constraints for table `billpayment`
--
ALTER TABLE `billpayment`
  ADD CONSTRAINT `billPay_fk` FOREIGN KEY (`eid`) REFERENCES `employee` (`empId`),
  ADD CONSTRAINT `bill_type_fk` FOREIGN KEY (`type`) REFERENCES `billtype` (`id`);

--
-- Constraints for table `deliveries`
--
ALTER TABLE `deliveries`
  ADD CONSTRAINT `FK_deliveries_delivery_vehicle` FOREIGN KEY (`vehicleId`) REFERENCES `delivery_vehicle` (`vehicleID`),
  ADD CONSTRAINT `delivery_bill_fk` FOREIGN KEY (`billId`) REFERENCES `bill` (`billId`),
  ADD CONSTRAINT `delivery_driver_fk` FOREIGN KEY (`driverId`) REFERENCES `employee` (`empId`);

--
-- Constraints for table `empattendance`
--
ALTER TABLE `empattendance`
  ADD CONSTRAINT `attend_fk` FOREIGN KEY (`empId`) REFERENCES `employee` (`empId`);

--
-- Constraints for table `empleave`
--
ALTER TABLE `empleave`
  ADD CONSTRAINT `leave_fk` FOREIGN KEY (`empId`) REFERENCES `employee` (`empId`);

--
-- Constraints for table `empsalary`
--
ALTER TABLE `empsalary`
  ADD CONSTRAINT `empSal_fk` FOREIGN KEY (`empId`) REFERENCES `employee` (`empId`);

--
-- Constraints for table `feedback`
--
ALTER TABLE `feedback`
  ADD CONSTRAINT `feedback_fk` FOREIGN KEY (`custId`) REFERENCES `customers` (`custId`);

--
-- Constraints for table `orderpayment`
--
ALTER TABLE `orderpayment`
  ADD CONSTRAINT `orderPay_fk` FOREIGN KEY (`oid`) REFERENCES `orders` (`orderId`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_fk2` FOREIGN KEY (`supplierId`) REFERENCES `suppliers` (`supplierId`),
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`itemId`) REFERENCES `items` (`itemId`);

--
-- Constraints for table `salarypayment`
--
ALTER TABLE `salarypayment`
  ADD CONSTRAINT `salPay_fk` FOREIGN KEY (`eid`) REFERENCES `employee` (`empId`);

--
-- Constraints for table `sales`
--
ALTER TABLE `sales`
  ADD CONSTRAINT `sales_ibfk_1` FOREIGN KEY (`billId`) REFERENCES `bill` (`billId`),
  ADD CONSTRAINT `sales_ibfk_2` FOREIGN KEY (`custId`) REFERENCES `customers` (`custId`),
  ADD CONSTRAINT `sales_ibfk_3` FOREIGN KEY (`itemId`) REFERENCES `items` (`itemId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
