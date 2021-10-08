import java.util.ArrayList;
import java.util.Scanner;

// Flight System for one single day at YYZ (Print this in title) Departing flights!!


public class FlightReservationSystem
{
	public static void main(String[] args) throws DuplicatePassengerException, SeatOccupiedException
	{
		FlightManager manager = new FlightManager();

		ArrayList<Reservation> myReservations = new ArrayList<Reservation>();	// my flight reservations

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		while (scanner.hasNextLine())
		{
			String inputLine = scanner.nextLine();
			if (inputLine == null || inputLine.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}

			Scanner commandLine = new Scanner(inputLine);
			String action = commandLine.next();

			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}

			else if (action.equals("Q") || action.equals("QUIT"))
				return;

			else if (action.equalsIgnoreCase("LIST"))
			{
				manager.printAllFlights(); 
			}
			else if (action.equalsIgnoreCase("RES"))
			{
				String flightNum = null;
				String passengerName = "";
				String passport = "";
				String seatType = "";

				if (commandLine.hasNext())
				{
					flightNum = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					passengerName = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					passport = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					seatType = commandLine.next();

					Reservation res = new Reservation(flightNum, passengerName, passport);
					try {
					res = manager.reserveSeatOnFlight(flightNum, passengerName, passport, seatType);

						if (res != null) {
							myReservations.add(res);
							res.print();
						}
					} catch (DuplicatePassengerException e) {
						e.getMessage();						
					} catch (SeatOccupiedException e) {
						e.getMessage();
					} catch (FlightNotFoundException e) {
						e.getMessage();
					}
				}
			}
			else if (action.equalsIgnoreCase("CANCEL"))
			{
				String flightNum = null;
				String passengerName = "";
				String passport = "";

				if (commandLine.hasNext())
				{
					flightNum = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					passengerName = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					passport = commandLine.next();

					try {
						int index = myReservations.indexOf(new Reservation(flightNum, passengerName, passport));
						if (index == -1)
							throw new PassengerNotInManifestException();
						manager.cancelReservation(myReservations.get(index));
						myReservations.remove(index);
					} catch (PassengerNotInManifestException e) {
						e.getMessage();
					} catch (FlightNotFoundException e) {
						e.getMessage();
					}
				}
			}
			else if (action.equalsIgnoreCase("SEATS"))
			{
				String flightNum = "";

				if (commandLine.hasNext())
				{
					flightNum = commandLine.next();

					if (manager.flightNumbers.contains(flightNum)) {
						manager.flightMap.get(flightNum).printSeats();
						System.out.println("\nXX = Occupied  + = First Class");
					} else {
						System.out.println("Flight " + flightNum + " not found");
					}
				}
			}
			else if (action.equalsIgnoreCase("MYRES"))
			{
				for (Reservation myres : myReservations)
					myres.print();
			}
			else if (action.equalsIgnoreCase("SORTBYDEP"))
			{
				manager.sortByDeparture();
			}
			else if (action.equalsIgnoreCase("SORTBYDUR"))
			{
				manager.sortByDuration();
			}
			else if (action.equalsIgnoreCase("CRAFT"))
			{
				manager.printAllAircraft();
			}
			else if (action.equalsIgnoreCase("SORTCRAFT"))
			{
				manager.sortAircraft();
			}
			else if (action.equalsIgnoreCase("PASMAN"))
			{
				String flightNum = "";

				if (commandLine.hasNext())
				{
					flightNum = commandLine.next();

					if (manager.flightNumbers.contains(flightNum)) {
						manager.flightMap.get(flightNum).printPassengerManifest();
					}
				} else {
					System.out.println("Flight " + flightNum + " not found");
				}
			}
			System.out.print("\n>");
		}
	}


}

