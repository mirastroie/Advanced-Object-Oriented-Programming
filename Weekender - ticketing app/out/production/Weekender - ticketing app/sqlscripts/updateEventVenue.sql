DELIMITER //
CREATE PROCEDURE updateEventVenue(IN idx int(6), IN venue_id int(6), OUT old_venue int(6))
BEGIN
SELECT VENUE INTO old_venue FROM EVENTS
WHERE ID = IDX;
UPDATE events
SET venue = venue_id
WHERE id = idx;
END//
DELIMITER ;