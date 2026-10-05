ALTER TABLE meal_category_model
    ADD COLUMN image_url VARCHAR(2048) NULL;

CREATE TABLE application_settings_model (
    id BIGINT NOT NULL,
    restaurant_name VARCHAR(255) NOT NULL,
    restaurant_description VARCHAR(1000) NOT NULL,
    name_color VARCHAR(20) NOT NULL,
    description_color VARCHAR(20) NOT NULL,
    font_family VARCHAR(100) NOT NULL,
    background_image_url VARCHAR(2048) NULL,
    PRIMARY KEY (id),
    CONSTRAINT chk_application_settings_singleton CHECK (id = 1)
);

INSERT INTO application_settings_model (
    id,
    restaurant_name,
    restaurant_description,
    name_color,
    description_color,
    font_family,
    background_image_url
) VALUES (
    1,
    'Nasza restauracja',
    'Poznaj nasze menu i wybierz coś dla siebie.',
    '#ffffff',
    '#f1f1f1',
    'Arial, sans-serif',
    NULL
);
