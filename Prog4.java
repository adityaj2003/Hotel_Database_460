/**
 * This Program is a hotel database management system for Motel460. This program is designed to manage the operations 
 * of Motel460 by providing a database and an associated manipulation/querying application.
 * The program allows customers to book a room for any available dates, add customers to hotel records
 * Customers are given different categories based on what or how they are booking.
 *  College students get discounts on bookings and services provided.
 * If the customer is signed up as a subscribed member of “Club 460,” they will get a variety of perks including
 * discounts, free use of amenities. Certain credit cards are given disount and customer's points can be used 
 * for discount as well. The program also keeps track of employees and their responsibilities/schedule. 
 * The program provides the following functionalities:
 * Record insertion: Supports inserting a new data record via a JDBC interface.
 * Record deletion: Supports deleting an existing data record via a JDBC interface.
 * Record update: Supports updating an existing data record via a JDBC interface.
 * Queries: Supports querying the database via a JDBC interface for the problem description given above. Implements
 * the four provided queries as well as at least one query of our own design.
 * The program provides a command line interface to interact with the dbms and perform the various tasks and functionalities. 
 * 
 * Author: Aditya Jadhav and Aditya Kumar
 * Instructor Name : Lester McCann
 * TA names : Ayush Pinto and Tanner Finken
 *  * Class : CSc 460 - Database design
 * 
 * Due Date: May 1, 2023
 * 
 * Operational requirements -- Written in Java, requires Java SDK 17 to run. The username and password are hardcoded. Also you have to give the classpath in lectura before running. 
 * 								The tables are already created for the hardcoded user
 * Required Features and Bugs- I believe that i have included everything and do not know of any bugs in the program
*/


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.*;
import java.sql.*;
import java.util.Date;

public class Prog4 {

	public static Map<String, Integer> cardDiscount = new HashMap<String, Integer>();
	public static Map<Integer, String> cardNumber = new HashMap<Integer, String>();

	public static void main(String[] args) {
		//Stores the discount for the various types of cards
		cardNumber.put(1000, "AMEX PLATINUM");
		cardNumber.put(2000, "CHASE SAPPHIRE");
		cardNumber.put(3000, "DISCOVER STUDENT");
		cardNumber.put(4000, "CITI PRESTIGE");
		cardDiscount.put("AMEX PLATINUM", 5);
		cardDiscount.put("CHASE SAPPHIRE", 3);
		cardDiscount.put("DISCOVER STUDENT", 1);
		cardDiscount.put("CITI PRESTIGE", 3);

		final String oracleURL = // Magic lectura -> aloe access spell
				"jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";

		String username = "adityajadhav"; // Oracle DBMS username
		String password = "a7683"; // Oracle DBMS password
		Statement stmt = null;
		try {

			Class.forName("oracle.jdbc.OracleDriver");// Initialize the driver.

		} catch (ClassNotFoundException e) {

			System.err.println("*** ClassNotFoundException:  " + "Error loading Oracle JDBC driver.  \n"
					+ "\tPerhaps the driver is not on the Classpath?");
			System.exit(-1);

		}
		//Create connection object and ResultSet object
		Connection dbconn = null;
		ResultSet answer = null;
		//Connect to the db
		try {
			dbconn = DriverManager.getConnection(oracleURL, username, password); // Connect to the sql database.
			stmt = dbconn.createStatement();

		} catch (SQLException e) {

			System.err.println("*** SQLException:  " + "Could not open JDBC connection.");
			System.err.println("\tMessage:   " + e.getMessage());
			System.err.println("\tSQLState:  " + e.getSQLState());
			System.err.println("\tErrorCode: " + e.getErrorCode());
			System.exit(-1);

		}
		//Initialise scanner and jump to mainMenu. 
		Scanner scanner = new Scanner(System.in);
		mainMenu(dbconn, stmt, answer, scanner);

	}

