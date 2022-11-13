package fr.futuro.enums;

public enum RarityTypes {
	COMMON(0.0f, "§7§lCommun"),
	UNCOMMON(25.0f, "§a§lPeu commun"),
	RARE(50.0f, "§b§lRare"),
	SUPER_RARE(75.0f, "§9§lSuper Rare"),
	LEGENDARY(95.0f, "§6§lLégendaire !"),
	MYTHICAL(97.5f, "§c§lMythique !!");
	
	private float pourcentage;
	
	private String message;
	
	private RarityTypes(float pourcentage,String message) {
		this.pourcentage = pourcentage;
		this.message = message;
	}

	public float getPourcentage() {
		return pourcentage;
	}

	public String getMessage() {
		return message;
	}
	
	public static RarityTypes getAppropriateRarityType(int i) {
		RarityTypes current = null;
		for(RarityTypes type : RarityTypes.values()) {
			if(type.pourcentage > i)return current;
			current = type;
		}
		return null;
	}
}
