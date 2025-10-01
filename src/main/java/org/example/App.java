package org.example;

import org.example.computer.GamingSetup;
import org.example.computer.CreateGamingSetup;
import org.example.computer.builders.BuildComputer;
import org.example.computer.builders.BuildGamingPC;
import org.example.computer.builders.Builder;
import org.example.computer.builders.PCBuilder;
import org.example.container.Container;

public class App {
	public static void main(String[] args) {
		System.out.println("Part 1: ");
		manualDependency();
		System.out.println();

		System.out.println("Part 2: ");
		containerDI();
		System.out.println();

		System.out.println("Part 3: ");
	}


	private static void manualDependency() {
		Builder PCBuilder = new PCBuilder();
		BuildComputer gamingPCBuilder = new BuildGamingPC(PCBuilder);
		GamingSetup gamingSetup = new CreateGamingSetup(gamingPCBuilder);

		gamingSetup.buildAndStorePC();
	}

	private static void containerDI() {
		GamingSetup gamingSetup = Container.resolve(GamingSetup.class);
		gamingSetup.buildAndStorePC();
	}
}
