CREATE TABLE `exam` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `logic_q_count` int NOT NULL,
  `prog_q_count` int NOT NULL,
  `status_id` bigint NOT NULL,
  `time_created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT fk_exam_status FOREIGN KEY (status_id) REFERENCES exam_status(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;