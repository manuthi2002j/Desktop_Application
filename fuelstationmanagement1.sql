-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 30, 2023 at 07:19 PM
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
-- Database: `fuelstationmanagement1`
--

-- --------------------------------------------------------

--
-- Table structure for table `admins`
--

CREATE TABLE `admins` (
  `AdminID` int(11) NOT NULL,
  `FirstName` varchar(255) NOT NULL,
  `LastName` varchar(255) NOT NULL,
  `NIC` varchar(30) NOT NULL,
  `Username` varchar(50) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Contact` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admins`
--

INSERT INTO `admins` (`AdminID`, `FirstName`, `LastName`, `NIC`, `Username`, `Password`, `Email`, `Contact`) VALUES
(1, 'Ranil', 'Silva', '891234567V', 'ranil123', 'password1', 'ranil@example.com', '+94785673421'),
(2, 'Kamala', 'Perera', '978765432V', 'kamala456', 'password2', 'kamala@example.com', '+94797561023'),
(3, 'Nishantha', 'Fernando', '789012345V', 'nishantha789', 'password3', 'nishantha@example.com', '+94781234567'),
(4, 'Dilanka', 'Jayasooriya', '654321890V', 'dilanka123', 'password4', 'dilanka@example.com', '+94771234567'),
(5, 'Malini', 'Samaraweera', '543219076V', 'malini567', 'password5', 'malini@example.com', '+94781234567');

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `CustomerID` int(11) NOT NULL,
  `FirstName` varchar(255) NOT NULL,
  `LastName` varchar(255) NOT NULL,
  `NIC` varchar(40) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Contact` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`CustomerID`, `FirstName`, `LastName`, `NIC`, `Email`, `Contact`) VALUES
(1, 'Saman', 'Rathnayake', '786543219V', 'saman@gmail.com', '+94782456789'),
(2, 'Nimal', 'Fernando', '654321987V', 'nimal@yahoo.com', '+94796543210'),
(3, 'Lakshmi', 'Silva', '123456789V', 'lakshmi@hotmail.com', '+94705674567'),
(4, 'Priyan', 'Fernando', '987654321V', 'priyan@gmail.com', '+94711289345'),
(5, 'Sujitha', 'Perera', '321654789V', 'sujitha@yahoo.com', '+94722376456');

-- --------------------------------------------------------

--
-- Table structure for table `customertransactions`
--

CREATE TABLE `customertransactions` (
  `CustomerTransactionID` int(11) NOT NULL,
  `CustomerID` int(11) NOT NULL,
  `LitersDispensed` decimal(10,2) NOT NULL,
  `TotalPrice` decimal(10,2) NOT NULL,
  `PaymentMethod` varchar(50) NOT NULL,
  `TransactionDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customertransactions`
--

INSERT INTO `customertransactions` (`CustomerTransactionID`, `CustomerID`, `LitersDispensed`, `TotalPrice`, `PaymentMethod`, `TransactionDate`) VALUES
(1, 1, 45.00, 8100.00, 'Credit Card', '2023-10-28'),
(2, 2, 35.00, 6300.00, 'Cash', '2023-10-28'),
(3, 3, 25.00, 4500.00, 'Debit Card', '2023-10-28'),
(4, 4, 15.00, 2700.00, 'Cash', '2023-10-28'),
(5, 5, 10.00, 1800.00, 'Cash', '2023-10-28');

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

CREATE TABLE `employees` (
  `EmployeeID` int(11) NOT NULL,
  `FirstName` varchar(255) NOT NULL,
  `LastName` varchar(255) NOT NULL,
  `NIC` varchar(40) NOT NULL,
  `EmployeeType` varchar(50) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Contact` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`EmployeeID`, `FirstName`, `LastName`, `NIC`, `EmployeeType`, `Email`, `Contact`) VALUES
(1, 'Ranjith', 'Perera', '123456789V', 'Cashier', 'ranjith@company.com', '+94735678901'),
(2, 'Shanika', 'Fernando', '987654321V', 'Manager', 'shanika@company.com', '+94747589012'),
(3, 'Kamal', 'Silva', '555555555V', 'Attendant', 'kamal@company.com', '+94761234567'),
(4, 'Chathura', 'Silva', '777777777V', 'Cashier', 'chathura@company.com', '+94772567890'),
(5, 'Dilani', 'Fernando', '888888888V', 'Manager', 'dilani@company.com', '+94783890123');

