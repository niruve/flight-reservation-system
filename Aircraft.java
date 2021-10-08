
public class Aircraft implements Comparable<Aircraft>
{
	int numEconomySeats;
	int numFirstClassSeats;
	String[][] seatLayout;

	String model;

	public Aircraft(int seats, String model)
	{
		this.numEconomySeats = seats;
		this.numFirstClassSeats = 0;
		this.model = model;
		if (seats > 4) {
			seatLayout = new String[4][seats/4];
			char c = 'A';
			// // creates seats for aircrafts with more than 4 seats
			for (int i = 0; i < seatLayout.length; i++) {
				for (int j = 0; j < seatLayout[i].length; j++) {
					seatLayout[i][j] = j + 1 + Character.toString(c);
				}
				c++;
			}
		} else { // creates seats for aircrafts with only 4 seats
			seatLayout = new String[2][seats/2];
			char c = 'A';
			for (int i = 0; i < seatLayout.length; i++) {
				for (int j = 0; j < seatLayout[i].length; j++) {
					seatLayout[i][j] = j + 1 + Character.toString(c);
				}
				c++;
			}
		}
		
	}

	public Aircraft(int economy, int firstClass, String model)
	{
		this.numEconomySeats = economy;
		this.numFirstClassSeats = firstClass;
		this.model = model;
		int seats = numEconomySeats + numFirstClassSeats;
		seatLayout = new String[4][seats/4];
		char c = 'A';
		if (numFirstClassSeats > 0) { // creates seats for aircrafts with first-class seats
			for (int i = 0; i < seatLayout.length; i++) {
				for (int j = numFirstClassSeats/3; j < seatLayout[i].length; j++) {
					for (int k = 0; k < numFirstClassSeats/3; k++) {
						seatLayout[i][k] = k + 1 + Character.toString(c) + "+";
					}
					seatLayout[i][j] = j + 1 + Character.toString(c);
				}
				c++;
			}
		} else { // creates seats for aircrafts with no first class seats
			for (int i = 0; i < seatLayout.length; i++) {
				for (int j = 1; j < seatLayout[i].length; j++) {
					seatLayout[i][j] = j + 1 + Character.toString(c);
				}
				c++;
			}
		}
	}

	public int getNumSeats()
	{
		return numEconomySeats;
	}

	public int getTotalSeats()
	{
		return numEconomySeats + numFirstClassSeats;
	}

	public int getNumFirstClassSeats()
	{
		return numFirstClassSeats;
	}

	public String getModel()
	{
		return model;
	}

	public void setModel(String model)
	{
		this.model = model;
	}

	public void print()
	{
		System.out.println("Model: " + model + "\t Economy Seats: " + numEconomySeats + "\t First Class Seats: " + numFirstClassSeats);
	}

	public int compareTo(Aircraft other)
	{
		if (this.numEconomySeats == other.numEconomySeats)
			return this.numFirstClassSeats - other.numFirstClassSeats;

		return this.numEconomySeats - other.numEconomySeats; 
	}
	
}
