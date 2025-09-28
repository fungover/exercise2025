package org.example.computer.builders;

import org.example.computer.PC;
import org.example.computer.PCType;

public class BuildVideoEditingPC implements BuildComputer {
	private final Builder builder;

	public BuildVideoEditingPC(Builder builder) {
		this.builder = builder;
	}

	@Override
	public PC build() {
		builder.setType(PCType.VIDEO_EDITING_PC);
		builder.setCpu("9 7900X3D");
		builder.setGpu("RTX 4080");
		builder.setRam("64 GB");
		builder.setDisk("1 TB");
		return builder.createComputer();
	}
}
