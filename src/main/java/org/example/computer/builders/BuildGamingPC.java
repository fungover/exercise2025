package org.example.computer.builders;

import org.example.computer.Computer;
import org.example.computer.ComputerType;

public class BuildGamingPC implements BuildComputer {
	@Override
	public Computer build(Builder builder) {
		builder.setType(ComputerType.GAMING_PC);
		builder.setCpu("R7 9800X3D");
		builder.setGpu("RTX 5080");
		builder.setRam("32 GB");
		builder.setDisk("2 TB");
		return builder.createComputer();
	}
}
