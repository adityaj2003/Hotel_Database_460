
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
 * Class : CSc 460 - Database design
 * 
 * Due Date: May 1, 2023
 * 
 * Operational requirements -- Written in Java, requires Java SDK 17 to run. The username and password are hardcoded. Also you have to give the classpath in lectura before running. 
 * 								The tables are already created for the hardcoded user
 * Required Features and Bugs- I believe that i have included everything and do not know of any bugs in the program
*/

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.sql.*;

public class Prog4 {

	private static Map<String, Integer> cardDiscount = new HashMap<String, Integer>();
	private static Map<Integer, String> cardNumber = new HashMap<Integer, String>();

	public static void main(String[] args) {
		// Stores the discount for the various types of cards
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

		String username = "adityakumar"; // Oracle DBMS username
		String password = "a8287"; // Oracle DBMS password
		Statement stmt = null;
		try {

			Class.forName("oracle.jdbc.OracleDriver");// Initialize the driver.

		} catch (ClassNotFoundException e) {

			System.err.print("\n*** ClassNotFoundException:  " + "Error loading Oracle JDBC driver.\n"
					+ "\tPerhaps the driver is not on the Classpath?");
			System.exit(-1);

		}
		// Create connection object and ResultSet object
		Connection dbconn = null;
		ResultSet answer = null;
		// Connect to the db
		try {
			dbconn = DriverManager.getConnection(oracleURL, username, password); // Connect to the sql database.
			stmt = dbconn.createStatement();

		} catch (SQLException e) {

			System.err.print("\n*** SQLException:  " + "Could not open JDBC connection.");
			System.err.print("\n\tMessage:   " + e.getMessage());
			System.err.print("\n\tSQLState:  " + e.getSQLState());
			System.err.print("\n\tErrorCode: " + e.getErrorCode());
			System.exit(-1);

		}
		// Initialise scanner and jump to mainMenu.
		Scanner scanner = new Scanner(System.in);
		mainMenu(dbconn, stmt, answer, scanner);

	}

	/**
	 * Displays the main menu for the application, providing various options for the
	 * user to interact with different entities in the database.
	 * 
	 * @param dbconn  The Connection object for interacting with the database.
	 * @param stmt    The Statement object for executing SQL queries on the
	 *                database.
	 * @param answer  The ResultSet object for storing the results of executed SQL
	 *                queries.
	 * @param scanner The Scanner object for receiving user input.
	 * 
	 *                Pre-condition : Displays menu result for answering various
	 *                queries Post-condition : Takes user input and displays the
	 *                corresponding queries on the console.
	 */
	public static void mainMenu(Connection dbconn, Statement stmt, ResultSet answer, Scanner scanner) {
		// Takes int input from user to determine what he wants to do
		System.out.print("\n\t\tMENU\nWhat do you wanna do today?");
		System.out.print("\nType a number from (1-9) to specify, -1 to exit\n");
		System.out.print("\n1. Add / Remove / Update Customer Details");
		System.out.print("\n2. Add / Remove / Update Booking Details");
		System.out.print("\n3. Add / Remove / Update Amenities");
		System.out.print("\n4. Add / Remove / Update Room Details");
		System.out.print("\n5. Add / Remove / Update UsedAmenities of Customer");
		System.out.print("\n6. Add / Remove / Update Club460 Members");
		System.out.print("\n7. Add / Remove / Update Employee Details");
		System.out.print("\n8. Add / Remove / Update Employee Responsibilities");
		System.out.print("\n9. Execute Predefined Queries\n");
		int input = scanner.nextInt();
		// Check user input to determine the chosen option and whether he wants to
		// add/delete/update chose option
		if (input == 1) {
			System.out.print("\n1. Add Customer Details");
			System.out.print("\n2. Remove Customer Details");
			System.out.print("\n3. Update Customer Details\n");
			int userSelection = scanner.nextInt();
			// Taking user input to check whether to update/delete or add.
			if (userSelection == 1) {
				addRecord(dbconn, stmt, answer, "Customer", scanner);
			} else if (userSelection == 2) {
				deleteRecord(dbconn, stmt, answer, "Customer", scanner);
			} else if (userSelection == 3) {
				updateRecord(dbconn, stmt, answer, "Customer", scanner);
			} else {
				System.out.print("\nINVALID SELECTION, please try again!\n");
			}
			mainMenu(dbconn, stmt, answer, scanner);
			// Check user input to determine the chosen option and whether he wants to
			// add/delete/update chose option
		} else if (input == 2) {
			System.out.print("\n1. Add Booking Details");
			System.out.print("\n2. Remove Booking Details");
			System.out.print("\n3. Update Booking Details\n");
			int userSelection = scanner.nextInt();
			// Taking user input to check whether to update/delete or add.
			if (userSelection == 1) {
				addRecord(dbconn, stmt, answer, "Booking", scanner);
			} else if (userSelection == 2) {
				deleteRecord(dbconn, stmt, answer, "Booking", scanner);
			} else if (userSelection == 3) {
				updateRecord(dbconn, stmt, answer, "Booking", scanner);
			} else {
				System.out.print("\nINVALID SELECTION, please try again! \n");
			}
			mainMenu(dbconn, stmt, answer, scanner);
			// Check user input to determine the chosen option and whether he wants to
			// add/delete/update chose option
		} else if (input == 3) {
			System.out.print("\n1. Add Amenities");
			System.out.print("\n2. Remove Amenities");
			System.out.print("\n3. Update Amenities\n");
			int userSelection = scanner.nextInt();
			// Taking user input to check whether to update/delete or add.
			if (userSelection == 1) {
				addRecord(dbconn, stmt, answer, "Amenity", scanner);
			} else if (userSelection == 2) {
				deleteRecord(dbconn, stmt, answer, "Amenity", scanner);
			} else if (userSelection == 3) {
				updateRecord(dbconn, stmt, answer, "Amenity", scanner);
			} else {
				System.out.print("\nINVALID SELECTION, please try again!\n");
			}
			mainMenu(dbconn, stmt, answer, scanner);
			// Check user input to determine the chosen option and whether he wants to
			// add/delete/update chose option
		} else if (input == 4) {
			System.out.print("\n1. Add Room Information");
			System.out.print("\n2. Remove Room Information");
			System.out.print("\n3. Update Room Information\n");
			int userSelection = scanner.nextInt();
			// Taking user input to check whether to update/delete or add.
			if (userSelection == 1) {
				addRecord(dbconn, stmt, answer, "RoomDetails", scanner);
			} else if (userSelection == 2) {
				deleteRecord(dbconn, stmt, answer, "RoomDetails", scanner);
			} else if (userSelection == 3) {
				updateRecord(dbconn, stmt, answer, "RoomDetails", scanner);
			} else {
				System.out.print("\nINVALID SELECTION, please try again!\n");
			}
			mainMenu(dbconn, stmt, answer, scanner);
			// Check user input to determine the chosen option and whether he wants to
			// add/delete/update chose option
		} else if (input == 5) {
			System.out.print("\n1. Add UsedAmenities");
			System.out.print("\n2. Remove UsedAmenities");
			System.out.print("\n3. Update UsedAmenities\n");
			int userSelection = scanner.nextInt();
			// Taking user input to check whether to update/delete or add.

			if (userSelection == 1) {
				addRecord(dbconn, stmt, answer, "usedAmenity", scanner);
			} else if (userSelection == 2) {
				deleteRecord(dbconn, stmt, answer, "usedAmenity", scanner);
			} else if (userSelection == 3) {
				updateRecord(dbconn, stmt, answer, "usedAmenity", scanner);
			} else {
				System.out.print("\nINVALID SELECTION, please try again!\n");
			}

			mainMenu(dbconn, stmt, answer, scanner);
			// Check user input to determine the chosen option and whether he wants to
			// add/delete/update chose option
		} else if (input == 6) {
			System.out.print("\n1. Add Club460 Members");
			System.out.print("\n2. Remove Club460 Members");
			System.out.print("\n3. Update Club460 Members\n");
			// Taking user input to check whether to update/delete or add.
			int userSelection = scanner.nextInt();

			if (userSelection == 1) {
				addRecord(dbconn, stmt, answer, "Club460", scanner);
			} else if (userSelection == 2) {
				deleteRecord(dbconn, stmt, answer, "Club460", scanner);
			} else if (userSelection == 3) {
				updateRecord(dbconn, stmt, answer, "Club460", scanner);
			} else {
				System.out.print("\nINVALID SELECTION, please try again!\n");
			}

			mainMenu(dbconn, stmt, answer, scanner);
			// Check user input to determine the chosen option and whether he wants to
			// add/delete/update chose option
		} else if (input == 7) {
			System.out.print("\n1. Add Employee Details");
			System.out.print("\n2. Remove Employee Details");
			System.out.print("\n3. Update Employee Details\n");
			int userSelection = scanner.nextInt();
			// Taking user input to check whether to update/delete or add.

			if (userSelection == 1) {
				addRecord(dbconn, stmt, answer, "Employee", scanner);
			} else if (userSelection == 2) {
				deleteRecord(dbconn, stmt, answer, "Employee", scanner);
			} else if (userSelection == 3) {
				updateRecord(dbconn, stmt, answer, "Employee", scanner);
			} else {
				System.out.print("\nInvalid Selection, please try again!");
			}

			mainMenu(dbconn, stmt, answer, scanner);
			// Check user input to determine the chosen option and whether he wants to
			// add/delete/update chose option
		} else if (input == 8) {
			System.out.print("\n1. Add Employee Responsibilities");
			System.out.print("\n2. Remove Employee Responsibilities");
			System.out.print("\n3. Update Employee Responsibilities\n");
			int userSelection = scanner.nextInt();
			// Taking user input to check whether to update/delete or add.
			if (userSelection == 1) {
				addRecord(dbconn, stmt, answer, "Responsibility", scanner);
			} else if (userSelection == 2) {
				deleteRecord(dbconn, stmt, answer, "Responsibility", scanner);
			} else if (userSelection == 3) {
				updateRecord(dbconn, stmt, answer, "Responsibility", scanner);
			} else {
				System.out.print("\nINVALID SELECTION, please try again!\n");
			}

			mainMenu(dbconn, stmt, answer, scanner);
			// Check user input to determine the chosen option and whether he wants to
			// add/delete/update chose option
		} else if (input == 9) {
			predefinedQueriesMenu(dbconn, stmt, answer, scanner);
		} else if (input == -1) {
			System.exit(0);
		}

	}

