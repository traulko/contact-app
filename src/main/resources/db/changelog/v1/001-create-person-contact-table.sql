CREATE TABLE IF NOT EXISTS PERSON_CONTACT
(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    FIRST_NAME VARCHAR(15) NOT NULL,
    LAST_NAME VARCHAR(15),
    DATE_OF_BIRTH DATE
)