INSERT INTO contacts(id, first, middle, last, email, type, score) 
SELECT id, first, middle, last, email, type, mscore*multiplier as score 
from fb 
natural join typemultipliers 
left join (select count(*) as mScore , senderID 
from messages 
group by senderID 
order by mScore) ON id = senderID;
