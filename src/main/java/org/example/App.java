package org.example;

import org.example.computer.GamingSetup;
import org.example.computer.CreateGamingSetup;
import org.example.computer.builders.BuildGamingPC;
import org.example.computer.builders.PCBuilder;

public class App {
    public static void main(String[] args) {
			manualDependency();
		}
		private static void manualDependency() {
			PCBuilder PCBuilder = new PCBuilder();
			BuildGamingPC gamingPCBuilder = new BuildGamingPC(PCBuilder);
			GamingSetup gamingSetup = new CreateGamingSetup(gamingPCBuilder);

			gamingSetup.buildAndStorePC();
			System.out.println("Type: " + gamingSetup.getPC().TYPE()
							+ "\nProcessor: " + gamingSetup.getPC().CPU()
							+ "\nGraphics Card: " +  gamingSetup.getPC().GPU()
							+ "\nRandom Access Memory: " +  gamingSetup.getPC().RAM()
							+ "\nDisk Space: " + gamingSetup.getPC().DISK());
		}
}
