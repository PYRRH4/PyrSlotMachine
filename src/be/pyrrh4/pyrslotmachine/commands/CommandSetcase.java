package be.pyrrh4.pyrslotmachine.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import be.pyrrh4.core.command.CommandCall;
import be.pyrrh4.core.command.CommandPattern;
import be.pyrrh4.core.messenger.Messenger;
import be.pyrrh4.core.messenger.Messenger.Level;
import be.pyrrh4.pyrslotmachine.PyrSlotMachine;
import be.pyrrh4.pyrslotmachine.machine.Machine;

public class CommandSetcase extends CommandPattern {

	public CommandSetcase() {
		super("setcase [string]%id [integer]%1/2/3", "set a case (the block you're pointing at)", "pyrslotmachine.admin", true);
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
		if (block == null || block.getType().equals(Material.AIR)) {
			Messenger.send(sender, Level.SEVERE_INFO, "PyrSlotMachine", "You're not pointing a valid block.");
			return;
		}
		int caseId = call.getArgAsInt(this, 2);
		Location loc = block.getLocation().clone().add(0.5D, 0.1D, 0.5D);
		machine.setCase(caseId, loc);
		PyrSlotMachine.instance().getData().save();
		Messenger.send(sender, Level.NORMAL_SUCCESS, "PyrSlotMachine", "Case " + caseId + " has been defined for machine " + id + ".");
	}

}
