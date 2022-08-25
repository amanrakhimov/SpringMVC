CREATE TABLE readerperson (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    fullName varchar(40) NOT NULL UNIQUE,
    yearOfBirth int NOT NULL
);

CREATE TABLE Book (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar(40) NOT NULL,
    author varchar(40) NOT NULL,
    year int NOT NULL,
    personId int REFERENCES readerperson(id) ON DELETE SET NULL
);
