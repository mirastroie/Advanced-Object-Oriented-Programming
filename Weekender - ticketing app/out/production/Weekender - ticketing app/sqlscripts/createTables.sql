
create table clients (
id int(6) primary key,
phoneNumber char(10),
username varchar(50),
email varchar(120),
fullName varchar(50),
password varchar(30) not null
);
insert into clients(id, phoneNumber, username, email, fullName, password)
values(1, '0199870825', 'andrew.flores', 'andrew.flores@outlook.com', 'Andrew Flores', 'Ferr2is');
insert into clients(id, phoneNumber, username, email, fullName, password)
values(2, '0859044854', 'beverly.hamilton', 'beverly.hamilton@gmail.com', 'Beverly Hamilton', 'Bamboo1234');
insert into clients(id, phoneNumber, username, email, fullName, password)
values(3, '3472779834', 'sylvia.martinez', 'sylvia.martinez@yahoo.com', 'Sylvia Martinez', 'Torin323');
insert into clients(id, phoneNumber, username, email, fullName, password)
values(4, '5868525635', 'brettHop12', 'brett.hopkins@gmail.com', 'Brett Hopkins', 'Alfie121');


create table venues(
id int(6) primary key,
name varchar(100),
description varchar(500),
capacity int(10),
address varchar(400),
region varchar(100),
zip varchar(8),
country varchar(30),
parkingFee float(6,2)
);

insert into venues
values(3, 'Hungarian State Opera House', 'Designed by Miklós Ybl, the Neo-Renaissance Hungarian State Opera House opened in 1884.', 2000, 'Andrássy út 22', 'Budapest', '10610', 'Hungary', 20.7);
insert into venues
values(1, 'Madison Square Garden', 'MSG is a multi-purpose indoor arena in New York City, being the oldest major sporting facility in the NY metropolitan area.', 20000, '4 Pennsylvania Plaza, NYC', 'New York', '10010', 'US', 50.6);
insert into venues
values(2, 'Philippine Arena', 'The world\'s largest indoor arena. Arranged on a greenfield site north of Manila, at Ciudad de Victoria in Sta. Maria Bulacan, the arena has been intended to have the greatest conceivable number of individuals.', 55000, 'Ciudad de Victoria, Bocaue', 'Bulacan', '05976', 'Philippines', 100.2);
insert into venues
values(4, 'Wembley Stadium', 'Wembley Stadium re-opened in May 2007 on the same site as the much loved former stadium. The stadium’s 133m high arch has become a new landmark for London and is now instantly recognisable around the world.', 90000, 'Wembley, London', 'Greater London', '60012', 'UK', 45.0);
insert into venues
values(5, 'Sydney Opera House', 'Sydney Opera House is one of the 20th century\'s most famous and distinctive buildings. The building comprises multiple performance venues, which together host well over 1,500 performances annually, attended by more than 1.2 million people.', 5738, 'Bennelong Point, Sydney', 'New South Wales', 20000, 'Australia', 70.0);
insert into venues
values(6, 'Cineteca Matadero', 'This Spanish theater looks more like an arthouse. It\'s the perfect ambiance for the experimental films and documentaries it shows.', 10000, '8 Plaza de Legazpi, Madrid', 'Madrid', '28045', 'Spain', 30.0);


create table organizers(
id int(6) primary key,
description varchar(300),
fullName varchar(100),
username varchar(100),
password varchar(40) not null,
email varchar(100) not null,
events varchar(300)
);

alter table organizers
drop column events;

insert into organizers(id, description, fullName, username, password, email)
values(6, 'Sydney Theatre Company has been a major force in Australian drama since its establishment in 1978.', 'Sydney Theatre Company', 'sydneytheatre', 'loveTheatre123', 'planning@sydneytheatre.au');
insert into organizers(id, description, fullName, username, password, email)
values(7, 'Kaptiva Sports is a Global Sports Management company with a major expertise in Event Management & Sports Marketing.', 'Kaptiva Sports', 'kaptivesports', 'K3pt2ve', 'planning@kaptivesports.com');

insert into organizers(id, description, fullName, username, password, email)
values(8, 'Events by Wonderland have more upbeat, modern, and young vibe than some other tenured companies.', 'Wonderland', 'wonderland', 'wonD2ers', 'agency@wonderland.com');

