package be.pyrrh4.pyrslotmachine.machine;

import java.util.ArrayList;

import be.pyrrh4.core.gui.ItemData;

public class MachineType {

	// fields and constructor
	private double cost;
	private ArrayList<ItemData> prizes;

	public MachineType(double cost, ArrayList<ItemData> prizes) {
		this.cost = cost;
		this.prizes = prizes;
	}

	// getters
	public double getCost() {
		return cost;
	}

	public ArrayList<ItemData> getPrizes() {
		return prizes;
	}

}
