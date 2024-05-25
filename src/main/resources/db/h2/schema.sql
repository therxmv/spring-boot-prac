DROP TABLE post IF EXISTS;

CREATE TABLE post (
    id INT AUTO_INCREMENT PRIMARY KEY,
    author VARCHAR(255),
    title VARCHAR(255),
    description VARCHAR(255),
    topic VARCHAR(255)
);