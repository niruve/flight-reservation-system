# flight-reservation-system
Simulated version of a Flight Reservation System that models flights departing from Pearson airport during a single day

Below are the program functionalities:

1.	FlightReservationSystem: 
This class has the main() method and is the user interaction part. In a while loop, a scanner reads a line of input from the user. The input lines contain words (Strings). Some input lines contain a single word that represents a command (an action). Some lines contain a command word and a parameter string. The commands take the following arguments:
  a.	LIST: lists flight information for all flights
  b.	RES flight name passport seat: reserve a flight for passenger with name, passport and specified seat. For example: res UA267 McInerney DD1234 7B. A first class seat is designated by a row, one of ABCD and a ‘+’ character (e.g. 4A+)
  c.	CANCEL flight name passport: cancel a reservation on flight for passenger with name and passport
  d.	SEATS flight: prints out the seats on this flight. If seat is occupied, prints “XX“ instead of the seat string.
  e.	PASMAN flight:  prints the passenger manifest for this flight
  f.	MYRES: prints all reservations 

2.	Reservation: class Reservation models a simple electronic reservation containing flight information, and whether the reservation is for a first-class seat. 

3.	Aircraft: 
class Aircraft models an aircraft type (e.g. Boeing 747) and contains information about the model name, the maximum number of economy seats and the maximum number of first class seats (most often this is 0). class Aircraft also has a field called seatLayout: String[][] seatLayout. This is a 2D array of strings. Methods are added to the class to specify the rows and columns for this aircraft. If the capacity is, for example 44, then make the number of rows 4 and the number of columns 11. Note that when this 2D array is printed the array columns are actually labelled as rows – this models the idea of a row on a real airplane. For simplicity, we will always use a seat capacity divisible by 4.

4.	Passenger: class Passenger contains a name, passport number, and a seat number and seat type.
There is added functionality to class Flight so that a flight keeps a list of its passengers and such that a passenger is assigned a random seat number. Class Flight ensures that there are no duplicate passengers when reserving a seat. There is also an equals method where a passenger is equal to another passenger if they have the same name and passport. Passenger commands include:
  a. RESPSNGR (reserve a flight for a passenger with name and passport number)
  b. CNCLPSNGR (cancel flight reservation)
  c. PSNGRS (print list of passengers for a given flight)

5.	Flight: class Flight contains several instance variables to model an airline flight departing Toronto and flying to several cities. Each flight has a string identifier flightNum (for example “AC210” for Air Canada flight 210). 

6.	FlightManager: this class contains the bulk of the logic for the system. It maintains an array list of Flight objects.

7.	LongHaulFlight: a subclass of class Flight. It models a long flight to a far-away destination. Long haul flights almost always have different classes of seats (first class, business, economy etc.). In this implementation, it adds variables to keep track of the number of first-class passengers as well as the seat type (first class or economy). It inherits all variables and methods from class Flight. It overrides inherited methods reserveSeat() and cancelSeat() and has them call new methods reserveSeat(String seatType) and cancelSeat(String seatType) respectively.

