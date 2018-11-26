package be.pyrrh4.pyrslotmachine.commands;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import be.pyrrh4.core.Perm;
import be.pyrrh4.core.command.CommandArgument;
import be.pyrrh4.core.command.CommandCall;
import be.pyrrh4.core.command.Param;
import be.pyrrh4.core.messenger.Locale;
import be.pyrrh4.core.util.Utils;
import be.pyrrh4.pyrslotmachine.PyrSlotMachine;
import be.pyrrh4.pyrslotmachine.machine.Machine;

public class CommandSetbutton extends CommandArgument {

	private static final Param paramMachine = new Param(Utils.asList("machine", "m"), "id", Perm.PYRSLOTMACHINE_ADMIN, true);

	public CommandSetbutton() {
		super(PyrSlotMachine.instance(), Utils.asList("setbutton"), "set the machine button", Perm.PYRSLOTMACHINE_ADMIN, true, paramMachine);
	}

	@Override
	public void perform(CommandCall call) {
		Player sender = call.getSenderAsPlayer();
		Machine machine = paramMachine.get(call, PyrSlotMachine.MACHINE_PARSER);
		if (machine != null) {
			// invalid button
			Block block = sender.getTargetBlock(null, 5);
			if (block == null || !block.getType().toString().contains("BUTTON")) {
				Locale.MSG_PYRSLOTMACHINE_INVALIDBUTTON.getActive().send(sender);
				return;
			}
			// set button
			machine.setButton(block.getLocation());
			PyrSlotMachine.instance().getData().mustSave(true);
			Locale.MSG_PYRSLOTMACHINE_SETBUTTON.getActive().send(sender, "{machine}", machine.getId());
		}
	}

}
