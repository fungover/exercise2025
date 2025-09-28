package org.example.computer.builders;

import org.example.computer.Computer;
import org.example.computer.ComputerType;

public interface Builder {
	void setType(ComputerType type);
	void setCpu(String cpu);
	void setGpu(String gpu);
	void setRam(String ram);
	void setDisk(String disk);
	Computer createComputer();
}
