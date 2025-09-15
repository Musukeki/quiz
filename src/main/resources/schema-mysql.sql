
CREATE TABLE IF NOT EXISTS `fillin` (
  `quiz_id` int NOT NULL,
  `question_id` int NOT NULL,
  `email` varchar(45) NOT NULL,
  `answer` varchar(200) DEFAULT NULL,
  `fillin_date` date DEFAULT NULL,
  PRIMARY KEY (`quiz_id`,`email`,`question_id`)
);
