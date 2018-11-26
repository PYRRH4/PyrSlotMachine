package be.pyrrh4.pyrslotmachine.commands;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import be.pyrrh4.core.Perm;
import be.pyrrh4.core.command.CommandArgument;
import be.pyrrh4.core.command.CommandCall;
import be.pyrrh4.core.command.Param;
import be.pyrrh4.core.material.Mat;
import be.pyrrh4.core.messenger.Locale;
import be.pyrrh4.core.util.Utils;
import be.pyrrh4.pyrslotmachine.PyrSlotMachine;
import be.pyrrh4.pyrslotmachine.machine.Machine;

public class CommandSetcase extends CommandArgument {

	private static final Param paramMachine = new Param(Utils.asList("machine", "m"), "id", Perm.PYRSLOTMACHINE_ADMIN, true);
	private static final Param paramCase = new Param(Utils.asList("case"), "id", Perm.PYRSLOTMACHINE_ADMIN, true);

	public CommandSetcase() {
		super(PyrSlotMachine.instance(), Utils.asList("setcase"), "set a machine case", Perm.PYRSLOTMACHINE_ADMIN, true, paramMachine, paramCase);
	}

	@Override
	public void perform(CommandCall call) {
		Player sender = call.getSenderAsPlayer();
		Machine machine = paramMachine.get(call, PyrSlotMachine.MACHINE_PARSER);
		int caseId = paramCase.getInt(call);
		if (machine != null && caseId != Integer.MIN_VALUE) {
			// block
			Block block = sender.getTargetBlock(null, 5);
			if (block == null || Mat.AIR.isMat(block)) {
				Locale.MSG_GENERIC_INVALIDCROSSHAIRBLOCK.getActive().send(sender, "{plugin}", PyrSlotMachine.instance().getName());
				return;
			}
			// set case
			Location loc = block.getLocation().clone().add(0.5D, 0.1D, 0.5D);
			machine.setCase(caseId, loc);
			PyrSlotMachine.instance().getData().mustSave(true);
			Locale.MSG_PYRSLOTMACHINE_SETCASE.getActive().send(sender, "{case}", caseId, "{machine}", machine.getId());
		}
	}

}
