-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Dec 17, 2020 at 05:00 PM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.2.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `spring_boot_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `brands`
--

CREATE TABLE `brands` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `country_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `brands`
--

INSERT INTO `brands` (`id`, `name`, `country_id`) VALUES
(1, 'Xiaomi', 1);

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` bigint(20) NOT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `logo`, `name`) VALUES
(1, '', 'Phones');

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE `comments` (
  `id` bigint(20) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `author_id` bigint(20) DEFAULT NULL,
  `items_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`id`, `comment`, `author_id`, `items_id`) VALUES
(3, '12312312', 1, 4),
(4, 'test', 2, 4);

-- --------------------------------------------------------

--
-- Table structure for table `counties`
--

CREATE TABLE `counties` (
  `id` bigint(20) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `counties`
--

INSERT INTO `counties` (`id`, `code`, `name`) VALUES
(1, 'CH', 'China');

-- --------------------------------------------------------

--
-- Table structure for table `items`
--

CREATE TABLE `items` (
  `id` bigint(20) NOT NULL,
  `added_date` datetime DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `in_top` bit(1) DEFAULT NULL,
  `large_pic` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `small_pic` varchar(255) DEFAULT NULL,
  `stars` int(11) DEFAULT NULL,
  `brand_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `items`
--

INSERT INTO `items` (`id`, `added_date`, `amount`, `description`, `in_top`, `large_pic`, `name`, `price`, `small_pic`, `stars`, `brand_id`) VALUES
(4, '2020-12-16 18:00:00', 200, '', b'1', 'https://avatars.mds.yandex.net/get-mpic/1244413/img_id7956183309788607550.jpeg/9hq', 'Xiaomi miA2', 10000, 'https://avatars.mds.yandex.net/get-mpic/1244413/img_id7956183309788607550.jpeg/9hq', 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `items_categories`
--

CREATE TABLE `items_categories` (
  `items_id` bigint(20) NOT NULL,
  `categories_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `t_roles`
--

CREATE TABLE `t_roles` (
  `id` bigint(20) NOT NULL,
  `role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_roles`
--

INSERT INTO `t_roles` (`id`, `role`) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN'),
(3, 'ROLE_MODERATOR');

-- --------------------------------------------------------

--
-- Table structure for table `t_users`
--

CREATE TABLE `t_users` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_avatar` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_users`
--

INSERT INTO `t_users` (`id`, `email`, `full_name`, `password`, `user_avatar`) VALUES
(1, 'admin@gmail.com', 'Ibragimov Ruslan', '$2y$12$yLZqGyOgUp2DPNZHNs.92eBS3OGAZm3OUXomtRkJo8NABsGxwAAbO', '55bf8325e07993ea6544de9fbd1ebeb4cbd820b3'),
(2, 'manager@gmail.com', 'Manager', '$2y$12$yLZqGyOgUp2DPNZHNs.92eBS3OGAZm3OUXomtRkJo8NABsGxwAAbO', 'd2b878a7fc751e8b97e1c52199f715b17a1d7f9e'),
(3, 'guest@gmail.com', 'Guest', '$2y$12$yLZqGyOgUp2DPNZHNs.92eBS3OGAZm3OUXomtRkJo8NABsGxwAAbO', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `t_users_roles`
--

CREATE TABLE `t_users_roles` (
  `users_id` bigint(20) NOT NULL,
  `roles_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_users_roles`
--

INSERT INTO `t_users_roles` (`users_id`, `roles_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(2, 3),
(3, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `brands`
--
ALTER TABLE `brands`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKdir2lskcgdevj3fd7kmvf5avh` (`country_id`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKpuy1ivlvb5d1md09lerrcl9w1` (`author_id`),
  ADD KEY `FKd7m6glp6ts5l4onp9nt6fgylf` (`items_id`);

--
-- Indexes for table `counties`
--
ALTER TABLE `counties`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `items`
--
ALTER TABLE `items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKi0gnxi21mo1gmbl3q2cqvpx69` (`brand_id`);

--
-- Indexes for table `items_categories`
--
ALTER TABLE `items_categories`
  ADD KEY `FKyxf17r34k73ewrqm0icx1xar` (`categories_id`),
  ADD KEY `FK16oha2jb2sw0ghqe659n752bh` (`items_id`);

--
-- Indexes for table `t_roles`
--
ALTER TABLE `t_roles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_users`
--
ALTER TABLE `t_users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_users_roles`
--
ALTER TABLE `t_users_roles`
  ADD KEY `FK9qf4n9htwf2hlfnqoucqmyeg9` (`roles_id`),
  ADD KEY `FK8rghlpl6w68ymy8c5sdv4bkeu` (`users_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `brands`
--
ALTER TABLE `brands`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `comments`
--
ALTER TABLE `comments`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `counties`
--
ALTER TABLE `counties`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `items`
--
ALTER TABLE `items`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `t_roles`
--
ALTER TABLE `t_roles`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `t_users`
--
ALTER TABLE `t_users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `brands`
--
ALTER TABLE `brands`
  ADD CONSTRAINT `FKdir2lskcgdevj3fd7kmvf5avh` FOREIGN KEY (`country_id`) REFERENCES `counties` (`id`);

--
-- Constraints for table `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `FKd7m6glp6ts5l4onp9nt6fgylf` FOREIGN KEY (`items_id`) REFERENCES `items` (`id`),
  ADD CONSTRAINT `FKpuy1ivlvb5d1md09lerrcl9w1` FOREIGN KEY (`author_id`) REFERENCES `t_users` (`id`);

--
-- Constraints for table `items`
--
ALTER TABLE `items`
  ADD CONSTRAINT `FKi0gnxi21mo1gmbl3q2cqvpx69` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`);

--
-- Constraints for table `items_categories`
--
ALTER TABLE `items_categories`
  ADD CONSTRAINT `FK16oha2jb2sw0ghqe659n752bh` FOREIGN KEY (`items_id`) REFERENCES `items` (`id`),
  ADD CONSTRAINT `FKyxf17r34k73ewrqm0icx1xar` FOREIGN KEY (`categories_id`) REFERENCES `categories` (`id`);

--
-- Constraints for table `t_users_roles`
--
ALTER TABLE `t_users_roles`
  ADD CONSTRAINT `FK8rghlpl6w68ymy8c5sdv4bkeu` FOREIGN KEY (`users_id`) REFERENCES `t_users` (`id`),
  ADD CONSTRAINT `FK9qf4n9htwf2hlfnqoucqmyeg9` FOREIGN KEY (`roles_id`) REFERENCES `t_roles` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
