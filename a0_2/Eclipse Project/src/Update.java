import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Update {

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

			int ItemToUpdate = 0;

			System.out.println("Please enter the item number to update: ");

			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				ItemToUpdate = Integer.parseInt(br.readLine());
			} catch (IOException ioe) {
				System.out.println(ioe);
				return;
			}

			while (!(ItemToUpdate >= 1 && ItemToUpdate <= Names.size())) {
				System.out.println("Please enter a valid item number to update: ");

				try {
					BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
					ItemToUpdate = Integer.parseInt(br.readLine());
				} catch (IOException ioe) {
					System.out.println(ioe);
					return;
				}
			}

			String NameToUpdate = Names.get(ItemToUpdate - 1);

			String NewName = null;

			System.out.println("Please the name you want to replace \"" + NameToUpdate + "\" with: ");

			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				NewName = br.readLine().toString();
			} catch (IOException ioe) {
				System.out.println(ioe);
				return;
			}

			if (Connection1 != null)
				Connection1.close();
			
			UpdateDBs(ConnectionInfo1, ConnectionInfo2, NameToUpdate, NewName);


		} catch (Exception e) {
			System.out.println("Updating the name failed; got Exception: " + e);
			e.printStackTrace();
			return;
		}

	}
	
	public static void UpdateDBs(String[] ConnectionInfo1, String[] ConnectionInfo2, String InitialName, String NewName)
	{
		
		try {

			Connection Connection1 = DriverManager.getConnection(ConnectionInfo1[0], ConnectionInfo1[1],
					ConnectionInfo1[2]);			
			
			Connection Connection2 = DriverManager.getConnection(ConnectionInfo2[0], ConnectionInfo2[1],
					ConnectionInfo2[2]);

			Connection1.setAutoCommit(false);

			Connection2.setAutoCommit(false);

			String update = "UPDATE TESTJ SET NAME = '" + NewName + "' WHERE NAME = '" + InitialName + "'";

			boolean db1_success = false;
			boolean db2_success = false;

			try (Statement UpdateStatetment1 = Connection1.createStatement()) {
				UpdateStatetment1.executeQuery(update);
				db1_success = true;
			} catch (SQLException e) {
				System.out.println(e.getErrorCode());
			}

			try (Statement UpdateStatetment2 = Connection2.createStatement()) {
				UpdateStatetment2.executeQuery(update);
				db2_success = true;
			} catch (SQLException e) {
				System.out.println(e.getErrorCode());
			}

			if (db1_success && db2_success) {
				Connection1.commit();
				Connection2.commit();
				System.out.println("SITE1 Update Transaction Completed");
				System.out.println("SITE2 Update Transaction Completed");
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