	/**
	 * Displays the main menu for the application, providing various options for the user to
	 * interact with different entities in the database.
	 * 
	 * @param dbconn   The Connection object for interacting with the database.
	 * @param stmt     The Statement object for executing SQL queries on the database.
	 * @param answer   The ResultSet object for storing the results of executed SQL queries.
	 * @param scanner  The Scanner object for receiving user input.
	 * 
	 * Pre-condition : Displays menu result for answering various queries
	 * Post-condition : Takes user input and displays the corresponding queries on the console. 
	 */
	public static void mainMenu(Connection dbconn, Statement stmt, ResultSet answer, Scanner scanner) {
		//Takes int input from user to determine what he wants to do
		System.out.println("What do you wanna do today? (Type a number from (1-5) to specify)\n");
		System.out.println("1. Add / Remove / Update Customer Details");
		System.out.println("2. Add / Remove / Update Booking Details");
		System.out.println("3. Add / Remove / Update Amenities");
		System.out.println("4. Add / Remove / Update Room Details");
		System.out.println("5. Add / Remove / Update UsedAmenities of Customer");
		System.out.println("6. Add / Remove / Update Club460 Members");
		System.out.println("7. Add / Remove / Update Employee Details");
		System.out.println("8. Add / Remove / Update Employee Responsibilities");
		System.out.println("9. Execute Predefined Queries");
		int input = scanner.nextInt();
		// Check user input to determine the chosen option and whether he wants to add/delete/update chose option
		if (input == 1) {
			System.out.println("1. Add Customer Details");
			System.out.println("2. Remove Customer Details");
			System.out.println("3. Update Customer Details");
			int userSelection = scanner.nextInt();
			//Taking user input to check whether to update/delete or add. 
			if (userSelection == 1) {
				addRecord(dbconn, stmt, answer, "Customer", scanner);
			} else if (userSelection == 2) {
				deleteRecord(dbconn, stmt, answer, "Customer", scanner);
			} else if (userSelection == 3) {
				updateRecord(dbconn, stmt, answer, "Customer", scanner);
			} else {
				System.out.println("Invalid Selection");
			}
			mainMenu(dbconn, stmt, answer, scanner);
			// Check user input to determine the chosen option and whether he wants to add/delete/update chose option
		} else if (input == 2) {
			System.out.println("1. Add Booking Details");
			System.out.println("2. Remove Booking Details");
			System.out.println("3. Update Booking Details");
			int userSelection = scanner.nextInt();
			//Taking user input to check whether to update/delete or add. 
			if (userSelection == 1) {
				addRecord(dbconn, stmt, answer, "Booking", scanner);
			} else if (userSelection == 2) {
				deleteRecord(dbconn, stmt, answer, "Booking", scanner);
			} else if (userSelection == 3) {
				updateRecord(dbconn, stmt, answer, "Booking", scanner);
			} else {
				System.out.println("Invalid Selection");
			}
			mainMenu(dbconn, stmt, answer, scanner);
			// Check user input to determine the chosen option and whether he wants to add/delete/update chose option
		} else if (input == 3) {
			System.out.println("1. Add Amenities");
			System.out.println("2. Remove Amenities");
			System.out.println("3. Update Amenities");
			int userSelection = scanner.nextInt();
			//Taking user input to check whether to update/delete or add. 
			if (userSelection == 1) {
				addRecord(dbconn, stmt, answer, "Amenity", scanner);
			} else if (userSelection == 2) {
				deleteRecord(dbconn, stmt, answer, "Amenity", scanner);
			} else if (userSelection == 3) {
				updateRecord(dbconn, stmt, answer, "Amenity", scanner);
			} else {
				System.out.println("Invalid Selection");
			}
			mainMenu(dbconn, stmt, answer, scanner);
			// Check user input to determine the chosen option and whether he wants to add/delete/update chose option
		} else if (input == 4) {
			System.out.println("1. Add Room Information");
			System.out.println("2. Remove Room Information");
			System.out.println("3. Update Room Information");
			int userSelection = scanner.nextInt();
			//Taking user input to check whether to update/delete or add. 
			if (userSelection == 1) {
				addRecord(dbconn, stmt, answer, "RoomDetails", scanner);
			} else if (userSelection == 2) {
				deleteRecord(dbconn, stmt, answer, "RoomDetails", scanner);
			} else if (userSelection == 3) {
				updateRecord(dbconn, stmt, answer, "RoomDetails", scanner);
			} else {
				System.out.println("Invalid Selection");
			}
			mainMenu(dbconn, stmt, answer, scanner);
			// Check user input to determine the chosen option and whether he wants to add/delete/update chose option
		} else if (input == 5) {
			System.out.println("1. Add UsedAmenities");
			System.out.println("2. Remove UsedAmenities");
			System.out.println("3. Update UsedAmenities");
			int userSelection = scanner.nextInt();
			//Taking user input to check whether to update/delete or add. 

			if (userSelection == 1) {
				addRecord(dbconn, stmt, answer, "usedAmenity", scanner);
			} else if (userSelection == 2) {
				deleteRecord(dbconn, stmt, answer, "usedAmenity", scanner);
			} else if (userSelection == 3) {
				updateRecord(dbconn, stmt, answer, "usedAmenity", scanner);
			} else {
				System.out.println("Invalid Selection");
			}

			mainMenu(dbconn, stmt, answer, scanner);
			// Check user input to determine the chosen option and whether he wants to add/delete/update chose option
		} else if (input == 6) {
			System.out.println("1. Add Club460 Members");
			System.out.println("2. Remove Club460 Members");
			System.out.println("3. Update Club460 Members");
			//Taking user input to check whether to update/delete or add. 
			int userSelection = scanner.nextInt();

			if (userSelection == 1) {
				addRecord(dbconn, stmt, answer, "Club460", scanner);
			} else if (userSelection == 2) {
				deleteRecord(dbconn, stmt, answer, "Club460", scanner);
			} else if (userSelection == 3) {
				updateRecord(dbconn, stmt, answer, "Club460", scanner);
			} else {
				System.out.println("Invalid Selection");
			}

			mainMenu(dbconn, stmt, answer, scanner);
			// Check user input to determine the chosen option and whether he wants to add/delete/update chose option
		} else if (input == 7) {
			System.out.println("1. Add Employee Details");
			System.out.println("2. Remove Employee Details");
			System.out.println("3. Update Employee Details");
			int userSelection = scanner.nextInt();
			//Taking user input to check whether to update/delete or add. 

			if (userSelection == 1) {
				addRecord(dbconn, stmt, answer, "Employee", scanner);
			} else if (userSelection == 2) {
				deleteRecord(dbconn, stmt, answer, "Employee", scanner);
			} else if (userSelection == 3) {
				updateRecord(dbconn, stmt, answer, "Employee", scanner);
			} else {
				System.out.println("Invalid Selection");
			}

			mainMenu(dbconn, stmt, answer, scanner);
			// Check user input to determine the chosen option and whether he wants to add/delete/update chose option
		} else if (input == 8) {
			System.out.println("1. Add Employee Responsibilities");
			System.out.println("2. Remove Employee Responsibilities");
			System.out.println("3. Update Employee Responsibilities");
			int userSelection = scanner.nextInt();
			//Taking user input to check whether to update/delete or add. 
			if (userSelection == 1) {
				addRecord(dbconn, stmt, answer, "Responsibility", scanner);
			} else if (userSelection == 2) {
				deleteRecord(dbconn, stmt, answer, "Responsibility", scanner);
			} else if (userSelection == 3) {
				updateRecord(dbconn, stmt, answer, "Responsibility", scanner);
			} else {
				System.out.println("Invalid Selection");
			}

			mainMenu(dbconn, stmt, answer, scanner);
			// Check user input to determine the chosen option and whether he wants to add/delete/update chose option
		} else if (input == 9) {
			predefinedQueriesMenu(dbconn, stmt, answer, scanner);
		}

	}

	
	/**
	 * Displays a menu of predefined queries and allows the user to select one.
	 * Executes the selected query and returns to the main menu.
	 *
	 * @param dbconn   the Connection object representing the connection to the database
	 * @param stmt     the Statement object used to execute SQL queries
	 * @param answer   the ResultSet object containing the results of executed queries
	 * @param scanner  the Scanner object used to read user input
	 */
	public static void predefinedQueriesMenu(Connection dbconn, Statement stmt, ResultSet answer, Scanner scanner) {
		//Gives a selection of predefined queries user can run. 
	    System.out.println("1. Print the current bill (total $) for a customer for their stay and all unpaid amenities.");
	    System.out.println("2. Customers that are currently staying at the hotel.");
	    System.out.println("3. Print the schedule of staff.");
	    System.out.println("4. Print the average ratings of different amenities.");
	    System.out.println("5. Print the employee names of specific responsibility IDs.");
	    int userSelection = scanner.nextInt();
	    scanner.nextLine();
	    //Takes user input from the user to decide what predefined queries they wanna run
	    if (userSelection == 1) {
	        finalBill(dbconn, stmt, answer);
	    } else if (userSelection == 2) {
	        getCurrentCustomers(dbconn, answer, scanner);
	    } else if (userSelection == 3) {
	        getStaffSchedule(dbconn, stmt, answer, scanner);
	    } else if (userSelection == 4) {
	        printAvgRatingsInRange(dbconn, stmt, answer, scanner);
	    } else if (userSelection == 5) {
	        printEmployeesByRespID(dbconn,stmt, scanner,answer);
	    } else {
	        System.out.println("Invalid Selection");
	    }

	    // Return to the main menu after executing the selected query
	    mainMenu(dbconn, stmt, answer, scanner);
	}

	
	/**

	Retrieves the schedule of all staff members on a specified week from the database and prints it to the console.
	The user is prompted to enter the start date of the week
	@param dbconn the database connection
	@param stmt the SQL statement
	@param answer the SQL result set
	@param scanner the scanner for user input
	*/

	private static void getStaffSchedule(Connection dbconn, Statement stmt, ResultSet answer, Scanner scanner) {
		
	}

