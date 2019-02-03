package be.pyrrh4.pyrslotmachine.commands;

import org.bukkit.entity.Player;

import be.pyrrh4.pyrcore.PCLocale;
import be.pyrrh4.pyrcore.lib.command.CommandArgument;
import be.pyrrh4.pyrcore.lib.command.CommandCall;
import be.pyrrh4.pyrcore.lib.command.Param;
import be.pyrrh4.pyrcore.lib.util.Utils;
import be.pyrrh4.pyrslotmachine.PSMLocale;
import be.pyrrh4.pyrslotmachine.PSMPerm;
import be.pyrrh4.pyrslotmachine.PyrSlotMachine;
import be.pyrrh4.pyrslotmachine.data.Machine;
import be.pyrrh4.pyrslotmachine.machine.MachineType;

public class CommandCreate extends CommandArgument {

	private static final Param paramMachine = new Param(Utils.asList("machine", "m"), "id", PSMPerm.PYRSLOTMACHINE_ADMIN, true, true);
	private static final Param paramType = new Param(Utils.asList("type", "t"), "id", PSMPerm.PYRSLOTMACHINE_ADMIN, true, true);

	public CommandCreate() {
		super(PyrSlotMachine.inst(), Utils.asList("create", "new"), "create a machine", PSMPerm.PYRSLOTMACHINE_ADMIN, true, paramMachine, paramType);
	}

	@Override
	public void perform(CommandCall call) {
		Player sender = call.getSenderAsPlayer();
		String id = paramMachine.getStringAlphanumeric(call);
		MachineType type = paramType.get(call, PyrSlotMachine.MACHINETYPE_PARSER);
		if (id != null && type != null) {
			// already taken
			if (PyrSlotMachine.inst().getData().getMachines().getElement(id) != null) {
				PCLocale.MSG_GENERIC_IDTAKEN.send(sender, "{plugin}", PyrSlotMachine.inst().getName(), "{name}", id);
				return;
			}
			// create
			Machine machine = new Machine(id, type);
			PyrSlotMachine.inst().getData().getMachines().add(machine);
			PSMLocale.MSG_PYRSLOTMACHINE_CREATE.send(sender, "{id}", id);
		}
	}

}
