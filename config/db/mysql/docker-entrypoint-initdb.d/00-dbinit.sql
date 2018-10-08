CREATE DATABASE IF NOT EXISTS `grad_stepup_test` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS `grad_stepup` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'dev';

CREATE USER bj_grad;

-- GRANT ALL PRIVILEGES ON *.* TO 'bj_grad'@'%';

GRANT ALL PRIVILEGES ON `grad_stepup`.* TO 'bj_grad'@'%' identified by '';
