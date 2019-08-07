BEGIN TRANSACTION;

create DATABASE goodfather;
CREATE USER 'goodfather_admin'@'localhost' IDENTIFIED BY 'admin';
GRANT ALL ON goodfather.* TO 'goodfather_admin'@'localhost';
FLUSH PRIVILEGES;

COMMIT;