	/**
	 * Displays a menu of predefined queries and allows the user to select one.
	 * Executes the selected query and returns to the main menu.
	 *
	 * @param dbconn  the Connection object representing the connection to the
	 *                database
	 * @param stmt    the Statement object used to execute SQL queries
	 * @param answer  the ResultSet object containing the results of executed
	 *                queries
	 * @param scanner the Scanner object used to read user input
	 * 
	 *                Pre-condition: All the arguments are functioning
	 *                Post-condition: The user has made a choice, and was given the
	 *                desired results
	 */
	public static void predefinedQueriesMenu(Connection dbconn, Statement stmt, ResultSet answer, Scanner scanner) {
		// Gives a selection of predefined queries user can run.
		System.out
				.print("\n1. Print the current bill (total $) for a customer for their stay and all unpaid amenities.");
		System.out.print("\n2. Customers that are currently staying at the hotel.");
		System.out.print("\n3. Print the schedule of staff.");
		System.out.print("\n4. Print the average ratings of different amenities.");
		System.out.print("\n5. Print the employee names of specific responsibility/job titles.\n");
		int userSelection = scanner.nextInt();
		scanner.nextLine();
		// Takes user input from the user to decide what predefined queries they wanna
		// run
		if (userSelection == 1) {
			finalBill(dbconn, stmt, answer);
		} else if (userSelection == 2) {
			getCurrentCustomers(dbconn, answer, scanner);
		} else if (userSelection == 3) {
			getStaffSchedule(dbconn, stmt, answer, scanner);
		} else if (userSelection == 4) {
			printAvgRatingsInRange(dbconn, stmt, answer, scanner);
		} else if (userSelection == 5) {
			printEmployeesByRespID(dbconn, stmt, scanner, answer);
		} else {
			System.out.print("\nINVALID SELECTION, please try again!\n");
		}

		// Return to the main menu after executing the selected query
		mainMenu(dbconn, stmt, answer, scanner);
	}

