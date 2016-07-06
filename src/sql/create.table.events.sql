CREATE TABLE IF NOT EXISTS events 
(eID INT PRIMARY KEY NOT NULL, 
title VARCHAR(20), 
date CHAR(10), 
address VARCHAR(20),  
city VARCHAR(20), 
state CHAR(2), 
country VARCHAR(10), 
thoughts TEXT);