insert into organizers(id, description, fullName, username, password, email)
values(9, 'Notable Playwrights Horizons premieres include “Assassins,” “Clybourne Park,” “Circle Mirror Transformation,” and “James Joyce’s The Dead.”', 'Playwrights Horizons', 'playwrights.horizons', 'plS2113', 'agency@phorizons.com');

insert into organizers(id, description, fullName, username, password, email)
values(10, 'Crokos describes herself as an architect, stylist, and producer for her sophisticated fêtes.', 'Sofia Crokos', 'sofia.crokos', 'Soph@123', 'sofiacrokos@savethedate.com');

create table employees(
id int(6) primary key,
description varchar(300),
occupation varchar(100),
firstName varchar(50),
lastName varchar(50)
);

insert into employees
values(5, 'Demetriades has featured in the films Nerve and Around The Block. Her theatre roles include Bell Shakespeare\'s Pericles, for which she was nominated for a 2009 Green Room Award.', 'Actress', 'Andrea', 'Demetriades');
insert into employees
values(3, 'He is a Russian professional ice hockey winger and alternate captain for the New York Rangers of the National Hockey League (NHL).', 'Hockey player', 'Artemi-Sergeyevich', 'Panarin');
insert into employees
values(6, 'She is an actress and writer, known for Eddy and Viv (2013), Crownies (2011) and Panic at Rock Island (2011).', 'Actress', 'Chantelle', 'Jamieson');
insert into employees
values(7, 'Henley sang the lead vocals on Eagles hits such as Hotel California and Life in the Fast Lane.', 'Singer', 'Donald-Hugh', 'Henley');
insert into employees
values(1, 'He is a British musician and actor, best known as the drummer, co-founder, and leader of the rock band Fleetwood Mac.', 'Singer', 'Mick', 'Fleetwood');

insert into employees
values(4, 'He is a Canadian professional ice hockey player and captain of the Pittsburgh Penguins of the National Hockey League (NHL). Nicknamed Sid the Kid, Crosby was selected first overall by the Penguins in the 2005 NHL Entry Draft.', 'Hockey player', 'Sidney-Patrick', 'Crosby');
insert into employees
values(2, 'She is known for her distinctive voice, mystical stage persona and poetic, symbolic lyrics', 'Singer', 'Stevie-Lynn', 'Nicks');


create table eventsEmployees
(
   event_id int(6),
   employee_id int(6),
   foreign key(event_id) references events(id),
   foreign key (employee_id) references employees(id),
   primary key(event_id, employee_id)
);
create table events
(
id int(6) primary key,
name varchar(100) not null,
startDate varchar(50),
endDate varchar(50),
description varchar(400),
availableSeats int(7),
basePrice float(6,2),
venue int(6),
organizer int(6),
foreign key (venue) references venues(id),
foreign key(organizer) references organizers(id)
);

insert into events
values(1, 'July Vibes',  '2021-07-18 18:00:00', '2021-07-22 23:30:00', 'Fleetwood Mac concert', 20000, 150.0, 1, 9);
insert into eventsEmployees
values(1,1);
insert into eventsEmployees
values(1,2);



insert into events
values(2, 'Eagles Concert', '2021-08-28 19:00:00','2021-08-28 22:00:00', 'Concert by the Eagles - an American rock band formed in Los Angeles in 1971. With five number-one singles and six number one albums, six Grammy Awards, and five American Music Awards, the Eagles were one of the most successful musical acts of the 1970s in North America.', 20000, 68.0, 4, 9);
insert into eventsEmployees
values(2,7);

insert into events
values(3, 'New York Rangers vs Pittsburgh Penguins', '2021-04-06 18:30:00','2021-04-06 21:30:00', 'The New York Rangers play the Pittsburgh Penguins at Madison Square Garden on April 6, 2021.', 10000, 70.0, 1, 8);
insert into eventsEmployees
values(3,3);
insert into eventsEmployees
values(3,4);
insert into events
values(4, 'Home I’m Darling', '2021-04-29 13:30:00','2021-04-29 14:30:00', 'This incisive and entertaining Olivier award-winning new comedy is full of big skirts and big questions.', 200, 100.0, 5, 6);
insert into eventsEmployees
values(4,5);
insert into eventsEmployees
values(4,6);

select * from organizers;
select * from clients;
select * from eventsemployees;
select * from events;
select * from tickets;
select * from venues;
