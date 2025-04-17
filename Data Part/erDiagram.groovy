erDiagram
    PASSENGER ||--o{ BOOKING : books
    BOOKING }|--|| FLIGHT : involves
    FLIGHT ||--|| AIRPORT : departsFrom
    FLIGHT ||--|| AIRPORT : arrivesAt
    FLIGHT ||--|| PLANE : uses
    AIRLINE ||--o{ PLANE : owns

    BOOKING {
        int BookingID PK
        int PassengerID FK references PASSENGER(PassengerID)
        int FlightID FK references FLIGHT(FlightID)
        srting BookingDate
        string SeatID
    }

    PASSENGER {
        int PassengerID PK
        string FirstName
        string LastName
        char Gender
        int Age
        string NationalityCode
    }

    AIRPORT {
        string AirportID PK
        string City
        string TimeZone
        string Type
    }

    PLANE {
        string PlaneID PK
        string PlaneModel
        int Capacity
        string RegistrationCountryCode
        int Age
        string CurrentOperator FK references AIRLINE(IATACode)
    }

    FLIGHT {
        string FlightID PK
        string DepartureAirportID FK references AIRPORT(AirportID)
        string ArrivalAirportID FK references AIRPORT(AirportID)
        string FlightStatus
        string PlaneID FK references PLANE(PlaneID)
        string ScheduledDeparture
        string ActualDeparture
        string ScheduledArrival
        string ActualArrival
    }

    AIRLINE {
        string IATACode PK
        string Headquarters
        string Alliance
        string FoundingYear
    }