	/**
	 * Adds a new record to the specified table in the database. Currently supports
	 * adding records to the Customer and UsedAmenity tables. Prompts the user for
	 * the required information to add the record.
	 *
	 * @param dbconn     the Connection object representing the connection to the
	 *                   database
	 * @param stmt       the Statement object used to execute SQL queries
	 * @param answer     the ResultSet object containing the results of executed
	 *                   queries
	 * @param recordType the type of record to add (e.g., "Customer", "usedAmenity")
	 * @param scanner    the Scanner object used to read user input
	 */
	public static void addRecord(Connection dbconn, Statement stmt, ResultSet answer, String recordType,
			Scanner scanner) {
		// Add a new record to the customer table
		if (recordType.equals("Customer")) {
			try {
				// Ask the user for various information to add to the record.
				System.out.print("Enter CustomerNo.: ");
				int customerNo = scanner.nextInt();
				scanner.nextLine();
				System.out.print("\nName: ");
				// Ask the user for various information to add to the record.
				String name = scanner.nextLine();
				System.out.print("\nAddress: ");
				String address = scanner.nextLine();
				// Ask the user for various information to add to the record.
				System.out.print("\nIs customer a student? (Y/N): ");
				String isAStudent = scanner.nextLine();
				System.out.print("\n\nIs customer a Club460 Member? (Y/N): ");
				String isAMember = scanner.nextLine();
				// Query to insert to table
				String insertQuery = "INSERT INTO Customer (CustomerNo, Name, Address, Student, Club460) VALUES ("
						+ customerNo + ",'" + name + "','" + address + "','" + isAStudent + "','" + isAMember + "')";
				stmt.executeUpdate(insertQuery);
				// If inserted record is member of 460, add to to Club460 table.
				if (isAMember.equals("Y")) {
					addClub460(dbconn, stmt, customerNo);
				}
			}

			catch (Exception e) {
				System.out.print("\nError adding record, maybe it already exists?");
			}

		} else if (recordType.equals("usedAmenity")) {
			try {
				// Ask the user for various information to add to the record.
				System.out.print("Enter BookingID: ");
				int bookingId = scanner.nextInt();
				boolean bookingExists = false;
				// Check if the booking inputted by user exists.
				String checkBookingIdQuery = "SELECT COUNT(*) FROM Booking WHERE BookingID = " + bookingId;
				answer = stmt.executeQuery(checkBookingIdQuery);

				if (answer.next()) {
					int count = answer.getInt(1);
					if (count > 0) {
						bookingExists = true;
					}
				}
				// If booking doesn't exist, print and return to main menu.
				if (!bookingExists) {
					System.out.print("\nBookingID " + bookingId + " does not exist in the Booking table.");
					return;
				}
				// Ask the user for various information to add to the record.
				System.out.print("\nEnter AmenityID: ");
				int amenityId = scanner.nextInt();
				boolean amenityExists = false;
				// Check if amenity inputted exists
				String checkAmenityIdQuery = "SELECT COUNT(*) FROM Amenity WHERE AmenityID = " + amenityId;
				answer = stmt.executeQuery(checkAmenityIdQuery);

				if (answer.next()) {
					int count = answer.getInt(1);
					if (count > 0) {
						amenityExists = true;
					}
				}
				// If amenity doesn't exist, print and return to main menu.
				if (!amenityExists) {
					System.out.print("\nAmenityID " + amenityId + " does not exist in the Amenity table.");
					addRecord(dbconn, stmt, answer, "Amenity", scanner);
					return;
				}
				// Ask the user for various information to add to the record.
				System.out.print("\nEnter Quantity: ");
				int quantity = scanner.nextInt();
				// Insert to table
				String insertQuery = "INSERT INTO UsedAmenity (BookingID, AmenityID, Quantity) VALUES (" + bookingId
						+ "," + amenityId + "," + quantity + ")";
				stmt.executeUpdate(insertQuery);
			} catch (SQLException e) {
				System.out.print("\nError adding the Amenity used by the booking." + "Maybe it was added before?");
			}
		} else if (recordType.equals("Booking")) {
			try {
				// Ask the user for various information to add to the record.
				System.out.print("\nEnter CustomerNo.: ");
				int customerNo = scanner.nextInt();
				boolean customerExists = false;
				// Check if customer exists of the customerNo inputed
				String checkCustomerNoQuery = "SELECT COUNT(*) FROM Customer WHERE CustomerNo = " + customerNo;
				ResultSet resultSet = stmt.executeQuery(checkCustomerNoQuery);
				// If customer exists, change flag to true
				if (resultSet.next()) {
					int count = resultSet.getInt(1);
					if (count > 0) {
						customerExists = true;
					}
				}
				// If the user doesn't exist, print and return to main menu.
				if (!customerExists) {
					System.out.print("\nCustomerNo " + customerNo + " does not exist in the Customer table.");
					addRecord(dbconn, stmt, answer, "Customer", scanner);
					return;
				}
				// Ask the user for various information to add to the record.
				System.out.print("\nRoomNo: ");
				int roomNo = scanner.nextInt();
				boolean roomExists = false;
				// Check if room inputed exists.
				String checkRoomNoQuery = "SELECT COUNT(*) FROM Room WHERE RoomNo = " + roomNo;
				resultSet = stmt.executeQuery(checkRoomNoQuery);

				if (resultSet.next()) {
					int count = resultSet.getInt(1);
					if (count > 0) {
						roomExists = true;
					}
				}
				// If room doesn't exist, print and return to main menu.

				if (!roomExists) {
					System.out.print("\nRoomNo " + roomNo + " does not exist in the Room table.");
					addRecord(dbconn, stmt, answer, "RoomDetails", scanner);
					return;
				}
				// Ask the user for various information to add to the record.
				System.out.print("\nBookingId: ");
				int bookingId = scanner.nextInt();
				scanner.nextLine();
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
				// Ask the user for various information to add to the record.
				System.out.print("\nStart Date (yyyy-MM-dd): ");
				String dateFrom = scanner.nextLine();
				// Parse and format the dateFrom input
				dateFrom = dateFormat1.format(dateFormat1.parse(dateFrom));
				// Ask the user for various information to add to the record.
				System.out.print("\nEnd Date (yyyy-MM-dd): ");
				String dateTo = scanner.nextLine();
				// Parse and format the dateTo input
				dateTo = dateFormat1.format(dateFormat1.parse(dateTo));
				if (dateTo.compareTo(dateFrom) < 0) {
					System.out.print("\nError adding booking : DateFrom must be before DateTo");
				}
				// Checks if there is no overlap between booking of a particular room
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
				// If there is a booking for the dates inputed or in-between them, then print
				// that cannot book and return to main menu.
				if (bookedCount > 0) {
					System.out.print(
							"\nRoomNo " + roomNo + " is already booked between " + dateFrom + " and " + dateTo + ".");
					return;
				}
				// Insert to table
				String insertQuery = "INSERT INTO Booking (CustomerNo, RoomNo, BookingID, dateFrom, dateTo) VALUES ("
						+ customerNo + "," + roomNo + "," + bookingId + ", TO_DATE('" + dateFrom
						+ "','YYYY-MM-DD'), TO_DATE('" + dateTo + "','YYYY-MM-DD'))";
				// Execute Query
				stmt.executeUpdate(insertQuery);
			} catch (Exception e) {
				;
				System.out.print("\nError adding record, maybe it already exists?");
			}

		}

		else if (recordType.equals("Amenity")) {
			try {
				// Ask the user for various information to add to the record.
				System.out.print("\nEnter AmenityID: ");
				int amenityId = scanner.nextInt();
				scanner.nextLine();
				System.out.print("\nEnter amenity's name: ");
				String name = scanner.nextLine();
				System.out.print("\nEnter price of amenity: ");
				// Ask the user for various information to add to the record.
				int price = scanner.nextInt();
				// Insert to the Amenity table
				String insertQuery = "INSERT INTO Amenity VALUES (" + amenityId + ", '" + name + "', " + price + ")";
				// Execute Query
				stmt.executeUpdate(insertQuery);
			} catch (Exception e) {
				System.out.print("\nError adding record, maybe it already exists?");
			}

		}

		else if (recordType.equals("RoomDetails")) {
			try {
				// Ask the user for various information to add to the record.
				System.out.print("\nEnter RoomID: ");
				int roomId = scanner.nextInt();
				System.out.print("\nEnter price of Room:");
				// Ask the user for various information to add to the record.
				int price = scanner.nextInt();
				// Insert to room table
				String insertQuery = "INSERT INTO Room VALUES (" + roomId + "," + price + ")";
				// Execute Query
				stmt.executeUpdate(insertQuery);
			} catch (Exception e) {
				;
				System.out.print("\nError adding record, maybe it already exists?");
			}
		} else if (recordType.equals("Employee")) {
			try {
				// Ask the user for various information to add to the record.
				System.out.print("\nEnter Employee ID: ");
				int employeeID = scanner.nextInt();
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
				scanner.nextLine();
				// Ask the user for various information to add to the record.
				System.out.print("\nEnter Employee Name: ");
				String employeeName = scanner.nextLine();
				// Ask the user for various information to add to the record.
				System.out.print("\nEnter Employee Date of Birth (yyyy-MM-dd): ");
				String dob = scanner.nextLine();
				// Parse and format the dob input
				dob = dateFormat1.format(dateFormat1.parse(dob));
				// Insert to table
				String insertQuery = "INSERT INTO Employee (EmployeeID, EmpName, DOB) VALUES (" + employeeID + ",'"
						+ employeeName + "',TO_DATE('" + dob + "','YYYY-MM-DD'))";
				// Execute Query
				stmt.executeUpdate(insertQuery);
			} catch (Exception e) {
				;
				System.out.print("\nError adding record, maybe it already exists?");
			}
		} else if (recordType.equals("Responsibility")) {
			try {
				// Ask the user for various information to add to the record.
				System.out.print("\nEnter Employee ID: ");
				int employeeID = scanner.nextInt();
				System.out.print("\nEnter Responsibility ID: ");
				// Ask the user for various information to add to the record.
				int responsibilityID = scanner.nextInt();
				scanner.nextLine();
				System.out.print("\nEnter the Responsibility title: ");
				String title = scanner.nextLine();
				
				System.out.print("\nEnter Day (0 for Sunday, 1 for Monday, etc.): ");
				int day = scanner.nextInt();
				// Ask the user for various information to add to the record.
				scanner.nextLine();
				System.out.print("\nEnter Start Time (HH:MM): ");
				// Ask the user for various information to add to the record.
				String startTime = scanner.nextLine();
				if (startTime.charAt(0) == '2' && startTime.charAt(1) > '4') {
					System.out.print("\nError adding record : Invalid Start Time");
					return;
				}
				System.out.print("\nEnter Stop Time (HH:MM): ");
				String stopTime = scanner.nextLine();
				if (stopTime.charAt(0) == '2' && stopTime.charAt(1) > '4') {
					System.out.print("\nError adding record : Invalid End Time");
					return;
				}
				if (stopTime.compareTo(startTime) < 0) {
					System.out.print("\nError updating record: Shift end time must be after start time");
					return;
				}
				// Add to responsibility table
				String insertQuery = "INSERT INTO Responsibility (EmployeeID, RespID, JobTitle, Day, startTime, stopTime) VALUES("
						+ employeeID + "," + responsibilityID + ",'" + title + "', " + day + ",'" + startTime + "','"
						+ stopTime + "')";
				// Execute Query
				stmt.executeUpdate(insertQuery);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.print("\nError adding record, maybe it already exists?");
			}
		} else if (recordType.equals("Club460")) {
			try {
				// Ask the user for various information to add to the record.
				System.out.print("\nEnter CustomerNo.:");
				int customerNo = scanner.nextInt();
				System.out.print("\nEnter Points:");
				// Ask the user for various information to add to the record.
				int points = scanner.nextInt();
				// Insert into Club460 table
				String insertQuery = "INSERT INTO Club460 (CustomerNo, Points) VALUES (" + customerNo + "," + points
						+ ")";
				// Execute Query
				stmt.executeUpdate(insertQuery);
				// Update the membership in the club460 table as well
				updateClub460InCustomerTable(dbconn, stmt, customerNo, "Y");
			} catch (Exception e) {
				;
				System.out.print("\nError adding record, maybe it already exists?");
			}
		}

	}

