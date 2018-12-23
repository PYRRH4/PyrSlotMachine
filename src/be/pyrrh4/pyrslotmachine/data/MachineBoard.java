package be.pyrrh4.pyrslotmachine.data;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;

import be.pyrrh4.pyrcore.PyrCore;
import be.pyrrh4.pyrcore.lib.data.DataBoard;
import be.pyrrh4.pyrcore.lib.data.mysql.Query;
import be.pyrrh4.pyrcore.lib.util.Utils;
import be.pyrrh4.pyrslotmachine.PyrSlotMachine;

public class MachineBoard extends DataBoard<Machine> {

	// fields
	private Map<String, Machine> machines = new HashMap<String, Machine>();

	// getters
	public Map<String, Machine> getAll() {
		return Collections.unmodifiableMap(machines);
	}

	public void add(Machine machine) {
		machines.put(machine.getId(), machine);
		machine.pushAsync();
	}

	public void delete(Machine machine) {
		if (machines.containsKey(machine.getId())) {
			machines.remove(machine.getId()).deleteAsync();
		}
	}

	/**
	 * Get a machine by its id
	 * @param param the machine id
	 */
	@Override
	public Machine getElement(Object param) {
		if (param instanceof String) {
			return machines.get((String) param);
		}
		throw new IllegalArgumentException("param type " + param.getClass() + " must be a String");
	}

	public Machine getElementByButton(Location button) {
		for (Machine machine : machines.values()) {
			if (Utils.coordsEquals(button, machine.getButton())) {
				return machine;
			}
		}
		return null;
	}

	// data
	@Override
	public PSMDataManager getDataManager() {
		return PyrSlotMachine.inst().getData();
	}

	@Override
	protected final File getJsonFile(Machine element) {
		return new File(PyrCore.inst().getDataRootFolder() + "/pyrslotmachine_machines/" + element.getId() + ".json");
	}

	@Override
	protected final void jsonPull() {
		File root = new File(PyrCore.inst().getDataRootFolder() + "/pyrslotmachine_machines/");
		if (root.exists() && root.isDirectory()) {
			Map<String, Machine> newMap = new HashMap<String, Machine>();
			for (File questFile : root.listFiles()) {
				if (questFile.getName().endsWith(".json")) {
					String id = questFile.getName().substring(0, questFile.getName().length() - 5);
					Machine arena = machines.containsKey(id) ? machines.get(id) : new Machine(id);
					arena.jsonPull();
					newMap.put(id, arena);
				}
			}
			machines.clear();
			machines.putAll(newMap);
		}
	}

	@Override
	protected final void jsonDelete() {
		File root = new File(PyrCore.inst().getDataRootFolder() + "/pyrslotmachine_machines/");
		if (root.exists() && root.isDirectory()) {
			root.delete();
		}
	}

	// MySQL
	@Override
	protected final String getMySQLTable() {
		return "pyrslotmachine_machines";
	}

	@Override
	protected final Query getMySQLInitQuery() {
		return new Query("CREATE TABLE IF NOT EXISTS `" + getMySQLTable() + "`("
				+ "`id` CHAR(36) NOT NULL,"
				+ "`type` TINYTEXT NOT NULL,"
				+ "`cases` TEXT NOT NULL,"
				+ "`button` TINYTEXT NOT NULL,"
				+ "PRIMARY KEY(`id`)"
				+ ") ENGINE=`InnoDB` DEFAULT CHARSET=?;", "utf8");
	}

	@Override
	protected final void mysqlPull() throws SQLException {
		ResultSet set = getDataManager().performMySQLGetQuery(new Query("SELECT * FROM `" + getMySQLTable() + "`;"));
		Map<String, Machine> newMap = new HashMap<String, Machine>();
		while (set.next()) {
			String id = set.getString("id");
			Machine arena = machines.containsKey(id) ? machines.get(id) : new Machine(id);
			arena.mysqlPull(set);
			newMap.put(id, arena);
		}
		machines.clear();
		machines.putAll(newMap);
	}

	@Override
	protected final void mysqlDelete() {
		getDataManager().performMySQLUpdateQuery(new Query("DELETE FROM `" + getMySQLTable() + "`;"));
	}

}
