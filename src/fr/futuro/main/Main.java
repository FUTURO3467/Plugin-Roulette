package fr.futuro.main;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.futuro.commands.CoinsCommand;
import fr.futuro.commands.RouletteCommand;
import fr.futuro.mysql.DatabaseManager;

public class Main extends JavaPlugin {
	
	public static DatabaseManager manager;
	
	public static final Map<Player,Float> coinsMap = new HashMap<>();
	
	@Override
	public void onEnable() {
		getCommand("coins").setExecutor(new RouletteCommand());
		getCommand("roulette").setExecutor(new CoinsCommand());
		getServer().getPluginManager().registerEvents(new CoinsCommand(), this);
		manager = new DatabaseManager("jdbc:mysql://", "localhost:3306", "roulette", "root", "");
		manager.connexion();
	}
	
	@Override
	public void onDisable() {
		manager.deconnexion();
		coinsMap.clear();
	}
}
