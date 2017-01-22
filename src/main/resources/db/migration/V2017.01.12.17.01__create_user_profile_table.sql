CREATE TABLE `user_profile` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NULL,
  `gender` bit NOT NULL,
  `tel` varchar(11) NULL,
  `school` varchar(64) NULL,
  `major` varchar(64) NULL,
  `time_created` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT fk_user_profile_id FOREIGN KEY (`id`) REFERENCES user(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;