	/**
	 * Adds a new Club 460 member to the Club460 table with the given customer
	 * number and initializes the points to 0.
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
			System.out.print("\nError adding record to Club 460");
		}
	}

	/**
	 * Deletes a Club 460 member from the Club460 table using the given customer
	 * number.
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
			System.out.println(rowsDeleted + " row(s) deleted from Club 460");
		} catch (Exception e) {
			System.out.print("\nError deleting record from Club 460");
		}
	}

	/**
	 * 
	 * Deletes records from the database based on the given record type. Supports
	 * deleting Customer, Booking,UsedAmenity, Room, Club460, Employees,
	 * Responsibilities record.
	 * 
	 * @param dbconn     The database connection object.
	 * @param stmt       The SQL statement object.
	 * @param answer     The result set object.
	 * @param recordType The type of record to delete. Must be one of "Customer",
	 *                   "Booking", or "usedAmenity".
	 * @param scanner    The Scanner object to read user input.
	 */

	public static void deleteRecord(Connection dbconn, Statement stmt, ResultSet answer, String recordType,
			Scanner scanner) {
		if (recordType.equals("Customer")) {
			// Delete record from customer table
			try {
				// Ask the user for various information to delete the record.
				System.out.print("\nEnter CustomerNo.: ");
				int customerNo = scanner.nextInt();
				deleteClub460(dbconn, stmt, customerNo);
				// Delete from child tables (where customerNo is foreign key)
				String deleteQueryBooking = "DELETE FROM Booking WHERE CustomerNo = " + customerNo;
				stmt.executeUpdate(deleteQueryBooking);
				// Delete from child tables (where customerNo is foreign key)
				String deleteQueryPayment = "DELETE FROM Payment WHERE CustomerNo = " + customerNo;
				stmt.executeUpdate(deleteQueryPayment);
				// Delete from child tables (where customerNo is foreign key)
				String deleteQueryClub = "DELETE FROM Club460 WHERE CustomerNo = " + customerNo;
				stmt.executeUpdate(deleteQueryClub);
				// Delete from table
				String deleteQuery = "DELETE FROM Customer WHERE CustomerNo = " + customerNo;
				int rowsDeleted = stmt.executeUpdate(deleteQuery);

				System.out.print("\nDeleted successfully.");
			} catch (Exception e) {
				System.out.print("\nError deleting record");
			}
			// Delete record from booking table
		} else if (recordType.equals("Booking")) {
			try {
				// Ask the user for various information to delete the record.
				System.out.print("\nEnt" + "er BookingID: ");
				int bookingId = scanner.nextInt();
				scanner.nextLine();
				// Delete from child table
				String deleteQuery = "DELETE FROM Booking WHERE BookingID = " + bookingId;
				stmt.executeUpdate(deleteQuery);
				System.out.print("\nDeleted successfully.");
			} catch (Exception e) {
				;
				System.out.print("\nError deleting record");
			}
			// Delete from usedAmenities table
		} else if (recordType.equals("usedAmenity")) {
			try {
				// Ask the user for various information to delete the record.
				System.out.print("\nEnter BookingID: ");
				int bookingId = scanner.nextInt();
				// Ask the user for various information to delete the record.
				System.out.print("\nEnter AmenityID: ");
				int amenityId = scanner.nextInt();
				scanner.nextLine();
				// Delete Record
				String deleteQuery = "DELETE FROM UsedAmenity WHERE BookingID = " + bookingId + " AND AmenityID = "
						+ amenityId;
				stmt.executeUpdate(deleteQuery);
			} catch (Exception e) {

				System.out.print("\nError deleting record");
			}
			// Delete from Amenity Table
		} else if (recordType.equals("Amenity")) {
			try {
				// Ask the user for various information to delete the record.
				System.out.print("\nEnter AmenityID: ");
				int amenityId = scanner.nextInt();
				// Delete from child table where AmenityID is foreign key
				scanner.nextLine();
				String deleteQueryUsedAmenity = "DELETE FROM UsedAmenity WHERE AmenityID = " + amenityId;
				stmt.executeUpdate(deleteQueryUsedAmenity);
				// Delete from child table where AmenityID is foreign key
				String deleteQueryRating = "DELETE FROM Rating WHERE AmenityID = " + amenityId;
				stmt.executeUpdate(deleteQueryRating);
				// Delete from table
				String deleteQuery = "DELETE FROM Amenity WHERE AmenityID = " + amenityId;
				stmt.executeUpdate(deleteQuery);
				System.out.print("\nDeleted successfully.");
			} catch (Exception e) {

				System.out.print("\nError deleting record");
			}
			// Delete from Room Details table
		} else if (recordType.equals("RoomDetails")) {
			try {
				// Ask the user for various information to delete the record.
				System.out.print("\nEnter RoomID: ");
				int roomId = scanner.nextInt();
				scanner.nextLine();
				// Delete from child table where RoomNo is foreign key
				String deleteQueryBooking = "DELETE FROM Booking WHERE RoomNo = " + roomId;
				stmt.executeUpdate(deleteQueryBooking);
				// Delete from table
				String deleteQuery = "DELETE FROM Room WHERE RoomNo = " + roomId;
				stmt.executeUpdate(deleteQuery);
				System.out.print("\nDeleted successfully.");
			} catch (Exception e) {
				;
				System.out.print("\nError deleting record");
			}
			// Delete from Employee table
		} else if (recordType.equals("Employee")) {
			try {
				System.out.print("\nEnter Employee ID: ");
				// Ask the user for various information to delete the record.
				int employeeID = scanner.nextInt();
				scanner.nextLine();
				// Delete record
				String deleteQuery = "DELETE FROM Employee WHERE EmployeeID = " + employeeID;
				stmt.executeUpdate(deleteQuery);
				System.out.print("\nEmployee deleted successfully.");
			} catch (Exception e) {
				;
				System.out.print("\nError deleting record");
			}
			// Delete from responsibility table
		} else if (recordType.equals("Responsibility")) {
			try {
				System.out.print("\nEnter Employee ID: ");
				// Ask the user for various information to delete the record.
				int employeeID = scanner.nextInt();
				System.out.print("\nEnter Responsibility ID: ");
				// Ask the user for various information to delete the record.
				int responsibilityID = scanner.nextInt();
				scanner.nextLine();
				// Delete from table
				String deleteQuery = "DELETE FROM Responsibility WHERE EmployeeID = " + employeeID + " AND RespID = "
						+ responsibilityID;
				stmt.executeUpdate(deleteQuery);
				System.out.print("\nResponsibility deleted successfully.");
			} catch (Exception e) {
				;
				System.out.print("\nError deleting record");
			}
			// Deletes from Club460 table
		} else if (recordType.equals("Club460")) {
			try {
				// Ask the user for various information to delete the record.
				System.out.print("\nEnter the CustomerNo of the Club460 record you want to delete: ");
				int customerNo = scanner.nextInt();
				scanner.nextLine();
				// Delete from table
				String deleteQuery = "DELETE FROM Club460 WHERE CustomerNo = " + customerNo;
				stmt.executeUpdate(deleteQuery);
				updateClub460InCustomerTable(dbconn, stmt, customerNo, "N");
			} catch (SQLException e) {
				System.out.print("\nError deleting " + recordType);
			}
		}
	}

