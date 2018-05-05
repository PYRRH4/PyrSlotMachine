package be.pyrrh4.pyrslotmachine.commands;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import be.pyrrh4.core.command.CommandCall;
import be.pyrrh4.core.command.CommandPattern;
import be.pyrrh4.core.messenger.Messenger;
import be.pyrrh4.core.messenger.Messenger.Level;
import be.pyrrh4.pyrslotmachine.PyrSlotMachine;
import be.pyrrh4.pyrslotmachine.machine.Machine;

public class CommandSetbutton extends CommandPattern {

	public CommandSetbutton() {
		super("setbutton [string]%id", "set the button (the block you're pointing at)", "pyrslotmachine.admin", true);
	}

	@Override
	public void perform(CommandCall call) {
		Player sender = call.getSenderAsPlayer();
		String id = call.getArgAsString(this, 1).toLowerCase();
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

}
