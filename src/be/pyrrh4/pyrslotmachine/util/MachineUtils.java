package be.pyrrh4.pyrslotmachine.util;

import org.bukkit.inventory.ItemStack;

public class MachineUtils {

	@SuppressWarnings("deprecation")
	public static String describe(ItemStack item) {
		return item.getAmount() + "x " + item.getType().toString().toLowerCase().replace("_", " ") + (item.getData().getData() == (byte) 0 ? "" : ":" + item.getData().getData());
	}

}
