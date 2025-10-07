package org.example;

import org.example.computer.GamingSetup;
import org.example.computer.CreateGamingSetup;
import org.example.computer.builders.BuildComputer;
import org.example.computer.builders.BuildGamingPC;
import org.example.computer.builders.Builder;
import org.example.computer.builders.PCBuilder;
import org.example.container.Container;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import java.util.Objects;

public class App {
	public static void main(String[] args) {
		System.out.println("Part 1: ");
		manualDependency();
		System.out.println();

		System.out.println("Part 2: ");
		containerDI();
		System.out.println();

		System.out.println("Part 3: ");
		weldDI();
		System.out.println();
	}


	private static void manualDependency() {
		Builder pcBuilder = new PCBuilder();
		BuildComputer gamingPCBuilder = new BuildGamingPC(pcBuilder);
		GamingSetup gamingSetup = new CreateGamingSetup(gamingPCBuilder);

		gamingSetup.buildAndStorePC();
	}

	private static void containerDI() {
		GamingSetup gamingSetup = Objects.requireNonNull(Container.resolve(GamingSetup.class), "DI resolution failed");
		gamingSetup.buildAndStorePC();
	}

	private static void weldDI() {
		try (WeldContainer container = new Weld().initialize()) {
			GamingSetup gamingSetup = container.select(GamingSetup.class).get();
			gamingSetup.buildAndStorePC();
		}
	}
}
