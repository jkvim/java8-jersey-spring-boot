CREATE TABLE `user_type` (
  `id` tinyint NOT NULL AUTO_INCREMENT,
  `type` varchar(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO user_type (`id`, `type`) VALUES ('1', 'student');
INSERT INTO user_type (`id`, `type`) VALUES ('2', 'admin');