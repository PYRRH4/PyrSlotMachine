package be.pyrrh4.pyrslotmachine.commands;

import org.bukkit.entity.Player;

import be.pyrrh4.core.command.CommandCall;
import be.pyrrh4.core.command.CommandPattern;
import be.pyrrh4.core.messenger.Messenger;
import be.pyrrh4.core.messenger.Messenger.Level;
import be.pyrrh4.pyrslotmachine.PyrSlotMachine;
import be.pyrrh4.pyrslotmachine.machine.Machine;
import be.pyrrh4.pyrslotmachine.machine.MachineType;

public class CommandCreate extends CommandPattern {

	public CommandCreate() {
		super("create [string]%id [string]%type", "create a machine", "pyrslotmachine.admin", true);
	}

	@Override
	public void perform(CommandCall call) {
		Player sender = call.getSenderAsPlayer();
		String id = call.getArgAsString(this, 1).toLowerCase();
		if (PyrSlotMachine.instance().getData().getMachine(id) != null) {
			Messenger.send(sender, Level.SEVERE_INFO, "PyrSlotMachine", "A machine with id " + id + " already exists.");
			return;
		}
		String typeS = call.getArgAsString(this, 2).toLowerCase();
		MachineType type = PyrSlotMachine.instance().getMachineType(typeS);
		if (type == null) {
			Messenger.send(sender, Level.SEVERE_INFO, "PyrSlotMachine", "Machine type " + typeS + " doesn't exists in the configuration.");
			return;
		}
		Machine machine = new Machine(id, typeS);
		PyrSlotMachine.instance().getData().addMachine(machine).save();
		Messenger.send(sender, Level.NORMAL_SUCCESS, "PyrSlotMachine", "Machine " + id + " has been created.");
	}

}
