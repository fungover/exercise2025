package org.example.computer.builders;

import org.example.computer.PC;
import org.example.computer.PCType;

public class PCBuilder implements Builder {
	private PCType PCType;
	private String cpu;
	private String ram;
	private String gpu;
	private String disk;

	@Override
	public void setType(PCType PCType) {
		this.PCType = PCType;
	}
	@Override
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	@Override
	public void setGpu(String gpu) {
		this.gpu = gpu;
	}
	@Override
	public void setRam(String ram) {
		this.ram = ram;
	}
	@Override
	public void setDisk(String disk) {
		this.disk = disk;
	}
	public PC createComputer() {
		return new PC(PCType, cpu, gpu, ram, disk);
	}
}
