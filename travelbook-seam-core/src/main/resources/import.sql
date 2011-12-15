INSERT INTO Rankable (DTYPE,id,name,votesRatio, x, y, country, region, code) VALUES ('Place',1,'Miami',30, 109, 113, 'USA', 'America del Norte', 'us')
INSERT INTO Rankable (DTYPE,id,name,votesRatio, x, y, country, region, code) VALUES ('Place',2,'Buenos Aires',20, 139, 186, 'Argentina', 'America del Sur', 'ar')
INSERT INTO Rankable (DTYPE,id,name,votesRatio, x, y, country, region, code) VALUES ('Place',3,'Dallas',10, 96, 106, 'USA', 'America del Norte', 'us')
INSERT INTO Rankable (DTYPE,id,name,votesRatio, x, y, country, region, code) VALUES ('Place',17,'Madrid',2, 195, 89, 'Espa&ntilde;a', 'Europa', 'es')
INSERT INTO Rankable (DTYPE,id,name,votesRatio, x, y, country, region, code) VALUES ('Place',18,'Paris',2, 204, 81, 'Francia', 'Europa', 'fr')
INSERT INTO Rankable (DTYPE,id,name,votesRatio, x, y, country, region, code) VALUES ('Place',19,'Berlin',2, 215, 74, 'Alemania', 'Europa', 'de')
INSERT INTO Rankable (DTYPE,id,name,votesRatio) VALUES ('Transport',4,'flecha bus',0)
INSERT INTO Rankable (DTYPE,id,name,votesRatio) VALUES ('Transport',5,'rapido del norte',10)
INSERT INTO Rankable (DTYPE,id,name,votesRatio) VALUES ('Transport',6,'Mi Empresa',30)
INSERT INTO Rankable (DTYPE,id,name,votesRatio) VALUES ('Transport',7,'LAPA',1000)
INSERT INTO Rankable (DTYPE,id,name,votesRatio) VALUES ('Accomodation',8,'marriot',20)
INSERT INTO Rankable (DTYPE,id,name,votesRatio) VALUES ('Accomodation',9,'hoteles albertarrio',1000)
INSERT INTO Rankable (DTYPE,id,name,votesRatio) VALUES ('Accomodation',10,'hoteles tuli',100)
INSERT INTO Rankable (DTYPE,id,name,votesRatio) VALUES ('Accomodation',11,'marriot hotel',10)
INSERT INTO Rankable (DTYPE,id,name,votesRatio) VALUES ('Provider',12,'Bailar Tango en la Viruta',10)
INSERT INTO Rankable (DTYPE,id,name,votesRatio) VALUES ('Provider',13,'Recital de los Maiquel Yaxon',100)
INSERT INTO Rankable (DTYPE,id,name,votesRatio) VALUES ('Provider',14,'Look Miami Vice',0)
INSERT INTO Rankable (DTYPE,id,name,votesRatio) VALUES ('Provider',15,'Miami Woodstook',0)
INSERT INTO Rankable (DTYPE,id,name,votesRatio) VALUES ('Provider',16,'Juego de Naipes',0)


INSERT INTO TransportTicket (DTYPE, id, price, description, transport_id, departureDateTime, arrivalDateTime, category, otherCategory, arrival_place_id, departure_place_id) VALUES  ('TransportTicketPublic', 3, 0, '', 4,'2009-02-22 18:00:09', '2009-02-22 18:01:51', 0, NULL, 2, 1)
INSERT INTO TransportTicket (DTYPE, id, price, description, transport_id, departureDateTime, arrivalDateTime, category, otherCategory, arrival_place_id, departure_place_id) VALUES  ('TransportTicketPublic', 8, 0, '', 5,'2009-02-23 18:00:09', '2009-02-23 18:01:51', 0, NULL, 2, 1)
INSERT INTO TransportTicket (DTYPE, id, price, description, transport_id, departureDateTime, arrivalDateTime, category, otherCategory, arrival_place_id, departure_place_id) VALUES  ('TransportTicketPublic', 9, 20, '',6 ,'2009-02-24 18:00:09', '2009-02-24 18:01:51', 0, NULL, 2, 1)
INSERT INTO TransportTicket (DTYPE, id, price, description, transport_id, departureDateTime, arrivalDateTime, category, otherCategory, arrival_place_id, departure_place_id) VALUES  ('TransportTicketPublic', 11, 0, '', 7,'2009-02-25 18:00:09', '2009-02-25 18:01:51', 1, NULL, 2, 1)
INSERT INTO TransportTicket (DTYPE, id, price, description, transport_id, departureDateTime, arrivalDateTime, category, otherCategory, arrival_place_id, departure_place_id) VALUES  ('TransportTicketPublic', 12, 0, '', 7, '2009-02-26 18:00:09', '2009-02-26 18:01:51', 1, NULL, 3, 2)

