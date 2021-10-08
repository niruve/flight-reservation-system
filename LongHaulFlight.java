import java.util.Map.Entry;

/*
 * A Long Haul Flight is a flight that travels a long distance and has two types of seats (First Class and Economy)
 */

public class LongHaulFlight extends Flight
{
	int firstClassPassengers;

	public LongHaulFlight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
	{
		super(flightNum, airline, dest, departure, flightDuration, aircraft);
		type = Flight.Type.LONGHAUL;
	}

	public LongHaulFlight()
	{
		firstClassPassengers = 0;
		type = Flight.Type.LONGHAUL;
	}

	public void assignSeat(Passenger p, String seat)
	{
		for (int i = 0; i < aircraft.seatLayout.length; i++) {
			for (int j = 0; j < aircraft.seatLayout[i].length; j++) {
				if (aircraft.seatLayout[i][j].equals(seat)) {
					aircraft.seatLayout[i][j] = "XX";
				}
			}
		}
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

	public int getPassengerCount()
	{
		return getNumPassengers() +  firstClassPassengers;
	}


	public boolean seatsAvailable(String seatType)
	{
		if (seatType.equals("FCL"))
			return firstClassPassengers < aircraft.getNumFirstClassSeats();
		else
			return super.seatsAvailable(seatType);
	}

	public String toString()
	{
		return super.toString() + "\t LongHaul";
	}
}
