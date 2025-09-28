package org.example.computer.builders;

import org.example.computer.Computer;
import org.example.computer.ComputerType;

public class BuildOfficePC implements BuildComputer {
	@Override
	public Computer build(Builder builder) {
		builder.setType(ComputerType.OFFICE_PC);
		builder.setCpu("Core Ultra 5");
		builder.setGpu("Intel UHD Graphics");
		builder.setRam("16 GB");
		builder.setDisk("512 GB");
		return builder.createComputer();
	}

}
