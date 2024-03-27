CREATE DATABASE IF NOT EXISTS `ems_todo_app`;
USE `ems_todo_app`;

CREATE USER IF NOT EXISTS 'todoist'@'localhost' IDENTIFIED BY 'todoist';
GRANT USAGE ON *.* TO `todoist`@`localhost`;
GRANT SELECT, INSERT, UPDATE, DELETE ON ems_todo_app.* TO 'todoist'@'localhost';



DROP TABLE IF EXISTS `todo`;

CREATE TABLE `todo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `completed` TINYINT(1) NOT NULL DEFAULT 0,
  `category` VARCHAR(255),
  PRIMARY KEY (`id`)
);