-- --------------------------------------------------------

--
-- Table structure for table `fuelsuppliers`
--

CREATE TABLE `fuelsuppliers` (
  `FuelSupplierID` int(11) NOT NULL,
  `FuelTypeID` int(11) NOT NULL,
  `SupplierID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `fuelsuppliers`
--

INSERT INTO `fuelsuppliers` (`FuelSupplierID`, `FuelTypeID`, `SupplierID`) VALUES
(1, 2, 1),
(2, 4, 2),
(3, 1, 1),
(4, 3, 2),
(5, 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `fuelsuppliertransactions`
--

CREATE TABLE `fuelsuppliertransactions` (
  `FuelSupplierTransactionID` int(11) NOT NULL,
  `FuelSupplierID` int(11) NOT NULL,
  `LitersSupplied` decimal(10,2) NOT NULL,
  `TotalPrice` decimal(10,2) NOT NULL,
  `PaymentMethod` varchar(50) NOT NULL,
  `TransactionDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `fuelsuppliertransactions`
--

INSERT INTO `fuelsuppliertransactions` (`FuelSupplierTransactionID`, `FuelSupplierID`, `LitersSupplied`, `TotalPrice`, `PaymentMethod`, `TransactionDate`) VALUES
(1, 1, 50.00, 9000.00, 'Credit Card', '2023-10-28'),
(2, 2, 40.00, 7200.00, 'Cash', '2023-10-28'),
(3, 1, 30.00, 5400.00, 'Debit Card', '2023-10-28'),
(4, 3, 20.00, 3600.00, 'Cash', '2023-10-28'),
(5, 2, 10.00, 1800.00, 'Cash', '2023-10-28');

-- --------------------------------------------------------

--
-- Table structure for table `fueltypes`
--

CREATE TABLE `fueltypes` (
  `FuelTypeID` int(11) NOT NULL,
  `FuelName` varchar(255) NOT NULL,
  `PricePerLiter` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `fueltypes`
--

INSERT INTO `fueltypes` (`FuelTypeID`, `FuelName`, `PricePerLiter`) VALUES
(1, 'Petrol 92', 365.00),
(2, 'Petrol 95', 420.00),
(3, 'Diesel', 341.00),
(4, 'Super Diesel', 421.00);

-- --------------------------------------------------------

--
-- Table structure for table `inventory`
--

CREATE TABLE `inventory` (
  `InventoryID` int(11) NOT NULL,
  `FuelSupplierID` int(11) NOT NULL,
  `StockQuantity` decimal(10,2) NOT NULL,
  `LastStockedDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `inventory`
--

INSERT INTO `inventory` (`InventoryID`, `FuelSupplierID`, `StockQuantity`, `LastStockedDate`) VALUES
(1, 1, 10000.00, '2023-10-28'),
(2, 2, 7500.00, '2023-10-28'),
(3, 2, 3345.00, '2023-10-28'),
(4, 1, 3345.00, '2023-10-28'),
(5, 1, 3345.00, '2023-10-28');

-- --------------------------------------------------------

--
-- Table structure for table `suppliers`
--

CREATE TABLE `suppliers` (
  `SupplierID` int(11) NOT NULL,
  `SupplierName` varchar(255) NOT NULL,
  `BRN` varchar(30) NOT NULL,
  `ContactName` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Contact` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `suppliers`
--

INSERT INTO `suppliers` (`SupplierID`, `SupplierName`, `BRN`, `ContactName`, `Email`, `Contact`) VALUES
(1, 'LankaFuel Distributors', 'BRN12345LK', 'Kaushalya Bandara', 'Kaushalya@fuelsupplier.com', '+94747589012'),
(2, 'PetroEnergy Suppliers', 'BRN98765SL', 'Upeksha Gunasekara', 'Upeksha@fuelsupplier.com', '+94783890123'),
(3, 'Ceylon Petro', 'BRN55555LK', 'Asanka Bandara', 'asanka@ceylonpetro.com', '+94746789123'),
(4, 'Sri Lanka Fuel Suppliers', 'BRN33333SL', 'Chathura Silva', 'chathura@slfuelsuppliers.com', '+94777789012'),
(5, 'Gasoline Lanka', 'BRN88888LK', 'Thilini Perera', 'thilini@gasolinelanka.com', '+94781234567');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admins`
--
ALTER TABLE `admins`
  ADD PRIMARY KEY (`AdminID`),
  ADD UNIQUE KEY `NIC` (`NIC`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`CustomerID`);

--
-- Indexes for table `customertransactions`
--
ALTER TABLE `customertransactions`
  ADD PRIMARY KEY (`CustomerTransactionID`),
  ADD KEY `customertransactions_fk_customer` (`CustomerID`);

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`EmployeeID`),
  ADD UNIQUE KEY `NIC` (`NIC`);

--
-- Indexes for table `fuelsuppliers`
--
ALTER TABLE `fuelsuppliers`
  ADD PRIMARY KEY (`FuelSupplierID`),
  ADD KEY `fuelsuppliers_fk_fueltype` (`FuelTypeID`),
  ADD KEY `fuelsuppliers_fk_supplier` (`SupplierID`);

--
-- Indexes for table `fuelsuppliertransactions`
--
ALTER TABLE `fuelsuppliertransactions`
  ADD PRIMARY KEY (`FuelSupplierTransactionID`),
  ADD KEY `fuelsuppliertransactions_fk_fuelsupplier` (`FuelSupplierID`);

--
-- Indexes for table `fueltypes`
--
ALTER TABLE `fueltypes`
  ADD PRIMARY KEY (`FuelTypeID`);

--
-- Indexes for table `inventory`
--
ALTER TABLE `inventory`
  ADD PRIMARY KEY (`InventoryID`),
  ADD KEY `inventory_fk_fuelsupplier` (`FuelSupplierID`);

--
-- Indexes for table `suppliers`
--
ALTER TABLE `suppliers`
  ADD PRIMARY KEY (`SupplierID`),
  ADD UNIQUE KEY `BRN` (`BRN`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admins`
--
ALTER TABLE `admins`
  MODIFY `AdminID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `CustomerID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `customertransactions`
--
ALTER TABLE `customertransactions`
  MODIFY `CustomerTransactionID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `employees`
--
ALTER TABLE `employees`
  MODIFY `EmployeeID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `fuelsuppliers`
--
ALTER TABLE `fuelsuppliers`
  MODIFY `FuelSupplierID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `fuelsuppliertransactions`
--
ALTER TABLE `fuelsuppliertransactions`
  MODIFY `FuelSupplierTransactionID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `fueltypes`
--
ALTER TABLE `fueltypes`
  MODIFY `FuelTypeID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `inventory`
--
ALTER TABLE `inventory`
  MODIFY `InventoryID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `suppliers`
--
ALTER TABLE `suppliers`
  MODIFY `SupplierID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `customertransactions`
--
ALTER TABLE `customertransactions`
  ADD CONSTRAINT `customertransactions_fk_customer` FOREIGN KEY (`CustomerID`) REFERENCES `customers` (`CustomerID`) ON DELETE CASCADE;

--
-- Constraints for table `fuelsuppliers`
--
ALTER TABLE `fuelsuppliers`
  ADD CONSTRAINT `fuelsuppliers_fk_fueltype` FOREIGN KEY (`FuelTypeID`) REFERENCES `fueltypes` (`FuelTypeID`) ON DELETE CASCADE,
  ADD CONSTRAINT `fuelsuppliers_fk_supplier` FOREIGN KEY (`SupplierID`) REFERENCES `suppliers` (`SupplierID`) ON DELETE CASCADE;

--
-- Constraints for table `fuelsuppliertransactions`
--
ALTER TABLE `fuelsuppliertransactions`
  ADD CONSTRAINT `fuelsuppliertransactions_fk_fuelsupplier` FOREIGN KEY (`FuelSupplierID`) REFERENCES `fuelsuppliers` (`FuelSupplierID`) ON DELETE CASCADE;

--
-- Constraints for table `inventory`
--
ALTER TABLE `inventory`
  ADD CONSTRAINT `inventory_fk_fuelsupplier` FOREIGN KEY (`FuelSupplierID`) REFERENCES `fuelsuppliers` (`FuelSupplierID`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
