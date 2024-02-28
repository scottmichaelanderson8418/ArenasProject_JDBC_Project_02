package com.arenas;

//Arena Object Class
public class Arena {
	/* HI MOM */
	// Initialize Arena Fields
	private String city;
	private int maxCapacity;
	private String state;
	// Aggregate field from Tenant Class
	private Tenant team;
	private String venueName;
	private int yearOpened;

	// Create a default constructor initialize the Arena Object fields
	public Arena() {
		this.city = "";
		this.maxCapacity = 0;
		this.state = "";
		this.venueName = "";
		this.yearOpened = 0;
		this.team = null;

	}

	// Overloaded Constructor
	public Arena(
			String venueName,
			String city,
			String state,
			int maxCapacity,
			int yearOpened,
			Tenant team) {
		super();
		this.venueName = venueName;
		this.city = city;
		this.state = state;
		this.maxCapacity = maxCapacity;
		this.yearOpened = yearOpened;
		this.team = team;
	}

	public String getCity() {
		return city;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public String getState() {
		return state;
	}

	// returning an instance of the Tenant Class initialized with the team object
	// The instance of the Tenant Class is associated with the current Arena Object
	public Tenant getTeam() {
		return new Tenant(team);
	}

	public String getVenueName() {
		return venueName;
	}

	public int getYearOpened() {
		return yearOpened;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public void setState(String state) {
		this.state = state;
	}

	// instantiating an instance of the Tenant Class initialized with the team
	// object
	// The instance of the Tenant Class is associated with the current Arena Object

	public void setTeam(Tenant team) {
		this.team = new Tenant(team);
	}

	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	public void setYearOpened(int yearOpened) {
		this.yearOpened = yearOpened;
	}

	// Method to create Arena Class Object
	public static Arena createArenaObject() {

		return new Arena();

	}

	// Prints Arena instance to the console
	@Override
	public String toString() {

		return this.venueName + "\n" + this.city + "\n" + this.state + "\n" + this.maxCapacity +
				"\n" + this.yearOpened + "\n" + this.getTeam().getTeamName() + "\n" +
				this.getTeam().getSport() + "\n" + this.getTeam().getLeague();
	}

}
