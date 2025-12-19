# Pay My Buddy

Spring Boot API enabling money transactions between users.

---

## Getting Started

These instructions allow you to retrieve and run the project
for development or testing.

### Prerequisites

Required software :

- Java 21.0.9
- Maven 3.9.11
- MySQL 9.5.0
- MySQL Workbench 8.0.45

### Installing

1 - Install Java : https://www.oracle.com/java/technologies/downloads/#java21.

2 - Install Maven : https://maven.apache.org/install.html.

3 - Install MySQL : https://dev.mysql.com/downloads/mysql/.

4 - Install MySQL Workbench : https://dev.mysql.com/downloads/workbench/.

3 - Clone the project on your local machine.

### Running the app

1 - Run the app ApiApplication.java via your IDE.

2 - On your browser, go to http://localhost:8080/swagger-ui/index.html.

OR 

Follow these steps :

1 - Open a terminal and go to the project folder.

2 - Enter the command "mvn spring-boot:run".

3 - On your browser, go to http://localhost:8080/swagger-ui/index.html.

### Testing the app

Via your IDE, right-click on the root folder and select "Run all tests".

OR

Enter the command "mvn clean verify" on the terminal.

### Physical Data Model (PDM)

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema PayMyBuddy
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema PayMyBuddy
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `PayMyBuddy` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;
USE `PayMyBuddy` ;

-- -----------------------------------------------------
-- Table `PayMyBuddy`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PayMyBuddy`.`users` (
`idUser` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`username` VARCHAR(45) NOT NULL,
`email` VARCHAR(255) NOT NULL,
`password` VARCHAR(255) NOT NULL,
`wallet` DECIMAL(10,2) NOT NULL DEFAULT 0,
PRIMARY KEY (`idUser`),
UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PayMyBuddy`.`transactions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PayMyBuddy`.`transactions` (
`idTransaction` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`description` VARCHAR(255) NULL,
`amount` DECIMAL(10,2) NOT NULL,
`idSender` INT UNSIGNED NOT NULL,
`idReceiver` INT UNSIGNED NOT NULL,
`transactionDate` DATETIME NOT NULL,
PRIMARY KEY (`idTransaction`),
INDEX `fk_transaction_user_idx` (`idSender` ASC) VISIBLE,
INDEX `fk_transaction_user1_idx` (`idReceiver` ASC) VISIBLE,
CONSTRAINT `fk_transaction_user`
FOREIGN KEY (`idSender`)
REFERENCES `PayMyBuddy`.`users` (`idUser`)
ON DELETE NO ACTION
ON UPDATE NO ACTION,
CONSTRAINT `fk_transaction_user1`
FOREIGN KEY (`idReceiver`)
REFERENCES `PayMyBuddy`.`users` (`idUser`)
ON DELETE NO ACTION
ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PayMyBuddy`.`connections`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PayMyBuddy`.`connections` (
`idUser_1` INT UNSIGNED NOT NULL,
`idUser_2` INT UNSIGNED NOT NULL,
PRIMARY KEY (`idUser_1`, `idUser_2`),
INDEX `fk_connections_user2_idx` (`idUser_2` ASC) VISIBLE,
CONSTRAINT `fk_connections_user1`
FOREIGN KEY (`idUser_1`)
REFERENCES `PayMyBuddy`.`users` (`idUser`)
ON DELETE NO ACTION
ON UPDATE NO ACTION,
CONSTRAINT `fk_connections_user2`
FOREIGN KEY (`idUser_2`)
REFERENCES `PayMyBuddy`.`users` (`idUser`)
ON DELETE NO ACTION
ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;