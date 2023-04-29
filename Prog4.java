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
				System.out.println("Error adding record");
			}
			
			
		}
		else if (recordType.equals("Booking")) {
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
				System.out.println("Error adding record");
			}
			
			
		}
		
		else if (recordType.equals("Amenity")) {
			try {
			System.out.print("Enter AmenityID:");
			int amenityId = scanner.nextInt();
			System.out.print("\nEnter price of amenity:");
			int price = scanner.nextInt();
			String insertQuery = "INSERT INTO Amenity VALUES ("+amenityId+","+price+")";
			stmt.executeUpdate(insertQuery);
			}
			catch (Exception e) {
				System.out.println("Error adding record");
			}
			
		}
		
		else if (recordType.equals("RoomDetails")) {
			try {
				System.out.print("Enter RoomID:");
				int roomId = scanner.nextInt();
				System.out.print("\nEnter price of Room:");
				int price = scanner.nextInt();
				String insertQuery = "INSERT INTO Room VALUES ("+roomId+","+price+")";
				stmt.executeUpdate(insertQuery);
			}
			catch (Exception e) {
				System.out.println("Error adding record");
			}
		}
		else if (recordType.equals("Employee")) {
	        try {
	            System.out.print("Enter Employee ID: ");
	            int employeeID = scanner.nextInt();
	            System.out.print("Enter Employee Name: ");
	            String employeeName = scanner.next();
	            System.out.print("Enter Employee Date of Birth (yyyy-MM-dd): ");
	            String dob = scanner.next();
	            String insertQuery = "INSERT INTO Employee (EmployeeID, EmpName, DOB) VALUES (" + employeeID + ",'" + employeeName + "','" + dob + "')";
	            stmt.executeUpdate(insertQuery);
	        } catch (Exception e) {
	        	System.out.println("Error adding record");
	        }
	    } else if (recordType.equals("Responsibility")) {
	        try {
	            System.out.print("Enter Employee ID: ");
	            int employeeID = scanner.nextInt();
	            System.out.print("Enter Responsibility ID: ");
	            int responsibilityID = scanner.nextInt();
	            System.out.print("Enter Day (0 for Sunday, 1 for Monday, etc.): ");
	            int day = scanner.nextInt();
	            System.out.print("Enter Start Time (HH:MM): ");
	            String startTime = scanner.next();
	            System.out.print("Enter Stop Time (HH:MM): ");
	            String stopTime = scanner.next();
	            String insertQuery = "INSERT INTO Responsibility (EmployeeID, RespID, Day, startTime, stopTime) VALUES (" + employeeID + "," + responsibilityID + "," + day + ",'" + startTime + "','" + stopTime + "')";
	            stmt.executeUpdate(insertQuery);
	        } catch (Exception e) {
	        	System.out.println("Error adding record");
	        }
	    } else if (recordType.equals("Club460")) {
	        try {
	            System.out.print("Enter CustomerNo.:");
	            int customerNo = scanner.nextInt();
	            System.out.print("\nEnter Points:");
	            int points = scanner.nextInt();
	            String insertQuery = "INSERT INTO Club460 (CustomerNo, Points) VALUES (" + customerNo + "," + points + ")";
	            stmt.executeUpdate(insertQuery);
	        } catch (Exception e) {
	        	System.out.println("Error adding record");
	        }
	    }
		
	}
	
	public static void deleteRecord(Connection dbconn, Statement stmt, ResultSet answer, String recordType, Scanner scanner) {
	    if (recordType.equals("Customer")) {
	        try {
	            System.out.print("Enter CustomerNo.: ");
	            int customerNo = scanner.nextInt();
	            String deleteQuery = "DELETE FROM Customer WHERE CustomerNo = " + customerNo;
	            int rowsDeleted = stmt.executeUpdate(deleteQuery);
	            System.out.println("Deleted successfully.");
	        } catch (Exception e) {
	        	System.out.println("Error deleting record");
	        }
	    }
	    else if (recordType.equals("Booking")) {
	        try {
	            System.out.print("Enter BookingID: ");
	            int bookingId = scanner.nextInt();
	            String deleteQuery = "DELETE FROM Booking WHERE BookingID = " + bookingId;
	            int rowsDeleted = stmt.executeUpdate(deleteQuery);
	            System.out.println("Deleted successfully.");
	        } catch (Exception e) {
	        	System.out.println("Error deleting record");
	        }
	    }
	    else if (recordType.equals("Amenity")) {
	        try {
	            System.out.print("Enter AmenityID: ");
	            int amenityId = scanner.nextInt();
	            String deleteQuery = "DELETE FROM Amenity WHERE AmenityID = " + amenityId;
	            int rowsDeleted = stmt.executeUpdate(deleteQuery);
	            System.out.println("Deleted successfully.");
	        } catch (Exception e) {
	        	System.out.println("Error deleting record");
	        }
	    }
	    else if (recordType.equals("RoomDetails")) {
	        try {
	            System.out.print("Enter RoomID: ");
	            int roomId = scanner.nextInt();
	            String deleteQuery = "DELETE FROM Room WHERE RoomID = " + roomId;
	            int rowsDeleted = stmt.executeUpdate(deleteQuery);
	            System.out.println("Deleted successfully.");
	        } catch (Exception e) {
	        	System.out.println("Error deleting record");
	        }
	    }
	    else if (recordType.equals("Employee")) {
	        try {
	            System.out.print("Enter Employee ID: ");
	            int employeeID = scanner.nextInt();
	            String deleteQuery = "DELETE FROM Employee WHERE EmployeeID = " + employeeID;
	            int rowsDeleted = stmt.executeUpdate(deleteQuery);
	            System.out.println(rowsDeleted + " row(s) deleted successfully.");
	        } catch (Exception e) {
	        	System.out.println("Error deleting record");
	        }
	    } else if (recordType.equals("Responsibility")) {
	        try {
	            System.out.print("Enter Employee ID: ");
	            int employeeID = scanner.nextInt();
	            System.out.print("Enter Responsibility ID: ");
	            int responsibilityID = scanner.nextInt();
	            String deleteQuery = "DELETE FROM Responsibility WHERE EmployeeID = " + employeeID + " AND RespID = " + responsibilityID;
	            int rowsDeleted = stmt.executeUpdate(deleteQuery);
	            System.out.println(rowsDeleted + " row(s) deleted successfully.");
	        } catch (Exception e) {
	        	System.out.println("Error deleting record");
	        }
	    } else if (recordType.equals("Club460")) {
	        try {
	            System.out.print("Enter CustomerNo.:");
	            int customerNo = scanner.nextInt();
	            String deleteQuery = "DELETE FROM Club460 WHERE CustomerNo = " + customerNo;
	            int rowsDeleted = stmt.executeUpdate(deleteQuery);
	            System.out.println(rowsDeleted + " row(s) deleted successfully.");
	        } catch (Exception e) {
	            System.out.println("Error deleting record");
	        }
	    }
	}
	
	
	public static void addPayment(Connection dbconn, Statement stmt,  int customerNo) throws SQLException {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter mode of payment (CC/Checking/Cash):");
		String mode = scanner.next();
		if (mode.equals("Checking") || mode.equals("CC")) {
			System.out.print("\nEnter CC or Checking account number:");
			int cardNo = scanner.nextInt();
			int discount = 0;
			if (cardNumber.containsKey(mode)) {
				discount = cardDiscount.get(cardNumber.get(mode));
			}
			String insertQuery = "INSERT INTO Payment VALUES ("+mode+","+cardNo+","+discount+")";
			stmt.executeQuery(insertQuery);
		}
	}
	 
	public static void addUsedAmenity(Connection dbconn, Statement stmt, ResultSet answer) {
	    try {
	    	Scanner scanner = new Scanner(System.in);
	        System.out.print("Enter Booking ID: ");
	        int bookingID = scanner.nextInt();
	        System.out.print("\nEnter Amenity ID: ");
	        int amenityID = scanner.nextInt();
	        System.out.print("\nEnter Quantity: ");
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
	        	System.out.print("Enter the customerNo of the record you want to update: ");
		        int id = scanner.nextInt();
	            System.out.print("\nEnter updated name: ");
	            String name = scanner.next();
	            System.out.print("\nEnter updated address: ");
	            String address = scanner.next();
	            System.out.print("\nIs the customer a student? (Y/N): ");
	            String isAStudent = scanner.next();
	            String updateQuery = "UPDATE Customer SET Name='" + name + "', Address='" + address + "', Student='" + isAStudent + "' WHERE CustomerNo=" + id;
	            stmt.executeUpdate(updateQuery);
	        } 
	        else if (recordType.equals("Booking")) {
	        	System.out.print("Enter the BookingID of the record you want to update: ");
		        int id = scanner.nextInt();
	            System.out.print("\nEnter updated start date (yyyy-MM-dd): ");
	            String dateFrom = scanner.next();
	            System.out.print("\nEnter updated end date (yyyy-MM-dd): ");
	            String dateTo = scanner.next();
	            String updateQuery = "UPDATE Booking SET dateFrom='" + dateFrom + "', dateTo='" + dateTo + "' WHERE BookingID=" + id;
	            stmt.executeUpdate(updateQuery);
	        } 
	        else if (recordType.equals("Amenity")) {
	        	System.out.print("Enter the Amenity ID of the record you want to update: ");
		        int id = scanner.nextInt();
	            System.out.print("\nEnter updated price: ");
	            double price = scanner.nextDouble();
	            String updateQuery = "UPDATE Amenity SET Price=" + price + " WHERE AmenityID=" + id;
	            stmt.executeUpdate(updateQuery);
	        } 
	        else if (recordType.equals("RoomDetails")) {
	        	System.out.print("Enter the RoomNo of the record you want to update: ");
		        int id = scanner.nextInt();
	            System.out.print("\nEnter updated price: ");
	            double price = scanner.nextDouble();
	            String updateQuery = "UPDATE Room SET Price=" + price + " WHERE RoomNo=" + id;
	            stmt.executeUpdate(updateQuery);
	        } 
	        else if (recordType.equals("Payment")) {
	        	System.out.print("Enter the CustomerNo of the record you want to update: ");
		        int id = scanner.nextInt();
	            System.out.print("\nEnter updated mode of payment (CC, Cash, DD, or Cheque): ");
	            String mode = scanner.next();
	            System.out.print("\nEnter updated card number: ");
	            int cardNo = scanner.nextInt();
	            System.out.print("\nEnter updated discount: ");
	            double discount = scanner.nextDouble();
	            String updateQuery = "UPDATE Payment SET Mode='" + mode + "', CardNo=" + cardNo + ", Discount=" + discount + " WHERE CustomerNo=" + id;
	            stmt.executeUpdate(updateQuery);
	        } 
	        else if (recordType.equals("Employee")) {
	        	System.out.print("Enter the Employee ID of the record you want to update: ");
		        int id = scanner.nextInt();
	            System.out.print("\nEnter updated employee name: ");
	            String employeeName = scanner.next();
	            System.out.print("\nEnter updated date of birth (yyyy-MM-dd): ");
	            String dob = scanner.next();
	            String updateQuery = "UPDATE Employee SET EmpName='" + employeeName + "', DOB='" + dob + "' WHERE EmployeeID=" + id;
	            stmt.executeUpdate(updateQuery);
	        } 
	        else if (recordType.equals("Responsibility")) {
	        	System.out.print("Enter the Employee ID of the record you want to update: ");
		        int id = scanner.nextInt();
		        System.out.print("\nEnter the Responsibility ID of the record you want to update: ");
		        int respId = scanner.nextInt();
	            System.out.print("\nEnter updated day (0 for Sunday, 1 for Monday, etc.): ");
	            int day = scanner.nextInt();
	            System.out.print("\nEnter updated start time (HH:MM): ");
	            String startTime = scanner.next();
	            System.out.print("\nEnter updated stop time (HH:MM): ");
	            String stopTime = scanner.next();
	            String updateQuery = "UPDATE Responsibility SET Day=" + day + ", startTime='" + startTime + "', stopTime='" + stopTime + "' WHERE EmployeeID=" + id+" and respID="+respId;
	            stmt.executeUpdate(updateQuery);
	        } 
         else if (recordType.equals("Club460")) {
        	 System.out.print("Enter the CustomerNo of the record you want to update: ");
		      int id = scanner.nextInt();
            System.out.print("\nEnter updated points: ");
            int points = scanner.nextInt();
            String updateQuery = "UPDATE Club460 SET Points=" + points + " WHERE CustomerNo=" + id;
            stmt.executeUpdate(updateQuery);
        } else if (recordType.equals("\nUsedAmenity")) {
        	System.out.print("Enter the BookingId of the record you want to update: ");
		      int id = scanner.nextInt();
            System.out.print("\nEnter updated quantity: ");
            int quantity = scanner.nextInt();
            String updateQuery = "UPDATE UsedAmenity SET Quantity=" + quantity + " WHERE BookingID=" + id;
            stmt.executeUpdate(updateQuery);
        } else {
            System.out.println("\nInvalid record type.");
            return;
        }
        System.out.println("Record updated successfully.");
    } catch (Exception e) {
        e.printStackTrace();
    }
}


	public static void printAvgRatingsInRange(Connection dbconn, Statement stmt, ResultSet answer, Scanner scanner) {
	    try {
	        System.out.print("Enter start date (yyyy-MM-dd): ");
	        String startDate = scanner.next();
	        System.out.print("Enter end date (yyyy-MM-dd): ");
	        String endDate = scanner.next();
	        String query = "SELECT AmenityID, AVG(Rating) AS AvgRating FROM Rating WHERE RatingDate BETWEEN '" + startDate + "' AND '" + endDate + "' GROUP BY AmenityID ORDER BY AvgRating DESC";
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
			
			query = "SELECT * FROM Payment WHERE CustomerNo = "+customerNo;
			answer = stmt.executeQuery(query);
			int discount = 0;
			String discountString = "";
			if (answer.next()) {
				if (answer.getString("Mode").equals("CC")) {
					Integer ccNum = answer.getInt("CardNo") % 10000;
					if (cardNumber.get(ccNum) != null) {
						discount += answer.getInt("discount");
						discountString += cardDiscount.get(ccNum);
					}
				}
			}
			else {
				addPayment(dbconn, stmt, customerNo);
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