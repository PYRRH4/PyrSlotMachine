package be.pyrrh4.pyrslotmachine;

import be.pyrrh4.pyrcore.lib.Perm;

public class PSMPerm {

	public static final Perm PYRSLOTMACHINE_ROOT = new Perm("PYRSLOTMACHINE_ROOT", null, "pyrslotmachine");
	public static final Perm PYRSLOTMACHINE_ADMIN = new Perm("PYRSLOTMACHINE_ADMIN", PYRSLOTMACHINE_ROOT, "admin");

}
