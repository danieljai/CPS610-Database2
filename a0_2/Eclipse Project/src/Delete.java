import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Delete {

	public static void Perform(String[] ConnectionInfo1, String[] ConnectionInfo2) throws SQLException {

		ResultSet rs1 = null;
		Statement SelectStatetment1 = null;
		ArrayList<String> Names = new ArrayList<String>();

		try {

			Connection Connection1 = DriverManager.getConnection(ConnectionInfo1[0], ConnectionInfo1[1],
					ConnectionInfo1[2]);

			SelectStatetment1 = Connection1.createStatement();

			// Execute the Query
			rs1 = SelectStatetment1.executeQuery("SELECT * FROM TESTJ");

			// Loop through the results
			int Counter = 1;
			while (rs1.next()) {
				Names.add(rs1.getString("Name"));
				System.out.println(Counter + ": " + rs1.getString("Name"));
				Counter++;
			}
			// Close all the resources
			rs1.close();
			SelectStatetment1.close();
			
			if (Connection1 != null)
				Connection1.close();

			int ItemToDelete = 0;

			System.out.println("Please enter the item number to delete: ");

			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				ItemToDelete = Integer.parseInt(br.readLine());
			} catch (IOException ioe) {
				System.out.println(ioe);
				return;
			}

			while (!(ItemToDelete >= 1 && ItemToDelete <= Names.size())) {
				System.out.println("Please enter a valid item number to delete: ");

				try {
					BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
					ItemToDelete = Integer.parseInt(br.readLine());
				} catch (IOException ioe) {
					System.out.println(ioe);
					return;
				}
			}

			String NameToDelete = Names.get(ItemToDelete - 1);

			DeleteFromDBs(ConnectionInfo1, ConnectionInfo2, NameToDelete);

		} catch (Exception e) {
			System.out.println("Deleting the name failed; got Exception: " + e);
			e.printStackTrace();
			return;
		}

	}
	
	public static void DeleteFromDBs(String[] ConnectionInfo1, String[] ConnectionInfo2, String NameToDelete)
	{
		
		try {

			Connection Connection1 = DriverManager.getConnection(ConnectionInfo1[0], ConnectionInfo1[1],
					ConnectionInfo1[2]);			
			
			Connection Connection2 = DriverManager.getConnection(ConnectionInfo2[0], ConnectionInfo2[1],
					ConnectionInfo2[2]);

			Connection1.setAutoCommit(false);

			Connection2.setAutoCommit(false);

			String delete = "DELETE TESTJ WHERE NAME = '" + NameToDelete + "'";

			boolean db1_success = false;
			boolean db2_success = false;

			try (Statement UpdateStatetment1 = Connection1.createStatement()) {
				UpdateStatetment1.executeQuery(delete);
				db1_success = true;
			} catch (SQLException e) {
				System.out.println(e.getErrorCode());
			}

			try (Statement UpdateStatetment2 = Connection2.createStatement()) {
				UpdateStatetment2.executeQuery(delete);
				db2_success = true;
			} catch (SQLException e) {
				System.out.println(e.getErrorCode());
			}

			if (db1_success && db2_success) {
				Connection1.commit();
				Connection2.commit();
				System.out.println("SITE1 Delete Transaction Completed");
				System.out.println("SITE2 Delete Transaction Completed");
			} else {
				Connection1.rollback();
				Connection2.rollback();
			}

			if (Connection1 != null)
				Connection1.close();
			if (Connection2 != null)
				Connection2.close();

		} catch (Exception e) {
			System.out.println("Updating the name failed; got Exception: " + e);
			e.printStackTrace();
			return;
		}
		
	}

}
