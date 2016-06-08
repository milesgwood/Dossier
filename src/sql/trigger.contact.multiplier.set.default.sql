CREATE TRIGGER multiplierDelete AFTER DELETE ON typemultipliers 
BEGIN 
UPDATE contacts 
SET type = 'DEFAULT' 
WHERE type NOT IN (Select type from typemultipliers); 
END; 