	/**
	 * Adds a payment record to the Payment table and returns any associated
	 * discount card name. The method takes in a Connection object, a Statement
	 * object, and a customer number.
	 *
	 * @param dbconn     the Connection object used to create a Statement
	 * @param stmt       the Statement object used to execute the query
	 * @param customerNo the customer number to be added to the Payment table
	 * @return the name of the discount card, if applicable, or an empty string
	 *         otherwise
	 * @throws SQLException if there is an error executing the query
	 */
	public static String addPayment(Connection dbconn, Statement stmt, int customerNo) throws SQLException {
		Scanner scanner = new Scanner(System.in);

		// Prompt the user to enter the mode of payment
		System.out.print("\nEnter mode of payment (CC/Checking/Cash):");
		String mode = scanner.nextLine();

		// Check if the payment mode is either Checking or CC
		if (mode.equals("Checking") || mode.equals("CC")) {
			System.out.print("\n\nEnter CC or Checking account number (leave empty for cash): ");
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

			// Return the card name if the mode is CC and the card number is found in the
			// map
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
	 * Adds a used amenity record to the UsedAmenity table by taking in a Connection
	 * object, a Statement object, and a ResultSet object.
	 *
	 * @param dbconn the Connection object used to create a Statement
	 * @param stmt   the Statement object used to execute the query
	 * @param answer the ResultSet object used to store the query result
	 */
	public static void addUsedAmenity(Connection dbconn, Statement stmt, ResultSet answer) {
		try {
			Scanner scanner = new Scanner(System.in);

			// Prompt the user to enter the booking ID
			System.out.print("\nEnter Booking ID: ");
			int bookingID = scanner.nextInt();

			// Prompt the user to enter the amenity ID
			System.out.print("\n\nEnter Amenity ID: ");
			int amenityID = scanner.nextInt();

			// Prompt the user to enter the quantity of the used amenity
			System.out.print("\n\nEnter Quantity: ");
			int quantity = scanner.nextInt();

			// Insert the used amenity record into the UsedAmenity table
			String insertQuery = "INSERT INTO UsedAmenity (BookingID, AmenityID, Quantity) VALUES (" + bookingID + ","
					+ amenityID + "," + quantity + ")";
			stmt.executeUpdate(insertQuery);

		} catch (Exception e) {
			System.out.print("\nError adding used amenities");
		}
	}

	/**
	 * 
	 * Updates records in the database based on the given record type. Supports
	 * updating Customer, booking, Room, Amenity, Employee, Responsibility, Club460,
	 * UsedAmenity.
	 * 
	 * @param dbconn     The database connection object.
	 * @param stmt       The SQL statement object.
	 * @param answer     The result set object.
	 * @param recordType The type of record to update. Must be one of "Customer" or
	 *                   "Booking".
	 * @param scanner    The Scanner object to read user input.
	 */
	public static void updateRecord(Connection dbconn, Statement stmt, ResultSet answer, String recordType,
			Scanner scanner) {
		try {
			// Updates Customer record
			if (recordType.equals("Customer")) {
				// Ask the user for various information to delete the record.
				System.out.print("\nEnter the customerNo of the record you want to update: ");
				int id = scanner.nextInt();
				scanner.nextLine();
				// Ask the user for various information to delete the record.
				System.out.print("\n\nEnter updated name: ");
				String name = scanner.nextLine();
				System.out.print("\n\nEnter updated address: ");
				// Ask the user for various information to delete the record.
				String address = scanner.nextLine();
				System.out.print("\n\nIs the customer a student? (Y/N): ");
				// Ask the user for various information to delete the record.
				String isAStudent = scanner.nextLine();
				System.out
						.print("\nIf you want to update Club460 membership, Add or Delete in Club460 table to update");
				String updateQuery = "UPDATE Customer SET Name='" + name + "', Address='" + address + "', Student='"
						+ isAStudent + "' WHERE CustomerNo=" + id;
				stmt.executeUpdate(updateQuery);
				// Updates booking record
			} else if (recordType.equals("Booking")) {
				System.out.print("\nEnter the BookingID of the record you want to update: ");
				// Ask the user for various information to delete the record.
				int id = scanner.nextInt();
				scanner.nextLine();
				System.out.print("\nEnter updated start date (yyyy-MM-dd): ");
				// Ask the user for various information to delete the record.
				String dateFrom = scanner.nextLine();

				String getRoomNoQuery = "SELECT RoomNo FROM Booking WHERE BookingID = " + id;
				ResultSet resultSet = stmt.executeQuery(getRoomNoQuery);
				int roomNo = 0;
				if (resultSet.next()) {
					roomNo = resultSet.getInt("RoomNo");
				} else {
					System.out.print("\nNo room associated with BookingID " + id);
					return;
				}

				// Parse and format the dateFrom input
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

				dateFrom = dateFormat.format(dateFormat.parse(dateFrom));
				// Ask the user for various information to delete the record.
				System.out.print("\nEnter updated end date (yyyy-MM-dd): ");
				String dateTo = scanner.nextLine();

				// Parse and format the dateTo input

				dateTo = dateFormat.format(dateFormat.parse(dateTo));
				if (dateTo.compareTo(dateFrom) < 0) {
					System.out.print("\nError updating booking : DateFrom must be before DateTo");
				}
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
				// Updates amenity records
			} else if (recordType.equals("Amenity")) {
				// Ask the user for various information to delete the record.
				System.out.print("\nEnter the Amenity ID of the record you want to update: ");
				int id = scanner.nextInt();
				// Ask the user for various information to delete the record.
				System.out.print("\nEnter updated price: ");
				double price = scanner.nextDouble();
				scanner.nextLine();
				// Ask the user for various information to delete the record.
				String updateQuery = "UPDATE Amenity SET Price=" + price + " WHERE AmenityID=" + id;
				stmt.executeUpdate(updateQuery);
				// Update room details
			} else if (recordType.equals("RoomDetails")) {
				System.out.print("\nEnter the RoomNo of the record you want to update: ");
				int id = scanner.nextInt();
				// Ask the user for various information to delete the record.
				System.out.print("\nEnter updated price: ");
				double price = scanner.nextDouble();
				scanner.nextLine();
				String updateQuery = "UPDATE Room SET Price=" + price + " WHERE RoomNo=" + id;
				stmt.executeUpdate(updateQuery);
			}
			// Update employee details
			else if (recordType.equals("Employee")) {
				System.out.print("\nEnter the Employee ID of the record you want to update: ");
				int id = scanner.nextInt();
				scanner.nextLine();
				System.out.print("\nEnter updated employee name: ");
				// Ask the user for various information to delete the record.
				String employeeName = scanner.nextLine();
				System.out.print("\nEnter updated date of birth (yyyy-MM-dd): ");
				String dob = scanner.nextLine();

				// Parse and format the dob input
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date parsedDob = dateFormat.parse(dob);
				String formattedDob = dateFormat.format(parsedDob);

				String updateQuery = "UPDATE Employee SET EmpName='" + employeeName + "', DOB=TO_DATE('" + formattedDob
						+ "', 'YYYY-MM-DD') WHERE EmployeeID=" + id;
				stmt.executeUpdate(updateQuery);
				// Update responsibility details
			} else if (recordType.equals("Responsibility")) {
				System.out.print("\nEnter the Employee ID of the record you want to update: ");
				// Ask the user for various information to delete the record.
				int id = scanner.nextInt();
				System.out.print("\nEnter the Responsibility ID of the record you want to update: ");
				// Ask the user for various information to delete the record.
				int respId = scanner.nextInt();
				System.out.print("\nEnter updated day (0 for Sunday, 1 for Monday, etc.): ");
				// Ask the user for various information to delete the record.
				int day = scanner.nextInt();
				scanner.nextLine();
				System.out.print("\n\nEnter updated start time (HH:MM): ");
				// Ask the user for various information to delete the record.
				String startTime = scanner.nextLine();
				if (startTime.charAt(0) == '2' && startTime.charAt(1) > '4') {
					System.out.print("\nError updating record : Invalid Start Time");
					return;
				}
				System.out.print("\nEnter updated stop time (HH:MM): ");
				// Ask the user for various information to delete the record.
				String stopTime = scanner.nextLine();
				if (stopTime.charAt(0) == '2' && stopTime.charAt(1) > '4') {
					System.out.print("\nError updating record : Invalid End Time");
					return;
				}
				if (stopTime.compareTo(startTime) < 0) {
					System.out.print("\nError updating record: Shift end time must be after start time");
					return;
				}
				String updateQuery = "UPDATE Responsibility SET Day=" + day + ", startTime='" + startTime
						+ "', stopTime='" + stopTime + "' WHERE EmployeeID=" + id + " and respID=" + respId;
				stmt.executeUpdate(updateQuery);
				// Update Club460 member details
			} else if (recordType.equals("Club460")) {
				System.out.print("\nEnter the CustomerNo of the record you want to update: ");
				// Ask the user for various information to delete the record.
				int id = scanner.nextInt();
				System.out.print("\nEnter updated points: ");
				// Ask the user for various information to delete the record.
				int points = scanner.nextInt();
				scanner.nextLine();
				String updateQuery = "UPDATE Club460 SET Points=" + points + " WHERE CustomerNo=" + id;
				// Ask the user for various information to delete the record.
				stmt.executeUpdate(updateQuery);
				// updates usedAmenity details
			} else if (recordType.equals("usedAmenity")) {
				System.out.print("\nEnter the BookingId of the record you want to update: ");
				// Ask the user for various information to delete the record.
				int id = scanner.nextInt();
				System.out.print("\nEnter updated quantity: ");
				int quantity = scanner.nextInt();
				scanner.nextLine();
				String updateQuery = "UPDATE UsedAmenity SET Quantity=" + quantity + " WHERE BookingID=" + id;
				stmt.executeUpdate(updateQuery);
			} else {
				System.out.print("\nInvalid record type.");
				return;
			}
			System.out.print("\nRecord updated successfully.");
		} catch (Exception e) {
			System.out.print("\nError updating " + recordType);
		}
	}

	/**
	 * Updates the Club 460 membership status of a customer in the Customer table.
	 *
	 * @param dbconn        the Connection object used to create a Statement
	 * @param stmt          the Statement object used to execute the query
	 * @param customerNo    the customer number whose membership status is to be
	 *                      updated
	 * @param setMembership the new membership status (Y/N)
	 */
	public static void updateClub460InCustomerTable(Connection dbconn, Statement stmt, int customerNo,
			String setMembership) {
		try {
			// Update the Club 460 membership status in the Customer table
			String updateQuery = "UPDATE Customer SET Club460='" + setMembership + "' WHERE CustomerNo=" + customerNo;
			stmt.executeUpdate(updateQuery);
		} catch (SQLException e) {
			System.out.print("\nError adding member to Club460");
		}
	}

	/**
	 * Updates the payment information for a customer.
	 *
	 * @param dbconn  the Connection object used to create a Statement
	 * @param stmt    the Statement object used to execute the query
	 * @param answer  the ResultSet object to store the result of the executed query
	 * @param scanner the Scanner object used to read user input
	 * @return a String containing the card type or an empty string if no card type
	 *         is found
	 */
	public static String updatePayment(Connection dbconn, Statement stmt, ResultSet answer, Scanner scanner) {
		// Prompt the user for the CustomerNo to update
		System.out.print("\nEnter the CustomerNo of the record you want to update: ");
		int id = scanner.nextInt();
		scanner.nextLine();

		// Prompt the user for the updated mode of payment and card number
		System.out.print("\nEnter updated mode of payment (CC, Cash, DD, or Cheque): ");
		String mode = scanner.nextLine();
		System.out.print("\nEnter updated card number: ");
		int cardNo = scanner.nextInt();
		scanner.nextLine();

		int discount = 0;
		System.out.print("\nCard last 4 digits: " + cardNo % 10000);
		if (cardNumber.containsKey(cardNo % 10000)) {
			discount = cardDiscount.get(cardNumber.get(cardNo % 10000));
		}

		// Update the payment information in the Payment table
		String updateQuery = "UPDATE Payment SET ModeOfPayment='" + mode + "', CardNo=" + cardNo + ", Discount="
				+ discount + " WHERE CustomerNo=" + id;
		try {
			stmt.executeUpdate(updateQuery);
		} catch (SQLException e) {
			System.out.print("\nError updating payment");
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
	 * 
	 * Retrieves the schedule of all staff members on a specified week from the
	 * database and prints it to the console. The user is prompted to enter the
	 * start date of the week
	 * 
	 * @param dbconn  the database connection
	 * @param stmt    the SQL statement
	 * @param answer  the SQL result set
	 * @param scanner the scanner for user input
	 */
	private static void getStaffSchedule(Connection dbconn, Statement stmt, ResultSet answer, Scanner scanner) {
		Date inputDate = null;
//		String formattedDate = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		System.out.print("\nPlease enter when the week starts (yyyy-MM-dd): ");
		String dateString = scanner.nextLine();

		try {
			inputDate = dateFormat.parse(dateString);
//			formattedDate = dateFormat.format(inputDate);
		} catch (ParseException e) {
			System.out.print("\nINPUT EXCEPTION: There was some error parsing the date.\n");
			System.exit(-1);
		}

		// Now we start writing the queries

		String query = "SELECT Employee.EmployeeID, EmpName, startime, stoptime, Day, JobTitle FROM Employee JOIN "
				+ "Responsibility ON Employee.EmployeeID = Responsibility.EmployeeID " + "ORDER BY Day";
		try {
			answer = stmt.executeQuery(query);
			if (answer == null) {
				System.out.print("\nNo Employees have any responsibilities!\n");
			} else {
				Calendar calendar = Calendar.getInstance();
				while (answer.next()) {
					System.out.print("\nEMPLOYEE ID: " + answer.getInt(1));
					System.out.print("\nNAME: " + answer.getString(2));
					calendar.setTime(inputDate);
					calendar.add(Calendar.DATE, answer.getInt(5));
					System.out.print("\nDATE: " + dateFormat.format(calendar.getTime()));
					System.out.print("\nSTART TIME: " + answer.getString(3));
					System.out.print("\nSTOP TIME: " + answer.getString(4));
					System.out.print("\nJOB TITLE: " + answer.getString(6) + "\n");
				}
			}
		} catch (SQLException e) {
			System.err.print("\n*** SQLException:  " + "Could not fetch results for query 3");
			System.err.print("\n\tMessage:   " + e.getMessage());
			System.err.print("\n\tSQLState:  " + e.getSQLState());
			System.err.print("\n\tErrorCode: " + e.getErrorCode());
			System.exit(-1);
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
			System.out.print("\nEnter start date (yyyy-MM-dd): ");
			String startDateString = scanner.nextLine();
			Date startDate = dateFormat.parse(startDateString);

			System.out.print("\nEnter end date (yyyy-MM-dd): ");
			String endDateString = scanner.nextLine();
			Date endDate = dateFormat.parse(endDateString);

			// Format the start and end dates
			String formattedStartDate = dateFormat.format(startDate);
			String formattedEndDate = dateFormat.format(endDate);

			// Construct the query to calculate average ratings for amenities in the given
			// date range
			String query = "SELECT AmenityID, AVG(Rating) AS AvgRating, AmenityName FROM Rating JOIN Amenity "
					+ "ON Rating.AmenityID = Amenity.AmenityID WHERE RatingDate BETWEEN TO_DATE('" + formattedStartDate
					+ "', 'yyyy-MM-dd') AND TO_DATE('" + formattedEndDate
					+ "', 'yyyy-MM-dd') GROUP BY Rating.AmenityID ORDER BY AvgRating DESC";

			// Execute the query
			answer = stmt.executeQuery(query);

			// Print the results
			System.out.print("\nAmenity ID\tName\tAverage Rating");
			while (answer.next()) {
				int amenityID = answer.getInt("AmenityID");
				double avgRating = answer.getDouble("AvgRating");
				String name = answer.getString(3);
				System.out.println(amenityID + "\t\t" + name + "\t\t" + avgRating);
			}
		} catch (Exception e) {
			System.out.print("\nError printing average ratings");
		}
	}

	/**
	 * Retrieves and prints current customers based on the specified date range and
	 * categorized by general customers, college students, Club 460 members, and
	 * college students who are also Club 460 members. The method takes in
	 * Connection and ResultSet objects to execute the query and store the results,
	 * and a Scanner to read user inputs.
	 *
	 * @param dbconn  the Connection object used to create a PreparedStatement
	 * @param answer  the ResultSet object used to store the query results
	 * @param scanner the Scanner object used to read user inputs for date range
	 */
	private static void getCurrentCustomers(Connection dbconn, ResultSet answer, Scanner scanner) {
		Date inputDate = null;
		String formattedDate = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		System.out.print("\nPlease enter the date you want to check for (YYYY-MM-DD): ");
		String dateString = scanner.nextLine();

		try {
			inputDate = dateFormat.parse(dateString);
			formattedDate = dateFormat.format(inputDate);
		} catch (ParseException e) {
			System.out.print("\nINPUT EXCEPTION: There was some error parsing the date.\n");
			System.exit(-1);
		}

		// Construct the query for retrieving current customers
		String query = "SELECT Name, RoomNo from Booking JOIN Customer ON Booking.CustomerNo = Customer.CustomerNo "
				+ "WHERE dateFrom <= TO_DATE('" + formattedDate + "', 'yyyy-MM-dd') AND " + "dateTo >= TO_DATE('"
				+ formattedDate + "', 'yyyy-MM-dd') AND Student = ? AND " + "Club460 = ? ORDER BY RoomNo";
		try {
			PreparedStatement pstmt = dbconn.prepareStatement(query);
			System.out.print("\nHere's the result of your queries:\n ");
			System.out.print("\n\tGENERAL CUSTOMERS: \n");
			getCustomers(pstmt, answer, "N", "N");
			System.out.print("\n\tCOLLEGE STUDENTS ONLY: \n");
			getCustomers(pstmt, answer, "Y", "N");
			System.out.print("\n\tCLUB 460 MEMBERS ONLY: \n");
			getCustomers(pstmt, answer, "N", "Y");
			System.out.print("\n\tCOLLEGE STUDENTS WHO ARE ALSO IN CLUB 460: \n");
			getCustomers(pstmt, answer, "Y", "Y");
		} catch (SQLException e) {
			System.err.print("\n*** SQLException:  " + "Could not fetch results for query 2");
			System.err.print("\n\tMessage:   " + e.getMessage());
			System.err.print("\n\tSQLState:  " + e.getSQLState());
			System.err.print("\n\tErrorCode: " + e.getErrorCode());
			System.exit(-1);
		}
	}

	/**
	 * Retrieves and prints customers based on the specified student and Club460
	 * membership status. The method takes in PreparedStatement and ResultSet
	 * objects to execute the query and store the results.
	 *
	 * @param pstmt   the PreparedStatement object used to execute the query
	 * @param answer  the ResultSet object used to store the query results
	 * @param student a String representing the student status (Y/N) to filter
	 *                customers by
	 * @param club460 a String representing the Club460 membership status (Y/N) to
	 *                filter customers by
	 * @throws SQLException if there is an issue executing the query or retrieving
	 *                      the results
	 */
	private static void getCustomers(PreparedStatement pstmt, ResultSet answer, String student, String club460)
			throws SQLException {
		// Set the PreparedStatement parameters for student and Club460 status
		pstmt.setString(1, student);
		pstmt.setString(2, club460);
		answer = pstmt.executeQuery();
		boolean printAnything = false;
		;

		// Check if there are any customers matching the provided criteria
		if (answer == null || answer.getFetchSize() == 0) {
			System.out.print("\n\t<!-- No Such Customers Exist --!>\n");
			printAnything = true;
		} else {
			// Print the customer's name and room number
			while (answer.next()) {
				System.out
						.print("\nCustomer Name: " + answer.getString(1) + "\t\t Room Number: " + answer.getString(2));
				System.out.println();
				printAnything = true;
			}
		}
		if (!printAnything) {
			System.out.print("\n\t<!-- No Such Customers Exist --!>\n");
		}
	}

	/**
	 * Calculates and prints the final bill for a customer along with any applicable
	 * discounts.
	 *
	 * @param dbconn the Connection object used to create a Statement
	 * @param stmt   the Statement object used to execute the query
	 * @param answer the ResultSet object to store the result of the executed query
	 */
	public static void finalBill(Connection dbconn, Statement stmt, ResultSet answer) {
		try {
			// Initialize variables
			Boolean isMember = false;

			// Accept user input
			Scanner scanner = new Scanner(System.in);
			System.out.print("\nEnter CustomerNo: ");
			int customerNo = scanner.nextInt();
			scanner.nextLine();
			System.out.print("\nEnter BookingID: ");
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
				System.out.print("\nNo booking found with the given CustomerNo and BookingID.");
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
				System.out.print("\nNo booking found with the given CustomerNo and BookingID.");
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
				System.out.print("\nDo you wanna update method of payment (Y/N)?: ");
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
			Double costRooms = 0.0;
			Integer costAmenities = 0;
			query = "SELECT SUM(Room.Price * (Booking.dateTo - Booking.dateFrom)) AS TotalRoomCost\n"
					+ "FROM Booking, Room\n" + "WHERE Booking.CustomerNo = " + customerNo + "\n"
					+ "  AND Booking.RoomNo = Room.RoomNo";
			answer = stmt.executeQuery(query);
			if (answer.next()) {
				costRooms = answer.getDouble("TotalRoomCost");
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
			// Asks the user if they wanna use points
			System.out.print("\nDo you wanna use points? (Y/N)");
			String usePointsFlag = scanner.nextLine();
			if (usePointsFlag.equals("Y")) {
				// If yes then subtract the points conversion from roomCost
				costRooms = costRooms - points / 10;
				discountString += ", " + points + " points used";
			}
			// Set the discount string to "NA" if no discounts were applied
			if (discountString.equals("")) {
				discountString = "NA";
			}

			// Print the final bill
			System.out.print("\nDiscounts Applied: " + discountString);
			System.out.print("\nTotal Cost: " + ((costAmenities + costRooms) * (100 - discount)) / 100);

			String updateQuery = "UPDATE Club460 SET Points = ? WHERE CustomerNo = ?";
			pstmt = dbconn.prepareStatement(updateQuery);
			pstmt.setDouble(1, (((costAmenities + costRooms) * (100 - discount)) / 100));
			pstmt.setInt(2, customerNo);
			pstmt.executeUpdate();
			if (isMember == true) {
				System.out.println((((costAmenities + costRooms) * (100 - discount)) / 100)
						+ " points added to Club460 membership");
			}
			// Add a rating for the stay

			addRating(dbconn, scanner);

		} catch (SQLException e) {
			System.out.print("\nError printing final bill for customer");
		}

	}

	/**
	 * Adds a new rating for the specified amenity into the Rating table. The user
	 * provides the AmenityID, RatingID, rating value, and rating date.
	 *
	 * @param dbconn  the Connection object representing the connection to the
	 *                database
	 * @param scanner the Scanner object used to read user input
	 */
	public static void addRating(Connection dbconn, Scanner scanner) {
		System.out.println();
		// Continuosly keep asking for if they wanna add a rating.
		System.out.print("\nDo you wanna add rating for an Amenity? (Y/N)");
		String ratingFlag = scanner.nextLine();
		if (ratingFlag.equals("Y")) {

		} else {
			return;
		}
		// Prompt the user for the AmenityID to rate
		System.out.print("\nEnter the AmenityID you want to rate: ");
		int amenityID = scanner.nextInt();
		scanner.nextLine();

		// Prompt the user for the RatingID for this rating
		System.out.print("\nEnter the RatingID for this rating: ");
		int ratingID = scanner.nextInt();
		scanner.nextLine();

		// Prompt the user for the rating value (1-10)
		System.out.print("\nEnter your rating (1-10): ");
		int rating = scanner.nextInt();
		scanner.nextLine();

		// Prompt the user for the rating date (yyyy-MM-dd)
		System.out.print("\nEnter the rating date (yyyy-MM-dd): ");
		String ratingDate = scanner.nextLine();

		// Create an insert query to add the rating to the Rating table
		String insertQuery = "INSERT INTO Rating (AmenityID, RatingID, Rating, RatingDate) VALUES (" + amenityID + ", "
				+ ratingID + ", " + rating + ", TO_DATE('" + ratingDate + "', 'YYYY-MM-DD'))";

		try {
			// Execute the insert query
			Statement stmt = dbconn.createStatement();
			stmt.executeUpdate(insertQuery);
			System.out.print("\nRating added successfully.");
			addRating(dbconn, scanner);
		} catch (SQLException e) {
			System.out.print("\nError adding rating.");
		}
	}

	/**
	 * Prints the names of employees with the specified RespID (responsibility ID).
	 * Retrieves the employee names from the Employee and Responsibility tables by
	 * performing an INNER JOIN operation.
	 *
	 * @param dbconn  the Connection object representing the connection to the
	 *                database
	 * @param stmt    the Statement object used to execute SQL queries
	 * @param scanner the Scanner object used to read user input
	 * @param answer  the ResultSet object containing the results of executed
	 *                queries
	 */
	public static void printEmployeesByRespID(Connection dbconn, Statement stmt, Scanner scanner, ResultSet answer) {
		// Prompt the user for the RespID to search employees by
		System.out.print("\nEnter the job title you want to search employees by: ");
		String respName = scanner.nextLine();
		scanner.nextLine();

		// Create a query to get employee names with the specified RespID
		String query = "SELECT EmpName FROM Employee e INNER JOIN Responsibility r ON e.EmployeeID = r.EmployeeID WHERE r.JobTitle = '"
				+ respName + "' ";

		try {
			// Execute the query and store the result in 'answer'
			stmt = dbconn.createStatement();
			answer = stmt.executeQuery(query);

			// Print the employee names with the specified RespID
			System.out.print("\nEmployees with the job title " + respName + ":");
			int i = 1;
			while (answer.next()) {
				String employeeName = answer.getString("EmpName");
				System.out.println(i + ". " + employeeName);
				i += 1;
			}
		} catch (SQLException e) {
			System.out.print("\nError printing employee names by responsibilites.");
		}
	}

}