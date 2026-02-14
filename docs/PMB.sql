-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema PayMyBuddy
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema PayMyBuddy
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `PayMyBuddy` ;
USE `PayMyBuddy` ;

-- -----------------------------------------------------
-- Table `PayMyBuddy`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PayMyBuddy`.`users` (
                                                    `id_user` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                                    `username` VARCHAR(45) COLLATE 'utf8mb3_unicode_ci' NULL DEFAULT NULL,
    `email` VARCHAR(255) COLLATE 'utf8mb3_unicode_ci' NOT NULL,
    `password` VARCHAR(255) COLLATE 'utf8mb3_unicode_ci' NOT NULL,
    `balance` DECIMAL(10,2) NOT NULL DEFAULT '0.00',
    PRIMARY KEY (`id_user`),
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
    UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
    ENGINE = InnoDB
    AUTO_INCREMENT = 7;


-- -----------------------------------------------------
-- Table `PayMyBuddy`.`connections`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PayMyBuddy`.`connections` (
                                                          `user_id` BIGINT UNSIGNED NOT NULL,
                                                          `friend_id` BIGINT UNSIGNED NOT NULL,
                                                          PRIMARY KEY (`user_id`, `friend_id`),
    INDEX `fk_connections_user2_idx` (`friend_id` ASC) VISIBLE,
    CONSTRAINT `fk_connections_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `PayMyBuddy`.`users` (`id_user`),
    CONSTRAINT `fk_connections_user2`
    FOREIGN KEY (`friend_id`)
    REFERENCES `PayMyBuddy`.`users` (`id_user`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PayMyBuddy`.`transactions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PayMyBuddy`.`transactions` (
                                                           `id_transaction` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                                           `description` VARCHAR(255) COLLATE 'utf8mb3_unicode_ci' NULL DEFAULT NULL,
    `amount` DECIMAL(10,2) NOT NULL,
    `id_sender` BIGINT UNSIGNED NOT NULL,
    `id_receiver` BIGINT UNSIGNED NOT NULL,
    `transaction_date` DATETIME NOT NULL,
    PRIMARY KEY (`id_transaction`),
    INDEX `fk_transaction_user_idx` (`id_sender` ASC) VISIBLE,
    INDEX `fk_transaction_user1_idx` (`id_receiver` ASC) VISIBLE,
    CONSTRAINT `fk_transaction_user`
    FOREIGN KEY (`id_sender`)
    REFERENCES `PayMyBuddy`.`users` (`id_user`),
    CONSTRAINT `fk_transaction_user1`
    FOREIGN KEY (`id_receiver`)
    REFERENCES `PayMyBuddy`.`users` (`id_user`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 18;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
