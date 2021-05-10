import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.io.*;

public class Demo extends Thread {

	int m_myId;

	String[] ConnectionInfo1;
	String[] ConnectionInfo2;

	static int c_nextId = 1;
	static Semaphore semaphore = new Semaphore(1);

	synchronized static int getNextId() {
		return c_nextId++;
	}

	public Demo() {
		super();
		// Assign an ID to the thread
		m_myId = getNextId();
	}

	public Demo(String[] _ConnectionInfo1, String[] _ConnectionInfo2) {
		super();
		// Assign an ID to the thread
		m_myId = getNextId();
		ConnectionInfo1 = _ConnectionInfo1;
		ConnectionInfo2 = _ConnectionInfo2;
	}

	public void run() {

		ResultSet rs1 = null;
		Statement SelectStatetment1 = null;
		ArrayList<String> lastitem1 = new ArrayList<String>();

		ResultSet rs2 = null;
		Statement SelectStatetment2 = null;
		ArrayList<String> lastitem2 = new ArrayList<String>();

		try {

			semaphore.acquire(); // providing mutual exclusion

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
				System.out.println("Thread " + m_myId + ", DB1, Name : " + rs1.getString("Name"));
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
				System.out.println("Thread " + m_myId + ", DB2, Name : " + rs2.getString("Name"));
				lastitem2.add(rs2.getString("Name"));
			}
			// Close all the resources
			rs2.close();
			SelectStatetment2.close();

			String nameToInsert = "test" + m_myId % 5;

			// Create a Statement
			if (!lastitem1.contains(nameToInsert) && !lastitem2.contains(nameToInsert)) {

				String insert = "INSERT into TESTJ VALUES('" + nameToInsert + "')";

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
				} else {
					Connection1.rollback();
					Connection2.rollback();
				}
			}

			if (Connection1 != null)
				Connection1.close();
			if (Connection2 != null)
				Connection2.close();

			System.out.println(
					"Thread " + m_myId + " is finished.\n===================================================\n\n");
		}

		catch (Exception e) {
			System.out.println("Thread " + m_myId + " got Exception: " + e);
			e.printStackTrace();
			return;
		}
		semaphore.release();
	}

	public static void PerformDemo(String[] ConnectionInfo1, String[] ConnectionInfo2, Integer NumberOfThreads) {

		try {

			// Create the threads
			Thread[] threadList = new Thread[NumberOfThreads];

			// spawn threads
			for (int i = 0; i < NumberOfThreads; i++) {
				threadList[i] = new Demo(ConnectionInfo1, ConnectionInfo2);
				threadList[i].start();
			}

			// wait for all threads to end
			for (int i = 0; i < NumberOfThreads; i++) {
				threadList[i].join();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}