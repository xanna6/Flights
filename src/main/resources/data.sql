INSERT INTO PASSENGER (ID, FIRSTNAME, LASTNAME, PHONE_NUMBER, EMAIL)
VALUES (default, 'Anna', 'Piotrowska', '123456789', 'mail@mail.com'),
       (default, 'Anna', 'Smith', '987654321', 'mail2@mail.com'),
       (default, 'Sam', 'Smith', '500100200', 'mail3@mail.com'),
       (default, 'Ben', 'Brown', '123123123', 'mail4@mail.com'),
       (default, 'Jan', 'Kowalski', '147852369', 'mail5@mail.com');

INSERT INTO FLIGHT (ID, FLIGHT_NUMBER, DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DEPARTURE_DATE, DEPARTURE_TIME, ALL_SEATS, AVAILABLE_SEATS)
VALUES (default, 'LO269', 'Warsaw (WAW)', 'Amsterdam (AMS)', '2024-04-26', '08:45', 100, 98),
       (default, 'LO355', 'Warsaw (WAW)', 'Barcelona (BCN)', '2024-04-26', '12:15', 120, 119),
       (default, 'LO3922', 'Cracow (KRK)', 'Warsaw (WAW)', '2024-04-26', '20:10', 100, 98),
       (default, 'LO285', 'Warsaw (WAW)', 'London - Heathrow (LHR)', '2024-04-27', '08:00', 150, 149);

INSERT INTO FLIGHT_PASSENGER (FLIGHT_ID, PASSENGER_ID)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (3, 3),
       (4, 5);