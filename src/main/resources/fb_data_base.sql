CREATE DATABASE fb;
CREATE TABLE USER (
  id_user VARCHAR(30)  NOT NULL,
  name    VARCHAR(40)  NOT NULL,
  city    VARCHAR(40)  NOT NULL,
  region  VARCHAR(50)  NOT NULL,
  token   VARCHAR(256) NOT NULL, PRIMARY KEY (id_user)
)
  ENGINE =InnoDB
  CHARSET =utf8;
CREATE TABLE post (
  id      INT AUTO_INCREMENT NOT NULL,
  id_post VARCHAR(70)        NOT NULL,
  id_user VARCHAR(256)       NOT NULL,
  message TEXT               NOT NULL, PRIMARY KEY (id)
)
  ENGINE =InnoDB
  CHARSET =utf8;
CREATE TABLE interest_of_posts (
  id       INT AUTO_INCREMENT NOT NULL,
  post_id  INT                NOT NULL,
  interest INT DEFAULT null,
  PRIMARY KEY (id)
)
  ENGINE =InnoDB
  CHARSET =utf8;
CREATE TABLE `comments_of_post` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `post_id` INT(11) NOT NULL,
  `text` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `post_id` (`post_id`),
  CONSTRAINT `interest_of_posts_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
)
  ENGINE=InnoDB
  CHARSET =utf8;