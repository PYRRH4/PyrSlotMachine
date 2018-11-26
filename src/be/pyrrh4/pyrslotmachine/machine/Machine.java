package be.pyrrh4.pyrslotmachine.machine;

import java.util.Collection;
import java.util.HashMap;

import org.bukkit.Location;

public class Machine {

	// fields and constructor
	private String id, type;
	private HashMap<Integer, Location> cases = new HashMap<Integer, Location>();
	private Location button;
	private transient RunningMachine running = null;

	public Machine(String id, MachineType type) {
		this.id = id;
		this.type = type.getId();
	}

	// getters
	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public void setType(MachineType type) {
		this.type = type.getId();
	}

	public Location getCase(int id) {
		return cases.containsKey(id) ? cases.get(id) : null;
	}

	public void setCase(int id, Location location) {
		cases.remove(id);
		cases.put(id, location);
	}

	public Collection<Location> getCases() {
		return cases.values();
	}

	public Location getButton() {
		return button;
	}

	public void setButton(Location button) {
		this.button = button;
	}

	public RunningMachine getRunning() {
		return running;
	}

	public void setRunningMachine(RunningMachine running) {
		this.running = running;
	}

}
