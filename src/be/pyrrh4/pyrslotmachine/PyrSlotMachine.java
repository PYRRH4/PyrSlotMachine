package be.pyrrh4.pyrslotmachine;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import be.pyrrh4.core.PyrPlugin;
import be.pyrrh4.core.command.CommandRoot;
import be.pyrrh4.core.command.Param;
import be.pyrrh4.core.command.ParamParser;
import be.pyrrh4.core.gui.ItemData;
import be.pyrrh4.core.messenger.Locale;
import be.pyrrh4.core.messenger.Messenger;
import be.pyrrh4.core.messenger.Messenger.Level;
import be.pyrrh4.core.util.Utils;
import be.pyrrh4.pyrslotmachine.commands.CommandCreate;
import be.pyrrh4.pyrslotmachine.commands.CommandSetbutton;
import be.pyrrh4.pyrslotmachine.commands.CommandSetcase;
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

	// static fields
	public static final ParamParser<Machine> MACHINE_PARSER = new ParamParser<Machine>() {
		@Override
		public Machine parse(CommandSender sender, Param parameter, String value) {
			// doesn't exist
			Machine machine = PyrSlotMachine.instance().getData().getMachine(value);
			if (machine == null) {
				Locale.MSG_PYRSLOTMACHINE_INVALIDMACHINEPARAM.getActive().send("{parameter}", parameter.toString(), "{value}", value);
				return null;
			}
			// exists
			return machine;
		}
	};

	public static final ParamParser<MachineType> MACHINETYPE_PARSER = new ParamParser<MachineType>() {
		@Override
		public MachineType parse(CommandSender sender, Param parameter, String value) {
			// doesn't exist
			MachineType machine = PyrSlotMachine.instance().getMachineType(value);
			if (machine == null) {
				Locale.MSG_PYRSLOTMACHINE_INVALIDMACHINETYPEPARAM.getActive().send("{parameter}", parameter.toString(), "{value}", value);
				return null;
			}
			// exists
			return machine;
		}
	};

	// fields
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
	protected void loadStorage() {
		data = Utils.getPluginData(PyrSlotMachineData.class);
	}

	@Override
	protected void saveStorage() {
		data.saveData();
	}

	@Override
	protected void closeUserData() {
	}

	// ----------------------------------------------------------------------
	// Pre enable
	// ----------------------------------------------------------------------

	@Override
	protected boolean preEnable() {
		this.spigotResourceId = 55107;
		return true;
	}

	// ----------------------------------------------------------------------
	// Override : reload
	// ----------------------------------------------------------------------

	@Override
	protected void reloadInner() {
		// load types
		types.clear();
		for (String id : getConfiguration().getKeysForSection("types", false)) {
			double cost = PyrSlotMachine.instance().getConfiguration().getInt("types." + id + ".cost");
			ArrayList<ItemData> prizes = PyrSlotMachine.instance().getConfiguration().getItems("types." + id + ".prizes");
			types.put(id, new MachineType(id, cost, prizes));
		}
	}

	// ----------------------------------------------------------------------
	// Enable
	// ----------------------------------------------------------------------

	@Override
	protected boolean enable() {
		// call reload
		reloadInner();
		// events
		Bukkit.getPluginManager().registerEvents(this, this);
		// commands
		CommandRoot root = new CommandRoot(this, Utils.asList("machine"), null, null, false);
		root.addChild(new CommandCreate());
		root.addChild(new CommandSetbutton());
		root.addChild(new CommandSetcase());
		return true;
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
