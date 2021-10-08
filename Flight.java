import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

public class Flight
{
	public enum Status {DELAYED, ONTIME, ARRIVED, INFLIGHT};
	public static enum Type {SHORTHAUL, MEDIUMHAUL, LONGHAUL};
	public static enum SeatType {ECONOMY, FIRSTCLASS, BUSINESS};

	private String flightNum;
	private String airline;
	private String origin, dest;
	private String departureTime;
	private Status status;
	private int flightDuration;
	protected Aircraft aircraft;
	protected int numPassengers;
	protected Type type;
	protected ArrayList<Passenger> manifest;
	protected TreeMap<String, Passenger> seatMap;

	protected Random random = new Random();

	public Flight()
	{
		this.flightNum = "";
		this.airline = "";
		this.dest = "";
		this.origin = "Toronto";
		this.departureTime = "";
		this.flightDuration = 0;
		this.aircraft = null;
		numPassengers = 0;
		status = Status.ONTIME;
		type = Type.MEDIUMHAUL;
		manifest = new ArrayList<Passenger>();
		seatMap = new TreeMap<String, Passenger>();
	}

	public Flight(String flightNum)
	{
		this.flightNum = flightNum;
	}

	public Flight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
	{
		this.flightNum = flightNum;
		this.airline = airline;
		this.dest = dest;
		this.origin = "Toronto";
		this.departureTime = departure;
		this.flightDuration = flightDuration;
		this.aircraft = aircraft;
		numPassengers = 0;
		status = Status.ONTIME;
		type = Type.MEDIUMHAUL;
		manifest = new ArrayList<Passenger>();
		seatMap = new TreeMap<String, Passenger>();
	}

	public Type getFlightType()
	{
		return type;
	}

	public String getFlightNum()
	{
		return flightNum;
	}
	public void setFlightNum(String flightNum)
	{
		this.flightNum = flightNum;
	}
	public String getAirline()
	{
		return airline;
	}
	public void setAirline(String airline)
	{
		this.airline = airline;
	}
	public String getOrigin()
	{
		return origin;
	}
	public void setOrigin(String origin)
	{
		this.origin = origin;
	}
	public String getDest()
	{
		return dest;
	}
	public void setDest(String dest)
	{
		this.dest = dest;
	}
	public String getDepartureTime()
	{
		return departureTime;
	}
	public void setDepartureTime(String departureTime)
	{
		this.departureTime = departureTime;
	}

	public Status getStatus()
	{
		return status;
	}
	public void setStatus(Status status)
	{
		this.status = status;
	}
	public int getFlightDuration()
	{
		return flightDuration;
	}
	public void setFlightDuration(int dur)
	{
		this.flightDuration = dur;
	}

	public int getNumPassengers()
	{
		return numPassengers;
	}
	public void setNumPassengers(int numPassengers)
	{
		this.numPassengers = numPassengers;
	}

	public void assignSeat(Passenger p, String seat)
	{
		for (int i = 0; i < aircraft.seatLayout.length; i++) {
			for (int j = 0; j < aircraft.seatLayout[i].length; j++) {
				if (aircraft.seatLayout[i][j].equals(seat)) {
					aircraft.seatLayout[i][j] = "XX"; // replaces seat with XX
				}
			}
		}
	}

	public void removeSeat(Passenger p, String seat)
	{
		// Finds and replaces occupied "XX" seat's with original seat number
		if (aircraft.getTotalSeats() > 4) {
			char c = 'A';
			for (int i = 0; i < aircraft.seatLayout.length; i++) {
				for (int j = 0; j < aircraft.seatLayout[i].length; j++) {
					String occupied = j + 1 + Character.toString(c); // original seat number
					if (occupied.equals(seat)) {
						aircraft.seatLayout[i][j] = seat;
						break;
					} else if ((occupied+"+").equals(seat)) { // checks if seat is first class
						aircraft.seatLayout[i][j] = seat;
						break;
					}
				}
				c++;
			}
			// Same process as above but when seats = 4
		} else {
			char c = 'A';
			for (int i = 0; i < aircraft.seatLayout.length; i++) {
				for (int j = 0; j < aircraft.seatLayout[i].length; j++) {
					String occupied = j + 1 + Character.toString(c);
					if (occupied.equals(seat)) {
						aircraft.seatLayout[i][j] = seat;
						break;
					}
				}
				c++;
			}
		}

	}

	public String getLastAssignedSeat()
	{
		if (!manifest.isEmpty())
			return manifest.get(manifest.size()-1).getSeat();
		return "";
	}

	public boolean seatsAvailable(String seatType)
	{
		if (!seatType.equalsIgnoreCase("ECO")) return false;
		return numPassengers < aircraft.numEconomySeats;
	}

	public void cancelSeat(Passenger p) throws PassengerNotInManifestException
	{
		if (manifest.indexOf(p) == -1) 
		{
			throw new PassengerNotInManifestException(); 														
		}
		for (Entry<String, Passenger> entry : seatMap.entrySet()) {
			String seat = entry.getKey();
			Passenger pass = entry.getValue();
			if (p.equals(pass)) {
				removeSeat(p, seat);
				seatMap.remove(seat);
				break;
			}
		}
		manifest.remove(p);
		return;
	}

	public void reserveSeat(Passenger p, String seat) throws SeatOccupiedException, DuplicatePassengerException
	{
		if (this.seatMap.containsKey(seat))  
		{
			throw new SeatOccupiedException("Seat " + seat + " already occupied");
		}	
		// Check for duplicate passenger
		if (manifest.indexOf(p) >=  0)
		{
			throw new DuplicatePassengerException("Duplicate Passenger " + p.getName() + " " + p.getPassport());
		}
		assignSeat(p, seat);
		manifest.add(p);
		seatMap.put(seat, p);
		return;
	}

	public boolean equals(Object other)
	{
		Flight otherFlight = (Flight) other;
		return this.flightNum.equals(otherFlight.flightNum);
	}

	public String toString()
	{
		return airline + "\t Flight: " + flightNum + "\t Dest: " + dest + "\tDeparting: " + departureTime + "\t Duration: " + flightDuration + "\tStatus: " + status;
	}

	public void printSeats() {
		for (int i = 0; i < aircraft.seatLayout.length; i++) {
			for (int j = 0; j < aircraft.seatLayout[i].length; j++) {
				System.out.print(aircraft.seatLayout[i][j] + " ");
			}
			System.out.println();
		}

	}
	public void printPassengerManifest() {
		for (Entry<String, Passenger> entry : seatMap.entrySet()) {
			String seat = entry.getKey();
			Passenger pass = entry.getValue();
			System.out.println(pass.toString() + seat.toString());
		}
	}
}

class DuplicatePassengerException extends RuntimeException {
	private static final long serialVersionUID = -1511916741471138947L;
	public DuplicatePassengerException(String s) {
		System.out.println(s);
	}
}
class PassengerNotInManifestException extends RuntimeException { 
	private static final long serialVersionUID = -8037363864287563106L;
	public PassengerNotInManifestException() {
		System.out.println("Passenger not found");
	}
}
class SeatOccupiedException extends RuntimeException {
	private static final long serialVersionUID = 5029224065995906867L;
	public SeatOccupiedException(String s) {
		System.out.println(s);
	}
}
class FlightNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 5029224065995906867L;
	public FlightNotFoundException(String s) {
		System.out.println("Flight " + s + " not found");
	}
}
