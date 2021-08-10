--DROP DATABASE if exists spring;
--
--CREATE database spring;

DROP TABLE IF EXISTS users;

CREATE TABLE users(
id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
password VARCHAR(100) NOT NULL,
username VARCHAR(50) NOT NULL,
email VARCHAR(50) NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS roles;

CREATE TABLE roles(
id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50) DEFAULT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS users_roles;

CREATE TABLE users_roles(
user_id INT(11) NOT NULL,
role_id INT(11) NOT NULL,
PRIMARY KEY (user_id, role_id),

CONSTRAINT FK_USER_ID_ROLES FOREIGN KEY (user_id)
REFERENCES users(id)
ON DELETE NO ACTION ON UPDATE NO ACTION,

CONSTRAINT FK_ROLE_ID FOREIGN KEY (role_id)
REFERENCES roles(id)
ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE = InnoDB DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS categories;

CREATE TABLE categories(
id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(80) NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS products;

CREATE TABLE products(
id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
price DECIMAL(8,2) NOT NULL,
category_id INT(11) NOT NULL,
title_english VARCHAR(80) NOT NULL,
title_russian VARCHAR(80) NOT NULL,

CONSTRAINT FK_CATEGORY_ID FOREIGN KEY (category_id)
REFERENCES categories (id)
ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS orders;

CREATE TABLE orders(
id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
data VARCHAR(50) NOT NULL,
number INT(11) NOT NULL,
user_id INT(11),

CONSTRAINT FK_USER_ID_ORDERS FOREIGN KEY (user_id)
REFERENCES users(id)
ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE = InnoDB DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS orders_products;

CREATE TABLE orders_products(
order_id INT(11) NOT NULL,
product_id INT(11) NOT NULL,
PRIMARY KEY (order_id, product_id),

CONSTRAINT FK_ORDER_ID FOREIGN KEY (order_id)
REFERENCES orders (id)
ON DELETE NO ACTION ON UPDATE NO ACTION,

CONSTRAINT FK_PRODUCT_ID_ORDERS FOREIGN KEY (product_id)
REFERENCES products (id)
ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE = InnoDB DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS product_images;

CREATE TABLE product_images(
id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
path VARCHAR(50) NOT NULL,
product_id INT(11),

CONSTRAINT FK_PRODUCT_ID_IMAGES FOREIGN KEY (product_id)
REFERENCES products (id)
ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE = InnoDB DEFAULT CHARSET = utf8;
