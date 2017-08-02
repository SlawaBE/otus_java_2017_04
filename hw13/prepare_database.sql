CREATE USER stas@localhost IDENTIFIED BY 'PA$$w0rd';
CREATE DATABASE db_stas;
GRANT ALL PRIVILEGES ON db_stas.* to stas@localhost;
FLUSH PRIVILEGES;