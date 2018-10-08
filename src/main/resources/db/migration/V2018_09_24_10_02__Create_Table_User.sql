CREATE TABLE `t_user` (
  `id`                INT AUTO_INCREMENT PRIMARY KEY,
  `name`              VARCHAR(255) NOT NULL UNIQUE KEY,
  `telephone_number`  VARCHAR(255) NOT NULL UNIQUE KEY,
  `password`          VARCHAR(255) NOT NULL,
  `role_symbol`       VARCHAR(255) NOT NULL
);