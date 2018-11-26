package be.pyrrh4.pyrslotmachine.util;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

import be.pyrrh4.core.gui.ItemData;
import be.pyrrh4.core.material.Mat;

public class MachineUtils {

	public static final ArrayList<ItemData> RANDOM_RESULTS = new ArrayList<ItemData>();

	static {
		RANDOM_RESULTS.add(new ItemData("DEFAULT_ITEM_1", -1, Mat.DIAMOND, 1, null, null));
		RANDOM_RESULTS.add(new ItemData("DEFAULT_ITEM_2", -1, Mat.DIRT, 1, null, null));
		RANDOM_RESULTS.add(new ItemData("DEFAULT_ITEM_3", -1, Mat.IRON_INGOT, 1, null, null));
		RANDOM_RESULTS.add(new ItemData("DEFAULT_ITEM_4", -1, Mat.LEATHER_BOOTS, 1, null, null));
		RANDOM_RESULTS.add(new ItemData("DEFAULT_ITEM_5", -1, Mat.BONE, 1, null, null));
		RANDOM_RESULTS.add(new ItemData("DEFAULT_ITEM_6", -1, Mat.COBBLESTONE, 1, null, null));
		RANDOM_RESULTS.add(new ItemData("DEFAULT_ITEM_7", -1, Mat.BRICK_STAIRS, 1, null, null));
		RANDOM_RESULTS.add(new ItemData("DEFAULT_ITEM_8", -1, Mat.ANVIL, 1, null, null));
		RANDOM_RESULTS.add(new ItemData("DEFAULT_ITEM_9", -1, Mat.APPLE, 1, null, null));
		RANDOM_RESULTS.add(new ItemData("DEFAULT_ITEM_10", -1, Mat.BREAD, 1, null, null));
		RANDOM_RESULTS.add(new ItemData("DEFAULT_ITEM_11", -1, Mat.DEAD_BUSH, 1, null, null));
		RANDOM_RESULTS.add(new ItemData("DEFAULT_ITEM_12", -1, Mat.LADDER, 1, null, null));
		RANDOM_RESULTS.add(new ItemData("DEFAULT_ITEM_13", -1, Mat.REDSTONE, 1, null, null));
		RANDOM_RESULTS.add(new ItemData("DEFAULT_ITEM_14", -1, Mat.FURNACE, 1, null, null));
		RANDOM_RESULTS.add(new ItemData("DEFAULT_ITEM_15", -1, Mat.CHEST, 1, null, null));
		RANDOM_RESULTS.add(new ItemData("DEFAULT_ITEM_16", -1, Mat.QUARTZ, 1, null, null));
		RANDOM_RESULTS.add(new ItemData("DEFAULT_ITEM_17", -1, Mat.EGG, 1, null, null));
	}

	@SuppressWarnings("deprecation")
	public static String describe(ItemStack item) {
		return item.getAmount() + "x " + item.getType().toString().toLowerCase().replace("_", " ") + (item.getData().getData() == (byte) 0 ? "" : ":" + item.getData().getData());
	}

}
