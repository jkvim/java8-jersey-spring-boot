CREATE TABLE `exam` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `logic_q_count` int NOT NULL,
  `prog_q_count` int NOT NULL,
  `status_type_id` bigint NOT NULL,
  `time_created` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT fk_exam_status_type FOREIGN KEY (status_type_id) REFERENCES exam_status_type(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;