import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;


public class FlightManager
{
	//ArrayList<Flight> flights = new ArrayList<Flight>();

	String[] cities 	= 	{"Dallas", "New York", "London", "Paris", "Tokyo"};
	final int DALLAS = 0;  final int NEWYORK = 1;  final int LONDON = 2;  final int PARIS = 3; final int TOKYO = 4;

	int[] flightTimes = { 3, // Dallas
			1, // New York
			7, // London
			8, // Paris
			16,// Tokyo
	};

	ArrayList<Aircraft> airplanes = new ArrayList<Aircraft>();  
	ArrayList<String> flightNumbers = new ArrayList<String>();
	TreeMap<String, Flight> flightMap = new TreeMap<String, Flight>();

	Random random = new Random();

	public FlightManager()
	{
		// Create some aircraft types with new max seat capacities
		airplanes.add(new Aircraft(44, "Boeing 737"));
		airplanes.add(new Aircraft(84, "Airbus 320"));
		airplanes.add(new Aircraft(24, "Dash-8 100"));
		airplanes.add(new Aircraft(4, "Bombardier 5000"));
		airplanes.add(new Aircraft(112, 16, "Boeing 747"));
		Collections.sort(airplanes); // sorts airplanes by number of seats

		// Populate the list of flights with test flights from text file
		try {
			File file = new File("flights.txt");
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				String airline = scanner.next().replace('_', ' ');
				String city = scanner.next().replace('_', ' ');
				int cityIndex = 0;
				for (int i = 0; i < cities.length; i++) {
					if (cities[i].equals(city)) {
						cityIndex = i;
						break;
					}
				}
				String departure = scanner.next();
				int capacity = scanner.nextInt();
				for (Aircraft plane : airplanes) {
					if (capacity <= plane.getTotalSeats()) {
						String flightNum = generateFlightNumber(airline);
						flightNumbers.add(flightNum);
						if (!city.equals("Tokyo")) { // creates flight
							Flight flight = new Flight(flightNum, airline, city, departure, flightTimes[cityIndex], plane);
							flightMap.put(flightNum, flight);
							break;
						} else { // creates longhaul flight for Tokyo flight
							Flight flight = new LongHaulFlight(flightNum, airline, city, departure, flightTimes[cityIndex], plane);
							flightMap.put(flightNum, flight);
							break;
						}
					}
				}
			}
		} 
		catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		}
	}

	private String generateFlightNumber(String airline)
	{
		String word1, word2;
		Scanner scanner = new Scanner(airline);
		word1 = scanner.next();
		word2 = scanner.next();
		String letter1 = word1.substring(0, 1);
		String letter2 = word2.substring(0, 1);
		letter1.toUpperCase(); letter2.toUpperCase();

		// Generate random number between 101 and 300
		boolean duplicate = false;
		int flight = random.nextInt(200) + 101;
		String flightNum = letter1 + letter2 + flight;
		return flightNum;
	}

	public void printAllFlights()
	{
		for (String f : flightMap.keySet())
			System.out.println(flightMap.get(f));
	}

	public boolean seatsAvailable(String flightNum, String seatType) throws FlightNotFoundException
	{
		if (!flightMap.containsKey(flightNum))
		{
			throw new FlightNotFoundException(flightNum);
		}
		return flightMap.get(flightNum).seatsAvailable(seatType);
	}

	public Reservation reserveSeatOnFlight(String flightNum, String name, String passport, String seatType) throws SeatOccupiedException, DuplicatePassengerException, FlightNotFoundException
	{
		if (!flightMap.containsKey(flightNum))
		{
			throw new FlightNotFoundException(flightNum);
		}
		Flight flight = flightMap.get(flightNum);
		Passenger p = new Passenger(name, passport, "", seatType);

		if (!seatType.equals(null))
				flight.reserveSeat(p, seatType);

		return new Reservation(flightNum, flight.toString(), name, passport, "", seatType);
	}

	public boolean cancelReservation(Reservation res) throws PassengerNotInManifestException, FlightNotFoundException
	{
		if (!flightMap.containsKey(res.getFlightNum()))
		{
			throw new FlightNotFoundException(res.getFlightNum());
		}
		Flight flight = flightMap.get(res.getFlightNum());
		Passenger p = new Passenger(res.passengerName, res.passengerPassport);

		flight.cancelSeat(p);
		return true;
	}

	public void sortByDeparture()
	{
		ArrayList<Flight> flightList = new ArrayList<Flight>();
		for (String f : flightMap.keySet()) {
			flightList.add(flightMap.get(f));
		}
		Collections.sort(flightList, new DepartureTimeComparator());
	}

	private class DepartureTimeComparator implements Comparator<Flight>
	{
		public int compare(Flight a, Flight b)
		{
			return a.getDepartureTime().compareTo(b.getDepartureTime());	  
		}
	}

	public void sortByDuration()
	{
		ArrayList<Flight> flightList = new ArrayList<Flight>();
		for (String f : flightMap.keySet()) {
			flightList.add(flightMap.get(f));
		}
		Collections.sort(flightList, new DurationComparator());
	}

	private class DurationComparator implements Comparator<Flight>
	{
		public int compare(Flight a, Flight b)
		{
			return a.getFlightDuration() - b.getFlightDuration();
		}
	}

	public void printAllAircraft()
	{
		for (Aircraft craft : airplanes)
			craft.print();
	}

	public void sortAircraft()
	{
		Collections.sort(airplanes);
	}
}
