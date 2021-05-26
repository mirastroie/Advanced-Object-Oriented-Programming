DELIMITER //
CREATE  PROCEDURE updateVenue(IN idx int(6),
                             IN nume varchar(100),
                             IN descr varchar(500),
                             IN cap int(10),
                             IN adresa varchar(400),
                             IN reg varchar(100),
                             IN zip varchar(8),
                             IN tara varchar(30),
                             IN fee float(4,2))
BEGIN
UPDATE venues
SET NAME = nume, DESCRIPTION = descr,
CAPACITY = cap, ADDRESS = adresa, REGION = reg, ZIP = zip, COUNTRY = tara, PARKINGFEE = fee
WHERE id = idx;
END//
DELIMITER ;