-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema serwis
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema serwis
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `serwis` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `serwis` ;

-- -----------------------------------------------------
-- Table `serwis`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `serwis`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(100) NULL DEFAULT NULL,
  `role` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 21
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `serwis`.`post`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `serwis`.`post` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL DEFAULT NULL,
  `body` LONGTEXT NULL DEFAULT NULL,
  `parent_id` INT NULL DEFAULT NULL,
  `date` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `serwis`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 201
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `serwis`.`image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `serwis`.`image` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `post_id` INT NOT NULL,
  `image_name` VARCHAR(75) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `post_id_image_idx` (`post_id` ASC) VISIBLE,
  CONSTRAINT `post_id_image`
    FOREIGN KEY (`post_id`)
    REFERENCES `serwis`.`post` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 44
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `serwis`.`profile_image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `serwis`.`profile_image` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `image_name` VARCHAR(75) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `user_id_image`
    FOREIGN KEY (`user_id`)
    REFERENCES `serwis`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 24
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `serwis`.`reaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `serwis`.`reaction` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `post_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `date` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `postid_idx` (`post_id` ASC) VISIBLE,
  INDEX `userid_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `postid`
    FOREIGN KEY (`post_id`)
    REFERENCES `serwis`.`post` (`id`),
  CONSTRAINT `userid`
    FOREIGN KEY (`user_id`)
    REFERENCES `serwis`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 891
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `serwis`.`relation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `serwis`.`relation` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_user_id` INT NOT NULL,
  `second_user_id` INT NOT NULL,
  `relation_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_id_idx` (`first_user_id` ASC) VISIBLE,
  CONSTRAINT `first_user_id`
    FOREIGN KEY (`first_user_id`)
    REFERENCES `serwis`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 59
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;



CREATE SCHEMA IF NOT EXISTS `serwistest` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `serwistest` ;

-- -----------------------------------------------------
-- Table `serwistest`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `serwistest`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(100) NULL DEFAULT NULL,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `serwistest`.`post`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `serwistest`.`post` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `body` LONGTEXT NOT NULL,
  `parent_id` INT NULL DEFAULT NULL,
  `date` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `serwistest`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 104
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `serwistest`.`image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `serwistest`.`image` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `post_id` INT NOT NULL,
  `image_name` VARCHAR(75) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `image_user_id_idx` (`post_id` ASC) VISIBLE,
  CONSTRAINT `image_post_id`
    FOREIGN KEY (`post_id`)
    REFERENCES `serwistest`.`post` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `serwistest`.`profile_image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `serwistest`.`profile_image` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `image_name` VARCHAR(75) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `image_user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `image_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `serwistest`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `serwistest`.`reaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `serwistest`.`reaction` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `post_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `date` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `reaction_post_id_idx` (`post_id` ASC) VISIBLE,
  INDEX `reaction_user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `reaction_post_id`
    FOREIGN KEY (`post_id`)
    REFERENCES `serwistest`.`post` (`id`),
  CONSTRAINT `reaction_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `serwistest`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 45
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `serwistest`.`relation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `serwistest`.`relation` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_user_id` INT NOT NULL,
  `second_user_id` INT NOT NULL,
  `relation_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `first_user_id_idx` (`first_user_id` ASC) VISIBLE,
  CONSTRAINT `first_user_id`
    FOREIGN KEY (`first_user_id`)
    REFERENCES `serwistest`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 52
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

