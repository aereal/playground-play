CREATE TABLE `article` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` varchar(256) NOT NULL,
  `body` mediumblob NOT NULL,

  primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
