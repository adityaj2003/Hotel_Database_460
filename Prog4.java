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
		cardNumber.put(1000, "AMEX PLATINUM");
		cardNumber.put(2000, "CHASE SAPPHIRE");
		cardNumber.put(3000, "DISCOVER STUDENT");
		cardNumber.put(4000, "CITI PRESTIGE");
		cardDiscount.put("AMEX PLATINUM", 5);
		cardDiscount.put("CHASE SAPPHIRE", 3);
		cardDiscount.put("DISCOVER STUDENT",1);
		cardDiscount.put("CITI PRESTIGE", 3);
		
		final String oracleURL =   // Magic lectura -> aloe access spell
                "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";
		
		String username = "adityajadhav";   // Oracle DBMS username
	    String password = "a7683";    // Oracle DBMS password
	    Statement stmt = null;
	    try {

            Class.forName("oracle.jdbc.OracleDriver");//Initialize the driver. 

    } catch (ClassNotFoundException e) {

            System.err.println("*** ClassNotFoundException:  "
                + "Error loading Oracle JDBC driver.  \n"
                + "\tPerhaps the driver is not on the Classpath?");
            System.exit(-1);

    }
	    
	    Connection dbconn = null;
	    ResultSet answer = null;
	    try {
            dbconn = DriverManager.getConnection
                           (oracleURL,username,password); //Connect to the sql database.   
            stmt = dbconn.createStatement();

    } catch (SQLException e) {

            System.err.println("*** SQLException:  "
                + "Could not open JDBC connection.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);

    }  
	    Scanner scanner = new Scanner(System.in);
	    mainMenu(dbconn, stmt, answer, scanner);
	    
	    
	}
	
	public static void mainMenu(Connection dbconn, Statement stmt, ResultSet answer, Scanner scanner) {	
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
		int input =  scanner.nextInt();
		if (input == 1) {
			System.out.println("1. Add Customer Details");
			System.out.println("2. Remove Customer Details");
			System.out.println("3. Update Customer Details");
			int userSelection = scanner.nextInt();
			if (userSelection == 1) {
				addRecord(dbconn, stmt, answer,"Customer",scanner);
			}
			else if (userSelection == 2) {
				deleteRecord(dbconn, stmt, answer, "Customer", scanner);
			}
			else if (userSelection == 3) {
				updateRecord(dbconn, stmt, answer, "Customer", scanner);
			}
			else {
				System.out.println("Invalid Selection");
			}
			mainMenu(dbconn, stmt, answer, scanner);
		}
		else if (input == 2){
			System.out.println("1. Add Booking Details");
			System.out.println("2. Remove Booking Details");
			System.out.println("3. Update Booking Details");
			int userSelection = scanner.nextInt();
			if (userSelection == 1) {
				addRecord(dbconn, stmt, answer,"Booking",scanner);
			}
			else if (userSelection == 2) {
				deleteRecord(dbconn, stmt, answer, "Booking", scanner);
			}
			else if (userSelection == 3) {
				updateRecord(dbconn, stmt, answer, "Booking", scanner);
			}
			else {
				System.out.println("Invalid Selection");
			}
			mainMenu(dbconn, stmt, answer, scanner);
		}
		else if (input == 3){
			System.out.println("1. Add Amenities");
			System.out.println("2. Remove Amenities");
			System.out.println("3. Update Amenities");
			int userSelection = scanner.nextInt();
			if (userSelection == 1) {
				addRecord(dbconn, stmt, answer,"Amenity",scanner);
			}
			else if (userSelection == 2) {
				deleteRecord(dbconn, stmt, answer, "Amenity", scanner);
			}
			else if (userSelection == 3) {
				updateRecord(dbconn, stmt, answer, "Amenity", scanner);
			}
			else {
				System.out.println("Invalid Selection");
			}
			mainMenu(dbconn, stmt, answer, scanner);
		}
		else if (input == 4){
			System.out.println("1. Add Room Information");
			System.out.println("2. Remove Room Information");
			System.out.println("3. Update Room Information");
			int userSelection = scanner.nextInt();
			if (userSelection == 1) {
				addRecord(dbconn, stmt, answer,"RoomDetails",scanner);
			}
			else if (userSelection == 2) {
				deleteRecord(dbconn, stmt, answer, "RoomDetails", scanner);
			}
			else if (userSelection == 3) {
				updateRecord(dbconn, stmt, answer, "RoomDetails", scanner);
			}
			else {
				System.out.println("Invalid Selection");
			}
			mainMenu(dbconn, stmt, answer, scanner);
		}
		else if (input == 5){
		    System.out.println("1. Add UsedAmenities");
		    System.out.println("2. Remove UsedAmenities");
		    System.out.println("3. Update UsedAmenities");
		    int userSelection = scanner.nextInt();
		    
		    if (userSelection == 1) {
				addRecord(dbconn, stmt, answer,"usedAmenity",scanner);
			}
			else if (userSelection == 2) {
				deleteRecord(dbconn, stmt, answer, "usedAmenity", scanner);
			}
			else if (userSelection == 3) {
				updateRecord(dbconn, stmt, answer, "usedAmenity", scanner);
			}
			else {
				System.out.println("Invalid Selection");
			}

		    mainMenu(dbconn, stmt, answer, scanner);
		}
		else if (input == 6){
		    System.out.println("1. Add Club460 Members");
		    System.out.println("2. Remove Club460 Members");
		    System.out.println("3. Update Club460 Members");
		    int userSelection = scanner.nextInt();
		    
		    if (userSelection == 1) {
				addRecord(dbconn, stmt, answer,"Club460",scanner);
			}
			else if (userSelection == 2) {
				deleteRecord(dbconn, stmt, answer,"Club460", scanner);
			}
			else if (userSelection == 3) {
				updateRecord(dbconn, stmt, answer, "Club460", scanner);
			}
			else {
				System.out.println("Invalid Selection");
			}

		    mainMenu(dbconn, stmt, answer, scanner);
		}
		else if (input == 7){
		    System.out.println("1. Add Employee Details");
		    System.out.println("2. Remove Employee Details");
		    System.out.println("3. Update Employee Details");
		    int userSelection = scanner.nextInt();
		    
		    if (userSelection == 1) {
				addRecord(dbconn, stmt, answer,"Employee",scanner);
			}
			else if (userSelection == 2) {
				deleteRecord(dbconn, stmt, answer, "Employee", scanner);
			}
			else if (userSelection == 3) {
				updateRecord(dbconn, stmt, answer, "Employee", scanner);
			}
			else {
				System.out.println("Invalid Selection");
			}

		    mainMenu(dbconn, stmt, answer, scanner);
		}
		else if (input == 8){
		    System.out.println("1. Add Employee Responsibilities");
		    System.out.println("2. Remove Employee Responsibilities");
		    System.out.println("3. Update Employee Responsibilities");
		    int userSelection = scanner.nextInt();
		    
		    if (userSelection == 1) {
				addRecord(dbconn, stmt, answer,"Responsibility",scanner);
			}
			else if (userSelection == 2) {
				deleteRecord(dbconn, stmt, answer, "Responsibility", scanner);
			}
			else if (userSelection == 3) {
				updateRecord(dbconn, stmt, answer, "Responsibility", scanner);
			}
			else {
				System.out.println("Invalid Selection");
			}

		    mainMenu(dbconn, stmt, answer, scanner);
		}
		else if (input == 9) {
			predefinedQueriesMenu(dbconn, stmt, answer, scanner);
		}
		
	}
	
	
	
	
	public static void predefinedQueriesMenu(Connection dbconn, Statement stmt, ResultSet answer, Scanner scanner) {
		System.out.println("1. Print the current bill (total $) for a customer for their stay and all unpaid amenities.");
		System.out.println("2. Customers that are currently staying at the hotel.");
		System.out.println("3. Print the schedule of staff.");
		System.out.println("4. Print the average ratings of different amenities");
		System.out.println("5. Query of choice");
		int userSelection = scanner.nextInt();
		scanner.nextLine();
		if (userSelection == 1) {
			finalBill(dbconn, stmt, answer);
		}
		else if (userSelection == 2) {
			
		}
		else if (userSelection == 3) {
			
		}
		else if (userSelection == 4) {
			printAvgRatingsInRange(dbconn, stmt, answer, scanner);
		}
		else if (userSelection == 5) {
			
		}
		else {
			
		}
		mainMenu(dbconn, stmt, answer, scanner);
	}
	
	
	public static void addRecord(Connection dbconn, Statement stmt, ResultSet answer, String recordType, Scanner scanner) {
		if (recordType.equals("Customer")) {
			try {
			System.out.println("Enter CustomerNo.: ");
			int customerNo = scanner.nextInt();
			scanner.nextLine();
			System.out.println("Name: ");
			String name = scanner.nextLine();
			System.out.println("Address: ");
			String address = scanner.nextLine();
			System.out.println("Is customer a student? (Y/N): ");
			String isAStudent = scanner.nextLine();
			System.out.println("Is customer a Club460 Member? (Y/N): ");
			String isAMember = scanner.nextLine();
			
			
			String insertQuery = "INSERT INTO Customer (CustomerNo, Name, Address, Student, Club460) VALUES (" + customerNo + ",'" + name + "','" + address + "','" + isAStudent + "','" + isAMember + "')";
			stmt.executeUpdate(insertQuery);
			if (isAMember.equals("Y")) {
				addClub460(dbconn, stmt, customerNo);
			}
			}
			
			catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error adding record");
			}
			
			
		}
		else if (recordType.equals("usedAmenity")) {
			try {
			System.out.println("Enter BookingID: ");
			int bookingId = scanner.nextInt();
			boolean bookingExists = false;

			String checkBookingIdQuery = "SELECT COUNT(*) FROM Booking WHERE BookingID = " + bookingId;
			answer = stmt.executeQuery(checkBookingIdQuery);

			if (answer.next()) {
			    int count = answer.getInt(1);
			    if (count > 0) {
			        bookingExists = true;
			    }
			}

			if (!bookingExists) {
			    System.out.println("BookingID " + bookingId + " does not exist in the Booking table.");
			    return;
			}

			System.out.println("Enter AmenityID: ");
			int amenityId = scanner.nextInt();
			boolean amenityExists = false;

			String checkAmenityIdQuery = "SELECT COUNT(*) FROM Amenity WHERE AmenityID = " + amenityId;
			answer = stmt.executeQuery(checkAmenityIdQuery);

			if (answer.next()) {
			    int count = answer.getInt(1);
			    if (count > 0) {
			        amenityExists = true;
			    }
			}

			if (!amenityExists) {
			    System.out.println("AmenityID " + amenityId + " does not exist in the Amenity table.");
			    addRecord(dbconn, stmt, answer, "Amenity", scanner);
			    return;
			}

			System.out.println("Enter Quantity: ");
			int quantity = scanner.nextInt();

			String insertQuery = "INSERT INTO UsedAmenity (BookingID, AmenityID, Quantity) VALUES (" + bookingId + "," + amenityId + "," + quantity + ")";
			stmt.executeUpdate(insertQuery);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if (recordType.equals("Booking")) {
			try {
				System.out.println("Enter CustomerNo.: ");
				int customerNo = scanner.nextInt();
				boolean customerExists = false;

				String checkCustomerNoQuery = "SELECT COUNT(*) FROM Customer WHERE CustomerNo = " + customerNo;
				ResultSet resultSet = stmt.executeQuery(checkCustomerNoQuery);

				if (resultSet.next()) {
				    int count = resultSet.getInt(1);
				    if (count > 0) {
				        customerExists = true;
				    }
				}

				if (!customerExists) {
				    System.out.println("CustomerNo " + customerNo + " does not exist in the Customer table.");
				    addRecord(dbconn, stmt, answer, "Customer", scanner);
				    return;
				}

				System.out.println("RoomNo: ");
				int roomNo = scanner.nextInt();
				boolean roomExists = false;

				String checkRoomNoQuery = "SELECT COUNT(*) FROM Room WHERE RoomNo = " + roomNo;
				resultSet = stmt.executeQuery(checkRoomNoQuery);

				if (resultSet.next()) {
				    int count = resultSet.getInt(1);
				    if (count > 0) {
				        roomExists = true;
				    }
				}

				if (!roomExists) {
				    System.out.println("RoomNo " + roomNo + " does not exist in the Room table.");
				    addRecord(dbconn, stmt, answer, "RoomDetails", scanner);
				    return;
				}

				System.out.println("BookingId: ");
				int bookingId = scanner.nextInt();
				scanner.nextLine();
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
				System.out.println("Start Date (yyyy-MM-dd): ");
				String dateFrom = scanner.nextLine();
				dateFrom = dateFormat1.format(dateFormat1.parse(dateFrom));
				System.out.println("End Date (yyyy-MM-dd): ");
				String dateTo = scanner.nextLine();
				dateTo = dateFormat1.format(dateFormat1.parse(dateTo));
				String availabilityQuery = "SELECT COUNT(*) FROM Booking WHERE RoomNo = " + roomNo + " AND BookingID != " + bookingId + " AND (dateFrom BETWEEN TO_DATE('" + dateFrom + "','YYYY-MM-DD') AND TO_DATE('" + dateTo + "','YYYY-MM-DD') OR dateTo BETWEEN TO_DATE('" + dateFrom + "','YYYY-MM-DD') AND TO_DATE('" + dateTo + "','YYYY-MM-DD') OR (TO_DATE('" + dateFrom + "','YYYY-MM-DD') BETWEEN dateFrom AND dateTo) OR (TO_DATE('" + dateTo + "','YYYY-MM-DD') BETWEEN dateFrom AND dateTo))";

				resultSet = stmt.executeQuery(availabilityQuery);
				int bookedCount = 0;
				if (resultSet.next()) {
				    bookedCount = resultSet.getInt(1);
				}

				if (bookedCount > 0) {
				    System.out.println("RoomNo " + roomNo + " is already booked between " + dateFrom + " and " + dateTo + ".");
				    return;
				}
				String insertQuery = "INSERT INTO Booking (CustomerNo, RoomNo, BookingID, dateFrom, dateTo) VALUES (" + customerNo + "," + roomNo + "," + bookingId + ", TO_DATE('" + dateFrom + "','YYYY-MM-DD'), TO_DATE('" + dateTo + "','YYYY-MM-DD'))";
				stmt.executeUpdate(insertQuery);
			}
			catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error adding record");
			}
			
			
		}
		
		else if (recordType.equals("Amenity")) {
			try {
			System.out.println("Enter AmenityID: ");
			int amenityId = scanner.nextInt();
			System.out.println("Enter price of amenity: ");
			int price = scanner.nextInt();
			String insertQuery = "INSERT INTO Amenity VALUES ("+amenityId+","+price+")";
			stmt.executeUpdate(insertQuery);
			}
			catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error adding record");
			}
			
		}
		
		else if (recordType.equals("RoomDetails")) {
			try {
				System.out.println("Enter RoomID: ");
				int roomId = scanner.nextInt();
				System.out.println("Enter price of Room:");
				int price = scanner.nextInt();
				String insertQuery = "INSERT INTO Room VALUES ("+roomId+","+price+")";
				stmt.executeUpdate(insertQuery);
			}
			catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error adding record");
			}
		}
		else if (recordType.equals("Employee")) {
	        try {
	            System.out.println("Enter Employee ID: ");
	            int employeeID = scanner.nextInt();
	            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
	            scanner.nextLine();
	            System.out.println("Enter Employee Name: ");
	            String employeeName = scanner.nextLine();
	            System.out.println("Enter Employee Date of Birth (yyyy-MM-dd): ");
	            String dob = scanner.nextLine();
	            dob = dateFormat1.format(dateFormat1.parse(dob));
	            String insertQuery = "INSERT INTO Employee (EmployeeID, EmpName, DOB) VALUES (" + employeeID + ",'" + employeeName + "',TO_DATE('" + dob + "','YYYY-MM-DD'))";
	            stmt.executeUpdate(insertQuery);
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	System.out.println("Error adding record");
	        }
	    } else if (recordType.equals("Responsibility")) {
	        try {
	            System.out.println("Enter Employee ID: ");
	            int employeeID = scanner.nextInt();
	            System.out.println("Enter Responsibility ID: ");
	            int responsibilityID = scanner.nextInt();
	            System.out.println("Enter Day (0 for Sunday, 1 for Monday, etc.): ");
	            int day = scanner.nextInt();
	            scanner.nextLine();
	            System.out.println("Enter Start Time (HH:MM): ");
	            String startTime = scanner.nextLine();
	            System.out.println("Enter Stop Time (HH:MM): ");
	            String stopTime = scanner.nextLine();
	            String insertQuery = "INSERT INTO Responsibility (EmployeeID, RespID, Day, startTime, stopTime) VALUES (" + employeeID + "," + responsibilityID + "," + day + ",'" + startTime + "','" + stopTime + "')";
	            stmt.executeUpdate(insertQuery);
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	System.out.println("Error adding record");
	        }
	    } else if (recordType.equals("Club460")) {
	        try {
	            System.out.println("Enter CustomerNo.:");
	            int customerNo = scanner.nextInt();
	            System.out.println("\nEnter Points:");
	            int points = scanner.nextInt();
	            String insertQuery = "INSERT INTO Club460 (CustomerNo, Points) VALUES (" + customerNo + "," + points + ")";
	            stmt.executeUpdate(insertQuery);
	            updateClub460InCustomerTable(dbconn, stmt, customerNo, "Y");
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	System.out.println("Error adding record");
	        }
	    }
		
	}
	
	public static void addClub460(Connection dbconn, Statement stmt, int customerNo) {
		try {
            int points = 0;
            String insertQuery = "INSERT INTO Club460 (CustomerNo, Points) VALUES (" + customerNo + "," + points + ")";
            stmt.executeUpdate(insertQuery);
        } catch (Exception e) {
        	e.printStackTrace();
        	System.out.println("Error adding record");
        }
	}
	
	public static void deleteClub460(Connection dbconn, Statement stmt, int customerNo) {
		 try {
	            String deleteQuery = "DELETE FROM Club460 WHERE CustomerNo = " + customerNo;
	            int rowsDeleted = stmt.executeUpdate(deleteQuery);
	            System.out.println(rowsDeleted + " row(s) deleted successfully.");
	        } catch (Exception e) {
	        	e.printStackTrace();
	            System.out.println("Error deleting record");
	        }
	}
	
	
	public static void deleteRecord(Connection dbconn, Statement stmt, ResultSet answer, String recordType, Scanner scanner) {
	    if (recordType.equals("Customer")) {
	        try {
	            System.out.println("Enter CustomerNo.: ");
	            int customerNo = scanner.nextInt();
	            deleteClub460(dbconn, stmt, customerNo);
	            String deleteQueryBooking = "DELETE FROM Booking WHERE CustomerNo = " + customerNo;
	            stmt.executeUpdate(deleteQueryBooking);
	            String deleteQueryPayment = "DELETE FROM Payment WHERE CustomerNo = " + customerNo;
	            stmt.executeUpdate(deleteQueryPayment);
	            String deleteQueryClub = "DELETE FROM Club460 WHERE CustomerNo = " + customerNo;
	            stmt.executeUpdate(deleteQueryClub);
	            String deleteQuery = "DELETE FROM Customer WHERE CustomerNo = " + customerNo;
	            int rowsDeleted = stmt.executeUpdate(deleteQuery);
	            
	            System.out.println("Deleted successfully.");
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	System.out.println("Error deleting record");
	        }
	    }
	    else if (recordType.equals("Booking")) {
	        try {
	            System.out.println("Enter BookingID: ");
	            int bookingId = scanner.nextInt();
	            scanner.nextLine();
	            String deleteQuery = "DELETE FROM Booking WHERE BookingID = " + bookingId;
	            int rowsDeleted = stmt.executeUpdate(deleteQuery);
	            System.out.println("Deleted successfully.");
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	System.out.println("Error deleting record");
	        }
	    }
	    else if (recordType.equals("usedAmenity")) {
	    	try {
	    	    System.out.println("Enter BookingID: ");
	    	    int bookingId = scanner.nextInt();
	    	    System.out.println("Enter AmenityID: ");
	    	    int amenityId = scanner.nextInt();
	    	    scanner.nextLine();
	    	    String deleteQuery = "DELETE FROM UsedAmenity WHERE BookingID = " + bookingId + " AND AmenityID = " + amenityId;
	    	    int rowsDeleted = stmt.executeUpdate(deleteQuery);

	    	    if (rowsDeleted > 0) {
	    	        System.out.println("UsedAmenity record with BookingID " + bookingId + " and AmenityID " + amenityId + " deleted successfully.");
	    	    } else {
	    	        System.out.println("No UsedAmenity record found with BookingID " + bookingId + " and AmenityID " + amenityId + ".");
	    	    }
	    	} catch (Exception e) {
	    	    e.printStackTrace();
	    	    System.out.println("Error deleting record");
	    	}

	    }
	    else if (recordType.equals("Amenity")) {
	        try {
	            System.out.println("Enter AmenityID: ");
	            int amenityId = scanner.nextInt();
	            scanner.nextLine();
	            String deleteQueryUsedAmenity = "DELETE FROM UsedAmenity WHERE AmenityID = " + amenityId;
	            stmt.executeUpdate(deleteQueryUsedAmenity);
	            String deleteQueryRating = "DELETE FROM Rating WHERE AmenityID = " + amenityId;
	            stmt.executeUpdate(deleteQueryRating);
	            String deleteQuery = "DELETE FROM Amenity WHERE AmenityID = " + amenityId;
	            int rowsDeleted = stmt.executeUpdate(deleteQuery);
	            System.out.println("Deleted successfully.");
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	System.out.println("Error deleting record");
	        }
	    }
	    else if (recordType.equals("RoomDetails")) {
	        try {
	            System.out.println("Enter RoomID: ");
	            int roomId = scanner.nextInt();
	            scanner.nextLine();
	            String deleteQueryBooking = "DELETE FROM Booking WHERE RoomNo = " + roomId;
	            stmt.executeUpdate(deleteQueryBooking);
	            String deleteQuery = "DELETE FROM Room WHERE RoomNo = " + roomId;
	            int rowsDeleted = stmt.executeUpdate(deleteQuery);
	            System.out.println("Deleted successfully.");
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	System.out.println("Error deleting record");
	        }
	    }
	    else if (recordType.equals("Employee")) {
	        try {
	            System.out.println("Enter Employee ID: ");
	            int employeeID = scanner.nextInt();
	            scanner.nextLine();
	            String deleteQuery = "DELETE FROM Employee WHERE EmployeeID = " + employeeID;
	            int rowsDeleted = stmt.executeUpdate(deleteQuery);
	            System.out.println(rowsDeleted + " row(s) deleted successfully.");
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	System.out.println("Error deleting record");
	        }
	    } else if (recordType.equals("Responsibility")) {
	        try {
	            System.out.println("Enter Employee ID: ");
	            int employeeID = scanner.nextInt();
	            System.out.println("Enter Responsibility ID: ");
	            int responsibilityID = scanner.nextInt();
	            scanner.nextLine();
	            String deleteQueryResponsibility = "DELETE FROM Responsibility WHERE EmployeeID = " + employeeID;
	            stmt.executeUpdate(deleteQueryResponsibility);
	            String deleteQuery = "DELETE FROM Responsibility WHERE EmployeeID = " + employeeID + " AND RespID = " + responsibilityID;
	            int rowsDeleted = stmt.executeUpdate(deleteQuery);
	            System.out.println(rowsDeleted + " row(s) deleted successfully.");
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	System.out.println("Error deleting record");
	        }
	    } 
	    else if (recordType.equals("Club460")) {
	    	try {
	    	System.out.println("Enter the CustomerNo of the Club460 record you want to delete: ");
	    	int customerNo = scanner.nextInt();
	    	scanner.nextLine();
	    	String deleteQuery = "DELETE FROM Club460 WHERE CustomerNo = " + customerNo;
	    	stmt.executeUpdate(deleteQuery);
	    	updateClub460InCustomerTable(dbconn, stmt, customerNo, "N");
	    	}
	    	catch (SQLException e) {
	    		e.printStackTrace();
	    	}
	    }
	}
	
	
	public static String addPayment(Connection dbconn, Statement stmt,  int customerNo) throws SQLException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter mode of payment (CC/Checking/Cash):");
		String mode = scanner.nextLine();
		if (mode.equals("Checking") || mode.equals("CC")) {
			System.out.println("\nEnter CC or Checking account number:");
			int cardNo = scanner.nextInt();
			int discount = 0;
			if (cardNumber.containsKey(cardNo%10000)) {
				discount = cardDiscount.get(cardNumber.get(cardNo%10000));
			}
			String insertQuery = "INSERT INTO Payment VALUES ("+customerNo+",'"+mode+"',"+cardNo+","+discount+")";
			stmt.executeQuery(insertQuery);
			if (mode == "CC") {
	        	if (cardNumber.containsKey(cardNo%10000)) {
	        		return cardNumber.get(cardNo%10000);
	        	}
	        	else {
	        		return "";
	        	}
	        }
		}
        return "";
	}
	 
	public static void addUsedAmenity(Connection dbconn, Statement stmt, ResultSet answer) {
	    try {
	    	Scanner scanner = new Scanner(System.in);
	        System.out.println("Enter Booking ID: ");
	        int bookingID = scanner.nextInt();
	        System.out.println("\nEnter Amenity ID: ");
	        int amenityID = scanner.nextInt();
	        System.out.println("\nEnter Quantity: ");
	        int quantity = scanner.nextInt();
	        String insertQuery = "INSERT INTO UsedAmenity (BookingID, AmenityID, Quantity) VALUES (" + bookingID + "," + amenityID + "," + quantity + ")";
	        stmt.executeUpdate(insertQuery);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
	
	public static void updateRecord(Connection dbconn, Statement stmt, ResultSet answer, String recordType, Scanner scanner) {
	    try {
	        
	        if (recordType.equals("Customer")) {
	        	System.out.println("Enter the customerNo of the record you want to update: ");
		        int id = scanner.nextInt();
		        scanner.nextLine();
	            System.out.println("\nEnter updated name: ");
	            String name = scanner.nextLine();
	            System.out.println("\nEnter updated address: ");
	            String address = scanner.nextLine();
	            System.out.println("\nIs the customer a student? (Y/N): ");
	            String isAStudent = scanner.nextLine();
	            System.out.println("If you want to update Club460 membership, Add or Delete in Club460 table to update");
	            String updateQuery = "UPDATE Customer SET Name='" + name + "', Address='" + address + "', Student='" + isAStudent + "' WHERE CustomerNo=" + id;
	            stmt.executeUpdate(updateQuery);
	        } 
	        else if (recordType.equals("Booking")) {
	        	System.out.println("Enter the BookingID of the record you want to update: ");
	        	int id = scanner.nextInt();
	        	scanner.nextLine();
	        	System.out.println("Enter updated start date (yyyy-MM-dd): ");
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

	        	System.out.println("Enter updated end date (yyyy-MM-dd): ");
	        	String dateTo = scanner.nextLine();

	        	// Parse and format the dateTo input
	        	
	        	dateTo = dateFormat.format(dateFormat.parse(dateTo));
	        	String availabilityQuery = "SELECT COUNT(*) FROM Booking WHERE RoomNo = " + roomNo + " AND BookingID != " + id + " AND (dateFrom BETWEEN TO_DATE('" + dateFrom + "','YYYY-MM-DD') AND TO_DATE('" + dateTo + "','YYYY-MM-DD') OR dateTo BETWEEN TO_DATE('" + dateFrom + "','YYYY-MM-DD') AND TO_DATE('" + dateTo + "','YYYY-MM-DD') OR (TO_DATE('" + dateFrom + "','YYYY-MM-DD') BETWEEN dateFrom AND dateTo) OR (TO_DATE('" + dateTo + "','YYYY-MM-DD') BETWEEN dateFrom AND dateTo))";

	        	answer = stmt.executeQuery(availabilityQuery);
	        	int bookedCount = 0;
	        	if (answer.next()) {
	        	    bookedCount = answer.getInt(1);
	        	}

	        	if (bookedCount > 0) {
	        	    System.out.println("RoomNo " + roomNo + " is already booked between " + dateFrom + " and " + dateTo + ".");
	        	    return;
	        	}

	        	String updateQuery = "UPDATE Booking SET dateFrom=TO_DATE('" + dateFrom + "', 'YYYY-MM-DD'), dateTo=TO_DATE('" + dateTo + "', 'YYYY-MM-DD') WHERE BookingID=" + id;
	        	stmt.executeUpdate(updateQuery);
	        } 
	        else if (recordType.equals("Amenity")) {
	        	System.out.println("Enter the Amenity ID of the record you want to update: ");
		        int id = scanner.nextInt();
	            System.out.println("Enter updated price: ");
	            double price = scanner.nextDouble();
	            scanner.nextLine();
	            String updateQuery = "UPDATE Amenity SET Price=" + price + " WHERE AmenityID=" + id;
	            stmt.executeUpdate(updateQuery);
	        } 
	        else if (recordType.equals("RoomDetails")) {
	        	System.out.println("Enter the RoomNo of the record you want to update: ");
		        int id = scanner.nextInt();
		        
	            System.out.println("Enter updated price: ");
	            double price = scanner.nextDouble();
	            scanner.nextLine();
	            String updateQuery = "UPDATE Room SET Price=" + price + " WHERE RoomNo=" + id;
	            stmt.executeUpdate(updateQuery);
	        } 
	        
	        else if (recordType.equals("Employee")) {
	        	System.out.println("Enter the Employee ID of the record you want to update: ");
	        	int id = scanner.nextInt();
	        	scanner.nextLine();
	        	System.out.println("Enter updated employee name: ");
	        	String employeeName = scanner.nextLine();
	        	System.out.println("Enter updated date of birth (yyyy-MM-dd): ");
	        	String dob = scanner.nextLine();

	        	// Parse and format the dob input
	        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        	Date parsedDob = dateFormat.parse(dob);
	        	String formattedDob = dateFormat.format(parsedDob);

	        	String updateQuery = "UPDATE Employee SET EmpName='" + employeeName + "', DOB=TO_DATE('" + formattedDob + "', 'YYYY-MM-DD') WHERE EmployeeID=" + id;
	        	stmt.executeUpdate(updateQuery);
	        } 
	        else if (recordType.equals("Responsibility")) {
	        	System.out.println("Enter the Employee ID of the record you want to update: ");
		        int id = scanner.nextInt();
		        System.out.println("Enter the Responsibility ID of the record you want to update: ");
		        int respId = scanner.nextInt();
	            System.out.println("Enter updated day (0 for Sunday, 1 for Monday, etc.): ");
	            int day = scanner.nextInt();
	            scanner.nextLine();
	            System.out.println("\nEnter updated start time (HH:MM): ");
	            String startTime = scanner.nextLine();
	            System.out.println("Enter updated stop time (HH:MM): ");
	            String stopTime = scanner.nextLine();
	            String updateQuery = "UPDATE Responsibility SET Day=" + day + ", startTime='" + startTime + "', stopTime='" + stopTime + "' WHERE EmployeeID=" + id+" and respID="+respId;
	            stmt.executeUpdate(updateQuery);
	        } 
         else if (recordType.equals("Club460")) {
        	 System.out.println("Enter the CustomerNo of the record you want to update: ");
		      int id = scanner.nextInt();
            System.out.println("Enter updated points: ");
            int points = scanner.nextInt();
            scanner.nextLine();
            String updateQuery = "UPDATE Club460 SET Points=" + points + " WHERE CustomerNo=" + id;
            stmt.executeUpdate(updateQuery);
            
        } else if (recordType.equals("usedAmenity")) {
        	System.out.println("Enter the BookingId of the record you want to update: ");
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
        e.printStackTrace();
    }
}

	public static void updateClub460InCustomerTable(Connection dbconn, Statement stmt, int customerNo, String setMembership) {
		try {
		String updateQuery = "UPDATE Customer SET Student='"+setMembership+"' WHERE CustomerNo=" + customerNo;
        stmt.executeUpdate(updateQuery);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	


	public static void printAvgRatingsInRange(Connection dbconn, Statement stmt, ResultSet answer, Scanner scanner) {
		try {
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		    System.out.println("Enter start date (yyyy-MM-dd): ");
		    String startDateString = scanner.nextLine();
		    Date startDate = dateFormat.parse(startDateString);

		    System.out.println("Enter end date (yyyy-MM-dd): ");
		    String endDateString = scanner.nextLine();
		    Date endDate = dateFormat.parse(endDateString);

		    String formattedStartDate = dateFormat.format(startDate);
		    String formattedEndDate = dateFormat.format(endDate);

		    String query = "SELECT AmenityID, AVG(Rating) AS AvgRating FROM Rating WHERE RatingDate BETWEEN TO_DATE('" + formattedStartDate + "', 'yyyy-MM-dd') AND TO_DATE('" + formattedEndDate + "', 'yyyy-MM-dd') GROUP BY AmenityID ORDER BY AvgRating DESC";
		    answer = stmt.executeQuery(query);

		    System.out.println("Amenity ID\tAverage Rating");
		    while (answer.next()) {
		        int amenityID = answer.getInt("AmenityID");
		        double avgRating = answer.getDouble("AvgRating");
		        System.out.println(amenityID + "\t\t" + avgRating);
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	public static String updatePayment(Connection dbconn, Statement stmt, ResultSet answer, Scanner scanner) {
		System.out.println("Enter the CustomerNo of the record you want to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter updated mode of payment (CC, Cash, DD, or Cheque): ");
        String mode = scanner.nextLine();
        System.out.println("Enter updated card number: ");
        int cardNo = scanner.nextInt();
        scanner.nextLine();
        int discount = 0;
		if (cardNumber.containsKey(cardNo%10000)) {
			discount = cardDiscount.get(cardNumber.get(cardNo%10000));
		}
        String updateQuery = "UPDATE Payment SET ModeOfPayment='" + mode + "', CardNo=" + cardNo + ", Discount=" + discount + " WHERE CustomerNo=" + id;
        try {
			stmt.executeUpdate(updateQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (mode == "CC") {
        	if (cardNumber.containsKey(cardNo%10000)) {
        		return cardNumber.get(cardNo%10000);
        	}
        	else {
        		return "";
        	}
        }
        else {
        	return "";
        }
	}
	
	
	
	public static void finalBill(Connection dbconn, Statement stmt, ResultSet answer) {
		try {
		Boolean isMember = false;
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter CustomerNo: ");
		int customerNo = scanner.nextInt();
		scanner.nextLine();
		System.out.println("Enter BookingID: ");
		int bookingId = scanner.nextInt();
		scanner.nextLine(); // Consume the newline character

		String query = "SELECT dateFrom, dateTo FROM Booking WHERE CustomerNo = " + customerNo + " AND BookingID = " + bookingId;
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

			query = "SELECT Club460 FROM Customer WHERE CustomerNo = " + customerNo;
			answer = stmt.executeQuery(query);
			if (answer.next()) {
			    String club460 = answer.getString("Club460");
			    if ("Y".equalsIgnoreCase(club460)) {
			        isMember = true;
			    }
			}

			
			query = "SELECT * FROM Payment WHERE CustomerNo = "+customerNo;
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
				
			}
			else {
				addPayment(dbconn, stmt, customerNo);
			}
			int discountPayment = 0;
			
			query = "SELECT Discount FROM Payment WHERE CustomerNo = " + customerNo;
			answer = stmt.executeQuery(query);

			
			if (answer.next()) {
			    discountPayment = answer.getInt("Discount");
			}
			if (discountPayment != 0) {
				discount += discountPayment;
				discountString += "Card Discount";
			}
			
			System.out.println("Discount Applied: " +discount);
			String customerName = "";
			
			query = "SELECT * FROM Customer WHERE CustomerNo = "+customerNo;
			answer = stmt.executeQuery(query);
			if (answer.next()) {
				customerName = answer.getString("Name");
				if (answer.getString("Student").equals("Y")) {
					discount += 10;
					discountString += ", Student Discount";
					
				}
			}
			Integer costRooms = 0;
			Integer costAmenities = 0;
			query = "SELECT SUM(Room.Price * (Booking.dateTo - Booking.dateFrom)) AS TotalRoomCost\n"
			        + "FROM Booking, Room\n"
			        + "WHERE Booking.CustomerNo = " + customerNo + "\n"
			        + "  AND Booking.RoomNo = Room.RoomNo";
			answer = stmt.executeQuery(query);
			if (answer.next()) {
				costRooms = answer.getInt("TotalRoomCost");
				
			}
			
			query = "SELECT SUM(Amenity.Price * UsedAmenity.Quantity) AS TotalAmenityCost\n"
			        + "FROM Booking, UsedAmenity, Amenity\n"
			        + "WHERE Booking.CustomerNo = " + customerNo + "\n"
			        + "  AND Booking.BookingID = UsedAmenity.BookingID\n"
			        + "  AND UsedAmenity.AmenityID = Amenity.AmenityID";
			answer = stmt.executeQuery(query);
			if (answer.next()) {
				costAmenities = answer.getInt("TotalAmenityCost");
				
			}
			if (isMember == true) {
				costAmenities = 0;
			}
			if (discountString.equals("")) {
				discountString = "NA";
			}
			
			System.out.println("CustomerName\t Total Cost\t Discounts Applied");
			System.out.println(customerName+"\t"+ (costAmenities+costRooms)*(1-(discount/100)) +"\t"+ discountString);
			
			addRating(dbconn, scanner);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void addRating(Connection dbconn, Scanner scanner) {
	    System.out.println("Enter the AmenityID you want to rate: ");
	    int amenityID = scanner.nextInt();
	    scanner.nextLine();

	    System.out.println("Enter the RatingID for this rating: ");
	    int ratingID = scanner.nextInt();
	    scanner.nextLine();

	    System.out.println("Enter your rating (1-10): ");
	    int rating = scanner.nextInt();
	    scanner.nextLine();

	    System.out.println("Enter the rating date (yyyy-MM-dd): ");
	    String ratingDate = scanner.nextLine();

	    String insertQuery = "INSERT INTO Rating (AmenityID, RatingID, Rating, RatingDate) VALUES (" + amenityID + ", " + ratingID + ", " + rating + ", TO_DATE('" + ratingDate + "', 'YYYY-MM-DD'))";
	    
	    try {
	        Statement stmt = dbconn.createStatement();
	        stmt.executeUpdate(insertQuery);
	        System.out.println("Rating added successfully.");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	
	
}