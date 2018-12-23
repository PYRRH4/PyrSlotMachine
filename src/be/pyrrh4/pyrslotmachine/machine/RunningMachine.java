package be.pyrrh4.pyrslotmachine.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import be.pyrrh4.pyrcore.PyrCore;
import be.pyrrh4.pyrcore.lib.gui.ItemData;
import be.pyrrh4.pyrcore.lib.material.Mat;
import be.pyrrh4.pyrcore.lib.util.Utils;
import be.pyrrh4.pyrcore.lib.versioncompat.particle.ParticleManager;
import be.pyrrh4.pyrcore.lib.versioncompat.sound.Sound;
import be.pyrrh4.pyrslotmachine.PSMLocale;
import be.pyrrh4.pyrslotmachine.PyrSlotMachine;
import be.pyrrh4.pyrslotmachine.data.Machine;
import be.pyrrh4.pyrslotmachine.util.MachineUtils;

public class RunningMachine {

	// fields and constructor
	private OfflinePlayer player;
	private Machine machine;
	private MachineType machineType;
	private List<ItemData> results = new ArrayList<ItemData>();
	private ItemData tend1, tend2, tend3, result;
	private int taskId = -1;
	private Map<Integer, Item> actualItems = new HashMap<Integer, Item>();

	public RunningMachine(OfflinePlayer player, Machine machine, MachineType machineType) {
		this.player = player;
		this.machine = machine;
		this.machineType = machineType;
		// precalculate prize chances
		results.addAll(machineType.getPrizes());
		// precalculate result
		List<ItemData> chances = new ArrayList<ItemData>();
		for (ItemData it : machineType.getPrizes()) {
			for (int i = 0; i < it.getChance(); i++) {
				chances.add(it);
			}
		}
		while (chances.size() < 100) {
			chances.add(null);
		}
		result = Utils.random(chances);
		// open cases
		for (Location loc : machine.getCases()) {
			Mat.AIR.setBlock(loc.getBlock());
		}
		// tend
		tend1 = result == null ? Utils.random(results) : result;
		tend2 = result == null ? Utils.random(results) : result;
		tend3 = result == null ? Utils.random(results) : result;
	}

	// start
	public void start() {
		// take money
		PyrCore.inst().getVaultIntegration().take(player, machineType.getCost());
		// start
		taskId = new BukkitRunnable() {
			private long delay = 2L, currentDelay = 0L, totalDuration = 0L;
			@Override
			public void run() {
				currentDelay += 1L;
				totalDuration += 1L;
				// stop
				if (totalDuration >= 260L) {
					stop(true);
					return;
				}
				// change delay or items
				if (currentDelay >= delay) {
					currentDelay = 0L;
					// change delay
					if (totalDuration >= 220L) delay = 20L;
					else if (totalDuration >= 180L) delay = 15L;
					else if (totalDuration >= 140L) delay = 10L;
					else if (totalDuration >= 100L) delay = 5L;
					else if (totalDuration >= 60L) delay = 3L;
					// set items
					setCase(1, totalDuration >= 180L ? tend1 : Utils.random(results));
					setCase(2, totalDuration >= 210L ? tend2 : Utils.random(results));
					setCase(3, totalDuration >= 240L ? tend3 : Utils.random(results));
					// sound
					if (machineType.getAnimationSound() != null) machineType.getAnimationSound().play(player.getPlayer());
				}
			}
		}.runTaskTimer(PyrSlotMachine.inst(), 0L, 1L).getTaskId();
	}

	private void setCase(int id, ItemData item) {
		Location loc = machine.getCase(id);
		if (loc != null) {
			// remove previous
			if (actualItems.containsKey(id)) {
				actualItems.get(id).remove();
			}
			// add new
			ItemStack itm = item.getItemStack();
			Item it = loc.getWorld().dropItem(loc, itm);
			it.setPickupDelay(Integer.MAX_VALUE);
			it.setVelocity(new Vector(0, 0, 0));
			actualItems.put(id, it);
			// particle effect
			ParticleManager.INSTANCE.send(ParticleManager.Type.CLOUD, it.getLocation(), 0.0F, 1, Utils.asList(loc.getWorld().getPlayers()));
			// sound
			Sound.NOTE_BASS_DRUM.play(loc);
		}
	}

	// stop
	public void stop(boolean succeed) {
		// succeed
		if (succeed && player.isOnline()) {
			Player player = this.player.getPlayer();
			// loose
			if (result == null) {
				PSMLocale.MSG_PYRSLOTMACHINE_LOSE.send(player);
				// play sound
				if (machineType.getLoseSound() != null) machineType.getLoseSound().play(player.getPlayer());
			}
			// win
			else {
				// give item
				result.give(player);
				// message
				PSMLocale.MSG_PYRSLOTMACHINE_WIN.send(player, "{item}", MachineUtils.describe(result.getItemStack()));
				// play sound
				if (machineType.getWinSound() != null) machineType.getWinSound().play(player.getPlayer());
			}
		}
		// fail, so refund
		else {
			PyrCore.inst().getVaultIntegration().add(player, machineType.getCost());
		}
		// clear items
		for (Item item : actualItems.values()) {
			item.remove();
		}
		actualItems.clear();
		// close cases
		for (Location loc : machine.getCases()) {
			Mat.GLASS.setBlock(loc.getBlock());
		}
		// cancel task
		Bukkit.getScheduler().cancelTask(taskId);
		// set not running
		machine.setRunningMachine(null);
	}

}
