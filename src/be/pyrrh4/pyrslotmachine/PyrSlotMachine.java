package be.pyrrh4.pyrslotmachine;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import be.pyrrh4.core.PyrPlugin;
import be.pyrrh4.core.command.Command;
import be.pyrrh4.core.gui.ItemData;
import be.pyrrh4.core.messenger.Messenger;
import be.pyrrh4.core.messenger.Messenger.Level;
import be.pyrrh4.core.util.Utils;
import be.pyrrh4.pyrslotmachine.machine.Machine;
import be.pyrrh4.pyrslotmachine.machine.MachineType;
import be.pyrrh4.pyrslotmachine.machine.RunningMachine;

public class PyrSlotMachine extends PyrPlugin implements Listener {

	// ----------------------------------------------------------------------
	// Instance
	// ----------------------------------------------------------------------

	private static PyrSlotMachine instance;

	public PyrSlotMachine() {
		instance = this;
	}

	public static PyrSlotMachine instance() {
		return instance;
	}

	// ----------------------------------------------------------------------
	// Fields
	// ----------------------------------------------------------------------

	private PyrSlotMachineData data;
	private HashMap<String, MachineType> types = new HashMap<String, MachineType>();

	public MachineType getMachineType(String type) {
		type = type.toLowerCase();
		return types.containsKey(type) ? types.get(type) : null;
	}

	public PyrSlotMachineData getData() {
		return data;
	}

	// ----------------------------------------------------------------------
	// Plugin data
	// ----------------------------------------------------------------------

	@Override
	protected void initStorage() {
		data = Utils.getPluginData(PyrSlotMachineData.class);
	}

	@Override
	protected void savePluginData() {
		data.save();
	}

	@Override
	protected void forceCloseUserPluginData() {}

	// ----------------------------------------------------------------------
	// Init
	// ----------------------------------------------------------------------

	@Override
	protected void init() {
		getSettings().localeDefault("pyrslotmachine_en_US.yml");
		getSettings().localeConfigName("locale");
	}

	// ----------------------------------------------------------------------
	// Override : reload
	// ----------------------------------------------------------------------

	@Override
	protected void innerReload() {
		// load types
		types.clear();
		for (String type : getConfiguration().getKeysForSection("types", false)) {
			type = type.toLowerCase();
			double cost = PyrSlotMachine.instance().getConfiguration().getInt("types." + type + ".cost");
			ArrayList<ItemData> prizes = PyrSlotMachine.instance().getConfiguration().getItems("types." + type + ".prizes", "", "");
			types.put(type, new MachineType(cost, prizes));
		}
	}

	// ----------------------------------------------------------------------
	// Enable
	// ----------------------------------------------------------------------

	@Override
	protected void enable() {
		// call reload
		innerReload();
		// register command
		Commands.registerCommands(new Command(this, "machine", "machine", null));
		// register listener
		Bukkit.getPluginManager().registerEvents(this, this);
	}

	// ----------------------------------------------------------------------
	// Disable
	// ----------------------------------------------------------------------

	@Override
	protected void disable() {}

	// ----------------------------------------------------------------------
	// Listeners
	// ----------------------------------------------------------------------

	@EventHandler
	public void run(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock().getType().toString().contains("BUTTON")) {
			Machine machine = data.getMachineByButton(event.getClickedBlock().getLocation());
			if (machine != null && machine.getRunning() == null) {
				Player player = event.getPlayer();
				MachineType type = getMachineType(machine.getType());
				if (type == null) {
					Messenger.send(player, Level.SEVERE_INFO, "PyrSlotMachine", "Machine type " + machine.getType() + " doesn't exists in the configuration.");
					return;
				}
				if (machine.getCase(1) == null || machine.getCase(2) == null || machine.getCase(3) == null) {
					Messenger.send(player, Level.SEVERE_INFO, "PyrSlotMachine", "Machine " + machine.getId() + " isn't correctly defined (need 3 cases).");
					return;
				}
				RunningMachine running = new RunningMachine(player, machine, type);
				machine.setRunningMachine(running);
				running.start();
			}
		}
	}

}
