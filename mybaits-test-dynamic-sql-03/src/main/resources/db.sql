CREATE DATABASE  IF NOT EXISTS `mydb` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `mydb`;
-- MySQL dump 10.13  Distrib 8.0.18, for macos10.14 (x86_64)
--
-- Host: localhost    Database: mydb
-- ------------------------------------------------------
-- Server version	8.0.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sys_permission`
--

DROP TABLE IF EXISTS `sys_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_permission` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(64) NOT NULL,
  `permission_value` varchar(64) NOT NULL,
  `permission_url` varchar(256) DEFAULT NULL,
  `description` varchar(512) DEFAULT NULL,
  `create_time` date NOT NULL,
  `create_user` varchar(64) DEFAULT NULL,
  `create_user_id` varchar(64) DEFAULT NULL,
  `modify_time` date NOT NULL,
  `modify_user` varchar(64) DEFAULT NULL,
  `modify_user_id` varchar(64) DEFAULT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_permission_name` (`permission_name`),
  KEY `idx_permission_name` (`permission_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_permission`
--

LOCK TABLES `sys_permission` WRITE;
/*!40000 ALTER TABLE `sys_permission` DISABLE KEYS */;
INSERT INTO `sys_permission` VALUES 
(1,'queryArticle','article:query','/article/query',NULL,'2019-12-23',NULL,NULL,'2019-12-23',NULL,NULL,0),
(2,'deleteArticle','article:delete','/article/delete',NULL,'2019-12-23',NULL,NULL,'2019-12-23',NULL,NULL,0),
(3,'updateArticle','article:update','/article/update',NULL,'2019-12-23',NULL,NULL,'2019-12-23',NULL,NULL,0),
(4,'onlineList','online:list','/online/list',NULL,'2019-12-31',NULL,NULL,'2019-12-31',NULL,NULL,0),
(5,'onlineRemove','online:remove','/online/remove',NULL,'2019-12-31',NULL,NULL,'2019-12-31',NULL,NULL,0);
/*!40000 ALTER TABLE `sys_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(64) NOT NULL,
  `description` varchar(512) DEFAULT NULL,
  `create_time` date NOT NULL,
  `create_user` varchar(64) DEFAULT NULL,
  `create_user_id` varchar(64) DEFAULT NULL,
  `modify_time` date NOT NULL,
  `modify_user` varchar(64) DEFAULT NULL,
  `modify_user_id` varchar(64) DEFAULT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_role_name` (`role_name`) USING BTREE,
  KEY `idx_role_name` (`role_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES 
(1,'admin','管理员角色','2019-12-23',NULL,NULL,'2019-12-23',NULL,NULL,0),
(2,'member','Member','2019-12-30',NULL,NULL,'2019-12-30',NULL,NULL,0);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_permission`
--

DROP TABLE IF EXISTS `sys_role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_permission` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `role_id` int(20) NOT NULL,
  `permission_id` int(20) NOT NULL,
  `create_time` date NOT NULL,
  `create_user` varchar(64) DEFAULT NULL,
  `create_user_id` varchar(64) DEFAULT NULL,
  `modify_time` date NOT NULL,
  `modify_user` varchar(64) DEFAULT NULL,
  `modify_user_id` varchar(64) DEFAULT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_role_id_permission_id` (`role_id`,`permission_id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_permission`
--

LOCK TABLES `sys_role_permission` WRITE;
/*!40000 ALTER TABLE `sys_role_permission` DISABLE KEYS */;
INSERT INTO `sys_role_permission` VALUES 
(1,1,1,'2019-12-23',NULL,NULL,'2019-12-23',NULL,NULL,0),
(2,1,2,'2019-12-23',NULL,NULL,'2019-12-23',NULL,NULL,0),
(3,1,3,'2019-12-23',NULL,NULL,'2019-12-23',NULL,NULL,0),
(4,2,1,'2019-12-30',NULL,NULL,'2019-12-30',NULL,NULL,0),
(5,1,4,'2019-12-31',NULL,NULL,'2019-12-31',NULL,NULL,0),
(6,2,3,'2019-12-31',NULL,NULL,'2019-12-31',NULL,NULL,0),
(7,2,4,'2019-12-31',NULL,NULL,'2019-12-31',NULL,NULL,0);
/*!40000 ALTER TABLE `sys_role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `nick_name` varchar(64) DEFAULT NULL,
  `signature` varchar(512) DEFAULT NULL,
  `email` varchar(256) NOT NULL,
  `phone` varchar(16) DEFAULT NULL,
  `password` varchar(64) NOT NULL,
  `salt` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` int(4) NOT NULL DEFAULT '0',
  `expire_time` date NOT NULL DEFAULT '9999-12-01',
  `create_time` datetime NOT NULL,
  `create_user` varchar(64) DEFAULT NULL,
  `create_user_id` varchar(64) DEFAULT NULL,
  `modify_time` datetime NOT NULL,
  `modify_user` varchar(64) DEFAULT NULL,
  `modify_user_id` varchar(64) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_username` (`username`),
  KEY `idx_username` (`username`),
  KEY `idx_email` (`email`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES 
(1,'admin','administrator','管理员','admin@test.com','010-03838232','3bb04e43e6f8b6775d3fb125b3aa02f6','gT1jO9zW5oY7',0,'9999-12-01','2019-12-23 15:31:38',NULL,NULL,'2019-12-23 15:31:46',NULL,NULL,0),
(2,'test','test','editor','test@test.com',NULL,'3bb04e43e6f8b6775d3fb125b3aa02f6','gT1jO9zW5oY7',0,'9999-12-01','2019-12-30 16:46:48',NULL,NULL,'2019-12-30 16:46:54',NULL,NULL,0);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `role_id` int(20) NOT NULL,
  `user_id` int(20) NOT NULL,
  `create_time` date NOT NULL,
  `create_user` varchar(64) DEFAULT NULL,
  `create_user_id` varchar(64) DEFAULT NULL,
  `modify_time` date NOT NULL,
  `modify_user` varchar(64) DEFAULT NULL,
  `modify_user_id` varchar(64) DEFAULT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_role_id` (`role_id`),
  KEY `un_user_id_role_id` (`role_id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES 
(1,1,1,'2019-12-23',NULL,NULL,'2019-12-23',NULL,NULL,0),
(2,2,2,'2019-12-30',NULL,NULL,'2019-12-30',NULL,NULL,0);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-02 11:34:27