	/**
	 * Adds a new record to the specified table in the database.
	 * Currently supports adding records to the Customer and UsedAmenity tables.
	 * Prompts the user for the required information to add the record.
	 *
	 * @param dbconn      the Connection object representing the connection to the database
	 * @param stmt        the Statement object used to execute SQL queries
	 * @param answer      the ResultSet object containing the results of executed queries
	 * @param recordType  the type of record to add (e.g., "Customer", "usedAmenity")
	 * @param scanner     the Scanner object used to read user input
	 */
	public static void addRecord(Connection dbconn, Statement stmt, ResultSet answer, String recordType,
			Scanner scanner) {
		//Add a new record to the customer table
		if (recordType.equals("Customer")) {
			try {
				//Ask the user for various information to add to the record. 
				System.out.println("Enter CustomerNo.: ");
				int customerNo = scanner.nextInt();
				scanner.nextLine();
				System.out.println("Name: ");
				//Ask the user for various information to add to the record. 
				String name = scanner.nextLine();
				System.out.println("Address: ");
				String address = scanner.nextLine();
				//Ask the user for various information to add to the record. 
				System.out.println("Is customer a student? (Y/N): ");
				String isAStudent = scanner.nextLine();
				System.out.println("Is customer a Club460 Member? (Y/N): ");
				String isAMember = scanner.nextLine();
				//Query to insert to table
				String insertQuery = "INSERT INTO Customer (CustomerNo, Name, Address, Student, Club460) VALUES ("
						+ customerNo + ",'" + name + "','" + address + "','" + isAStudent + "','" + isAMember + "')";
				stmt.executeUpdate(insertQuery);
				//If inserted record is member of 460, add to to Club460 table. 
				if (isAMember.equals("Y")) {
					addClub460(dbconn, stmt, customerNo);
				}
			}

			catch (Exception e) {
				;
				System.out.println("Error adding record");
			}

		} else if (recordType.equals("usedAmenity")) {
			try {
				//Ask the user for various information to add to the record. 
				System.out.println("Enter BookingID: ");
				int bookingId = scanner.nextInt();
				boolean bookingExists = false;
				//Check if the booking inputted by user exists.
				String checkBookingIdQuery = "SELECT COUNT(*) FROM Booking WHERE BookingID = " + bookingId;
				answer = stmt.executeQuery(checkBookingIdQuery);

				if (answer.next()) {
					int count = answer.getInt(1);
					if (count > 0) {
						bookingExists = true;
					}
				}
				//If booking doesn't exist, print and return to main menu. 
				if (!bookingExists) {
					System.out.println("BookingID " + bookingId + " does not exist in the Booking table.");
					return;
				}
				//Ask the user for various information to add to the record. 
				System.out.println("Enter AmenityID: ");
				int amenityId = scanner.nextInt();
				boolean amenityExists = false;
				//Check if amenity inputted exists
				String checkAmenityIdQuery = "SELECT COUNT(*) FROM Amenity WHERE AmenityID = " + amenityId;
				answer = stmt.executeQuery(checkAmenityIdQuery);

				if (answer.next()) {
					int count = answer.getInt(1);
					if (count > 0) {
						amenityExists = true;
					}
				}
				//If amenity doesn't exist, print and return to main menu. 
				if (!amenityExists) {
					System.out.println("AmenityID " + amenityId + " does not exist in the Amenity table.");
					addRecord(dbconn, stmt, answer, "Amenity", scanner);
					return;
				}
				//Ask the user for various information to add to the record. 
				System.out.println("Enter Quantity: ");
				int quantity = scanner.nextInt();
				//Insert to table
				String insertQuery = "INSERT INTO UsedAmenity (BookingID, AmenityID, Quantity) VALUES (" + bookingId
						+ "," + amenityId + "," + quantity + ")";
				stmt.executeUpdate(insertQuery);
			} catch (SQLException e) {
				System.out.println("Error adding used amenity");
			}
		} else if (recordType.equals("Booking")) {
			try {
				//Ask the user for various information to add to the record. 
				System.out.println("Enter CustomerNo.: ");
				int customerNo = scanner.nextInt();
				boolean customerExists = false;
				//Check if customer exists of the customerNo inputted
				String checkCustomerNoQuery = "SELECT COUNT(*) FROM Customer WHERE CustomerNo = " + customerNo;
				ResultSet resultSet = stmt.executeQuery(checkCustomerNoQuery);
				//If customer exists, change flag to true
				if (resultSet.next()) {
					int count = resultSet.getInt(1);
					if (count > 0) {
						customerExists = true;
					}
				}
				//If the user doesn't exist, print and return to main menu. 
				if (!customerExists) {
					System.out.println("CustomerNo " + customerNo + " does not exist in the Customer table.");
					addRecord(dbconn, stmt, answer, "Customer", scanner);
					return;
				}
				//Ask the user for various information to add to the record. 
				System.out.println("RoomNo: ");
				int roomNo = scanner.nextInt();
				boolean roomExists = false;
				//Check if room inputted exists. 
				String checkRoomNoQuery = "SELECT COUNT(*) FROM Room WHERE RoomNo = " + roomNo;
				resultSet = stmt.executeQuery(checkRoomNoQuery);

				if (resultSet.next()) {
					int count = resultSet.getInt(1);
					if (count > 0) {
						roomExists = true;
					}
				}
				//If room doesn't exist, print and return to main menu. 

				if (!roomExists) {
					System.out.println("RoomNo " + roomNo + " does not exist in the Room table.");
					addRecord(dbconn, stmt, answer, "RoomDetails", scanner);
					return;
				}
				//Ask the user for various information to add to the record. 
				System.out.println("BookingId: ");
				int bookingId = scanner.nextInt();
				scanner.nextLine();
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
				//Ask the user for various information to add to the record. 
				System.out.println("Start Date (yyyy-MM-dd): ");
				String dateFrom = scanner.nextLine();
				// Parse and format the dateFrom input
				dateFrom = dateFormat1.format(dateFormat1.parse(dateFrom));
				//Ask the user for various information to add to the record. 
				System.out.println("End Date (yyyy-MM-dd): ");
				String dateTo = scanner.nextLine();
				// Parse and format the dateTo input
				dateTo = dateFormat1.format(dateFormat1.parse(dateTo));
				//Checks if there is no overlap between booking of a particular room
				String availabilityQuery = "SELECT COUNT(*) FROM Booking WHERE RoomNo = " + roomNo
						+ " AND BookingID != " + bookingId + " AND (dateFrom BETWEEN TO_DATE('" + dateFrom
						+ "','YYYY-MM-DD') AND TO_DATE('" + dateTo + "','YYYY-MM-DD') OR dateTo BETWEEN TO_DATE('"
						+ dateFrom + "','YYYY-MM-DD') AND TO_DATE('" + dateTo + "','YYYY-MM-DD') OR (TO_DATE('"
						+ dateFrom + "','YYYY-MM-DD') BETWEEN dateFrom AND dateTo) OR (TO_DATE('" + dateTo
						+ "','YYYY-MM-DD') BETWEEN dateFrom AND dateTo))";

				resultSet = stmt.executeQuery(availabilityQuery);
				int bookedCount = 0;
				if (resultSet.next()) {
					bookedCount = resultSet.getInt(1);
				}
				//If there is a booking for the dates inputted or inbetween them, then print that cannot book and reutnr to main menu. 
				if (bookedCount > 0) {
					System.out.println(
							"RoomNo " + roomNo + " is already booked between " + dateFrom + " and " + dateTo + ".");
					return;
				}
				//Insert to table
				String insertQuery = "INSERT INTO Booking (CustomerNo, RoomNo, BookingID, dateFrom, dateTo) VALUES ("
						+ customerNo + "," + roomNo + "," + bookingId + ", TO_DATE('" + dateFrom
						+ "','YYYY-MM-DD'), TO_DATE('" + dateTo + "','YYYY-MM-DD'))";
				//Execute Query
				stmt.executeUpdate(insertQuery);
			} catch (Exception e) {
				;
				System.out.println("Error adding record");
			}

		}

		else if (recordType.equals("Amenity")) {
			try {
				//Ask the user for various information to add to the record. 
				System.out.println("Enter AmenityID: ");
				int amenityId = scanner.nextInt();
				System.out.println("Enter price of amenity: ");
				//Ask the user for various information to add to the record. 
				int price = scanner.nextInt();
				//Insert to the Amenity table
				String insertQuery = "INSERT INTO Amenity VALUES (" + amenityId + "," + price + ")";
				//Execute Query
				stmt.executeUpdate(insertQuery);
			} catch (Exception e) {
				;
				System.out.println("Error adding record");
			}

		}

		else if (recordType.equals("RoomDetails")) {
			try {
				//Ask the user for various information to add to the record. 
				System.out.println("Enter RoomID: ");
				int roomId = scanner.nextInt();
				System.out.println("Enter price of Room:");
				//Ask the user for various information to add to the record. 
				int price = scanner.nextInt();
				//Insert to room table
				String insertQuery = "INSERT INTO Room VALUES (" + roomId + "," + price + ")";
				//Execute Query
				stmt.executeUpdate(insertQuery);
			} catch (Exception e) {
				;
				System.out.println("Error adding record");
			}
		} else if (recordType.equals("Employee")) {
			try {
				//Ask the user for various information to add to the record.
				System.out.println("Enter Employee ID: ");
				int employeeID = scanner.nextInt();
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
				scanner.nextLine();
				//Ask the user for various information to add to the record.
				System.out.println("Enter Employee Name: ");
				String employeeName = scanner.nextLine();
				//Ask the user for various information to add to the record.
				System.out.println("Enter Employee Date of Birth (yyyy-MM-dd): ");
				String dob = scanner.nextLine();
				// Parse and format the dob input
				dob = dateFormat1.format(dateFormat1.parse(dob));
				//Insert to table
				String insertQuery = "INSERT INTO Employee (EmployeeID, EmpName, DOB) VALUES (" + employeeID + ",'"
						+ employeeName + "',TO_DATE('" + dob + "','YYYY-MM-DD'))";
				//Execute Query
				stmt.executeUpdate(insertQuery);
			} catch (Exception e) {
				;
				System.out.println("Error adding record");
			}
		} else if (recordType.equals("Responsibility")) {
			try {
				//Ask the user for various information to add to the record.
				System.out.println("Enter Employee ID: ");
				int employeeID = scanner.nextInt();
				System.out.println("Enter Responsibility ID: ");
				//Ask the user for various information to add to the record.
				int responsibilityID = scanner.nextInt();
				System.out.println("Enter Day (0 for Sunday, 1 for Monday, etc.): ");
				int day = scanner.nextInt();
				//Ask the user for various information to add to the record.
				scanner.nextLine();
				System.out.println("Enter Start Time (HH:MM): ");
				//Ask the user for various information to add to the record.
				String startTime = scanner.nextLine();
				System.out.println("Enter Stop Time (HH:MM): ");
				String stopTime = scanner.nextLine();
				//Add to responsibility table
				String insertQuery = "INSERT INTO Responsibility (EmployeeID, RespID, Day, startTime, stopTime) VALUES ("
						+ employeeID + "," + responsibilityID + "," + day + ",'" + startTime + "','" + stopTime + "')";
				//Execute Query
				stmt.executeUpdate(insertQuery);
			} catch (Exception e) {
				;
				System.out.println("Error adding record");
			}
		} else if (recordType.equals("Club460")) {
			try {
				//Ask the user for various information to add to the record.
				System.out.println("Enter CustomerNo.:");
				int customerNo = scanner.nextInt();
				System.out.println("\nEnter Points:");
				//Ask the user for various information to add to the record.
				int points = scanner.nextInt();
				//Insert into Club460 table
				String insertQuery = "INSERT INTO Club460 (CustomerNo, Points) VALUES (" + customerNo + "," + points
						+ ")";
				//Execute Query
				stmt.executeUpdate(insertQuery);
				//Update the membership in the club460 table as well
				updateClub460InCustomerTable(dbconn, stmt, customerNo, "Y");
			} catch (Exception e) {
				;
				System.out.println("Error adding record");
			}
		}

	}

