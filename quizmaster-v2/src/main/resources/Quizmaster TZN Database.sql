CREATE DATABASE  IF NOT EXISTS `Quizmaster` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `Quizmaster`;

-- MySQL dump 10.13  Distrib 8.0.19, for macos10.15 (x86_64)
--
-- Host: localhost    Database: Quizmaster
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `Answer`
--

DROP TABLE IF EXISTS `Answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Answer` (
  `idAnswer` int NOT NULL AUTO_INCREMENT,
  `textAnswer` varchar(75) NOT NULL,
  `isCorrect` tinyint NOT NULL,
  `idQuestion` int NOT NULL,
  PRIMARY KEY (`idAnswer`),
  KEY `fk_Answer_Question1_idx` (`idQuestion`),
  CONSTRAINT `fk_Answer_Question` FOREIGN KEY (`idQuestion`) REFERENCES `Question` (`idQuestion`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Answer`
--

LOCK TABLES `Answer` WRITE;
/*!40000 ALTER TABLE `Answer` DISABLE KEYS */;
INSERT INTO `Answer` VALUES (1,'Buenos Aires',1,1),(2,'La Paz',0,1),(3,'Quito',0,1),(4,'Asuncion',0,1),(5,'Santiago',1,2),(6,'Bogota',0,2),(7,'Montevideo',0,2),(8,'Caracas',0,2),(9,'Kathmandu',1,3),(10,'Kabul',0,3),(11,'Jakarta',0,3),(12,'Manilla',0,3),(13,'Istanbul',1,4),(14,'Hanoi',0,4),(15,'Beiroet',0,4),(16,'Tokio',0,4),(17,'2577',1,5),(18,'2576',0,5),(19,'2587',0,5),(20,'2578',0,5),(21,'260',1,6),(22,'268',0,6),(23,'258',0,6),(24,'248',0,6),(25,'828',1,7),(26,'538',0,7),(27,'830',0,7),(28,'778',0,7),(29,'5',1,8),(30,'75',0,8),(31,'50',0,8),(32,'20',0,8),(33,'Jane Austin',1,9),(34,'Mark Twain',0,9),(35,'George Orwell',0,9),(36,'J.K. Rowling',0,9),(37,'Emily Bronte',1,10),(38,'Oscar Wilde',0,10),(39,'Ayn Rand',0,10),(40,'Ernest Hemingway',0,10),(41,'William Shakespeare',1,11),(42,'Edgar Allan Poe',0,11),(43,'Joseph Conrad',0,11),(44,'Charles Dickens',0,11),(45,'Harper Lee',1,12),(46,'Stan Lee',0,12),(47,'Brian Bolland',0,12),(48,'Tennessee Williams',0,12);
/*!40000 ALTER TABLE `Answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
  `idCourse` int NOT NULL AUTO_INCREMENT,
  `nameCourse` varchar(45) NOT NULL,
  `idCoordinator` int NOT NULL,
  PRIMARY KEY (`idCourse`),
  KEY `fk_Course_User_idx` (`idCoordinator`),
  CONSTRAINT `fk_Course_User` FOREIGN KEY (`idCoordinator`) REFERENCES `user` (`idUser`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (1,'Aardrijkskunde',2),(2,'Wiskunde',6),(3,'Engelse Literatuur',2),(4,'Bedrijfseconomie',6),(5,'Biologie',2);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courseregistration`
--

DROP TABLE IF EXISTS `courseregistration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courseregistration` (
  `idStudent` int NOT NULL,
  `idCourse` int NOT NULL,
  PRIMARY KEY (`idStudent`,`idCourse`),
  KEY `courseID_idx` (`idCourse`),
  CONSTRAINT `courseID` FOREIGN KEY (`idCourse`) REFERENCES `course` (`idCourse`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `studentID` FOREIGN KEY (`idStudent`) REFERENCES `user` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courseregistration`
--

LOCK TABLES `courseregistration` WRITE;
/*!40000 ALTER TABLE `courseregistration` DISABLE KEYS */;
INSERT INTO `courseregistration` VALUES (1,1),(8,1),(9,1),(1,2),(7,2),(9,2),(1,3),(8,3),(1,4),(7,4),(8,4),(1,5),(7,5),(9,5);
/*!40000 ALTER TABLE `courseregistration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Group`
--

DROP TABLE IF EXISTS `Group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Group` (
  `idGroup` int NOT NULL AUTO_INCREMENT,
  `nameGroup` varchar(45) NOT NULL,
  `idCourse` int NOT NULL,
  `idTeacher` int NOT NULL,
  PRIMARY KEY (`idGroup`),
  KEY `fk_Group_Course1_idx` (`idCourse`),
  KEY `fk_Group_User2_idx` (`idTeacher`),
  CONSTRAINT `fk_Group_Course1` FOREIGN KEY (`idCourse`) REFERENCES `Course` (`idCourse`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_Group_User2` FOREIGN KEY (`idTeacher`) REFERENCES `User` (`idUser`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Group`
--

LOCK TABLES `Group` WRITE;
/*!40000 ALTER TABLE `Group` DISABLE KEYS */;
INSERT INTO `Group` VALUES (1,'Groep 1-A',1,5),(2,'Groep 2-B',2,5),(3,'Groep 3-C',3,2);
/*!40000 ALTER TABLE `Group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Question`
--

DROP TABLE IF EXISTS `Question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Question` (
  `idQuestion` int NOT NULL AUTO_INCREMENT,
  `textQuestion` varchar(75) NOT NULL,
  `idQuiz` int NOT NULL,
  PRIMARY KEY (`idQuestion`),
  KEY `fk_Question_Quiz1_idx` (`idQuiz`),
  CONSTRAINT `fk_Question_Quiz1` FOREIGN KEY (`idQuiz`) REFERENCES `Quiz` (`idQuiz`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Question`
--

LOCK TABLES `Question` WRITE;
/*!40000 ALTER TABLE `Question` DISABLE KEYS */;
INSERT INTO `Question` VALUES (1,'Wat is de hoofdstad van Argentinië?',1),(2,'Wat is de hoofdstad van Chili?',1),(3,'Wat is de hoofdstad van Nepal?',1),(4,'Wat is de hoofdstad van Turkije?',1),(5,'Wat is 2343+234?!',3),(6,'Wat is 324-64?',3),(7,'Wat is 36*23?',4),(8,'Wat is vierkantswortel van 25?',4),(9,'Wie heeft \'Pride and Prejudice\' geschreven?',5),(10,'Wie heeft \'Wuthering Heights\' geschreven?',5),(11,'Wie heeft \'Hamlet\' geschreven?',6),(12,'Wie heeft \'To Kill A Mockingbird\' geschreven?',6);
/*!40000 ALTER TABLE `Question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Quiz`
--

DROP TABLE IF EXISTS `Quiz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Quiz` (
  `idQuiz` int NOT NULL AUTO_INCREMENT,
  `nameQuiz` varchar(45) NOT NULL,
  `resultaatdefinitie` int NOT NULL,
  `idCourse` int NOT NULL,
  PRIMARY KEY (`idQuiz`),
  KEY `fk_Quiz_Course1_idx` (`idCourse`),
  CONSTRAINT `fk_Quiz_Course1` FOREIGN KEY (`idCourse`) REFERENCES `Course` (`idCourse`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Quiz`
--

LOCK TABLES `Quiz` WRITE;
/*!40000 ALTER TABLE `Quiz` DISABLE KEYS */;
INSERT INTO `Quiz` VALUES (1,'Aardrijkskunde Semester 1',1,1),(2,'Aardrijkskunde Semester 2',1,1),(3,'Wiskunde Semester 1',2,2),(4,'Wiskunde Semester 2',2,2),(5,'Engelse Literatuur Semester 1',1,3),(6,'Engelse Literatuur Semester 2',1,3);
/*!40000 ALTER TABLE `Quiz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `quiz_course`
--

DROP TABLE IF EXISTS `quiz_course`;
/*!50001 DROP VIEW IF EXISTS `quiz_course`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `quiz_course` AS SELECT 
 1 AS `idQuiz`,
 1 AS `nameQuiz`,
 1 AS `idCourse`,
 1 AS `nameCourse`,
 1 AS `idCoordinator`,
 1 AS `idRole`,
 1 AS `nameRole`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `Quizresult`
--

DROP TABLE IF EXISTS `Quizresult`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Quizresult` (
  `idAttempt` int NOT NULL AUTO_INCREMENT,
  `idQuiz` int NOT NULL,
  `idStudent` int NOT NULL,
  `passed` tinyint NOT NULL,
  `datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`idAttempt`),
  KEY `fk_QuizUser_User1_idx` (`idStudent`),
  KEY `fk_QuizUser_Quiz1_idx` (`idQuiz`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Quizresult`
--

LOCK TABLES `Quizresult` WRITE;
/*!40000 ALTER TABLE `Quizresult` DISABLE KEYS */;
INSERT INTO `Quizresult` VALUES (1,1,1,1,'2020-04-06 13:25:37'),(2,2,1,1,'2020-04-06 11:39:16'),(3,3,1,0,'2020-04-06 12:30:41');
/*!40000 ALTER TABLE `Quizresult` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Role`
--

DROP TABLE IF EXISTS `Role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Role` (
  `idRole` int NOT NULL AUTO_INCREMENT,
  `nameRole` varchar(75) NOT NULL,
  PRIMARY KEY (`idRole`),
  UNIQUE KEY `nameRole_UNIQUE` (`nameRole`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Role`
--

LOCK TABLES `Role` WRITE;
/*!40000 ALTER TABLE `Role` DISABLE KEYS */;
INSERT INTO `Role` VALUES (3,'administrator'),(2,'coordinator'),(5,'docent'),(1,'student'),(4,'technisch beheerder');
/*!40000 ALTER TABLE `Role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User` (
  `idUser` int NOT NULL AUTO_INCREMENT,
  `gebruikersnaam` varchar(45) NOT NULL,
  `wachtwoord` varchar(45) NOT NULL,
  `idRole` int NOT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE KEY `gebruikersnaam_UNIQUE` (`gebruikersnaam`),
  KEY `fk_User_Role1_idx` (`idRole`),
  CONSTRAINT `fk_User_Role1` FOREIGN KEY (`idRole`) REFERENCES `Role` (`idRole`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'BadrHari','TZN19',1),(2,'ChicaCarolina','TZN19',2),(3,'YoJosta','TZN19',3),(4,'PushMaster','TZN',4),(5,'TanteTrello','TZN',5),(6,'TioPepe','TZN29',2),(7,'JomTanssen','TZN19',1),(8,'BerkedeGoer','TZN19',1),(9,'BemideRoer','TZN19',1);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!50001 DROP VIEW IF EXISTS `user_role`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `user_role` AS SELECT 
 1 AS `idUser`,
 1 AS `gebruikersnaam`,
 1 AS `idRole`,
 1 AS `nameRole`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `quiz_course`
--

/*!50001 DROP VIEW IF EXISTS `quiz_course`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `quiz_course` AS select `q`.`idQuiz` AS `idQuiz`,`q`.`nameQuiz` AS `nameQuiz`,`q`.`idCourse` AS `idCourse`,`c`.`nameCourse` AS `nameCourse`,`c`.`idCoordinator` AS `idCoordinator`,`r`.`idRole` AS `idRole`,`r`.`nameRole` AS `nameRole` from (((`quiz` `q` join `course` `c` on((`q`.`idCourse` = `c`.`idCourse`))) join `user` `u` on((`u`.`idUser` = `c`.`idCoordinator`))) join `role` `r` on((`r`.`idRole` = `u`.`idRole`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `user_role`
--

/*!50001 DROP VIEW IF EXISTS `user_role`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `user_role` AS select `u`.`idUser` AS `idUser`,`u`.`gebruikersnaam` AS `gebruikersnaam`,`u`.`idRole` AS `idRole`,`r`.`nameRole` AS `nameRole` from (`user` `u` join `role` `r` on((`u`.`idRole` = `r`.`idRole`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-06 14:25:15
