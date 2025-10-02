package org.example.computer.builders;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.InjectionPoint;
import org.example.computer.PC;
import org.example.computer.PCType;

@ApplicationScoped
public class BuildGamingPC implements BuildComputer {
	private final Builder builder;

	@Inject
	public BuildGamingPC(Builder builder) {
		this.builder = builder;
	}

	@Override
	public PC build() {
		builder.setType(PCType.GAMING_PC);
		builder.setCpu("R7 9800X3D");
		builder.setGpu("RTX 5080");
		builder.setRam("32 GB");
		builder.setDisk("2 TB");
		return builder.createComputer();
	}
}
