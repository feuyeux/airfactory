CREATE SCHEMA `air-restful`;
USE `air-restful`;
DROP TABLE IF EXISTS `abc`;

CREATE TABLE `abc` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(45) default NULL,
  `value` varchar(45) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;