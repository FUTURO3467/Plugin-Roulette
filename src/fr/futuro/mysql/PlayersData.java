package fr.futuro.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;

import com.mysql.jdbc.PreparedStatement;

public class PlayersData {
	
	private UUID uuid;
	
	public PlayersData(UUID uuid) {
		this.uuid = uuid;
		
	}
	public void addCoins(float amount) {
		try {
			
			PreparedStatement prepareStatement = (PreparedStatement) DatabaseManager.getConnection().prepareStatement("UPDATE players SET coins = coins + ? WHERE uuid_player = ?");
			prepareStatement.setFloat(1, amount);
			prepareStatement.setString(2, uuid.toString());
			prepareStatement.executeUpdate();
			prepareStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void removeCoins(float amount) {
	try {
			
			PreparedStatement prepareStatement = (PreparedStatement) DatabaseManager.getConnection().prepareStatement("UPDATE players SET coins = coins - ? WHERE uuid_player = ?");
			prepareStatement.setFloat(1, amount);
			prepareStatement.setString(2, uuid.toString());
			prepareStatement.executeUpdate();
			prepareStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public float getcoins() {
		try {
			PreparedStatement presta = (PreparedStatement) DatabaseManager.getConnection().prepareStatement("SELECT coins FROM players WHERE uuid_player = ?");
			presta.setString(1, uuid.toString());
			ResultSet rs = presta.executeQuery();
			float coins = 0;
			
			while(rs.next()) {
				coins = rs.getFloat("coins");
			}
			presta.close();
			
			return coins;
			
		} catch (SQLException e) {
			e.getStackTrace();
		}
		return 0.0F;
	}
}
