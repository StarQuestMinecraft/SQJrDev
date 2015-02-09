
package us.higashiyama.george.Redeem;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	public static String driverString = "com.mysql.jdbc.Driver";
	public static String hostname = "192.99.20.8";
	public static String port = "3306";
	public static String db_name = "minecraft";
	public static String username = "minecraft";
	public static String password = "R3b!rth!ng";
	public static Connection cntx = null;
	public static String dsn = ("jdbc:mysql://" + hostname + ":" + port + "/" + db_name);

	public static void setUp() {

		//@formatting:off
		String Database_table = "CREATE TABLE IF NOT EXISTS Redeem_Prizes (" 
				+ "`name` VARCHAR(32),"
				+ "`rvtokens` LONGTEXT,"
				+ "`money` DOUBLE,"
				+ "`sponges` DOUBLE,"
				+ "PRIMARY KEY(`name`)"
				+ ")";
		getContext();
		//@formatting:on
		try {

			Driver driver = (Driver) Class.forName(driverString).newInstance();
			DriverManager.registerDriver(driver);
		} catch (Exception e) {
			System.out.println("[CCDBs] Driver error: " + e);
		}
		Statement s = null;
		try {
			s = cntx.createStatement();
			s.executeUpdate(Database_table);
			System.out.println("[CCDB] Table check/creation sucessful");
		} catch (SQLException ee) {
			System.out.println("[CCDB] Table Creation Error");
		} finally {
			close(s);
		}
	}

	public static boolean hasKey(String player) {

		if (!getContext())
			System.out.println("something is wrong!");
		PreparedStatement s = null;
		try {

			s = cntx.prepareStatement("SELECT `name` FROM Redeem_Prizes WHERE (`name`) = (?)");
			s.setString(1, player);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				s.close();
				return true;
			}
			s.close();
		} catch (SQLException e) {
			System.out.print("[CCDB] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[CCDB] SQL Error (Unknown)");
			e.printStackTrace();
		} finally {
			close(s);
		}
		return false;
	}

	public static void newPlayerEntry(String player, int tokens, int money, int sponges) {

		if (!getContext())
			System.out.println("something is wrong!");

		PreparedStatement s = null;
		try {

			s = cntx.prepareStatement("INSERT INTO Redeem_Prizes VALUES (?,?,?,?)");
			s.setString(1, player);
			s.setDouble(2, tokens);
			s.setDouble(3, (money));
			s.setDouble(4, (sponges));
			s.execute();
			s.close();
		} catch (SQLException e) {
			System.out.print("[CCDB] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[CCDB] SQL Error (Unknown)");
			e.printStackTrace();
		} finally {
			close(s);
		}

	}

	public static void updatePlayerEntry(final String player, final int tokens, final int money, final int sponges) {

		Runnable task = new Runnable() {

			public void run() {

				try {
					if (!getContext())
						System.out.println("something is wrong!");
					PreparedStatement s = null;
					try {

						s = cntx.prepareStatement("UPDATE redeem_prizes SET `rvtokens` = (`rvtokens`+ ?), `money` = (`money` + ?), `sponges` = (`sponges`+ ?) WHERE `name` = ?");
						s.setString(4, player);
						s.setDouble(1, tokens);
						s.setDouble(2, (money));
						s.setDouble(3, (sponges));
						s.execute();
						s.close();
					} catch (SQLException e) {
						System.out.print("[CCDB] SQL Error" + e.getMessage());
					} catch (Exception e) {
						System.out.print("[CCDB] SQL Error (Unknown)");
						e.printStackTrace();
					} finally {
						close(s);
					}
				} catch (Exception ex) {
					System.out.println("Thread Exception: " + ex);
				}

			}
		};
		new Thread(task, "ServiceThread").start();
	}

	/**
	 * 
	 * @return Double[] (tokens, money, sponges)
	 * 
	 */
	public static int[] getPrizes(String player) {

		int[] prizes = new int[3];
		if (!getContext())
			System.out.println("something is wrong!");
		PreparedStatement s = null;
		try {

			s = cntx.prepareStatement("SELECT * FROM Redeem_Prizes WHERE (`name`) = (?)");
			s.setString(1, player);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				prizes[0] = (int) rs.getDouble("rvtokens");
				prizes[1] = (int) rs.getDouble("money");
				prizes[2] = (int) rs.getDouble("sponges");
			}
			s.close();
		} catch (SQLException e) {
			System.out.print("[CCDB] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[CCDB] SQL Error (Unknown)");
			e.printStackTrace();
		} finally {
			close(s);
		}
		return prizes;
	}

	public static boolean getContext() {

		try {
			if (cntx == null || cntx.isClosed() || !cntx.isValid(1)) {
				if (cntx != null && !cntx.isClosed()) {
					try {
						cntx.close();
					} catch (SQLException e) {
						System.out.print("Exception caught");
					}
					cntx = null;
				}
				if ((username.equalsIgnoreCase("")) && (password.equalsIgnoreCase(""))) {
					cntx = DriverManager.getConnection(dsn);
				} else
					cntx = DriverManager.getConnection(dsn, username, password);

				if (cntx == null || cntx.isClosed())
					return false;
			}

			return true;
		} catch (SQLException e) {
			System.out.print("Error could not Connect to db " + dsn + ": " + e.getMessage());
		}
		return false;
	}

	private static void close(Statement s) {

		if (s == null) {
			return;
		}
		try {
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
