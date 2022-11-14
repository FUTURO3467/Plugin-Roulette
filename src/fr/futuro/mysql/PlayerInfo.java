package fr.futuro.mysql;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.OfflinePlayer;

public class PlayerInfo {
	private static Map <OfflinePlayer, PlayerInfo> playerinfo = new HashMap<OfflinePlayer, PlayerInfo>();
	private OfflinePlayer player;
	private PlayersData playerData;
	
	public PlayerInfo(OfflinePlayer t) {
		this.player = t;
		this.playerData = new PlayersData(t.getUniqueId());
		playerinfo.put(t, this);
	}
	
	public static PlayerInfo getInfosPlayer(OfflinePlayer player) {
		return playerinfo.get(player);
	}

	public OfflinePlayer getPlayer() {
		return player;
	}
	
	public PlayersData getPlayerData() {
		return playerData;
	}
	public float getCoinsNumber() {
		return playerData.getcoins();
	}
	public void setCoinsNumber(float amount) {
		playerData.setCoins(amount);
	}
	public void addCoins(float amount) {
		playerData.addCoins(amount);
	}
	public void removeCoins(float amount) {
		playerData.removeCoins(amount);
	}
	
}
