package org.example.computer.builders;

import org.example.computer.PC;
import org.example.computer.PCType;

public class BuildOfficePC implements BuildComputer {
	private final Builder builder;

	public BuildOfficePC(Builder builder) {
		this.builder = builder;
	}

	@Override
	public PC build() {
		builder.setType(PCType.OFFICE_PC);
		builder.setCpu("Core Ultra 5");
		builder.setGpu("Intel UHD Graphics");
		builder.setRam("16 GB");
		builder.setDisk("512 GB");
		return builder.createComputer();
	}

}
