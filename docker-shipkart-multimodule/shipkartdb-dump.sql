-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: shipkartdb
-- ------------------------------------------------------
-- Server version	8.0.43

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `api_user`
--

DROP TABLE IF EXISTS `api_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `api_user` (
  `apiuser_id` int NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`apiuser_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api_user`
--

LOCK TABLES `api_user` WRITE;
/*!40000 ALTER TABLE `api_user` DISABLE KEYS */;
INSERT INTO `api_user` VALUES (20,'$2a$10$BmxKGNHgKd4.ouqn1RWTPOIEs.4eB1TZQy8Ydr.J5nbSgEUJmrXDS','manish'),(21,'$2a$10$fB.qmfpUDKPIiFceq.R0RuGafKod1qarm3tpdkY4KBRPB/N./pWWy','kamal');
/*!40000 ALTER TABLE `api_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apiuser_roles`
--

DROP TABLE IF EXISTS `apiuser_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apiuser_roles` (
  `apiuser_id` int NOT NULL,
  `roles` varchar(255) DEFAULT NULL,
  KEY `FKnyvc7j8i0ym5x1fmx2t7nnfq0` (`apiuser_id`),
  CONSTRAINT `FKnyvc7j8i0ym5x1fmx2t7nnfq0` FOREIGN KEY (`apiuser_id`) REFERENCES `api_user` (`apiuser_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apiuser_roles`
--

LOCK TABLES `apiuser_roles` WRITE;
/*!40000 ALTER TABLE `apiuser_roles` DISABLE KEYS */;
INSERT INTO `apiuser_roles` VALUES (20,'ADMIN'),(20,'USER'),(21,'USER');
/*!40000 ALTER TABLE `apiuser_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brand`
--

DROP TABLE IF EXISTS `brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brand` (
  `brand_id` int NOT NULL,
  `brand_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`brand_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brand`
--

LOCK TABLES `brand` WRITE;
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
INSERT INTO `brand` VALUES (20,'Adidas'),(21,'Samsung'),(22,'Nilkamal'),(23,'Raymonds'),(24,'Essence'),(25,'Annibale Colombo'),(26,'Archies'),(27,'Philips');
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brand_seq`
--

DROP TABLE IF EXISTS `brand_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brand_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brand_seq`
--

LOCK TABLES `brand_seq` WRITE;
/*!40000 ALTER TABLE `brand_seq` DISABLE KEYS */;
INSERT INTO `brand_seq` VALUES (28);
/*!40000 ALTER TABLE `brand_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `cart_id` int NOT NULL,
  `total_price` double NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`cart_id`),
  UNIQUE KEY `UK9emlp6m95v5er2bcqkjsw48he` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (1,8160,1);
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_item`
--

DROP TABLE IF EXISTS `cart_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_item` (
  `cart_item_id` int NOT NULL,
  `price` double NOT NULL,
  `product_id` int DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `quantity` int NOT NULL,
  `cart_id` int DEFAULT NULL,
  PRIMARY KEY (`cart_item_id`),
  KEY `FK1uobyhgl1wvgt1jpccia8xxs3` (`cart_id`),
  CONSTRAINT `FK1uobyhgl1wvgt1jpccia8xxs3` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`cart_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_item`
--

LOCK TABLES `cart_item` WRITE;
/*!40000 ALTER TABLE `cart_item` DISABLE KEYS */;
INSERT INTO `cart_item` VALUES (150,1200,1,'Basketball',1,1),(153,580,7,'Family Tree Photo Frame',12,1);
/*!40000 ALTER TABLE `cart_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_seq`
--

DROP TABLE IF EXISTS `cart_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_seq`
--

LOCK TABLES `cart_seq` WRITE;
/*!40000 ALTER TABLE `cart_seq` DISABLE KEYS */;
INSERT INTO `cart_seq` VALUES (2);
/*!40000 ALTER TABLE `cart_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cartitem_seq`
--

DROP TABLE IF EXISTS `cartitem_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cartitem_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cartitem_seq`
--

LOCK TABLES `cartitem_seq` WRITE;
/*!40000 ALTER TABLE `cartitem_seq` DISABLE KEYS */;
INSERT INTO `cartitem_seq` VALUES (154);
/*!40000 ALTER TABLE `cartitem_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `category_id` int NOT NULL,
  `category_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (150,'Sports'),(151,'Kids'),(152,'Electronics'),(153,'Mobiles'),(154,'Furnishing'),(155,'Beauty'),(156,'Makeup'),(157,'Mascara'),(158,'Beds'),(159,'Home Decor'),(160,'Kitchen Appliances'),(161,'Mens Clothing');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category_seq`
