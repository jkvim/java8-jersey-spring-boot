CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(64) NOT NULL,
  `password` varchar(24) NOT NULL,
  `user_type_id` bigint NOT NULL,
  `time_created` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT fk_user_type_id FOREIGN KEY (user_type_id) REFERENCES user_type(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;