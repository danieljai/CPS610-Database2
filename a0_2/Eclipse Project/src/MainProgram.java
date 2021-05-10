import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.io.*;

public class MainProgram {

	static String GroupMember1_Name = "Andy Lee";
	static String GroupMember1_StudentNum = "500163559";

	static String GroupMember2_Name = "Sohrab Soltani";
	static String GroupMember2_StudentNum = "500801172";

	static Integer SelectedGroupMember = 1;

	static Integer NumberOfThreads = 10;

	static boolean KeepRunning = true;

	public static String[] GetConnection1() {

		String[] ConnectionInfo1 = new String[3];

		String dbURL1 = null;
		String Username1 = null;
		String Password1 = null;

		if (SelectedGroupMember == 1) {
			dbURL1 = "jdbc:oracle:thin:@192.168.124.3:1521:orclcdb";
			Username1 = "system";
			Password1 = "oracle";
		} else {
			dbURL1 = "jdbc:oracle:thin:@localhost:1521:xe";
			Username1 = "sys as sysdba";
			Password1 = "oracle";
		}

		ConnectionInfo1[0] = dbURL1;
		ConnectionInfo1[1] = Username1;
		ConnectionInfo1[2] = Password1;

		return ConnectionInfo1;
	}

	public static String[] GetConnection2() {

		String[] ConnectionInfo2 = new String[3];

		String dbURL2 = null;
		String Username2 = null;
		String Password2 = null;

		if (SelectedGroupMember == 1) {
			dbURL2 = "jdbc:oracle:thin:@192.168.1.191:1521:orclcdb";
			Username2 = "system";
			Password2 = "oracle";
		} else {
			dbURL2 = "jdbc:oracle:thin:@192.168.25.65:1521:orclcdb";
			Username2 = "system";
			Password2 = "oracle";
//			dbURL2 = "jdbc:oracle:thin:@localhost:15022/ODBPC2.localdomain_PC2";			
//			Username2 = "sys as sysdba";
//			Password2 = "Oradoc_db1";
		}

		ConnectionInfo2[0] = dbURL2;
		ConnectionInfo2[1] = Username2;
		ConnectionInfo2[2] = Password2;

		return ConnectionInfo2;
	}

	public static void PrintInfo() {
		System.out.println("CPS/CCPS 610 - Database Systems II - Winter 2021");
		System.out.println("Assignment 1 - Lab 3");
		System.out.println("Wednesday Section - Group 13");
		System.out.println("Member #1: " + GroupMember1_Name + " (" + GroupMember1_StudentNum + ")");
		System.out.println("Member #2: " + GroupMember2_Name + " (" + GroupMember2_StudentNum + ")");
	}

	public static void PrintMainMenu() {
		System.out.println("----------------------------------------------------------------");
		System.out.println("Please enter an option from the menu:");
		System.out.println("1. Demo (Insert generated names to DB 1 and DB 2)");
		System.out.println("2. Display all names");
		System.out.println("3. Add");
		System.out.println("4. Update");
		System.out.println("5. Delete");
//		System.out.println("6. Check consistency");
		System.out.println("0. Exit program");
		System.out.println("----------------------------------------------------------------");
	}

	public static Integer ReadMainMenuOption() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			return Integer.parseInt(br.readLine());
		} catch (IOException ioe) {
			System.out.println(ioe);
			return 0;
		}
	}

	public static void ProcessMainMenuOption(Integer SelectedMainMenuOption) {

		try {

			String[] ConnectionInfo1 = GetConnection1();
			String[] ConnectionInfo2 = GetConnection2();

			switch (SelectedMainMenuOption) {
			case 1:
				Demo.PerformDemo(ConnectionInfo1, ConnectionInfo2, NumberOfThreads);
				break;
			case 2:
				DisplayAll.Perform(ConnectionInfo1, ConnectionInfo2);
				break;
			case 3:
				Add.Perform(ConnectionInfo1, ConnectionInfo2);
				break;
			case 4:
				Update.Perform(ConnectionInfo1, ConnectionInfo2);
				break;
			case 5:
				Delete.Perform(ConnectionInfo1, ConnectionInfo2);
				break;
			case 0:
				System.out.println("Exiting program ...");
				KeepRunning = false;
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {

		try {

			// Load the JDBC driver //
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			Class.forName("oracle.jdbc.OracleDriver");

			// If NoOfThreads is specified, then read it
			if (args.length > 1) {

				System.out.println("Error: Invalid Syntax. ");
				System.out.println("java JdbcMTSample [NoOfThreads]");
				System.exit(0);

			} else if (args.length == 1)

				NumberOfThreads = Integer.parseInt(args[0]);

			PrintInfo();

			Integer SelectedMainMenuOption = 0;

			while (KeepRunning) {
				PrintMainMenu();
				SelectedMainMenuOption = ReadMainMenuOption();
				ProcessMainMenuOption(SelectedMainMenuOption);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}