package be.pyrrh4.pyrslotmachine.util;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import be.pyrrh4.core.gui.ItemData;

public class MachineUtils {

	public static final ArrayList<ItemData> RANDOM_RESULTS = new ArrayList<ItemData>();

	static {
		RANDOM_RESULTS.add(ItemData.create(null, -1, -1, Material.DIAMOND, 0, 1, null, null));
		RANDOM_RESULTS.add(ItemData.create(null, -1, -1, Material.DIRT, 0, 1, null, null));
		RANDOM_RESULTS.add(ItemData.create(null, -1, -1, Material.WOOL, 0, 1, null, null));
		RANDOM_RESULTS.add(ItemData.create(null, -1, -1, Material.IRON_INGOT, 0, 1, null, null));
		RANDOM_RESULTS.add(ItemData.create(null, -1, -1, Material.LEATHER_BOOTS, 0, 1, null, null));
		RANDOM_RESULTS.add(ItemData.create(null, -1, -1, Material.BONE, 0, 1, null, null));
		RANDOM_RESULTS.add(ItemData.create(null, -1, -1, Material.COBBLESTONE, 0, 1, null, null));
		RANDOM_RESULTS.add(ItemData.create(null, -1, -1, Material.LOG, 0, 1, null, null));
		RANDOM_RESULTS.add(ItemData.create(null, -1, -1, Material.BRICK_STAIRS, 0, 1, null, null));
		RANDOM_RESULTS.add(ItemData.create(null, -1, -1, Material.ANVIL, 0, 1, null, null));
		RANDOM_RESULTS.add(ItemData.create(null, -1, -1, Material.APPLE, 0, 1, null, null));
		RANDOM_RESULTS.add(ItemData.create(null, -1, -1, Material.BREAD, 0, 1, null, null));
		RANDOM_RESULTS.add(ItemData.create(null, -1, -1, Material.DEAD_BUSH, 0, 1, null, null));
		RANDOM_RESULTS.add(ItemData.create(null, -1, -1, Material.FIREBALL, 0, 1, null, null));
		RANDOM_RESULTS.add(ItemData.create(null, -1, -1, Material.LADDER, 0, 1, null, null));
		RANDOM_RESULTS.add(ItemData.create(null, -1, -1, Material.REDSTONE, 0, 1, null, null));
		RANDOM_RESULTS.add(ItemData.create(null, -1, -1, Material.FURNACE, 0, 1, null, null));
		RANDOM_RESULTS.add(ItemData.create(null, -1, -1, Material.CHEST, 0, 1, null, null));
		RANDOM_RESULTS.add(ItemData.create(null, -1, -1, Material.RECORD_3, 0, 1, null, null));
		RANDOM_RESULTS.add(ItemData.create(null, -1, -1, Material.QUARTZ, 0, 1, null, null));
		RANDOM_RESULTS.add(ItemData.create(null, -1, -1, Material.PORK, 0, 1, null, null));
		RANDOM_RESULTS.add(ItemData.create(null, -1, -1, Material.DIODE, 0, 1, null, null));
		RANDOM_RESULTS.add(ItemData.create(null, -1, -1, Material.SNOW_BALL, 0, 1, null, null));
		RANDOM_RESULTS.add(ItemData.create(null, -1, -1, Material.EGG, 0, 1, null, null));
	}

	public static String describe(ItemStack item) {
		return item.getAmount() + "x " + item.getType().toString().toLowerCase().replace("_", " ") + (item.getData().getData() == (byte) 0 ? "" : ":" + item.getData().getData());
	}

}
