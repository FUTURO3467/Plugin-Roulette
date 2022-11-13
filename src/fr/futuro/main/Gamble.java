package fr.futuro.main;

import org.bukkit.inventory.Inventory;

public class Gamble {
	private float mise;
	private Inventory inventory;

	public Gamble(float mise, Inventory inventory) {
		this.mise = mise;
		this.inventory = inventory;
	}
	public Inventory getInventory() {
		return inventory;
	}
	public float getMise() {
		return mise;
	}
}
