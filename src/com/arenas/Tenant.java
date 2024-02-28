package com.arenas;

//Tenant Class
public class Tenant {

	// Class fields
	private String teamName;
	private String sport;
	private String league;

	// Default Constructor
	public Tenant() {
		this.teamName = "";
		this.sport = "";
		this.league = "";

	}

	// Overloaded Constructor
	public Tenant(String teamName, String sport, String league) {
		super();
		this.teamName = teamName;
		this.sport = sport;
		this.league = league;
	}

	// Copy Constructor used to create a new Tenant Object by copying the values of
	// the fields from an existing Tenant Object
	public Tenant(Tenant obj) {
		this.teamName = obj.teamName;
		this.sport = obj.sport;
		this.league = obj.league;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public String getLeague() {
		return league;
	}

	public void setLeague(String league) {
		this.league = league;
	}

	// Method creates a Tenant Class Object
	public static Tenant createTenantObject() {

		return new Tenant();

	}

	// toString Method for the Tenant instance/object
	@Override
	public String toString() {
		return "Tenant [teamName=" + teamName + ", sport=" + sport + ", league=" + league + "]";
	}

}
