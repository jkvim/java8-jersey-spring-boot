CREATE TABLE `user_exam_question` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_exam_id` bigint NOT NULL,
  `question_id` bigint NOT NULL,
  `answer` varchar(8192) DEFAULT NULL,
  `is_correct` bit DEFAULT NULL,
  `time_created` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT fk_user_exam FOREIGN KEY (user_exam_id) REFERENCES user_exam(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;