create table FLIGHT
(
    ID int constraint FLIGHT_PK primary key auto_increment,
    NUMBER varchar(8) not null,
    "FROM" char(3) not null,
    TO char(3) not null
);
