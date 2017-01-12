CREATE TABLE `question` (
  `id` tinyint NOT NULL AUTO_INCREMENT,
  `type_id` tinyint NOT NULL,
  `content` varchar(4096) DEFAULT NULL,
  `result` varchar(512) DEFAULT NULL,
  `time_created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT fk_question_type FOREIGN KEY (type_id) REFERENCES question_type(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;