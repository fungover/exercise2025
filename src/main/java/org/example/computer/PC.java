package org.example.computer;

public class PC {
	private final PCType TYPE;
	private final String CPU;
	private final String GPU;
	private final String RAM;
	private final String DISK;


	public PC(PCType TYPE, String CPU, String GPU, String RAM, String DISK) {
		this.TYPE = TYPE;
		this.CPU = CPU;
		this.GPU = GPU;
		this.RAM = RAM;
		this.DISK = DISK;
	}

	public PCType TYPE() {
		return TYPE;
	}

	public String CPU() {
		return CPU;
	}

	public String GPU() {
		return GPU;
	}

	public String RAM() {
		return RAM;
	}

	public String DISK() {
		return DISK;
	}

	@Override
	public String toString() {
		return "Computer [TYPE=" + TYPE + ", CPU=" + CPU + ", GPU=" + GPU + ", RAM=" + RAM + ", DISK=" + DISK + "]";
	}

}
