CREATE TABLE app_user (
    id BIGINT NOT NULL AUTO_INCREMENT,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    type ENUM ('ADMIN', 'ORDERER') NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_app_user_login (login)
);

CREATE TABLE meal_category_model (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE meal_model (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    description VARCHAR(255),
    price DECIMAL(38, 2),
    meal_category_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_meal_category FOREIGN KEY (meal_category_id) REFERENCES meal_category_model (id)
);

CREATE TABLE cart_model (
    id BIGINT NOT NULL AUTO_INCREMENT,
    comment VARCHAR(255),
    owner_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_cart_owner (owner_id),
    CONSTRAINT fk_cart_owner FOREIGN KEY (owner_id) REFERENCES app_user (id)
);

CREATE TABLE cart_item_model (
    id BIGINT NOT NULL AUTO_INCREMENT,
    quantity INT,
    cart_id BIGINT NOT NULL,
    meal_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_cart_item_meal (cart_id, meal_id),
    CONSTRAINT fk_cart_item_cart FOREIGN KEY (cart_id) REFERENCES cart_model (id),
    CONSTRAINT fk_cart_item_meal FOREIGN KEY (meal_id) REFERENCES meal_model (id)
);
