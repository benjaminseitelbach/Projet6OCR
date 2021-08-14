-- MySQL Script generated by MySQL Workbench
-- Thu Jul 29 16:34:27 2021
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema paymybuddyprod
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema paymybuddyprod
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `paymybuddyprod` DEFAULT CHARACTER SET utf8 ;
USE `paymybuddyprod` ;

-- -----------------------------------------------------
-- Table `paymybuddyprod`.`customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddyprod`.`customer` (
  `customer_id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `first_name` VARCHAR(255) NULL,
  `last_name` VARCHAR(255) NULL,
  `amount` DOUBLE NULL,
  PRIMARY KEY (`customer_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `paymybuddyprod`.`relationship`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddyprod`.`relationship` (
  `customer_id` INT NOT NULL,
  `connection_id` INT NOT NULL,
  PRIMARY KEY (`customer_id`, `connection_id`),
  INDEX `fk_customer_has_customer_customer2_idx` (`connection_id` ASC) VISIBLE,
  INDEX `fk_customer_has_customer_customer1_idx` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `fk_customer_has_customer_customer1`
    FOREIGN KEY (`customer_id`)
    REFERENCES `paymybuddyprod`.`customer` (`customer_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_customer_has_customer_customer2`
    FOREIGN KEY (`connection_id`)
    REFERENCES `paymybuddyprod`.`customer` (`customer_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `paymybuddyprod`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddyprod`.`transaction` (
  `transaction_id` INT NOT NULL AUTO_INCREMENT,
  `amount` DOUBLE NOT NULL,
  `description` VARCHAR(255) NULL,
  `date` DATETIME NULL,
  `sender_id` INT NOT NULL,
  `receiver_id` INT NOT NULL,
  PRIMARY KEY (`transaction_id`, `sender_id`, `receiver_id`),
  INDEX `fk_transaction_customer_has_customer1_idx` (`sender_id` ASC, `receiver_id` ASC) VISIBLE,
  CONSTRAINT `fk_transaction_customer_has_customer1`
    FOREIGN KEY (`sender_id` , `receiver_id`)
    REFERENCES `paymybuddyprod`.`relationship` (`customer_id` , `connection_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `paymybuddyprod`.`bank_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddyprod`.`bank_account` (
  `bank_account_id` INT NOT NULL AUTO_INCREMENT,
  `iban` VARCHAR(255) NOT NULL,
  `amount` DOUBLE NULL,
  `customer_id` INT NOT NULL,
  PRIMARY KEY (`bank_account_id`, `customer_id`),
  INDEX `fk_bank_account_customer1_idx` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `fk_bank_account_customer1`
    FOREIGN KEY (`customer_id`)
    REFERENCES `paymybuddyprod`.`customer` (`customer_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO customer(email, password, first_name, last_name, amount)
VALUES("jaboyd@email.com", "$2a$10$alZ2ltll1Euc5utRzJSxBuLa/TB1NuJXqLRqBIRl3OukEBwP68iF.", "John", "Boyd", 100);

INSERT INTO bank_account(iban, amount, customer_id)
VALUES("FR7612548029989876543210916", 300, 1);

INSERT INTO customer(email, password, first_name, last_name, amount)
VALUES("drk@email.com", "$2a$10$xrf4tzGQWPoWDV4oycQWtecvWbaY5ph0uK0EY7A52Hkc7M1hdWjV.", "Jonathan", "Marrack", 200);

INSERT INTO customer(email, password, first_name, last_name, amount)
VALUES("tenz@email.com", "$2a$10$fPpeRJAV/5pmSKMYvdexuO7gOhFraC6xG8hOLusarr5WBsA04b7SO", "Tessa", "Carman", 300);

INSERT INTO relationship(customer_ID, connection_ID)
VALUES(1, 2);

INSERT INTO relationship(customer_ID, connection_ID)
VALUES(1, 3);

INSERT INTO transaction(amount, description, date, sender_ID, receiver_ID)
VALUES(10, "description1", DATE '2021-06-08', 1, 2);

