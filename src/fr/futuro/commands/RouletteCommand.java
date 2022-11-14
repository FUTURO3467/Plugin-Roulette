package fr.futuro.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.futuro.main.Main;
import fr.futuro.mysql.PlayerInfo;
import fr.futuro.mysql.PlayersData;

public class RouletteCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			player.sendMessage("§6Vous avez §e" + Main.coinsMap.getOrDefault(player,0.0f) +" §6coins");
		}
		return false;
	}

}
