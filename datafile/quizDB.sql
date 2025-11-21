-- MySQL dump 10.13  Distrib 8.0.43, for macos15 (arm64)
--
-- Host: localhost    Database: quiz
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `fillin`
--

DROP TABLE IF EXISTS `fillin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fillin` (
  `quiz_id` int NOT NULL,
  `question_id` int NOT NULL,
  `email` varchar(45) NOT NULL,
  `answer` varchar(200) DEFAULT NULL,
  `fillin_date` date DEFAULT NULL,
  PRIMARY KEY (`quiz_id`,`email`,`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fillin`
--

LOCK TABLES `fillin` WRITE;
/*!40000 ALTER TABLE `fillin` DISABLE KEYS */;
INSERT INTO `fillin` VALUES (144,1,'abc123@gmail.com','[\"海邊放空，看日出日落\"]','2025-09-04'),(144,2,'abc123@gmail.com','[\"暢玩美食、逛街、看電影\",\"好好睡一覺，睡到自然醒\"]','2025-09-04'),(144,3,'abc123@gmail.com','[\"睡到自然醒\"]','2025-09-04'),(144,1,'allen666@gmail.com','[\"海邊放空，看日出日落\"]','2025-09-04'),(144,2,'allen666@gmail.com','[\"嘗試一些平常不敢做的事\"]','2025-09-04'),(144,3,'allen666@gmail.com','[\"幹嘛告訴你\"]','2025-09-04'),(144,1,'lai0730@gmail.com','[\"海邊放空，看日出日落\"]','2025-09-05'),(144,2,'lai0730@gmail.com','[\"好好睡一覺，睡到自然醒\",\"嘗試一些平常不敢做的事\"]','2025-09-05'),(144,3,'lai0730@gmail.com','[\"打原神\"]','2025-09-05'),(144,1,'leo666@gmail.com','[\"海邊放空，看日出日落\"]','2025-09-04'),(144,2,'leo666@gmail.com','[\"嘗試一些平常不敢做的事\",\"好好睡一覺，睡到自然醒\"]','2025-09-04'),(144,3,'leo666@gmail.com','[\"I dont know\"]','2025-09-04'),(144,1,'marry777@gmail.com','[\"森林或山裡，完全與世隔絕\"]','2025-09-04'),(144,2,'marry777@gmail.com','[\"好好睡一覺，睡到自然醒\",\"嘗試一些平常不敢做的事\"]','2025-09-04'),(144,3,'marry777@gmail.com','[\"遊泳游到死\"]','2025-09-04'),(144,1,'w@gmail.com','[\"海邊放空，看日出日落\"]','2025-09-05'),(144,2,'w@gmail.com','[\"好好睡一覺，睡到自然醒\",\"嘗試一些平常不敢做的事\"]','2025-09-05'),(144,3,'w@gmail.com','[\"吃肯德基一整天\"]','2025-09-05'),(145,1,'allen666@gmail.com','[\"氣泡水\"]','2025-09-04'),(145,2,'allen666@gmail.com','[\"番茄鐘 25/5\"]','2025-09-04'),(145,3,'allen666@gmail.com','[]','2025-09-04'),(145,1,'leo666@gmail.com','[\"無糖茶\"]','2025-09-04'),(145,2,'leo666@gmail.com','[\"降噪耳機或白噪音\",\"起身走動伸展\"]','2025-09-04'),(145,3,'leo666@gmail.com','[]','2025-09-04'),(145,1,'marry777@gmail.com','[\"美式咖啡\"]','2025-09-04'),(145,2,'marry777@gmail.com','[\"起身走動伸展\"]','2025-09-04'),(145,3,'marry777@gmail.com','[]','2025-09-04'),(146,1,'allen666@gmail.com','[\"Microsoft OneDrive\"]','2025-09-04'),(146,2,'allen666@gmail.com','[\"速度\"]','2025-09-04'),(146,3,'allen666@gmail.com','[\"版本回溯與誤刪救援\"]','2025-09-04'),(146,1,'lai0730@gmail.com','[\"Dropbox\"]','2025-09-04'),(146,2,'lai0730@gmail.com','[\"sdsd\"]','2025-09-04'),(146,3,'lai0730@gmail.com','[]','2025-09-04'),(146,1,'leo666@gmail.com','[\"Dropbox\"]','2025-09-04'),(146,2,'leo666@gmail.com','[\"價格便宜一點\"]','2025-09-04'),(146,3,'leo666@gmail.com','[\"跨裝置同步穩定、分享\",\"權限控管好用\"]','2025-09-04'),(146,1,'marry777@gmail.com','[\"Google Drive\"]','2025-09-04'),(146,2,'marry777@gmail.com','[\"免費我就用\"]','2025-09-04'),(146,3,'marry777@gmail.com','[\"跨裝置同步穩定、分享\",\"版本回溯與誤刪救援\"]','2025-09-04'),(147,1,'allen666@gmail.com','[\"行程整理與提醒\"]','2025-09-04'),(147,2,'allen666@gmail.com','[\"回答可靠且可追溯\",\"資料隱私與權限控制\"]','2025-09-04'),(147,3,'allen666@gmail.com','[\"需要手動修正太多\"]','2025-09-04'),(147,4,'allen666@gmail.com','[]','2025-09-04'),(147,1,'leo666@gmail.com','[\"信件摘要與回覆草稿\"]','2025-09-04'),(147,2,'leo666@gmail.com','[\"回答可靠且可追溯\",\"與常用工具整合（雲端硬碟/筆記/日曆）\"]','2025-09-04'),(147,3,'leo666@gmail.com','[\"需要手動修正太多\"]','2025-09-04'),(147,4,'leo666@gmail.com','[]','2025-09-04'),(147,1,'marry777@gmail.com','[\"行程整理與提醒\"]','2025-09-04'),(147,2,'marry777@gmail.com','[\"與常用工具整合（雲端硬碟/筆記/日曆）\",\"回答可靠且可追溯\"]','2025-09-04'),(147,3,'marry777@gmail.com','[\"成本或額度限制\"]','2025-09-04'),(147,4,'marry777@gmail.com','[]','2025-09-04'),(148,1,'allen666@gmail.com','[\"超過6小時\"]','2025-09-04'),(148,2,'allen666@gmail.com','[\"智慧型手機\",\"筆記型電腦\",\"智慧家電（如掃地機器人、智慧音箱）\"]','2025-09-04'),(148,3,'allen666@gmail.com','[]','2025-09-04'),(148,1,'leo666@gmail.com','[\"少於1小時\"]','2025-09-04'),(148,2,'leo666@gmail.com','[\"筆記型電腦\",\"平板電腦\"]','2025-09-04'),(148,3,'leo666@gmail.com','[]','2025-09-04'),(148,1,'marry777@gmail.com','[\"4-6小時\"]','2025-09-04'),(148,2,'marry777@gmail.com','[\"智慧型手機\",\"筆記型電腦\"]','2025-09-04'),(148,3,'marry777@gmail.com','[]','2025-09-04'),(148,1,'w@gmail.com','[\"4-6小時\"]','2025-09-04'),(148,2,'w@gmail.com','[\"智慧型手機\",\"平板電腦\",\"筆記型電腦\",\"智慧手錶/穿戴式裝置\",\"智慧家電（如掃地機器人、智慧音箱）\"]','2025-09-04'),(148,3,'w@gmail.com','[]','2025-09-04'),(149,1,'abc123@gmail.com','[\"機車\"]','2025-09-04'),(149,2,'abc123@gmail.com','[\"時間成本/穩定性\",\"花費（票價/油資/停車費）\",\"天氣與安全性\",\"舒適度與擁擠程度\",\"環境友善（減碳）\"]','2025-09-04'),(149,3,'abc123@gmail.com','[]','2025-09-04'),(149,1,'allen666@gmail.com','[\"汽車\"]','2025-09-04'),(149,2,'allen666@gmail.com','[\"公司/學校補助或福利\",\"天氣與安全性\",\"舒適度與擁擠程度\",\"花費（票價/油資/停車費）\"]','2025-09-04'),(149,3,'allen666@gmail.com','[]','2025-09-04'),(149,1,'leo666@gmail.com','[\"大眾運輸（公車/捷運/火車）\"]','2025-09-04'),(149,2,'leo666@gmail.com','[\"時間成本/穩定性\",\"花費（票價/油資/停車費）\",\"環境友善（減碳）\"]','2025-09-04'),(149,3,'leo666@gmail.com','[]','2025-09-04'),(149,1,'marry777@gmail.com','[\"機車\"]','2025-09-04'),(149,2,'marry777@gmail.com','[\"時間成本/穩定性\",\"花費（票價/油資/停車費）\",\"天氣與安全性\"]','2025-09-04'),(149,3,'marry777@gmail.com','[]','2025-09-04'),(150,1,'abc123@gmail.com','[\"瞬間移動一日券（通勤零時間）\"]','2025-09-04'),(150,2,'abc123@gmail.com','[\"零延遲通勤裝置（地表任意門）\",\"口袋電量無限（不會累）\",\"美食複製器（想吃就有）\"]','2025-09-04'),(150,3,'abc123@gmail.com','[\"睡到死\"]','2025-09-04'),(150,1,'allen666@gmail.com','[\"語言全解鎖（聽說讀寫都順）\"]','2025-09-04'),(150,2,'allen666@gmail.com','[\"AI 行程管家（自動排程＋提醒）\",\"美食複製器（想吃就有）\",\"口袋電量無限（不會累）\"]','2025-09-04'),(150,3,'allen666@gmail.com','[\"打一整天手遊\"]','2025-09-04'),(150,1,'lai0730@gmail.com','[\"專注力滿格模式（不被分心）\"]','2025-09-04'),(150,2,'lai0730@gmail.com','[\"零延遲通勤裝置（地表任意門）\"]','2025-09-04'),(150,3,'lai0730@gmail.com','[\"SV2 打一整天\"]','2025-09-04'),(150,1,'leo666@gmail.com','[\"專注力滿格模式（不被分心）\"]','2025-09-04'),(150,2,'leo666@gmail.com','[\"AI 行程管家（自動排程＋提醒）\",\"零延遲通勤裝置（地表任意門）\",\"美食複製器（想吃就有）\"]','2025-09-04'),(150,3,'leo666@gmail.com','[\"睡到死\"]','2025-09-04'),(150,1,'marry777@gmail.com','[\"瞬間移動一日券（通勤零時間）\"]','2025-09-04'),(150,2,'marry777@gmail.com','[\"口袋電量無限（不會累）\",\"零延遲通勤裝置（地表任意門）\"]','2025-09-04'),(150,3,'marry777@gmail.com','[\"我社畜，沒有休假\"]','2025-09-04'),(150,1,'w@gmail.com','[\"料理天賦 MAX（看到食材就會做）\"]','2025-09-05'),(150,2,'w@gmail.com','[\"美食複製器（想吃就有）\",\"口袋電量無限（不會累）\"]','2025-09-05'),(150,3,'w@gmail.com','[\"睡覺吧\"]','2025-09-05'),(155,1,'user01@gmail.com','[\"行程與待辦自動決策\"]','2025-09-05'),(155,2,'user01@gmail.com','[\"瀏覽與內容偏好\",\"定位與通勤紀錄\"]','2025-09-05'),(155,3,'user01@gmail.com','[\"qwe\"]','2025-09-05');
/*!40000 ALTER TABLE `fillin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `quiz_id` int NOT NULL,
  `question_id` int NOT NULL,
  `question` varchar(200) NOT NULL,
  `type` varchar(10) NOT NULL,
  `is_required` tinyint DEFAULT '0',
  `options` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`quiz_id`,`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (144,1,'如果只能選一個地方度過這一天，你最想去哪？','SINGLE',1,'[\"海邊放空，看日出日落\",\"森林或山裡，完全與世隔絕\",\"熱鬧的都市，想去哪就去哪\"]'),(144,2,'在這無人知曉的一天裡，你會想做哪些事？','MULTI',0,'[\"暢玩美食、逛街、看電影\",\"好好睡一覺，睡到自然醒\",\"嘗試一些平常不敢做的事\"]'),(144,3,'如果可以完全自由安排，你會怎麼度過這神祕的一天？請描述一下你的計劃。','TEXT',1,'[]'),(145,1,'下午你最常選的提神飲品是？','SINGLE',0,'[\"美式咖啡\",\"無糖茶\",\"氣泡水\"]'),(145,2,'你維持專注最常用的方法（可複選）？','MULTI',0,'[\"番茄鐘 25/5\",\"降噪耳機或白噪音\",\"起身走動伸展\"]'),(145,3,'分享一個你下午提神的私房撇步（或歌單）。','TEXT',0,'[]'),(146,1,'你主要使用的雲端儲存服務是？','SINGLE',1,'[\"Google Drive\",\"Dropbox\",\"Microsoft OneDrive\"]'),(146,2,'若能改善一件事，你最希望雲端服務在哪方面更好？（例如：速度、價格、搜尋、與常用工具整合）','TEXT',1,'[]'),(146,3,'你在挑選或使用雲端服務時最在意哪些因素？','MULTI',0,'[\"跨裝置同步穩定、分享\",\"權限控管好用\",\"版本回溯與誤刪救援\"]'),(147,1,'你最希望 AI 助理先自動化哪一件日常任務？','SINGLE',1,'[\"信件摘要與回覆草稿\",\"行程整理與提醒\",\"資料蒐集與重點整理\"]'),(147,2,'你在評估/選擇 AI 助理時最在意哪些因素？','MULTI',0,'[\"回答可靠且可追溯\",\"與常用工具整合（雲端硬碟/筆記/日曆）\",\"資料隱私與權限控制\"]'),(147,3,'你目前使用 AI 助理時最大的阻礙？','SINGLE',0,'[\"組織政策或權限限制\",\"成本或額度限制\",\"需要手動修正太多\"]'),(147,4,'如果能與一個現有工具深度整合（如日曆、雲端硬碟、任務看板），你最想選哪個？為什麼？','TEXT',0,'[]'),(148,1,'您每天花多少時間使用手機/電腦（非工作必要）？','SINGLE',1,'[\"少於1小時\",\"1-3小時\",\"4-6小時\",\"超過6小時\"]'),(148,2,'您最常使用的科技產品是？','MULTI',0,'[\"智慧型手機\",\"筆記型電腦\",\"平板電腦\",\"智慧手錶/穿戴式裝置\",\"智慧家電（如掃地機器人、智慧音箱）\"]'),(148,3,'如果有一項尚未出現的科技產品能完全改變你的生活，你希望它是什麼？請描述它的功能與用途。','TEXT',0,'[]'),(149,1,'你主要的通勤工具是？','SINGLE',1,'[\"步行/腳踏車\",\"機車\",\"汽車\",\"大眾運輸（公車/捷運/火車）\",\"其他\"]'),(149,2,'影響你選擇通勤方式的因素？','MULTI',0,'[\"時間成本/穩定性\",\"花費（票價/油資/停車費）\",\"天氣與安全性\",\"舒適度與擁擠程度\",\"環境友善（減碳）\",\"公司/學校補助或福利\"]'),(149,3,'若能改善一項通勤體驗，你最希望是什麼？為什麼？','TEXT',0,'[]'),(150,1,'如果能為今天加上一個 Buff，你最想選哪個？','SINGLE',0,'[\"瞬間移動一日券（通勤零時間）\",\"專注力滿格模式（不被分心）\",\"語言全解鎖（聽說讀寫都順）\",\"一鍵社交隱身（只跟想見的人互動）\",\"料理天賦 MAX（看到食材就會做）\"]'),(150,2,'你會帶哪些道具或系統設定一起出門？','MULTI',0,'[\"AI 行程管家（自動排程＋提醒）\",\"零延遲通勤裝置（地表任意門）\",\"口袋電量無限（不會累）\",\"美食複製器（想吃就有）\",\"回憶錄快照（可標記當下心情與畫面）\"]'),(150,3,'請描述你在這個平行宇宙的一天：從早到晚會怎麼過？哪些場景最想體驗，為什麼？','TEXT',1,'[]'),(155,1,'你最想先開啟的數位分身核心能力是？','SINGLE',1,'[\"行程與待辦自動決策\",\"即時溝通翻譯與代答\",\"內容彙整與個人化摘要\"]'),(155,2,'為了讓它運作，你可接受提供哪些資料/權限？','MULTI',0,'[\"日曆與郵件存取\",\"定位與通勤紀錄\",\"瀏覽與內容偏好\",\"穿戴裝置健康數據\"]'),(155,3,'請描述你與數位分身的一日協作流程；在哪些情境最有幫助？又有哪些你不希望被觸碰的界線？','TEXT',1,'[]'),(157,1,'324234','SINGLE',0,'[\"324234\",\"23423423\"]');
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz`
--

DROP TABLE IF EXISTS `quiz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` varchar(200) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `is_published` tinyint DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=158 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz`
--

LOCK TABLES `quiz` WRITE;
/*!40000 ALTER TABLE `quiz` DISABLE KEYS */;
INSERT INTO `quiz` VALUES (144,'無人知曉的一天，你會去哪裡？','假如有一天，你的行蹤完全不會被任何人發現，不用打卡、不用回訊息、也不用解釋。這一天，你會選擇去哪裡？是享受一場冒險、補眠到天荒地老，還是偷偷完成某個小心願？\n這份問卷想了解大家內心最隱藏的渴望與嚮往的去處，快來分享屬於你的秘密計畫吧！','2025-11-15','2025-12-22',1),(145,'午後精神救援包調查','為了改善下午 2–4 點的能量低谷，我們想了解你提神與維持專注的小習慣。結果僅作內部優化與研究使用。','2025-11-15','2025-12-17',0),(146,'雲端備份與檔案協作習慣調查','想了解大家日常如何備份檔案與與他人協作（例如 Google Drive、Dropbox、OneDrive 等），包含你最常使用的平台、選擇時在意的重點，以及希望改進的地方。結果僅做匿名統計，作為後續流程與產品優化的參考。','2025-09-02','2025-09-22',1),(147,'我的 AI 助理使用藍圖','想了解你在日常工作與生活中，最想交給 AI 助理處理的任務、選擇 AI 工具時在意的重點，以及你心目中「一鍵完成」的理想流程。結果僅做匿名彙整，用於規劃教學與產品優化（例如可追溯引用、與日曆/雲端硬碟/筆記工具的整合、權限與隱私設定等）。','2026-01-01','2026-02-01',1),(148,'未來科技與生活方式調查問卷','隨著人工智慧、虛擬實境、物聯網等新興科技的快速發展，未來的生活可能與現在截然不同。本問卷旨在了解您對於未來科技應用的看法與期待，調查結果僅作為研究與學習之用，請放心作答。','2025-12-07','2026-01-07',0),(149,'通勤與微出行體驗調查','本問卷聚焦通勤與微出行的現況與偏好，欲了解你日常主要工具、選擇背後的考量，以及對改善方案的期待。請依平日習慣作答：單選題請選最符合的情況；多選題可勾選多項並以實際經驗為主；文字題歡迎補充痛點、理想服務或具體策略，讓結果更貼近真實需求。','2025-09-03','2025-09-03',1),(150,'平行宇宙的一天：腦洞生活設定檢查表','這份問卷以輕鬆方式蒐集大家對「理想的一天」的想像與偏好，關注便利性、情緒狀態與生活小幫手等元素。請依直覺作答，分享你覺得有趣、實用或最想嘗試的設定與情境，內容僅做觀點整理與靈感參考，無需照實際經驗限制，盡情發揮即可。','2025-09-03','2025-09-12',1),(155,'口袋數位分身使用想像調查','本問卷聚焦「個人數位分身（Digital Twin）」在日常的可用情境與界線。想了解你會優先啟用的能力、願意提供的資料，以及對協作方式的期待與疑慮。請依自身經驗與想像作答，內容僅用於概念驗證與功能設計參考，協助思考更貼近生活的 AI 協作模式。','2025-09-05','2025-10-31',1),(157,'123123','213123123','2025-09-26','2025-09-28',1);
/*!40000 ALTER TABLE `quiz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `name` varchar(20) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `email` varchar(45) NOT NULL,
  `age` int DEFAULT '0',
  `password` varchar(60) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('Miller','0912876543','abc123@gmail.com',20,'$2a$10$Q9HaX9F2m./WZhCh8CRMzeZA1RQE/B0gcabzX1R4YQzuJStXzrhOC'),('Allen','0986345677','allen666@gmail.com',29,'$2a$10$ylSYBf2yPui9e7VakifRNu5sxunA4.z1La3WeI4oqtV2HHsf829Le'),('Lai','0965632312','lai0730@gmail.com',22,'$2a$10$agZmV6uBqB5eM.2atR4uyeZvOBqPS9wNMbukxFq13JqjIDCT9dbfS'),('Leo','0965632312','leo666@gmail.com',25,'$2a$10$mz9wP79D7fPK9KI8k1uPg.Ens6MwS/6s7V7SJW6EiloMIn75q4lUe'),('Marry','0988213656','marry777@gmail.com',21,'$2a$10$6FV2/muB4LpzCT2XfSSZlOw156GxPFszaaUB5NdpD1QQ0Sw56b1Ji'),('Kurifu','0972312788','super01@gmail.com',18,'$2a$10$RMGWBPJZkio3rgtahfM7NuziwGMGDrPmv0HLcexr1vYiBLD9nLSYm'),('super02','0972576593','super02@gmail.com',18,'$2a$10$nCWKKcFJns/JYMHxLZFD2.0EhU41QgssxF/SKF4Pf47fq2ErxO2q.'),('Mars','0976596593','user01@gmail.com',21,'$2a$10$rVv9lwJFnym1h8cnZrYF/eH1sZwC1sYJ6YcJLY87/ODz76XKljeli'),('狗恆','0972312793','w@gmail.com',18,'$2a$10$acUUQg2D7PFWNsTXiOUYWu0MMSx1BDBf97yB.n728vaUNQQdPi8AW');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-21 18:19:30
