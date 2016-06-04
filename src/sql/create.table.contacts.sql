CREATE TABLE IF NOT EXISTS contacts
(id INT PRIMARY KEY NOT NULL, 
first VARCHAR(15) NOT NULL, 
middle VARCHAR(15),
last VARCHAR(15),
alias VARCHAR (15),
age INT, 
sex CHAR CHECK(SEX = 'F' OR SEX = 'M'),
address VARCHAR(50), 
city VARCHAR(20),
state CHAR(2), 
type VARCHAR (15),
multiplier INT,
score INT,
phone VARCHAR(15),
email VARCHAR(30),
interests TEXT);