	/**
	 * Adds a new Club 460 member to the Club460 table with the given customer number
	 * and initializes the points to 0.
	 *
	 * @param dbconn     the Connection object used to create a Statement
	 * @param stmt       the Statement object used to execute the query
	 * @param customerNo the customer number to be added to the Club460 table
	 */
	public static void addClub460(Connection dbconn, Statement stmt, int customerNo) {
	    try {
	        int points = 0; // Initialize Club 460 member points to 0

	        // Insert the new Club 460 member record into the Club460 table
	        String insertQuery = "INSERT INTO Club460 (CustomerNo, Points) VALUES (" + customerNo + "," + points + ")";
	        stmt.executeUpdate(insertQuery);
	    } catch (Exception e) {
	        System.out.println("Error adding record to Club 460");
	    }
	}

	/**
	 * Deletes a Club 460 member from the Club460 table using the given customer number.
	 *
	 * @param dbconn     the Connection object used to create a Statement
	 * @param stmt       the Statement object used to execute the query
	 * @param customerNo the customer number to be deleted from the Club460 table
	 */
	public static void deleteClub460(Connection dbconn, Statement stmt, int customerNo) {
	    try {
	        // Delete the Club 460 member record from the Club460 table
	        String deleteQuery = "DELETE FROM Club460 WHERE CustomerNo = " + customerNo;
	        int rowsDeleted = stmt.executeUpdate(deleteQuery);

	        // Display the number of rows deleted
	        System.out.println(rowsDeleted + " row(s) deleted successfully.");
	    } catch (Exception e) {
	        System.out.println("Error deleting record from Club 460");
	    }
	}

	/**

	Deletes records from the database based on the given record type.
	Supports deleting Customer, Booking,UsedAmenity, Room, Club460, Employees, Responsibilities record.
	@param dbconn The database connection object.
	@param stmt The SQL statement object.
	@param answer The result set object.
	@param recordType The type of record to delete. Must be one of "Customer", "Booking", or "usedAmenity".
	@param scanner The Scanner object to read user input.
	*/

	public static void deleteRecord(Connection dbconn, Statement stmt, ResultSet answer, String recordType,
			Scanner scanner) {
		if (recordType.equals("Customer")) {
			//Delete record from customer table
			try {
				//Ask the user for various information to delete the record.
				System.out.println("Enter CustomerNo.: ");
				int customerNo = scanner.nextInt();
				deleteClub460(dbconn, stmt, customerNo);
				//Delete from child tables (where customerNo is foreign key)
				String deleteQueryBooking = "DELETE FROM Booking WHERE CustomerNo = " + customerNo;
				stmt.executeUpdate(deleteQueryBooking);
				//Delete from child tables (where customerNo is foreign key)
				String deleteQueryPayment = "DELETE FROM Payment WHERE CustomerNo = " + customerNo;
				stmt.executeUpdate(deleteQueryPayment);
				//Delete from child tables (where customerNo is foreign key)
				String deleteQueryClub = "DELETE FROM Club460 WHERE CustomerNo = " + customerNo;
				stmt.executeUpdate(deleteQueryClub);
				//Delete from table
				String deleteQuery = "DELETE FROM Customer WHERE CustomerNo = " + customerNo;
				int rowsDeleted = stmt.executeUpdate(deleteQuery);

				System.out.println("Deleted successfully.");
			} catch (Exception e) {
				System.out.println("Error deleting record");
			}
			//Delete record from booking table
		} else if (recordType.equals("Booking")) {
			try {
				//Ask the user for various information to delete the record.
				System.out.println("Ent"
						+ "er BookingID: ");
				int bookingId = scanner.nextInt();
				scanner.nextLine();
				//Delete from child table 
				String deleteQuery = "DELETE FROM Booking WHERE BookingID = " + bookingId;
				stmt.executeUpdate(deleteQuery);
				System.out.println("Deleted successfully.");
			} catch (Exception e) {
				;
				System.out.println("Error deleting record");
			}
			//Delete from usedAmenities table
		} else if (recordType.equals("usedAmenity")) {
			try {
				//Ask the user for various information to delete the record.
				System.out.println("Enter BookingID: ");
				int bookingId = scanner.nextInt();
				//Ask the user for various information to delete the record.
				System.out.println("Enter AmenityID: ");
				int amenityId = scanner.nextInt();
				scanner.nextLine();
				//Delete Record
				String deleteQuery = "DELETE FROM UsedAmenity WHERE BookingID = " + bookingId + " AND AmenityID = "
						+ amenityId;
				stmt.executeUpdate(deleteQuery);
			} catch (Exception e) {
				
				System.out.println("Error deleting record");
			}
			//Delete from Amenity Table
		} else if (recordType.equals("Amenity")) {
			try {
				//Ask the user for various information to delete the record.
				System.out.println("Enter AmenityID: ");
				int amenityId = scanner.nextInt();
				//Delete from child table where AmenityID is foreign key
				scanner.nextLine();
				String deleteQueryUsedAmenity = "DELETE FROM UsedAmenity WHERE AmenityID = " + amenityId;
				stmt.executeUpdate(deleteQueryUsedAmenity);
				//Delete from child table where AmenityID is foreign key
				String deleteQueryRating = "DELETE FROM Rating WHERE AmenityID = " + amenityId;
				stmt.executeUpdate(deleteQueryRating);
				//Delete from table
				String deleteQuery = "DELETE FROM Amenity WHERE AmenityID = " + amenityId;
				stmt.executeUpdate(deleteQuery);
				System.out.println("Deleted successfully.");
			} catch (Exception e) {
				
				System.out.println("Error deleting record");
			}
			//Delete from Room Details table
		} else if (recordType.equals("RoomDetails")) {
			try {
				//Ask the user for various information to delete the record.
				System.out.println("Enter RoomID: ");
				int roomId = scanner.nextInt();
				scanner.nextLine();
				//Delete from child table where RoomNo is foreign key
				String deleteQueryBooking = "DELETE FROM Booking WHERE RoomNo = " + roomId;
				stmt.executeUpdate(deleteQueryBooking);
				//Delete from table
				String deleteQuery = "DELETE FROM Room WHERE RoomNo = " + roomId;
				stmt.executeUpdate(deleteQuery);
				System.out.println("Deleted successfully.");
			} catch (Exception e) {
				;
				System.out.println("Error deleting record");
			}
			//Delete from Employee table
		} else if (recordType.equals("Employee")) {
			try {
				System.out.println("Enter Employee ID: ");
				//Ask the user for various information to delete the record.
				int employeeID = scanner.nextInt();
				scanner.nextLine();
				//Delete record
				String deleteQuery = "DELETE FROM Employee WHERE EmployeeID = " + employeeID;
				stmt.executeUpdate(deleteQuery);
				System.out.println("Employee deleted successfully.");
			} catch (Exception e) {
				;
				System.out.println("Error deleting record");
			}
			// Delete from responsibility table
		} else if (recordType.equals("Responsibility")) {
			try {
				System.out.println("Enter Employee ID: ");
				//Ask the user for various information to delete the record.
				int employeeID = scanner.nextInt();
				System.out.println("Enter Responsibility ID: ");
				//Ask the user for various information to delete the record.
				int responsibilityID = scanner.nextInt();
				scanner.nextLine();
				//Delete from table
				String deleteQuery = "DELETE FROM Responsibility WHERE EmployeeID = " + employeeID + " AND RespID = "
						+ responsibilityID;
				stmt.executeUpdate(deleteQuery);
				System.out.println("Responsibility deleted successfully.");
			} catch (Exception e) {
				;
				System.out.println("Error deleting record");
			}
			//Deletes from Club460 table
		} else if (recordType.equals("Club460")) {
			try {
				//Ask the user for various information to delete the record.
				System.out.println("Enter the CustomerNo of the Club460 record you want to delete: ");
				int customerNo = scanner.nextInt();
				scanner.nextLine();
				//Delete from table
				String deleteQuery = "DELETE FROM Club460 WHERE CustomerNo = " + customerNo;
				stmt.executeUpdate(deleteQuery);
				updateClub460InCustomerTable(dbconn, stmt, customerNo, "N");
			} catch (SQLException e) {
				System.out.println("Error deleting "+recordType);
			}
		}
	}

