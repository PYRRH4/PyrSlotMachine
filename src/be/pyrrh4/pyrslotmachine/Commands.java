package be.pyrrh4.pyrslotmachine;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import be.pyrrh4.core.command.Arguments;
import be.pyrrh4.core.command.Arguments.Performer;
import be.pyrrh4.core.command.CallInfo;
import be.pyrrh4.core.command.Command;
import be.pyrrh4.core.messenger.Messenger;
import be.pyrrh4.core.messenger.Messenger.Level;
import be.pyrrh4.pyrslotmachine.machine.Machine;
import be.pyrrh4.pyrslotmachine.machine.MachineType;

public class Commands {

	public static void registerCommands(Command command) {
		command.addArguments(new Arguments("create [string] [string]", "create [id] [type]", "create a machine", "pyrslotmachine.admin", true, new Performer() {
			@Override
			public void perform(CallInfo call) {
				Player sender = call.getSenderAsPlayer();
				String id = call.getArgAsString(1).toLowerCase();
				if (PyrSlotMachine.instance().getData().getMachine(id) != null) {
					Messenger.send(sender, Level.SEVERE_INFO, "PyrSlotMachine", "A machine with id " + id + " already exists.");
					return;
				}
				String typeS = call.getArgAsString(2).toLowerCase();
				MachineType type = PyrSlotMachine.instance().getMachineType(typeS);
				if (type == null) {
					Messenger.send(sender, Level.SEVERE_INFO, "PyrSlotMachine", "Machine type " + typeS + " doesn't exists in the configuration.");
					return;
				}
				Machine machine = new Machine(id, typeS);
				PyrSlotMachine.instance().getData().addMachine(machine).save();
				Messenger.send(sender, Level.NORMAL_SUCCESS, "PyrSlotMachine", "Machine " + id + " has been created.");
			}
		}));

		command.addArguments(new Arguments("setcase [string] [integer]", "setcase [id] [1-2-3]", "set a case (the block you're pointing at)", "pyrslotmachine.admin", true, new Performer() {
			@Override
			public void perform(CallInfo call) {
				Player sender = call.getSenderAsPlayer();
				String id = call.getArgAsString(1).toLowerCase();
				Machine machine = PyrSlotMachine.instance().getData().getMachine(id);
				if (machine == null) {
					Messenger.send(sender, Level.SEVERE_INFO, "PyrSlotMachine", "Machine with id " + id + " doesn't exists.");
					return;
				}
				Block block = sender.getTargetBlock(null, 5);
				if (block == null || block.getType().equals(Material.AIR)) {
					Messenger.send(sender, Level.SEVERE_INFO, "PyrSlotMachine", "You're not pointing a valid block.");
					return;
				}
				int caseId = call.getArgAsInt(2);
				Location loc = block.getLocation().clone().add(0.5D, 0.1D, 0.5D);
				machine.setCase(caseId, loc);
				PyrSlotMachine.instance().getData().save();
				Messenger.send(sender, Level.NORMAL_SUCCESS, "PyrSlotMachine", "Case " + caseId + " has been defined for machine " + id + ".");
			}
		}));

		command.addArguments(new Arguments("setbutton [string]", "setbutton [id]", "set the button (the block you're pointing at)", "pyrslotmachine.admin", true, new Performer() {
			@Override
			public void perform(CallInfo call) {
				Player sender = call.getSenderAsPlayer();
				String id = call.getArgAsString(1).toLowerCase();
				Machine machine = PyrSlotMachine.instance().getData().getMachine(id);
				if (machine == null) {
					Messenger.send(sender, Level.SEVERE_INFO, "PyrSlotMachine", "Machine with id " + id + " doesn't exists.");
					return;
				}
				Block block = sender.getTargetBlock(null, 5);
				if (block == null || !block.getType().toString().contains("BUTTON")) {
					Messenger.send(sender, Level.SEVERE_INFO, "PyrSlotMachine", "You're not pointing a valid button.");
					return;
				}
				machine.setButton(block.getLocation());
				PyrSlotMachine.instance().getData().save();
				Messenger.send(sender, Level.NORMAL_SUCCESS, "PyrSlotMachine", "Button has been defined for machine " + id + ".");
			}
		}));
	}

}
