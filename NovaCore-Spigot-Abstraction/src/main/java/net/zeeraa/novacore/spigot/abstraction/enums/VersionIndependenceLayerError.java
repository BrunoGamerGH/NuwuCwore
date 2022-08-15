package net.zeeraa.novacore.spigot.abstraction.enums;

public enum VersionIndependenceLayerError { // oh wait this is the enum package
	NONE, MISSING_MATERIAL, MISSING_SOUND, UNIMPLEMENTED_FEATURE;

	public boolean isProblem() {
		return this != NONE;
	}
}