	/**
	 * Adds a payment record to the Payment table and returns any associated discount
	 * card name. The method takes in a Connection object, a Statement object, and
	 * a customer number.
	 *
	 * @param dbconn     the Connection object used to create a Statement
	 * @param stmt       the Statement object used to execute the query
	 * @param customerNo the customer number to be added to the Payment table
	 * @return the name of the discount card, if applicable, or an empty string otherwise
	 * @throws SQLException if there is an error executing the query
	 */
	public static String addPayment(Connection dbconn, Statement stmt, int customerNo) throws SQLException {
	    Scanner scanner = new Scanner(System.in);

	    // Prompt the user to enter the mode of payment
	    System.out.println("Enter mode of payment (CC/Checking/Cash):");
	    String mode = scanner.nextLine();

	    // Check if the payment mode is either Checking or CC
	    if (mode.equals("Checking") || mode.equals("CC")) {
	        System.out.println("\nEnter CC or Checking account number:");
	        int cardNo = scanner.nextInt();

	        // Calculate discount based on the card number
	        int discount = 0;
	        if (cardNumber.containsKey(cardNo % 10000)) {
	            discount = cardDiscount.get(cardNumber.get(cardNo % 10000));
	        }

	        // Insert the payment record into the Payment table
	        String insertQuery = "INSERT INTO Payment VALUES (" + customerNo + ",'" + mode + "'," + cardNo + ","
	                + discount + ")";
	        stmt.executeQuery(insertQuery);

	        // Return the card name if the mode is CC and the card number is found in the map
	        if (mode == "CC") {
	            if (cardNumber.containsKey(cardNo % 10000)) {
	                return cardNumber.get(cardNo % 10000);
	            } else {
	                return "";
	            }
	        }
	    }

	    return "";
	}

	/**
	 * Adds a used amenity record to the UsedAmenity table by taking in a
	 * Connection object, a Statement object, and a ResultSet object.
	 *
	 * @param dbconn the Connection object used to create a Statement
	 * @param stmt   the Statement object used to execute the query
	 * @param answer the ResultSet object used to store the query result
	 */
	public static void addUsedAmenity(Connection dbconn, Statement stmt, ResultSet answer) {
	    try {
	        Scanner scanner = new Scanner(System.in);

	        // Prompt the user to enter the booking ID
	        System.out.println("Enter Booking ID: ");
	        int bookingID = scanner.nextInt();

	        // Prompt the user to enter the amenity ID
	        System.out.println("\nEnter Amenity ID: ");
	        int amenityID = scanner.nextInt();

	        // Prompt the user to enter the quantity of the used amenity
	        System.out.println("\nEnter Quantity: ");
	        int quantity = scanner.nextInt();

	        // Insert the used amenity record into the UsedAmenity table
	        String insertQuery = "INSERT INTO UsedAmenity (BookingID, AmenityID, Quantity) VALUES (" + bookingID + ","
	                + amenityID + "," + quantity + ")";
	        stmt.executeUpdate(insertQuery);

	    } catch (Exception e) {
	        System.out.println("Error adding used amenities");
	    }
	}


