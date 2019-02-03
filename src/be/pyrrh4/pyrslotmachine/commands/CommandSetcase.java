package be.pyrrh4.pyrslotmachine.commands;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import be.pyrrh4.pyrcore.PCLocale;
import be.pyrrh4.pyrcore.lib.command.CommandArgument;
import be.pyrrh4.pyrcore.lib.command.CommandCall;
import be.pyrrh4.pyrcore.lib.command.Param;
import be.pyrrh4.pyrcore.lib.material.Mat;
import be.pyrrh4.pyrcore.lib.util.Utils;
import be.pyrrh4.pyrslotmachine.PSMLocale;
import be.pyrrh4.pyrslotmachine.PSMPerm;
import be.pyrrh4.pyrslotmachine.PyrSlotMachine;
import be.pyrrh4.pyrslotmachine.data.Machine;

public class CommandSetcase extends CommandArgument {

	private static final Param paramMachine = new Param(Utils.asList("machine", "m"), "id", PSMPerm.PYRSLOTMACHINE_ADMIN, true, true);
	private static final Param paramCase = new Param(Utils.asList("case"), "id", PSMPerm.PYRSLOTMACHINE_ADMIN, true, true);

	public CommandSetcase() {
		super(PyrSlotMachine.inst(), Utils.asList("setcase"), "set a machine case", PSMPerm.PYRSLOTMACHINE_ADMIN, true, paramMachine, paramCase);
	}

	@Override
	public void perform(CommandCall call) {
		Player sender = call.getSenderAsPlayer();
		Machine machine = paramMachine.get(call, PyrSlotMachine.MACHINE_PARSER);
		int caseId = paramCase.getInt(call);
		if (machine != null && caseId != Integer.MIN_VALUE) {
			// block
			Block block = sender.getTargetBlock((Set<Material>) null, 5);
			if (block == null || Mat.from(block).isAir()) {
				PCLocale.MSG_GENERIC_INVALIDCROSSHAIRBLOCK.send(sender, "{plugin}", PyrSlotMachine.inst().getName());
				return;
			}
			// set case
			Location loc = block.getLocation().clone().add(0.5D, 0.1D, 0.5D);
			machine.setCase(caseId, loc);
			PSMLocale.MSG_PYRSLOTMACHINE_SETCASE.send(sender, "{case}", caseId, "{machine}", machine.getId());
		}
	}

}
