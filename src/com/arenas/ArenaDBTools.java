package com.arenas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ArenaDBTools {

	// Initialize method to initialize the list type
	public static List<Arena> initialize(List<Arena> arenaObjList) {

		return arenaObjList;

	}

	public static void averageCapacity(List<Arena> arenaObjList, Connection connB) {

		Statement stmt = null;
		ResultSet result = null;

		int avgMaxCapacity = 0;

		try {

			// Creating Statement object
			stmt = connB.createStatement();

			// Sending PostgresSQL code in Statement object to create tables
			result = stmt.executeQuery("SELECT AVG(maxcapacity) FROM public.arenas");

			result.next();

			String[] titleBar = { "Venue", "City", "State", "Max Capacity", "Year Opened",
					"Team Name", "Sport", "League" };
			int k = 0;

			while (k < titleBar.length) {
				System.out.printf("%-30s \t", titleBar[k].toUpperCase());
				k++;
			}
			System.out.println();
			System.out.println("result.getDouble(1)=" + result.getDouble(1));

			System.out.print("\n\n");

		} catch (SQLException ee) {
			ee.printStackTrace();
		}

		catch (Exception e) {
			// will show what type of error & the message returned from the
			// error
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

	public static List<Arena> createArenaObjList(List<String> strList, List<Arena> arenaObjList) {

		// counter to keep track of the location of Strings and Arena setter methods in
		// the switch function
		int countA = 0;

		// LOOP through the ArrayList<String> and fill up the ArrayList<Arena> list with
		// Arena Objects
		// we subtract "strList.size()" -1 because there are two blank lines at the end
		// of the document
		for (int i = 0; i < strList.size() - 1; i++) {

			Arena arenaObj = new Arena();

			Tenant tenantObj = new Tenant();

			countA = 0;

			// Loop through the lines for each new instance of the Arena Class and Tenant
			// Class
			for (int k = 0; k < 8; k++) {

				// switch function to assign the "arenaObj" fields with values from the
				// "IndoorArena.txt" file
				switch (countA) {

				case 0:
					// Set VenueName
					arenaObj.setVenueName(strList.get(i));
					break;
				case 1:
					// Set City
					arenaObj.setCity(strList.get(i));
					break;
				case 2:
					// SetState
					arenaObj.setState(strList.get(i));
					break;
				case 3:
					// Set Max Capacity
					int number = (Integer.parseInt(strList.get(i)));
					arenaObj.setMaxCapacity(number);
					break;
				case 4:
					// Set Year Opened
					arenaObj.setYearOpened(Integer.parseInt(strList.get(i)));
					break;
				case 5:
					// Set Team Name
					tenantObj.setTeamName(strList.get(i));
					break;
				case 6:
					// Set Sport name
					tenantObj.setSport(strList.get(i));
					break;
				case 7:
					// set league name
					tenantObj.setLeague(strList.get(i));
					break;
				}

				// When the arenaObj is completely instantiated then we can add it to the
				// arenaList
				if (countA == 7) {

					// instantiating an instance of the Tenant Class initialized with the team
					// object
					// The instance of the Tenant Class is associated with the current Arena Object
					arenaObj.setTeam(tenantObj);

					// Add the object to the list
					arenaObjList.add(arenaObj);

				}

				i++;

				countA++;

			}

		}
		return arenaObjList;
	}

	// ------------------------------------------------------------------------------------
	// Method to build the tables
	public static void buildDatabaseTables(final Connection connB, List<Arena> arenaObjList) {

		try {

			// Creating Statement object
			Statement stmt = connB.createStatement();

			// Sending PostgresSQL code in Statement object to create tables
			stmt.execute("CREATE TABLE arenas(Venue CHAR(45) primary key," +
					"City CHAR(15), State CHAR(15), MaxCapacity INT, YearOpened INT, TeamName CHAR(30), Sport CHAR(10), LEAGUE CHAR(15));");

			// Inserting values into table rows
			int number = arenaObjList.size();

			for (int i = 0; i < number; i++) {

				StringBuilder sb = new StringBuilder();

				sb.append(
						"INSERT INTO arenas(Venue, City, State, MaxCapacity, YearOpened, TeamName, Sport, League) ");

				sb.append("VALUES ");

				sb.append("('" + arenaObjList.get(i).getVenueName() + "', ");
				sb.append("'" + arenaObjList.get(i).getCity() + "', ");
				sb.append("'" + arenaObjList.get(i).getState() + "', ");
				sb.append("'" + arenaObjList.get(i).getMaxCapacity() + "', ");
				sb.append("'" + arenaObjList.get(i).getYearOpened() + "', ");
				sb.append("'" + arenaObjList.get(i).getTeam().getTeamName() + "', ");
				sb.append("'" + arenaObjList.get(i).getTeam().getSport() + "', ");
				sb.append("'" + arenaObjList.get(i).getTeam().getLeague() + "'); ");

				stmt.execute(sb.toString());

			}

			System.out.println("Arena table created.");

		} catch (SQLException ee) {
			ee.printStackTrace();
		}

		catch (Exception e) {
			// will show what type of error & the message returned from the
			// error
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	// ------------------------------------------------------------------------------------
	// Method to return query of Arenas with Basketball Tenants
	public static void buildArenaBasketballTenants(List<Arena> arenaObjList, Connection connB) {

		Statement stmt = null;
		ResultSet result = null;
		try {

			// Creating Statement object
			stmt = connB.createStatement();

			// Sending PostgresSQL code in Statement object to create tables
			result = stmt.executeQuery("SELECT * FROM public.arenas WHERE sport = 'Basketball';");

			System.out.print("\n\n");

			System.out.printf("%100S", "ARENAS WHERE SPORT IS BASKETBALL");
			System.out.println();
			String[] titleBar = { "Venue", "City", "State", "Max Capacity", "Year Opened",
					"Team Name", "Sport", "League" };
			int k = 0;

			while (k < titleBar.length) {
				System.out.printf("%-30s \t", titleBar[k].toUpperCase());
				k++;
			}
			System.out.println();

			while (result.next()) {

				System.out.printf("%-30s \t", result.getString("venue"));
				System.out.printf("%-30s \t", result.getString("city"));
				System.out.printf("%-30s \t", result.getString("state"));
				System.out.printf("%-30s \t", result.getInt("maxcapacity"));
				System.out.printf("%-30s \t", result.getInt("yearopened"));
				System.out.printf("%-30s \t", result.getString("teamname"));
				System.out.printf("%-30s \t", result.getString("sport"));
				System.out.printf("%-30s \t", result.getString("league"));

				System.out.println("result.next() = " + result.next());

			}
			System.out.println();

		} catch (SQLException ee) {
			ee.printStackTrace();
		}

		catch (Exception e) {
			// will show what type of error & the message returned from the
			// error
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

	public static void createDatabase(final Connection conn) {

		// check if database exists

		// message to check for tables
		// System.out

		// SQL Code --> must use Try Catch block
		try {
			// statement object
			Statement stmt = conn.createStatement();

			stmt.execute("CREATE DATABASE arenasdb");

			// print confirmation that the table is dropped
			System.out.println("arenasDB database created!!!");

		} catch (SQLException ee) {
			ee.printStackTrace();
		}

		catch (Exception e) {
			// Prints to console what type of error & the message returned from the error
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

	// Create connection to default postgres database
	public static Connection createDatabaseConnectionA(List<Arena> arenaObjList) {

		try {

			// Create connection to database

			// Implicitly loads the driver using the Class.forName() method
			Class.forName("org.postgresql.Driver");

			// Creates connection to the default postgres database
			final Connection conn = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/postgres", "postgres", "five2one");

			// return connection instance
			return conn;

		} catch (SQLException ee) {
			ee.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
			// Prints to console what type of error & the message returned from the error
			System.out.println(e.getClass().getName() + ": " + e.getMessage());

			// Used when we want to terminate the execution of the program at any instance
			System.exit(0);
		}

		// return null if connection fails
		return null;

	}

	// Create connection to the "areasdb" database we created
	public static Connection createDatabaseConnectionB(List<Arena> arenaObjList) {

		try {

			// Create connection to database
			// this will tell us what type of driver we are using
			Class.forName("org.postgresql.Driver");

			// creates connection to "areasdb" Database
			Connection connB = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/arenasdb", "postgres", "five2one");

			// returns instance of Connection Class
			return connB;

			// Catch SQL Exception Error
		} catch (SQLException ee) {
			ee.printStackTrace();
			// Catch any other Exception Errors
		} catch (Exception e) {

			// Prints to console what type of error & the message returned from the error
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			// Used when we want to terminate the execution of the program at any instance

			System.exit(0);
		}

		return null;

	}

	// -------------------------------------------------------------------------------------
	// Method to Delete the existing database
	public static void deleteDatabase(final Connection conn) {

		// check if database exists

		// SQL Code --> must use Try Catch block
		try {
			// statement object
			Statement stmt = conn.createStatement();

			// execute a statement to drop the table
			// use DROP TABLE IF EXISTS to drop a postgres table
			ResultSet resultSet = stmt.executeQuery(
					"SELECT EXISTS(SELECT 1 FROM pg_database WHERE datname='arenasdb');");

			// this moves the resultset up to the first column
			resultSet.next();

			boolean booA = resultSet.getBoolean(1);

			if (booA) {
				// System.out.println(arenasdb + " database is found !!!");
				stmt.execute("DROP DATABASE arenasdb;");

				// System.out.println(arenasdb + " database is deleted!!!");

			}

		} catch (SQLException ee) {
			ee.printStackTrace();

		} catch (Exception e) {
			// will show what type of error & the message returned from the
			// error
			e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());

			System.exit(0);
		}

		// System.out.println("Press Enter");
		// DataTools.pressEnter();

	}

	// -------------------------------------------------------------------------------
	// drop table method drops existing ArenasDB Tables, in case the database
	// already exists
	public static void dropTable(final Connection connB) {
		// boolean value to check for existence of "arenas" table
		boolean tableExists = false;
		try {

			// creating a statement object
			Statement stmt = connB.createStatement();

			// Create a ResultSet Object to receive the boolean value
			ResultSet rs = stmt.executeQuery(
					"SELECT EXISTS (SELECT 1 FROM pg_catalog.pg_tables WHERE schemaname = 'public' AND tablename = 'arenas');");

			while (rs.next()) {

				tableExists = rs.getBoolean(1);

			}

			// if table exists then drop the table
			if (tableExists) {

				stmt.execute("DROP TABLE IF EXISTS arenas CASCADE");
				System.out.println("Arena table dropped.");

			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		} catch (Exception e) {
			// will show what type of error & the message returned from the error
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

}