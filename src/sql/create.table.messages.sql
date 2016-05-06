CREATE TABLE IF NOT EXISTS messages(
	tID INT, 
	mID INT PRIMARY KEY, 
	date VARCHAR(45), 
	senderID INT, 
	message TEXT);
