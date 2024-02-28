package com.arenas;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) throws InputMismatchException, Exception {
		Scanner scanner = new Scanner(System.in);

		Boolean done = false;

		// Initialize the type of list for storing "Arena" Objects
		List<Arena> arenaObjList = ArenaDBTools.initialize(new ArrayList<Arena>());

		// Create a list of Strings to process the text in the IndoorArena.txt file
		List<String> strList = new ArrayList<String>();

		// Read "indoorArena.txt" and fill up an ArrayList with Strings
		strList = createStrList(strList);

		// Create database connection A
		Connection connA = ArenaDBTools.createDatabaseConnectionA(arenaObjList);

		/* 1. Delete Database if it exists */
		ArenaDBTools.deleteDatabase(connA);

		/* 2. Create database connection to 'postgres' database */
		ArenaDBTools.createDatabase(connA);

		// Close the connection to 'postgres' database */

		closeConnection(connA);

		/* 3. Create database connection to 'ArenasDB' database */
		// Initialize Connection B to null
		Connection connB = null;

		// create database connection
		connB = ArenaDBTools.createDatabaseConnectionB(arenaObjList);

		/* 4. Check if the table exist, if so, drop the table */
		System.out.println("Checking for existing tables.");
		ArenaDBTools.dropTable(connB);

		/* 5. Create list of objects */
		arenaObjList = ArenaDBTools.createArenaObjList(strList, arenaObjList);

		/* Build database tables for arenasDB */
		ArenaDBTools.buildDatabaseTables(connB, arenaObjList);

		while (!done) {

			Integer selection = getInput(scanner);

			done = executeAction(selection, connB, arenaObjList);

		}

		// close connection to "ArenasDB"
		closeConnection(connB);
		scanner.close();

	}

	// Method to close connection to database
	public static void closeConnection(Connection connection) {

		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// get input from user
	public static Integer getInput(final Scanner scanner) throws Exception, InputMismatchException {
		String input = "";
		int selection = -1;
		boolean booleanA = false;

		while (!booleanA) {
			System.out.println("Select an option from the following: ");
			System.out.println("1. Build / Rebuild Arena Table");
			System.out.println("2. Display all of the Arena info in the Arena Table");
			System.out.println("3. Display all of the Arena info for Basketball Tenants");
			System.out.println("4. Display the average capacity for all Arenas");
			System.out.println(
					"5. Display the Venue and Year Opened info for all Arenas Opened after 2000 in ascending order by year");
			System.out.println("6. Display the Total Attendance for Arenas in California");
			System.out.println("7. Exit");

			try {

				input = scanner.nextLine();

				selection = Integer.parseInt(input);

				verifyInput(selection);

				return selection;

			} catch (InputMismatchException e) {
				System.out.println("Invalid input type... must be a positive integer value 1-7");

			} catch (Exception e) {
				e.getMessage();
			}
		}

		return selection;

	}

	public static void verifyInput(int selection) throws Exception {
		if (selection <= 0 || selection > 7) {
			throw new Exception("Invalid input... must be a positive integer value 1-7");
		}
	}

	public static Boolean executeAction(int selection, final Connection connB,
			List<Arena> arenaObjList) {

		switch (selection) {

		case 1:
			/* Build Database Tables */
			System.out.println();
			ArenaDBTools.dropTable(connB);
			ArenaDBTools.buildDatabaseTables(connB, arenaObjList);
			System.out.println();
			break;
		case 2:
			/* Display All Tables */
			System.out.println();
			printObjList(arenaObjList);

			System.out.println();
			break;

		case 3:

			ArenaDBTools.buildArenaBasketballTenants(arenaObjList, connB);

			break;

		case 4:
			ArenaDBTools.averageCapacity(arenaObjList, connB);
			break;
		case 5:
			System.out.println("Case #5");
			break;
		case 6:
			System.out.println("Case #6");
			break;
		case 7:
			System.out.println("Case #7");
			return true;

		}

		return false;

	}

	// Method prints the ArenaList
	public static void printObjList(List<Arena> arenaObjList) {
		System.out.print("\n\n");

		String[] titleBar = { "Venue", "City", "State", "Max Capacity", "Year Opened", "Team Name",
				"Sport", "League" };
		int k = 0;

		while (k < titleBar.length) {
			System.out.printf("%-50s \t", titleBar[k].toUpperCase());
			k++;
		}
		System.out.println();

		for (int i = 0; i < arenaObjList.size(); i++) {

			System.out.printf("%-50s \t", arenaObjList.get(i).getVenueName());

			System.out.printf("%-50s \t", arenaObjList.get(i).getCity());

			System.out.printf("%-50s \t", arenaObjList.get(i).getState());

			System.out.printf("%-50s \t", arenaObjList.get(i).getMaxCapacity());

			System.out.printf("%-50d \t", arenaObjList.get(i).getYearOpened());

			System.out.printf("%-50d \t", arenaObjList.get(i).getMaxCapacity());

			System.out.printf("%-50s \t", arenaObjList.get(i).getTeam().getTeamName());

			System.out.printf("%-50s \t", arenaObjList.get(i).getMaxCapacity());

			System.out.printf("%-50s \t", arenaObjList.get(i).getTeam().getSport());

			System.out.printf("%-50s \t", arenaObjList.get(i).getTeam().getLeague());

			System.out.println();

		}
	}

	// Method Creates an ArrayList of Strings using the lines of text in the "Indoor
	// Arena.txt"
	public static List<String> createStrList(List<String> strList) {

		// Streams allow us to process data in a clear and concise way
		FileReader fileReader = null;
		String line = "";
		try {

			fileReader = new FileReader("IndoorArenas.txt");

			// BufferedReader Class uses the FileReader Object and reads the file line by
			// line
			BufferedReader reader = new BufferedReader(fileReader);

			// readLine() --> reads each line of text and terminates when it reaches the
			// of the line
			while ((line = reader.readLine()) != null) {

				strList.add(line);

			}

			fileReader.close();
			reader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (NumberFormatException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return strList;

	}

}