	/**

	Updates records in the database based on the given record type.
	Supports updating Customer, booking, Room, Amenity, Employee, Responsibility, Club460, UsedAmenity.
	
	@param dbconn The database connection object.
	@param stmt The SQL statement object.
	@param answer The result set object.
	@param recordType The type of record to update. Must be one of "Customer" or "Booking".
	@param scanner The Scanner object to read user input.
	*/
	public static void updateRecord(Connection dbconn, Statement stmt, ResultSet answer, String recordType,
			Scanner scanner) {
		try {
			//Updates Customer record
			if (recordType.equals("Customer")) {
				//Ask the user for various information to delete the record.
				System.out.println("Enter the customerNo of the record you want to update: ");
				int id = scanner.nextInt();
				scanner.nextLine();
				//Ask the user for various information to delete the record.
				System.out.println("\nEnter updated name: ");
				String name = scanner.nextLine();
				System.out.println("\nEnter updated address: ");
				//Ask the user for various information to delete the record.
				String address = scanner.nextLine();
				System.out.println("\nIs the customer a student? (Y/N): ");
				//Ask the user for various information to delete the record.
				String isAStudent = scanner.nextLine();
				System.out
						.println("If you want to update Club460 membership, Add or Delete in Club460 table to update");
				String updateQuery = "UPDATE Customer SET Name='" + name + "', Address='" + address + "', Student='"
						+ isAStudent + "' WHERE CustomerNo=" + id;
				stmt.executeUpdate(updateQuery);
				//Updates booking record
			} else if (recordType.equals("Booking")) {
				System.out.println("Enter the BookingID of the record you want to update: ");
				//Ask the user for various information to delete the record.
				int id = scanner.nextInt();
				scanner.nextLine();
				System.out.println("Enter updated start date (yyyy-MM-dd): ");
				//Ask the user for various information to delete the record.
				String dateFrom = scanner.nextLine();

				String getRoomNoQuery = "SELECT RoomNo FROM Booking WHERE BookingID = " + id;
				ResultSet resultSet = stmt.executeQuery(getRoomNoQuery);
				int roomNo = 0;
				if (resultSet.next()) {
					roomNo = resultSet.getInt("RoomNo");
				} else {
					System.out.println("No room associated with BookingID " + id);
					return;
				}

				// Parse and format the dateFrom input
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

				dateFrom = dateFormat.format(dateFormat.parse(dateFrom));
				//Ask the user for various information to delete the record.
				System.out.println("Enter updated end date (yyyy-MM-dd): ");
				String dateTo = scanner.nextLine();

				// Parse and format the dateTo input

				dateTo = dateFormat.format(dateFormat.parse(dateTo));
				String availabilityQuery = "SELECT COUNT(*) FROM Booking WHERE RoomNo = " + roomNo
						+ " AND BookingID != " + id + " AND (dateFrom BETWEEN TO_DATE('" + dateFrom
						+ "','YYYY-MM-DD') AND TO_DATE('" + dateTo + "','YYYY-MM-DD') OR dateTo BETWEEN TO_DATE('"
						+ dateFrom + "','YYYY-MM-DD') AND TO_DATE('" + dateTo + "','YYYY-MM-DD') OR (TO_DATE('"
						+ dateFrom + "','YYYY-MM-DD') BETWEEN dateFrom AND dateTo) OR (TO_DATE('" + dateTo
						+ "','YYYY-MM-DD') BETWEEN dateFrom AND dateTo))";

				answer = stmt.executeQuery(availabilityQuery);
				int bookedCount = 0;
				if (answer.next()) {
					bookedCount = answer.getInt(1);
				}

				if (bookedCount > 0) {
					System.out.println(
							"RoomNo " + roomNo + " is already booked between " + dateFrom + " and " + dateTo + ".");
					return;
				}

				String updateQuery = "UPDATE Booking SET dateFrom=TO_DATE('" + dateFrom
						+ "', 'YYYY-MM-DD'), dateTo=TO_DATE('" + dateTo + "', 'YYYY-MM-DD') WHERE BookingID=" + id;
				stmt.executeUpdate(updateQuery);
				//Updates amenity records
			} else if (recordType.equals("Amenity")) {
				//Ask the user for various information to delete the record.
				System.out.println("Enter the Amenity ID of the record you want to update: ");
				int id = scanner.nextInt();
				//Ask the user for various information to delete the record.
				System.out.println("Enter updated price: ");
				double price = scanner.nextDouble();
				scanner.nextLine();
				//Ask the user for various information to delete the record.
				String updateQuery = "UPDATE Amenity SET Price=" + price + " WHERE AmenityID=" + id;
				stmt.executeUpdate(updateQuery);
				//Update room details
			} else if (recordType.equals("RoomDetails")) {
				System.out.println("Enter the RoomNo of the record you want to update: ");
				int id = scanner.nextInt();
				//Ask the user for various information to delete the record.
				System.out.println("Enter updated price: ");
				double price = scanner.nextDouble();
				scanner.nextLine();
				String updateQuery = "UPDATE Room SET Price=" + price + " WHERE RoomNo=" + id;
				stmt.executeUpdate(updateQuery);
			}
			//Update employee details
			else if (recordType.equals("Employee")) {
				System.out.println("Enter the Employee ID of the record you want to update: ");
				int id = scanner.nextInt();
				scanner.nextLine();
				System.out.println("Enter updated employee name: ");
				//Ask the user for various information to delete the record.
				String employeeName = scanner.nextLine();
				System.out.println("Enter updated date of birth (yyyy-MM-dd): ");
				String dob = scanner.nextLine();

				// Parse and format the dob input
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date parsedDob = dateFormat.parse(dob);
				String formattedDob = dateFormat.format(parsedDob);

				String updateQuery = "UPDATE Employee SET EmpName='" + employeeName + "', DOB=TO_DATE('" + formattedDob
						+ "', 'YYYY-MM-DD') WHERE EmployeeID=" + id;
				stmt.executeUpdate(updateQuery);
				//Update responsibility details
			} else if (recordType.equals("Responsibility")) {
				System.out.println("Enter the Employee ID of the record you want to update: ");
				//Ask the user for various information to delete the record.
				int id = scanner.nextInt();
				System.out.println("Enter the Responsibility ID of the record you want to update: ");
				//Ask the user for various information to delete the record.
				int respId = scanner.nextInt();
				System.out.println("Enter updated day (0 for Sunday, 1 for Monday, etc.): ");
				//Ask the user for various information to delete the record.
				int day = scanner.nextInt();
				scanner.nextLine();
				System.out.println("\nEnter updated start time (HH:MM): ");
				//Ask the user for various information to delete the record.
				String startTime = scanner.nextLine();
				System.out.println("Enter updated stop time (HH:MM): ");
				//Ask the user for various information to delete the record.
				String stopTime = scanner.nextLine();
				String updateQuery = "UPDATE Responsibility SET Day=" + day + ", startTime='" + startTime
						+ "', stopTime='" + stopTime + "' WHERE EmployeeID=" + id + " and respID=" + respId;
				stmt.executeUpdate(updateQuery);
				//Update Club460 member details
			} else if (recordType.equals("Club460")) {
				System.out.println("Enter the CustomerNo of the record you want to update: ");
				//Ask the user for various information to delete the record.
				int id = scanner.nextInt();
				System.out.println("Enter updated points: ");
				//Ask the user for various information to delete the record.
				int points = scanner.nextInt();
				scanner.nextLine();
				String updateQuery = "UPDATE Club460 SET Points=" + points + " WHERE CustomerNo=" + id;
				//Ask the user for various information to delete the record.
				stmt.executeUpdate(updateQuery);
				//updates usedAmenity details
			} else if (recordType.equals("usedAmenity")) {
				System.out.println("Enter the BookingId of the record you want to update: ");
				//Ask the user for various information to delete the record.
				int id = scanner.nextInt();
				System.out.println("Enter updated quantity: ");
				int quantity = scanner.nextInt();
				scanner.nextLine();
				String updateQuery = "UPDATE UsedAmenity SET Quantity=" + quantity + " WHERE BookingID=" + id;
				stmt.executeUpdate(updateQuery);
			} else {
				System.out.println("Invalid record type.");
				return;
			}
			System.out.println("Record updated successfully.");
		} catch (Exception e) {
			System.out.println("Error updating "+recordType);
		}
	}

	/**
	 * Updates the Club 460 membership status of a customer in the Customer table.
	 *
	 * @param dbconn        the Connection object used to create a Statement
	 * @param stmt          the Statement object used to execute the query
	 * @param customerNo    the customer number whose membership status is to be updated
	 * @param setMembership the new membership status (Y/N)
	 */
	public static void updateClub460InCustomerTable(Connection dbconn, Statement stmt, int customerNo,
	        String setMembership) {
	    try {
	        // Update the Club 460 membership status in the Customer table
	        String updateQuery = "UPDATE Customer SET Student='" + setMembership + "' WHERE CustomerNo=" + customerNo;
	        stmt.executeUpdate(updateQuery);
	    } catch (SQLException e) {
	        System.out.println("Error adding member to Club460");
	    }
	}

	/**
	 * Updates the payment information for a customer.
	 *
	 * @param dbconn  the Connection object used to create a Statement
	 * @param stmt    the Statement object used to execute the query
	 * @param answer  the ResultSet object to store the result of the executed query
	 * @param scanner the Scanner object used to read user input
	 * @return a String containing the card type or an empty string if no card type is found
	 */
	public static String updatePayment(Connection dbconn, Statement stmt, ResultSet answer, Scanner scanner) {
	    // Prompt the user for the CustomerNo to update
	    System.out.println("Enter the CustomerNo of the record you want to update: ");
	    int id = scanner.nextInt();
	    scanner.nextLine();

	    // Prompt the user for the updated mode of payment and card number
	    System.out.println("Enter updated mode of payment (CC, Cash, DD, or Cheque): ");
	    String mode = scanner.nextLine();
	    System.out.println("Enter updated card number: ");
	    int cardNo = scanner.nextInt();
	    scanner.nextLine();

	    int discount = 0;
	    System.out.println("Card last 4 digits: " + cardNo % 10000);
	    if (cardNumber.containsKey(cardNo % 10000)) {
	        discount = cardDiscount.get(cardNumber.get(cardNo % 10000));
	    }

	    // Update the payment information in the Payment table
	    String updateQuery = "UPDATE Payment SET ModeOfPayment='" + mode + "', CardNo=" + cardNo + ", Discount="
	            + discount + " WHERE CustomerNo=" + id;
	    try {
	        stmt.executeUpdate(updateQuery);
	    } catch (SQLException e) {
	        System.out.println("Error updating payment");
	    }

	    // Return the card type or an empty string
	    if (mode.equals("CC")) {
	        if (cardNumber.containsKey(cardNo % 10000)) {
	            return cardNumber.get(cardNo % 10000);
	        } else {
	            return "";
	        }
	    } else {
	        return "";
	    }
	}