--

DROP TABLE IF EXISTS `category_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_seq`
--

LOCK TABLES `category_seq` WRITE;
/*!40000 ALTER TABLE `category_seq` DISABLE KEYS */;
INSERT INTO `category_seq` VALUES (162);
/*!40000 ALTER TABLE `category_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feature`
--

DROP TABLE IF EXISTS `feature`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feature` (
  `feature_id` int NOT NULL,
  `color` varchar(60) DEFAULT NULL,
  `material` varchar(100) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`feature_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feature`
--

LOCK TABLES `feature` WRITE;
/*!40000 ALTER TABLE `feature` DISABLE KEYS */;
INSERT INTO `feature` VALUES (20,'Maroon','Rubber','This can be used by kids'),(21,'Space Grey','Gorilla Glass 5','Foldable phone of the future'),(22,'Thunderstorm Violet','Helion HyperFoam & Rubber Outsoles','HyperFoam to absorb shock & rubber outsoles for grip & traction'),(24,'Dark Charcoal Grey','Beeswax, castor oil, carbon black, and iron oxides.','The Essence Mascara Lash Princess is a popular mascara known for its volumizing and lengthening effects. Achieve dramatic lashes with this long-lasting and cruelty-free formula.'),(25,'Faint Brown','Polished teak wood.','The Annibale Colombo Bed is a luxurious and elegant bed frame, crafted with high-quality materials for a comfortable and stylish bedroom.'),(26,'Silver','Steel','The Family Tree Photo Frame is a sentimental and stylish way to display your cherished family memories. With multiple photo slots, it tells the story of your loved ones.'),(27,'Glossy Red','Hard carbon fibre & Aluminium frame','The Hand Blender is a versatile kitchen appliance for blending, pureeing, and mixing. Its compact design and powerful motor make it a convenient tool for various recipes.'),(28,'Blue & Black','Egyptian Cotton','The Blue & Black Check Shirt is a stylish and comfortable men\'s shirt featuring a classic check pattern. Made from high-quality fabric, it\'s suitable for both casual and semi-formal occasions.');
/*!40000 ALTER TABLE `feature` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feature_seq`
--

DROP TABLE IF EXISTS `feature_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feature_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feature_seq`
--

LOCK TABLES `feature_seq` WRITE;
/*!40000 ALTER TABLE `feature_seq` DISABLE KEYS */;
INSERT INTO `feature_seq` VALUES (29);
/*!40000 ALTER TABLE `feature_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
  `inventory_id` int NOT NULL,
  `product_id` int NOT NULL,
  `stock` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`inventory_id`),
  UNIQUE KEY `UKce3rbi3bfstbvvyne34c1dvyv` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` VALUES (1,9,200);
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory_seq`
--

DROP TABLE IF EXISTS `inventory_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory_seq`
--

LOCK TABLES `inventory_seq` WRITE;
/*!40000 ALTER TABLE `inventory_seq` DISABLE KEYS */;
INSERT INTO `inventory_seq` VALUES (2);
/*!40000 ALTER TABLE `inventory_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jwt_user`
--

DROP TABLE IF EXISTS `jwt_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jwt_user` (
  `jwtuser_id` int NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`jwtuser_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jwt_user`
--

LOCK TABLES `jwt_user` WRITE;
/*!40000 ALTER TABLE `jwt_user` DISABLE KEYS */;
INSERT INTO `jwt_user` VALUES (20,'$2a$10$yhLJfGy/I7hmp47iV8WD8uXnwHDSjgTaCXlPJTqqq2N1exrZHRfjS','manish'),(21,'$2a$10$JHzKVAulq9Ao4EdwPdKsYuUChw05nk8WqqkriP8wCi2AVrdEXS64i','kamal');
/*!40000 ALTER TABLE `jwt_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jwtuser_roles`
--

DROP TABLE IF EXISTS `jwtuser_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jwtuser_roles` (
  `jwtuser_id` int NOT NULL,
  `roles` varchar(255) DEFAULT NULL,
  KEY `FK26gnxiyjqjjbwti37gnymbp6` (`jwtuser_id`),
  CONSTRAINT `FK26gnxiyjqjjbwti37gnymbp6` FOREIGN KEY (`jwtuser_id`) REFERENCES `jwt_user` (`jwtuser_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jwtuser_roles`
--

LOCK TABLES `jwtuser_roles` WRITE;
/*!40000 ALTER TABLE `jwtuser_roles` DISABLE KEYS */;
INSERT INTO `jwtuser_roles` VALUES (20,'ADMIN'),(20,'USER'),(21,'USER');
/*!40000 ALTER TABLE `jwtuser_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jwtuser_seq`
--

DROP TABLE IF EXISTS `jwtuser_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jwtuser_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jwtuser_seq`
--

LOCK TABLES `jwtuser_seq` WRITE;
/*!40000 ALTER TABLE `jwtuser_seq` DISABLE KEYS */;
INSERT INTO `jwtuser_seq` VALUES (22);
/*!40000 ALTER TABLE `jwtuser_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `offer`
--

DROP TABLE IF EXISTS `offer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `offer` (
  `offer_id` int NOT NULL,
  `product_id` int DEFAULT NULL,
  `offer_name` varchar(60) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`offer_id`),
  KEY `FK3cow2cmfxb0nrt43hxm7yu1q3` (`product_id`),
  CONSTRAINT `FK3cow2cmfxb0nrt43hxm7yu1q3` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offer`
--

LOCK TABLES `offer` WRITE;
/*!40000 ALTER TABLE `offer` DISABLE KEYS */;
INSERT INTO `offer` VALUES (50,1,'BANK OFFER','Upto 5% offer'),(51,1,'CASH BACK','Upto Rs. 1200/- off'),(52,1,'NO COST EMI','No cost EMI available'),(53,2,'PARTNER OFFER','Flat Rs. 150/- worth Sodexo cash'),(54,2,'BANK OFFER','SBI Credit Card Rs. 2000/- discount'),(55,3,'CASH BACK','Get Rs. 399/- flat off for training shoes purchase worth Rs. 5000/- or more'),(56,3,'PARTNER OFFER','Get 10% refund upto maximum of Rs. 200/- on payment from Paytm Wallet'),(58,5,'CASH BACK','PhonePe - Flat 5% cash-back up to Rs.100/-. Limited time offer.'),(59,6,'BANK OFFER','Citi Net Banking offer on beds purchase - Get up to Rs. 2000/- off for limited time.');
/*!40000 ALTER TABLE `offer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `offer_seq`
--

DROP TABLE IF EXISTS `offer_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `offer_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offer_seq`
--

LOCK TABLES `offer_seq` WRITE;
/*!40000 ALTER TABLE `offer_seq` DISABLE KEYS */;
INSERT INTO `offer_seq` VALUES (60);
/*!40000 ALTER TABLE `offer_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `brand_id` int DEFAULT NULL,
  `feature_id` int DEFAULT NULL,
  `price` double NOT NULL,
  `product_id` int NOT NULL,
  `rating` double NOT NULL,
  `product_name` varchar(160) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `UKl9dveq6jpamsohyklvb36um4v` (`feature_id`),
  KEY `FKs6cydsualtsrprvlf2bb3lcam` (`brand_id`),
  CONSTRAINT `FK1d4anj9kf8srw36hi4857wn4t` FOREIGN KEY (`feature_id`) REFERENCES `feature` (`feature_id`),
  CONSTRAINT `FKs6cydsualtsrprvlf2bb3lcam` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`brand_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (20,20,1200,1,4.5,'Basketball'),(21,21,98000,2,4.1,'Galaxy Fold S35 Plus'),(20,22,6250,3,4.8,'Sprint Master Running Shoes'),(24,24,899,5,3.56,'Essence Mascara Lash Princess'),(25,25,22385,6,3.9,'Annibale Colombo Bed - Queen Size'),(26,26,580,7,4,'Family Tree Photo Frame'),(27,27,995,8,4.2,'Philips - Hand Blender Mini 600 Watt'),(23,28,530,9,4.1,'Raymonds Semi-Casual Shirt - Spring 2026');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_category`
--

DROP TABLE IF EXISTS `product_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_category` (
  `category_id` int NOT NULL,
  `product_id` int NOT NULL,
  KEY `FKkud35ls1d40wpjb5htpp14q4e` (`category_id`),
  KEY `FK2k3smhbruedlcrvu6clued06x` (`product_id`),
  CONSTRAINT `FK2k3smhbruedlcrvu6clued06x` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  CONSTRAINT `FKkud35ls1d40wpjb5htpp14q4e` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_category`
--

LOCK TABLES `product_category` WRITE;
/*!40000 ALTER TABLE `product_category` DISABLE KEYS */;
INSERT INTO `product_category` VALUES (150,1),(151,1),(152,2),(153,2),(150,3),(155,5),(156,5),(157,5),(154,6),(158,6),(159,7),(160,8),(161,9);
/*!40000 ALTER TABLE `product_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_delivery`
--

DROP TABLE IF EXISTS `product_delivery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_delivery` (
  `product_id` int NOT NULL,
  `delivery_types` varchar(255) DEFAULT NULL,
  KEY `FKq1dme5205p8h01suswk06lssq` (`product_id`),
  CONSTRAINT `FKq1dme5205p8h01suswk06lssq` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_delivery`
--

LOCK TABLES `product_delivery` WRITE;
/*!40000 ALTER TABLE `product_delivery` DISABLE KEYS */;
INSERT INTO `product_delivery` VALUES (1,'PRIME'),(1,'STANDARD'),(2,'AMAZON'),(3,'STANDARD'),(3,'AMAZON'),(5,'PRIME'),(6,'AMAZON'),(7,'STANDARD'),(8,'PRIME'),(9,'STANDARD');
/*!40000 ALTER TABLE `product_delivery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_payment`
--

DROP TABLE IF EXISTS `product_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_payment` (
  `product_id` int NOT NULL,
  `payment_modes` varchar(255) DEFAULT NULL,
  KEY `FKl4g0ddfaxhm31oj9cvuq3uwel` (`product_id`),
  CONSTRAINT `FKl4g0ddfaxhm31oj9cvuq3uwel` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_payment`
--

LOCK TABLES `product_payment` WRITE;
/*!40000 ALTER TABLE `product_payment` DISABLE KEYS */;
INSERT INTO `product_payment` VALUES (1,'COD'),(1,'CARD'),(2,'NB'),(2,'UPI'),(3,'COD'),(5,'UPI'),(6,'COD'),(6,'NB'),(7,'COD'),(8,'CARD'),(8,'NB'),(9,'COD');
/*!40000 ALTER TABLE `product_payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_seq`
--

DROP TABLE IF EXISTS `product_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_seq`
--

LOCK TABLES `product_seq` WRITE;
/*!40000 ALTER TABLE `product_seq` DISABLE KEYS */;
INSERT INTO `product_seq` VALUES (10);
/*!40000 ALTER TABLE `product_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_seq`
--

DROP TABLE IF EXISTS `user_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_seq`
--

LOCK TABLES `user_seq` WRITE;
/*!40000 ALTER TABLE `user_seq` DISABLE KEYS */;
INSERT INTO `user_seq` VALUES (22);
/*!40000 ALTER TABLE `user_seq` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-10 16:48:17
