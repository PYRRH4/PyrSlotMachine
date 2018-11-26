package be.pyrrh4.pyrslotmachine.commands;

import org.bukkit.entity.Player;

import be.pyrrh4.core.Perm;
import be.pyrrh4.core.command.CommandArgument;
import be.pyrrh4.core.command.CommandCall;
import be.pyrrh4.core.command.Param;
import be.pyrrh4.core.messenger.Locale;
import be.pyrrh4.core.util.Utils;
import be.pyrrh4.pyrslotmachine.PyrSlotMachine;
import be.pyrrh4.pyrslotmachine.machine.Machine;
import be.pyrrh4.pyrslotmachine.machine.MachineType;

public class CommandCreate extends CommandArgument {

	private static final Param paramMachine = new Param(Utils.asList("machine", "m"), "id", Perm.PYRSLOTMACHINE_ADMIN, true);
	private static final Param paramType = new Param(Utils.asList("type", "t"), "id", Perm.PYRSLOTMACHINE_ADMIN, true);

	public CommandCreate() {
		super(PyrSlotMachine.instance(), Utils.asList("create", "new"), "create a machine", Perm.PYRSLOTMACHINE_ADMIN, true, paramMachine, paramType);
	}

	@Override
	public void perform(CommandCall call) {
		Player sender = call.getSenderAsPlayer();
		String id = paramMachine.getStringAlphanumeric(call);
		MachineType type = paramType.get(call, PyrSlotMachine.MACHINETYPE_PARSER);
		if (id != null && type != null) {
			// already taken
			if (PyrSlotMachine.instance().getData().getMachine(id) != null) {
				Locale.MSG_GENERIC_NAMETAKEN.getActive().send(sender, "{plugin}", PyrSlotMachine.instance().getName(), "{name}", id);
				return;
			}
			// create
			Machine machine = new Machine(id, type);
			PyrSlotMachine.instance().getData().addMachine(machine);
			Locale.MSG_PYRSLOTMACHINE_CREATE.getActive().send(sender, "{id}", id);
		}
	}

}
