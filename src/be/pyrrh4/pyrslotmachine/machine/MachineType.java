package be.pyrrh4.pyrslotmachine.machine;

import java.util.ArrayList;

import be.pyrrh4.core.gui.ItemData;

public class MachineType {

	// fields and constructor
	private String id;
	private double cost;
	private ArrayList<ItemData> prizes;

	public MachineType(String id, double cost, ArrayList<ItemData> prizes) {
		this.id = id;
		this.cost = cost;
		this.prizes = prizes;
	}

	// getters
	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return getId();
	}

	public double getCost() {
		return cost;
	}

	public ArrayList<ItemData> getPrizes() {
		return prizes;
	}

}
