package be.pyrrh4.pyrslotmachine.commands;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import be.pyrrh4.pyrcore.lib.command.CommandArgument;
import be.pyrrh4.pyrcore.lib.command.CommandCall;
import be.pyrrh4.pyrcore.lib.command.Param;
import be.pyrrh4.pyrcore.lib.util.Utils;
import be.pyrrh4.pyrslotmachine.PSMLocale;
import be.pyrrh4.pyrslotmachine.PSMPerm;
import be.pyrrh4.pyrslotmachine.PyrSlotMachine;
import be.pyrrh4.pyrslotmachine.data.Machine;

public class CommandSetbutton extends CommandArgument {

	private static final Param paramMachine = new Param(Utils.asList("machine", "m"), "id", PSMPerm.PYRSLOTMACHINE_ADMIN, true, true);

	public CommandSetbutton() {
		super(PyrSlotMachine.inst(), Utils.asList("setbutton"), "set the machine button", PSMPerm.PYRSLOTMACHINE_ADMIN, true, paramMachine);
	}

	@Override
	public void perform(CommandCall call) {
		Player sender = call.getSenderAsPlayer();
		Machine machine = paramMachine.get(call, PyrSlotMachine.MACHINE_PARSER);
		if (machine != null) {
			// invalid button
			Block block = sender.getTargetBlock(null, 5);
			if (block == null || !block.getType().toString().contains("BUTTON")) {
				PSMLocale.MSG_PYRSLOTMACHINE_INVALIDBUTTON.send(sender);
				return;
			}
			// set button
			machine.setButton(block.getLocation());
			PSMLocale.MSG_PYRSLOTMACHINE_SETBUTTON.send(sender, "{machine}", machine.getId());
		}
	}

}
