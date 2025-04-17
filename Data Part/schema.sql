-- Creating the PASSENGER table
CREATE TABLE PASSENGER (
    PassengerID INT PRIMARY KEY, 
    FirstName VARCHAR(255),
    LastName VARCHAR(255),
    Gender CHAR(1),
    Age INT,
    Nationality VARCHAR(255)
);

-- Creating the AIRPORT table
CREATE TABLE AIRPORT (
    AirportName VARCHAR(255) PRIMARY KEY,
    AirportCountryCode CHAR(2),
    CountryName VARCHAR(255),
    AirportContinent VARCHAR(255)
);

-- Creating the PLANE table
CREATE TABLE PLANE (
    PlaneID INT PRIMARY KEY,
    PlaneModel VARCHAR(255),
    Capacity INT
);

-- Creating the AIRLINE table
CREATE TABLE AIRLINE (
    AirlineID INT PRIMARY KEY,
    AirlineName VARCHAR(255),
    Headquarters VARCHAR(255)
);

-- Creating the FLIGHT table
-- Note that DepartureAirport and ArrivalAirport are assumed to be VARCHAR to match the AirportName
-- PlaneID and AirlineID are assumed to be INT to match the primary keys in their respective tables
CREATE TABLE FLIGHT (
    FlightID INT PRIMARY KEY,
    DepartureAirport VARCHAR(255),
    ArrivalAirport VARCHAR(255),
    DepartureDate DATE,
    FlightStatus VARCHAR(255),
    PlaneID INT,
    AirlineID INT,
    FOREIGN KEY (DepartureAirport) REFERENCES AIRPORT(AirportName),
    FOREIGN KEY (ArrivalAirport) REFERENCES AIRPORT(AirportName),
    FOREIGN KEY (PlaneID) REFERENCES PLANE(PlaneID),
    FOREIGN KEY (AirlineID) REFERENCES AIRLINE(AirlineID)
);

-- Creating the FLIGHT-SCHEDULE table
CREATE TABLE FLIGHT_SCHEDULE (
    FlightID INT PRIMARY KEY,
    ScheduledDeparture DATETIME,
    ActualDeparture DATETIME,
    ScheduledArrival DATETIME,
    ActualArrival DATETIME,
    FOREIGN KEY (FlightID) REFERENCES FLIGHT(FlightID)
);


-- insert data into DB from csv file name under "/data"
-- FK constraint:
-- player: 2493
-- team: 30
-- game: 26109 -> 26080
-- player_season_info: 3159 -> 3047
-- team_season_info: 150 -> 150
-- game_player_info: 668628 -> 652440
