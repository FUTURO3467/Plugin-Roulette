package fr.futuro.mysql;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class DatabaseManager{
	
	private String urlBase;
	private String host;
	private String database;
	private String username;
	private String password;
	private static Connection connection;
	

	public DatabaseManager(String urlBase, String host, String database, String username, String password) {
		this.urlBase = urlBase;
		this.host = host;
		this.database = database;
		this.username = username;
		this.password = password;
	}
	public static Connection getConnection() {
		return connection;
	}
	
	public void createAccount(UUID uuid) {
		if(!hasAccount(uuid)) {
		try {
			PreparedStatement preparedstatement = (PreparedStatement) connection.prepareStatement("INSERT INTO players (uuid_player, coins) VALUES(?, ?)");
		preparedstatement.setString(1, uuid.toString());
		preparedstatement.setFloat(2, 1000.0f);
		preparedstatement.execute();
		preparedstatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
	public boolean hasAccount(UUID uuid) {
		try {
			PreparedStatement prepareStatement = (PreparedStatement) connection.prepareStatement("SELECT coins FROM players WHERE uuid_player = ?");
			prepareStatement.setString(1, uuid.toString());
			ResultSet rs = prepareStatement.executeQuery();
			while(rs.next()){
				return true;
			}
				return false;
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void connexion() {
		if(!isOnline()) {
			try {
				connection = (Connection) DriverManager.getConnection(this.urlBase + this.host + "/" + this.database, this.username, this.password);
				System.out.println("§a[DataBaseManager] Succeffuly connected");
			} catch (SQLException e) {
               e.printStackTrace();
			}
			
		}
	}
	
	public void deconnexion() {
		if(isOnline()) {
			try {
				connection.close();
				System.out.println("§a[DataBaseManager] Succeffuly disconnected");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isOnline() {
		try {
			if ((connection == null) || (connection.isClosed())) {
				return false;
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
}
