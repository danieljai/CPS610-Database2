import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.SQLException;

public class DisplayAll {

	public static void Perform(String[] ConnectionInfo1, String[] ConnectionInfo2) throws SQLException {

		ResultSet rs1 = null;
		Statement SelectStatetment1 = null;

		ResultSet rs2 = null;
		Statement SelectStatetment2 = null;

		Connection Connection1 = DriverManager.getConnection(ConnectionInfo1[0], ConnectionInfo1[1],
				ConnectionInfo1[2]);

		if (Connection1 != null) {
			System.out.println("Connected with connection #1");
			Connection1.setAutoCommit(false);
		} else {
			System.out.println("Connection Failed with connection #1");
		}

		SelectStatetment1 = Connection1.createStatement();
		// Execute the Query
		rs1 = SelectStatetment1.executeQuery("SELECT * FROM TESTJ");
		// Loop through the results
		int Counter1 = 1;
		while (rs1.next()) {
			System.out.println("DB1, Record: " + Counter1 + ", Name: " + rs1.getString("Name"));
			Counter1++;
		}
		// Close all the resources
		rs1.close();
		SelectStatetment1.close();

		Connection Connection2 = DriverManager.getConnection(ConnectionInfo2[0], ConnectionInfo2[1],
				ConnectionInfo2[2]);

		// Test connection 2
		if (Connection2 != null) {
			System.out.println("Connected with connection #2");
			Connection2.setAutoCommit(false);
		} else {
			System.out.println("Connection Failed with connection #2");
		}

		SelectStatetment2 = Connection2.createStatement();
		// Execute the Query
		rs2 = SelectStatetment2.executeQuery("SELECT * FROM TESTJ");
		int Counter2 = 1;
		// Loop through the results
		while (rs2.next()) {
			System.out.println("DB2, Record: " + Counter2 + ", Name: " + rs2.getString("Name"));
			Counter2++;
		}
		// Close all the resources
		rs2.close();
		SelectStatetment2.close();

		if (Connection1 != null)
			Connection1.close();
		if (Connection2 != null)
			Connection2.close();

	}

}
