DELIMITER //
CREATE FUNCTION updateOrganizerDescription(idx int(6), newDescr varchar(300)) returns varchar(300)
BEGIN
DECLARE old varchar(300);
SELECT description into old from organizers where id = idx;
UPDATE organizers
SET description = newDescr
WHERE id = idx;
return old;
END//organizers
DELIMITER ;