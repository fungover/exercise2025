package org.example.computer.builders;

import org.example.computer.Computer;
import org.example.computer.ComputerType;

public class ComputerBuilder implements Builder {
	private ComputerType computerType;
	private String cpu;
	private String ram;
	private String gpu;
	private String disk;

	@Override
	public void setType(ComputerType computerType) {
		this.computerType = computerType;
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
	public Computer createComputer() {
		return new Computer(computerType, cpu, gpu, ram, disk);
	}
}
