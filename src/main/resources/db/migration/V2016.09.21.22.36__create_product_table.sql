CREATE TABLE `PRODUCT` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `time_created` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
