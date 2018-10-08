CREATE TABLE `t_user` (
  `id`                VARCHAR(255) PRIMARY KEY,
  `name`              VARCHAR(255) NOT NULL,
  `password`          VARCHAR(255) NOT NULL,
  `role_symbol`       VARCHAR(255) NOT NULL,
  UNIQUE KEY uk_username(`name`)
);