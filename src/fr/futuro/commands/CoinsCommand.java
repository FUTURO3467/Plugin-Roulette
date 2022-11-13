package fr.futuro.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.futuro.enums.RarityTypes;
import fr.futuro.main.Gamble;
import fr.futuro.main.Main;
import fr.futuro.mysql.PlayerInfo;
import fr.futuro.mysql.PlayersData;

public class CoinsCommand implements CommandExecutor, Listener{
	
	private static Map<Player,Gamble> invMap = new HashMap<>();
	
	private final int ROULETTE_SLOT = 22;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length != 1) {
			sender.sendMessage("§eErreur : /roulette <mise | all>");
			return false;
		}
		if(sender instanceof Player) {
			Player player = (Player)sender;
			PlayerInfo data = new PlayerInfo(player);
			float mise = 0.0f;
			
			String miseStr = args[0];
			float coins = data.getCoinsNumber();
			
			if(miseStr.equalsIgnoreCase("all")) {
				mise = coins;
			}else {
				try {
					mise = Float.parseFloat(args[0]);	
				}catch (NumberFormatException | NullPointerException e) {
					player.sendMessage("§4La mise doit etre un nombre");
					return false;
				}
			}
			
			if(mise <= 0) {
				player.sendMessage("§4La mise doit être supérieure à 0");
				return false;
			}
			
			
			if(coins < mise) {
				player.sendMessage("§4Vous n'avez pas assez d'argent pour miser " + mise +" coins, vous avez " + coins +" coins");
				return false;
			}
			
			Inventory inv = Bukkit.createInventory(player, 54, "Roulette");
			
			ItemStack stack = new ItemStack(Material.LIME_STAINED_GLASS_PANE,1);
			ItemMeta glassMeta = stack.getItemMeta();
			glassMeta.setDisplayName(" ");
			stack.setItemMeta(glassMeta);
			
			for(int i = 0; i < 54; i++) {
				inv.setItem(i, stack);
			}
			ItemStack roulette = new ItemStack(Material.COMPASS);
			ItemMeta meta = roulette.getItemMeta();
			meta.setDisplayName("§6Roulette");
			meta.setLore(Arrays.asList("§r§7Vous avez misé " + mise + " coins"));
			roulette.setItemMeta(meta);
			
			inv.setItem(ROULETTE_SLOT, roulette);
			invMap.put(player, new Gamble(mise, inv));
			player.openInventory(inv);
		}
		return false;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player p = (Player)event.getWhoClicked();
		if(!invMap.containsKey(p)) return;
		if(!invMap.get(p).getInventory().equals(event.getClickedInventory()))return;
		event.setCancelled(true);
		if(event.getSlot() != ROULETTE_SLOT)return;
		PlayerInfo data = new PlayerInfo(p);
		
		Random random = new Random();
		
		int result = (random.nextInt(201)-100);
		
		float toAdd = (invMap.get(p).getMise()/100) *result;
		
		if(toAdd >= 0) {
			RarityTypes rarityType = RarityTypes.getAppropriateRarityType(result);
			
			p.sendMessage(rarityType.getMessage() +" §6Vous avez gagné §e" + result +"% §6de votre mise soit §e" + toAdd +" §6coins");	
		}else {
			p.sendMessage("§4Vous avez perdu §e" + Math.abs(result) +"% §4de votre mise soit §e" + toAdd +" §4coins");
		}
		data.addCoins(toAdd);
		p.closeInventory();
	}
	@EventHandler
	public void onConnect(PlayerLoginEvent event) {
		if(!Main.manager.hasAccount(event.getPlayer().getUniqueId())) {
			Main.manager.createAccount(event.getPlayer().getUniqueId());
		}
	}

}