	/**
	 * Prints the average ratings for amenities in a given date range.
	 *
	 * @param dbconn  the Connection object used to create a Statement
	 * @param stmt    the Statement object used to execute the query
	 * @param answer  the ResultSet object to store the result of the executed query
	 * @param scanner the Scanner object used to read user input
	 */
	public static void printAvgRatingsInRange(Connection dbconn, Statement stmt, ResultSet answer, Scanner scanner) {
	    try {
	        // Initialize the date format
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	        // Prompt the user for the start date and end date
	        System.out.println("Enter start date (yyyy-MM-dd): ");
	        String startDateString = scanner.nextLine();
	        Date startDate = dateFormat.parse(startDateString);

	        System.out.println("Enter end date (yyyy-MM-dd): ");
	        String endDateString = scanner.nextLine();
	        Date endDate = dateFormat.parse(endDateString);

	        // Format the start and end dates
	        String formattedStartDate = dateFormat.format(startDate);
	        String formattedEndDate = dateFormat.format(endDate);

	        // Construct the query to calculate average ratings for amenities in the given date range
	        String query = "SELECT AmenityID, AVG(Rating) AS AvgRating FROM Rating WHERE RatingDate BETWEEN TO_DATE('"
	                + formattedStartDate + "', 'yyyy-MM-dd') AND TO_DATE('" + formattedEndDate
	                + "', 'yyyy-MM-dd') GROUP BY AmenityID ORDER BY AvgRating DESC";
	        
	        // Execute the query
	        answer = stmt.executeQuery(query);

	        // Print the results
	        System.out.println("Amenity ID\tAverage Rating");
	        while (answer.next()) {
	            int amenityID = answer.getInt("AmenityID");
	            double avgRating = answer.getDouble("AvgRating");
	            System.out.println(amenityID + "\t\t" + avgRating);
	        }
	    } catch (Exception e) {
	        System.out.println("Error printing average ratings");
	    }
	}


	/**
	 * Retrieves and prints current customers based on the specified date range and
	 * categorized by general customers, college students, Club 460 members, and college
	 * students who are also Club 460 members. The method takes in Connection and ResultSet
	 * objects to execute the query and store the results, and a Scanner to read user inputs.
	 *
	 * @param dbconn  the Connection object used to create a PreparedStatement
	 * @param answer  the ResultSet object used to store the query results
	 * @param scanner the Scanner object used to read user inputs for date range
	 */
	private static void getCurrentCustomers(Connection dbconn, ResultSet answer, Scanner scanner) {
	    Date startDate, endDate;
	    //Takes various inputs from the user
	    String formattedStartDate = null, formattedEndDate = null;
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	    System.out.println("Please enter start date (yyyy-MM-dd): ");
	    String startDateString = scanner.nextLine();

	    System.out.println("Please enter end date (yyyy-MM-dd): ");
	    String endDateString = scanner.nextLine();
	    //Parse the dates to the format constricted in the table
	    try {
	        startDate = dateFormat.parse(startDateString);
	        endDate = dateFormat.parse(endDateString);
	        formattedStartDate = dateFormat.format(startDate);
	        formattedEndDate = dateFormat.format(endDate);

	    } catch (ParseException e) {
	        System.out.println("INPUT EXCEPTION: There was some error parsing the date.\n");
	        scanner.close();
	        System.exit(-1);
	    }

	    // Construct the query for retrieving current customers
	    String query = "SELECT Name, RoomNo from Booking JOIN Customer ON Booking.CustomerNo = Customer.CustomerNo"
	            + "WHERE dateFrom >= TO_DATE('" + formattedStartDate + "', 'yyyy-MM-dd') AND " + "dateTo <= TO_DATE('"
	            + formattedEndDate + "', 'yyyy-MM-dd') AND Student = ? AND" + "Club460 = ? ORDER BY RoomNo";
	    try {
	        PreparedStatement pstmt = dbconn.prepareStatement(query);
	        System.out.println("Here's the result of your queries:\n ");
	        System.out.println("\tGENERAL CUSTOMERS: \n");
	        getCustomers(pstmt, answer, "N", "N");
	        System.out.println("\tCOLLEGE STUDENTS ONLY: \n");
	        getCustomers(pstmt, answer, "Y", "N");
	        System.out.println("\tCLUB 460 MEMBERS ONLY: \n");
	        getCustomers(pstmt, answer, "N", "Y");
	        System.out.println("\tCOLLEGE STUDENTS WHO ARE ALSO IN CLUB 460: \n");
	        getCustomers(pstmt, answer, "Y", "Y");
	    } catch (SQLException e) {
	        System.err.println("*** SQLException:  " + "Could not fetch results for query 2");
	        System.err.println("\tMessage:   " + e.getMessage());
	        System.err.println("\tSQLState:  " + e.getSQLState());
	        System.err.println("\tErrorCode: " + e.getErrorCode());
	        System.exit(-1);
	    }
	}