INSERT INTO AccomodationTicket (DTYPE, category,accomodation_id, address, price, description, place_id) VALUES ('AccomodationTicketPublic',0,8, 'fifth avenue 2021', 300,'hotel lindo con flores', 1)
INSERT INTO AccomodationTicket (DTYPE, category,accomodation_id, address, price, description, place_id) VALUES ('AccomodationTicketPublic',0,9, 'gomez 202', 10,'hotel lindo con flores1', 2)
INSERT INTO AccomodationTicket (DTYPE, category,accomodation_id, address, price, description, place_id) VALUES ('AccomodationTicketPublic',0,10, 'cordoba 22202', 20,'hotel lindo con flores3', 2)
INSERT INTO AccomodationTicket (DTYPE, category,accomodation_id, address, price, description, place_id) VALUES ('AccomodationTicketPublic',0,11, 'west 200', 50,'hotel lindo con flores2', 3)

INSERT INTO Activity(DTYPE, place_id, provider_id, price, name, startdatetime, enddatetime) VALUES ('ActivityPublic', 2, 12, 0, '','2009-08-17 02:00:09', '2009-08-17 18:01:51')
INSERT INTO Activity(DTYPE, place_id, provider_id, price, name, startdatetime, enddatetime) VALUES ('ActivityPublic', 2, 13, 100, '','2009-02-22 18:00:09', '2009-02-22 18:01:51')
INSERT INTO Activity(DTYPE, place_id, provider_id, price, name, startdatetime, enddatetime) VALUES ('ActivityPublic', 1, 14, 50, '',NULL, NULL)
INSERT INTO Activity(DTYPE, place_id, provider_id, price, name, startdatetime, enddatetime) VALUES ('ActivityPublic', 1, 15, 75, '','2009-02-21 18:00:09', '2009-02-23 18:01:51')
INSERT INTO Activity(DTYPE, place_id, provider_id, price, name, startdatetime, enddatetime) VALUES ('ActivityPublic', 3, 16 , 4, '',NULL, NULL)

INSERT INTO User(id,username,password) VALUES (1,'cruz','cruz')
INSERT INTO User(id,username,password) VALUES (2,'juan','juan')
INSERT INTO User(id,username,password) VALUES (3,'gabriel','gabriel')

INSERT INTO Comment(user_id,text,rankable_id) VALUES(1,'Mejor Ciudad del Mundo',1);
INSERT INTO Comment(user_id,text,rankable_id) VALUES(1,'Mal servicio',7);
INSERT INTO Vote(user_id, rankable_id, positive) VALUES(1,1,TRUE);
INSERT INTO Vote(user_id, rankable_id, positive) VALUES(2,1,TRUE);
INSERT INTO Vote(user_id, rankable_id, positive) VALUES(2,2,TRUE);
INSERT INTO Vote(user_id, rankable_id,  positive) VALUES(2,3,TRUE);
INSERT INTO Vote(user_id, rankable_id,  positive) VALUES(2,4,FALSE);
INSERT INTO Vote(user_id, rankable_id,  positive) VALUES(2,5,FALSE);
INSERT INTO Vote(user_id, rankable_id,  positive) VALUES(2,14,TRUE);
INSERT INTO Vote(user_id, rankable_id,  positive) VALUES(2,15,TRUE);
INSERT INTO Vote(user_id, rankable_id,  positive) VALUES(2,16,FALSE);
INSERT INTO Vote(user_id, rankable_id,  positive) VALUES(2,17,FALSE);
INSERT INTO Vote(user_id, rankable_id,  positive) VALUES(3,17,TRUE);
INSERT INTO Vote(user_id, rankable_id,  positive) VALUES(1,17,TRUE);

INSERT INTO Vote(user_id, rankable_id, positive) VALUES(3,1,TRUE);
INSERT INTO Vote(user_id, rankable_id, positive) VALUES(3,2,TRUE);
INSERT INTO Vote(user_id, rankable_id,  positive) VALUES(3,3,TRUE);
INSERT INTO Vote(user_id, rankable_id,  positive) VALUES(3,4,FALSE);
INSERT INTO Vote(user_id, rankable_id,  positive) VALUES(3,5,FALSE);
INSERT INTO Vote(user_id, rankable_id,  positive) VALUES(3,6,TRUE);
INSERT INTO Vote(user_id, rankable_id,  positive) VALUES(3,14,TRUE);
INSERT INTO Vote(user_id, rankable_id,  positive) VALUES(3,15,TRUE);

INSERT INTO Travel (id, name) VALUES (1, 'viaje a buenos aires');
INSERT INTO Travel (id, name) VALUES (2, 'viaje a buenos aires en 2010');

INSERT INTO User_Travel (User_id, travels_id) VALUES (1, 1);
INSERT INTO User_Travel (User_id, travels_id) VALUES (1, 2);

INSERT INTO Destination (id, place_id, travelticket_id, travel_id) VALUES (1, 1, NULL, 1);
INSERT INTO Destination (id, place_id, travelticket_id, travel_id) VALUES (2, 2, 11, 1);
INSERT INTO Destination (id, place_id, travelticket_id, travel_id) VALUES (3, 1, NULL, 2);
INSERT INTO Destination (id, place_id, travelticket_id, travel_id) VALUES (4, 2, 9, 2);

INSERT INTO Destination_AccomodationTickets (destination_id, accomodationticket_id) VALUES (1, 3);
INSERT INTO Destination_AccomodationTickets (destination_id, accomodationticket_id) VALUES (2, 4);
INSERT INTO Destination_AccomodationTickets (destination_id, accomodationticket_id) VALUES (3, 3);
INSERT INTO Destination_AccomodationTickets (destination_id, accomodationticket_id) VALUES (4, 4);
