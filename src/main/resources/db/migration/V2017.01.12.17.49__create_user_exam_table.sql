CREATE TABLE `user_exam` (
  `id` tinyint NOT NULL AUTO_INCREMENT,
  `user_id` tinyint NOT NULL,
  `exam_id` tinyint NOT NULL,
  `score` int NOT NULL,
  `time_created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES exam(id),
  CONSTRAINT fk_exam FOREIGN KEY (exam_id) REFERENCES user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;