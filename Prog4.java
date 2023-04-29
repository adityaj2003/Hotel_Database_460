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
	
	public static Map<String, Integer> cardDiscount;
	public static Map<Integer, String> cardNumber;
	
	
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
		System.out.println("5. Execute Predefined Queries");
		
	}
	
	public static void predefinedQueriesMenu(Connection dbconn, Statement stmt, ResultSet answer, Scanner scanner) {
		System.out.println("1. Print a current bill (total $) for a customer for their stay and all unpaid amenities.");
		System.out.println("2. Customers that are currently staying at the hotel.");
		System.out.println("3. Print the schedule of staff.");
		System.out.println("4. Print the average ratings of different amenities");
		System.out.println("5. Query of choice");
	}
	
	
	public static void addRecord(Connection dbconn, Statement stmt, ResultSet answer, String recordType, Scanner scanner) {
		if (recordType.equals("Customer")) {
			try {
			System.out.print("Enter CustomerNo.:");
			int customerNo = scanner.nextInt();
			System.out.print("\nName");
			String name = scanner.next();
			System.out.print("\nAddress:");
			String address = scanner.next();
			System.out.print("\nIs customer a student? (Y/N):");
			String isAStudent = scanner.next();
			String insertQuery = "INSERT INTO Customer (CustomerNo, Name, Address, Student) VALUES "+customerNo+ ","+name+","+address+","+isAStudent+")";
			stmt.executeUpdate(insertQuery);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		if (recordType.equals("Booking")) {
			try {
			System.out.print("Enter CustomerNo.:");
			int customerNo = scanner.nextInt();
			System.out.print("\nBookingId:");
			int bookingId = scanner.nextInt();
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			System.out.print("\nStart Date (yyyy-MM-dd):");
			String dateFrom= scanner.next();
			dateFrom = dateFormat1.format(dateFrom);
			System.out.print("\nEnd Date (yyyy-MM-dd):");
			String dateTo = scanner.next();
			dateTo = dateFormat1.format(dateTo);
			String insertQuery = "INSERT INTO Booking (CustomerNo, BookingID, dateFrom, dateTo) VALUES "+customerNo+ ","+bookingId+",TO_DATE("+dateFrom+"),TO_DATE("+dateTo+"))";
			stmt.executeUpdate(insertQuery);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
		if (recordType.equals("Amenity")) {
			try {
			System.out.print("Enter AmenityID:");
			int amenityId = scanner.nextInt();
			System.out.print("\nEnter price of amenity:");
			int price = scanner.nextInt();
			String insertQuery = "INSERT INTO Amenity VALUES ("+amenityId+","+price+")";
			stmt.executeUpdate(insertQuery);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		if (recordType.equals("RoomDetails")) {
			try {
				System.out.print("Enter RoomID:");
				int roomId = scanner.nextInt();
				System.out.print("\nEnter price of Room:");
				int price = scanner.nextInt();
				String insertQuery = "INSERT INTO Room VALUES ("+roomId+","+price+")";
				stmt.executeUpdate(insertQuery);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	public static void finalBill(Connection dbconn, Statement stmt, ResultSet answer) {
		Boolean isMember = false;
		
		
		Scanner scanner = new Scanner(System.in);
        System.out.print("Enter RoomNo: ");
        int roomNo = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter DateFrom (yyyy-MM-dd): ");
        String dateFromString = scanner.nextLine();
        System.out.print("Enter DateTo (yyyy-MM-dd): ");
        String dateToString = scanner.nextLine();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFrom = null;
        Date dateTo = null;
        try {
        	dateFrom = sdf.parse(dateFromString);
			dateTo = sdf.parse(dateToString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		try {
			stmt = dbconn.createStatement();
			String sql = "SELECT CustomerNo FROM Booking WHERE RoomNo = ? AND dateFrom = ? AND dateTo = ?";
            PreparedStatement pstmt = dbconn.prepareStatement(sql);
            pstmt.setInt(1, roomNo);
            pstmt.setDate(2, new java.sql.Date(dateFrom.getTime()));
            pstmt.setDate(3, new java.sql.Date(dateTo.getTime()));
            answer = pstmt.executeQuery();
            int customerNo = answer.getInt("CustomerNo");
			
			String query = "SELECT COUNT(*) AS count FROM Club460 WHERE CustomerNo = "+customerNo;
			answer = stmt.executeQuery(query);
			if (answer.next()) {
				int count = answer.getInt("count");
				if (count > 0) {
					isMember = true;
				}
			}
			
			query = "SELECT COUNT(*) AS count FROM Payment WHERE CustomerNo = "+customerNo;
			answer = stmt.executeQuery(query);
			int discount = 0;
			String discountString = "";
			if (answer.next()) {
				if (answer.getString("Mode").equals("CC")) {
					Integer ccNum = answer.getInt("CardNo") % 10000;
					if (cardNumber.get(ccNum) != null) {
						discount += cardDiscount.get(cardNumber.get(ccNum));
						discountString += cardDiscount.get(ccNum);
					}
				}
			}
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
			query = "SELECT SUM(Room.Price * (DATEDIFF(Booking.dateTo, Booking.dateFrom))) AS TotalRoomCost\n"
					+ "FROM Booking, Room\n"
					+ "WHERE Booking.CustomerNo = "+customerNo+"\n"
					+ "  AND Booking.RoomNo = Room.RoomNo\n"
					+ "  AND Booking.dateFrom >= "+dateFrom+"\n"
					+ "  AND Booking.dateTo <= "+dateTo;
			answer = stmt.executeQuery(query);
			if (answer.next()) {
				costRooms = answer.getInt("TotalRoomCost");
				
			}
			
			query = "SELECT SUM(Amenity.Price * UsedAmenity.Qty) AS TotalAmenityCost\n"
					+ "FROM Booking, UsedAmenity, Amenity\n"
					+ "WHERE Booking.CustomerNo = "+customerNo+"\n"
					+ "  AND Booking.BookingID = UsedAmenity.BookingID\n"
					+ "  AND UsedAmenity.AID = Amenity.AID\n"
					+ "  AND Booking.dateFrom >= "+dateFrom+"\n"
					+ "  AND Booking.dateTo <= "+dateTo+";";
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
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
}