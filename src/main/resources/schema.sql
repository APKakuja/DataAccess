CREATE TABLE customer (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100),
  age INT,
  cicle VARCHAR(50),
  year_value INT,
  image_path VARCHAR(500) NULL
);
