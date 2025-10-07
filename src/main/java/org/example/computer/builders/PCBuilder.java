package org.example.computer.builders;

import jakarta.enterprise.context.Dependent;
import org.example.computer.PC;
import org.example.computer.PCType;

@Dependent
public class PCBuilder implements Builder {
	private PCType type;
	private String cpu;
	private String ram;
	private String gpu;
	private String disk;

	@Override
	public void setType(PCType PCType) {
		this.type = PCType;
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
		return new PC(type, cpu, gpu, ram, disk);
	}
}
