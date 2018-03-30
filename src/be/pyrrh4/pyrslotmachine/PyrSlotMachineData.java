package be.pyrrh4.pyrslotmachine;

import java.util.ArrayList;

import org.bukkit.Location;

import be.pyrrh4.core.PluginData;
import be.pyrrh4.core.util.Utils;
import be.pyrrh4.pyrslotmachine.machine.Machine;

public class PyrSlotMachineData extends PluginData {

	// ------------------------------------------------------------
	// Fields
	// ------------------------------------------------------------

	private ArrayList<Machine> machines = new ArrayList<Machine>();

	// ------------------------------------------------------------
	// Getters
	// ------------------------------------------------------------

	public Machine getMachine(String id) {
		for (Machine machine : machines) {
			if (machine.getId().equalsIgnoreCase(id)) {
				return machine;
			}
		}
		return null;
	}

	public Machine getMachineByButton(Location button) {
		for (Machine machine : machines) {
			if (Utils.coordsEquals(button, machine.getButton())) {
				return machine;
			}
		}
		return null;
	}

	public ArrayList<Machine> getMachines() {
		return machines;
	}

	public PyrSlotMachineData addMachine(Machine machine) {
		machines.add(machine);
		return this;
	}

}
