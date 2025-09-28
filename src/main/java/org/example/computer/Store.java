package org.example.computer;

import org.example.computer.builders.BuildComputer;

public interface Store {
	void storeBuiltComputer(BuildComputer builder);
	void storeComputer(Computer computer);
	void getAllComputers();
	void getComputerById(int id);
	void getComputerByName(String name);
}
