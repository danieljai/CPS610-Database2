import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Add {

	public static void Perform(String[] ConnectionInfo1, String[] ConnectionInfo2)
			throws SQLException {
		
		String NameToAdd = null;
		
		System.out.println("Please the name you want to add: ");
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			NameToAdd = br.readLine().toString();
		} catch (IOException ioe) {
			System.out.println(ioe);
			return;
		}

		ResultSet rs1 = null;
		Statement SelectStatetment1 = null;
		ArrayList<String> lastitem1 = new ArrayList<String>();

		ResultSet rs2 = null;
		Statement SelectStatetment2 = null;
		ArrayList<String> lastitem2 = new ArrayList<String>();

		try {

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
			while (rs1.next()) {
				lastitem1.add(rs1.getString("Name"));
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

			// Loop through the results
			while (rs2.next()) {
				lastitem2.add(rs2.getString("Name"));
			}
			// Close all the resources
			rs2.close();
			SelectStatetment2.close();

			// Create a Statement
			if (!lastitem1.contains(NameToAdd) && !lastitem2.contains(NameToAdd)) {

				String insert = "INSERT into TESTJ VALUES('" + NameToAdd + "')";

				boolean db1_success = false;
				boolean db2_success = false;

				try (Statement InsertStatetment1 = Connection1.createStatement()) {
					InsertStatetment1.executeQuery(insert);
					db1_success = true;
				} catch (SQLException e) {
					System.out.println(e.getErrorCode());
				}

				try (Statement InsertStatetment2 = Connection2.createStatement()) {
					InsertStatetment2.executeQuery(insert);
					db2_success = true;
				} catch (SQLException e) {
					System.out.println(e.getErrorCode());
				}

				if (db1_success && db2_success) {
					Connection1.commit();
					Connection2.commit();
					System.out.println("SITE1 Transaction Completed");
					System.out.println("SITE2 Transaction Completed");
					System.out.println("The new name \"" + NameToAdd + "\" was successfully added to both sites");
				} else {
					Connection1.rollback();
					Connection2.rollback();
				}
			}
			else
			{
				System.out.println("The new name \"" + NameToAdd + "\" already exists");
			}

			if (Connection1 != null)
				Connection1.close();
			if (Connection2 != null)
				Connection2.close();

		} catch (Exception e) {
			System.out.println("Adding new name failed; got Exception: " + e);
			e.printStackTrace();
			return;
		}

	}

}
