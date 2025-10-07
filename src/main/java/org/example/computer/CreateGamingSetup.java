package org.example.computer;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.example.computer.builders.BuildComputer;

@Dependent
public class CreateGamingSetup implements GamingSetup {
	private final BuildComputer pcBuilder;
	private PC computer;

	@Inject
	public CreateGamingSetup(BuildComputer pcBuilder) {
		this.pcBuilder = pcBuilder;
	}

	@Override
	public void buildAndStorePC() {
		this.computer = pcBuilder.build();

		System.out.println("Type: " + computer.TYPE()
						+ "\nProcessor: " + computer.CPU()
						+ "\nGraphics Card: " + computer.GPU()
						+ "\nRandom Access Memory: " + computer.RAM()
						+ "\nDisk Space: " + computer.DISK());
	}
	@Override
	public PC getPC() {
		return this.computer;
	}
}
