CREATE TABLE `airrole` (
  `roleId` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(128) NOT NULL,
  PRIMARY KEY (`roleId`),
  UNIQUE KEY `roleId` (`roleId`)
);
CREATE TABLE `airuser` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(128) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `userId` (`userId`)
);
CREATE TABLE `airuser_airrole` (
  `user_userId` int(11) NOT NULL,
  `role_roleId` int(11) NOT NULL,
  KEY `FK37D0CE96BB12B260` (`user_userId`),
  KEY `FK37D0CE96ED5941EB` (`role_roleId`),
  CONSTRAINT `FK37D0CE96ED5941EB` FOREIGN KEY (`role_roleId`) REFERENCES `airrole` (`roleId`),
  CONSTRAINT `FK37D0CE96BB12B260` FOREIGN KEY (`user_userId`) REFERENCES `airuser` (`userId`)
);

--INSERT INTO `airrole` VALUES ('1', 'Admin');
--INSERT INTO `airrole` VALUES ('2', 'Guest');
--INSERT INTO `airrole` VALUES ('3', 'Git');

--INSERT INTO `airuser` VALUES ('1', 'Eric');
--INSERT INTO `airuser` VALUES ('2', 'Peter');

--INSERT INTO `airuser_airrole` VALUES ('1', '1');
--INSERT INTO `airuser_airrole` VALUES ('1', '3');
--INSERT INTO `airuser_airrole` VALUES ('2', '2');