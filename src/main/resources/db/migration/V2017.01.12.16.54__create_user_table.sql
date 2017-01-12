CREATE TABLE `user` (
  `id` tinyint NOT NULL AUTO_INCREMENT,
  `email` varchar(64) NOT NULL,
  `password` varchar(24) NOT NULL,
  `name` varchar(64) NULL,
  `gender` bit NOT NULL,
  `tel` varchar(11) NULL,
  `school` varchar(64) NULL,
  `major` varchar(64) NULL,
  `user_type_id` tinyint NOT NULL,
  `time_created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT fk_user_type FOREIGN KEY (user_type_id) REFERENCES user_type(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;