CREATE TABLE `exam_status_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `status` varchar(8) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO exam_status_type (`id`, `status`) VALUES ('1', 'open');
INSERT INTO exam_status_type (`id`, `status`) VALUES ('2', 'close');