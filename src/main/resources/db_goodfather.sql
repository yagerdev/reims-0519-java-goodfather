BEGIN TRANSACTION;

create DATABASE goodfather_db;
CREATE USER 'goodfather'@'localhost' IDENTIFIED BY 'motdepasse';
GRANT ALL ON goodfather_db.* TO 'goodfather'@'localhost';
FLUSH PRIVILEGES;

COMMIT;