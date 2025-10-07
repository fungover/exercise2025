package org.example.computer;

public class PC {
	private final PCType type;
	private final String cpu;
	private final String gpu;
	private final String ram;
	private final String disk;


	public PC(PCType type, String cpu, String gpu, String ram, String disk) {
		this.type = type;
		this.cpu = cpu;
		this.gpu = gpu;
		this.ram = ram;
		this.disk = disk;
	}

	public PCType TYPE() {
		return type;
	}

	public String CPU() {
		return cpu;
	}

	public String GPU() {
		return gpu;
	}

	public String RAM() {
		return ram;
	}

	public String DISK() {
		return disk;
	}

	@Override
	public String toString() {
		return "Computer [TYPE=" + type + ", CPU=" + cpu + ", GPU=" + gpu + ", RAM=" + ram + ", DISK=" + disk + "]";
	}

}
