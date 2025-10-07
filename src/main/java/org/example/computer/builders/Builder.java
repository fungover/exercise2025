package org.example.computer.builders;

import org.example.computer.PC;
import org.example.computer.PCType;

public interface Builder {
	void setType(PCType type);
	void setCpu(String cpu);
	void setGpu(String gpu);
	void setRam(String ram);
	void setDisk(String disk);
	PC createComputer();
}