	/**
	 * Retrieves and prints customers based on the specified student and Club460 membership status.
	 * The method takes in PreparedStatement and ResultSet objects to execute the query and store the results.
	 *
	 * @param pstmt   the PreparedStatement object used to execute the query
	 * @param answer  the ResultSet object used to store the query results
	 * @param student a String representing the student status (Y/N) to filter customers by
	 * @param club460 a String representing the Club460 membership status (Y/N) to filter customers by
	 * @throws SQLException if there is an issue executing the query or retrieving the results
	 */
	private static void getCustomers(PreparedStatement pstmt, ResultSet answer, String student, String club460)
	        throws SQLException {
	    // Set the PreparedStatement parameters for student and Club460 status
	    pstmt.setString(1, student);
	    pstmt.setString(2, club460);
	    answer = pstmt.executeQuery();

	    // Check if there are any customers matching the provided criteria
	    if (answer == null) {
	        System.out.println("<!-- No Such Customers Exist --!>");
	    } else {
	        // Print the customer's name and room number
	        System.out.println("Customer Name: " + answer.getString(1) + "\t\t Room Number: " + answer.getString(2));
	        System.out.println();
	    }
	}

	
	/**
	 * Calculates and prints the final bill for a customer along with any applicable discounts.
	 *
	 * @param dbconn  the Connection object used to create a Statement
	 * @param stmt    the Statement object used to execute the query
	 * @param answer  the ResultSet object to store the result of the executed query
	 */
	public static void finalBill(Connection dbconn, Statement stmt, ResultSet answer) {
		try {
	        // Initialize variables
	        Boolean isMember = false;

	        // Accept user input
	        Scanner scanner = new Scanner(System.in);
	        System.out.println("Enter CustomerNo: ");
	        int customerNo = scanner.nextInt();
	        scanner.nextLine();
	        System.out.println("Enter BookingID: ");
	        int bookingId = scanner.nextInt();
	        scanner.nextLine(); // Consume the newline character

	        // Retrieve the booking with the given CustomerNo and BookingID
	        String query = "SELECT dateFrom, dateTo FROM Booking WHERE CustomerNo = " + customerNo + " AND BookingID = "
	                + bookingId;
	        ResultSet resultSet = stmt.executeQuery(query);

	        Date dateFrom = null;
	        Date dateTo = null;
	        if (resultSet.next()) {
	            dateFrom = resultSet.getDate("dateFrom");
	            dateTo = resultSet.getDate("dateTo");
	        } else {
	            System.out.println("No booking found with the given CustomerNo and BookingID.");
	            return;
	        }

	        // Retrieve the room number for the given booking
	        stmt = dbconn.createStatement();
	        int roomNo;
	        String sql = "SELECT RoomNo FROM Booking WHERE CustomerNo = ? AND BookingID = ?";
	        PreparedStatement pstmt = dbconn.prepareStatement(sql);
	        pstmt.setInt(1, customerNo);
	        pstmt.setInt(2, bookingId);
	        answer = pstmt.executeQuery();
	        if (answer.next()) {
	            roomNo = answer.getInt("RoomNo");
	        } else {
	            System.out.println("No booking found with the given CustomerNo and BookingID.");
	            return;
	        }

	        // Check if the customer is a Club460 member
	        query = "SELECT Club460 FROM Customer WHERE CustomerNo = " + customerNo;
	        answer = stmt.executeQuery(query);
	        if (answer.next()) {
	            String club460 = answer.getString("Club460");
	            if ("Y".equalsIgnoreCase(club460)) {
	                isMember = true;
	            }
	        }

	        // Check for existing payment and offer to update it
	        query = "SELECT * FROM Payment WHERE CustomerNo = " + customerNo;
	        answer = stmt.executeQuery(query);
	        int discount = 0;
	        String discountString = "";
	        String payment = "";
	        if (answer.next()) {
	            System.out.println("Do you wanna update method of payment (Y/N)?: ");
	            String updatePayment = scanner.nextLine();
	            if (updatePayment.equals("Y")) {
	                updatePayment(dbconn, stmt, answer, scanner);
	            }

	        } else {
	            addPayment(dbconn, stmt, customerNo);
	        }
	        int discountPayment = 0;

	        // Retrieve the discount from the payment record
	        query = "SELECT Discount FROM Payment WHERE CustomerNo = " + customerNo;
	        answer = stmt.executeQuery(query);

	        if (answer.next()) {
	            discountPayment = answer.getInt("Discount");
	        }
	        if (discountPayment != 0) {
	            discount += discountPayment;
	            discountString += "Card Discount";
	        }

	        String customerName = "";
	        query = "SELECT * FROM Customer WHERE CustomerNo = " + customerNo;
	        answer = stmt.executeQuery(query);
	        if (answer.next()) {
	            customerName = answer.getString("Name");
	            if (answer.getString("Student").equals("Y")) {
	            	
	            	// Apply student discount
	                discount += 10;
	                discountString += ", Student Discount";
	            }
	        }

	        // Calculate the cost of rooms
	        Integer costRooms = 0;
	        Integer costAmenities = 0;
	        query = "SELECT SUM(Room.Price * (Booking.dateTo - Booking.dateFrom)) AS TotalRoomCost\n"
	                + "FROM Booking, Room\n" + "WHERE Booking.CustomerNo = " + customerNo + "\n"
	                + "  AND Booking.RoomNo = Room.RoomNo";
	        answer = stmt.executeQuery(query);
	        if (answer.next()) {
	            costRooms = answer.getInt("TotalRoomCost");
	        }

	        // Calculate the cost of amenities
	        query = "SELECT SUM(Amenity.Price * UsedAmenity.Quantity) AS TotalAmenityCost\n"
	                + "FROM Booking, UsedAmenity, Amenity\n" + "WHERE Booking.CustomerNo = " + customerNo + "\n"
	                + "  AND Booking.BookingID = UsedAmenity.BookingID\n"
	                + "  AND UsedAmenity.AmenityID = Amenity.AmenityID";
	        answer = stmt.executeQuery(query);
	        if (answer.next()) {
	            costAmenities = answer.getInt("TotalAmenityCost");
	        }

	        // Apply Club460 discount, if applicable
	        if (isMember == true) {
	            costAmenities = 0;
	        }
	        query = "SELECT Points FROM Club460 WHERE CustomerNo = " + customerNo;
	        answer = stmt.executeQuery(query);
	        int points = 0;
	        if (answer.next()) {
	            points = answer.getInt("Points");
	        } else {
	        	
	        }
	        //Asks the user if they wanna use points
	        System.out.println("Do you wanna use points? (Y/N)");
	        String usePointsFlag = scanner.nextLine();
	        if (usePointsFlag.equals("Y")) {
	        	//If yes then subtract the points conversion from roomCost
	        	costRooms =  costRooms - points/10;
	        	discountString += ", "+points+" points used";
	        }
	        // Set the discount string to "NA" if no discounts were applied
	        if (discountString.equals("")) {
	            discountString = "NA";
	        }

	        // Print the final bill
	        System.out.println("Discount Int: " + discount);
	        System.out.println("costRooms: " + costRooms);
	        System.out.println("CustomerName\t Total Cost\t Discounts Applied");
	        System.out.println(
	                customerName + "\t" + ((costAmenities + costRooms) * (100 - discount)) / 100 + "\t" + discountString);

	        String updateQuery = "UPDATE Club460 SET Points = ? WHERE CustomerNo = ?";
	        pstmt = dbconn.prepareStatement(updateQuery);
	        pstmt.setInt(1,(((costAmenities + costRooms) * (100 - discount)) / 100));
	        pstmt.setInt(2, customerNo);
	        pstmt.executeUpdate();
	       if (isMember == true) {
	    	   System.out.println((((costAmenities + costRooms) * (100 - discount)) / 100)/10+" points added to Club460 membership");
	       }
	        // Add a rating for the stay
	       
	       addRating(dbconn, scanner);

		} catch (SQLException e) {
			System.out.println("Error printing final bill for customer");
		}

	}

	/**
	 * Adds a new rating for the specified amenity into the Rating table.
	 * The user provides the AmenityID, RatingID, rating value, and rating date.
	 *
	 * @param dbconn  the Connection object representing the connection to the database
	 * @param scanner the Scanner object used to read user input
	 */
	public static void addRating(Connection dbconn, Scanner scanner) {
		System.out.println();
		//Continuosly keep asking for if they wanna add a rating. 
		System.out.println("Do you wanna add rating for an Amenity? (Y/N)");
	    String ratingFlag = scanner.nextLine();
	    if (ratingFlag.equals("Y")) {
	    	
	    }
	    else {
	    	return;
	    }
	    // Prompt the user for the AmenityID to rate
	    System.out.println("Enter the AmenityID you want to rate: ");
	    int amenityID = scanner.nextInt();
	    scanner.nextLine();

	    // Prompt the user for the RatingID for this rating
	    System.out.println("Enter the RatingID for this rating: ");
	    int ratingID = scanner.nextInt();
	    scanner.nextLine();

	    // Prompt the user for the rating value (1-10)
	    System.out.println("Enter your rating (1-10): ");
	    int rating = scanner.nextInt();
	    scanner.nextLine();

	    // Prompt the user for the rating date (yyyy-MM-dd)
	    System.out.println("Enter the rating date (yyyy-MM-dd): ");
	    String ratingDate = scanner.nextLine();

	    // Create an insert query to add the rating to the Rating table
	    String insertQuery = "INSERT INTO Rating (AmenityID, RatingID, Rating, RatingDate) VALUES (" + amenityID + ", "
	            + ratingID + ", " + rating + ", TO_DATE('" + ratingDate + "', 'YYYY-MM-DD'))";

	    try {
	        // Execute the insert query
	        Statement stmt = dbconn.createStatement();
	        stmt.executeUpdate(insertQuery);
	        System.out.println("Rating added successfully.");
	        addRating(dbconn, scanner);
	    } catch (SQLException e) {
	        System.out.println("Error adding rating.");
	    }
	}

	
	/**
	 * Prints the names of employees with the specified RespID (responsibility ID).
	 * Retrieves the employee names from the Employee and Responsibility tables
	 * by performing an INNER JOIN operation.
	 *
	 * @param dbconn  the Connection object representing the connection to the database
	 * @param stmt    the Statement object used to execute SQL queries
	 * @param scanner the Scanner object used to read user input
	 * @param answer  the ResultSet object containing the results of executed queries
	 */
	public static void printEmployeesByRespID(Connection dbconn, Statement stmt, Scanner scanner, ResultSet answer) {
	    // Prompt the user for the RespID to search employees by
	    System.out.println("Enter the RespID you want to search employees by: ");
	    int respID = scanner.nextInt();
	    scanner.nextLine();

	    // Create a query to get employee names with the specified RespID
	    String query = "SELECT EmpName FROM Employee e INNER JOIN Responsibility r ON e.EmployeeID = r.EmployeeID WHERE r.RespID = " + respID;

	    try {
	        // Execute the query and store the result in 'answer'
	        stmt = dbconn.createStatement();
	        answer = stmt.executeQuery(query);

	        // Print the employee names with the specified RespID
	        System.out.println("Employees with RespID " + respID + ":");
	        int i = 1;
	        while (answer.next()) {
	            String employeeName = answer.getString("EmpName");
	            System.out.println(i + ". " + employeeName);
	            i += 1;
	        }
	    } catch (SQLException e) {
	        System.out.println("Error printing employee names by responsibilites.");
	    }
	}


}
