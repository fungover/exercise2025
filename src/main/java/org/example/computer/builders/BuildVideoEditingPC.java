package org.example.computer.builders;

import org.example.computer.Computer;
import org.example.computer.ComputerType;

public class BuildVideoEditingPC implements BuildComputer {
	@Override
	public Computer build(Builder builder) {
		builder.setType(ComputerType.VIDEO_EDITING_PC);
		builder.setCpu("9 7900X3D");
		builder.setGpu("RTX 4080");
		builder.setRam("64 GB");
		builder.setDisk("1 TB");
		return builder.createComputer();
	}
}
