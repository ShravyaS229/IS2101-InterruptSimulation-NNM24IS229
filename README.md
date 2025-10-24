# IS2101-InterruptSimulation-NNM24IS229
# Interrupt Simulation

## Assignment Title
Interrupt Simulation using Java Threads

## Description
This Java project simulates **hardware interrupts** from three devices:  
- Keyboard  
- Mouse  
- Printer  

Each device runs in a separate thread and generates interrupts at random intervals.  
The main program allows the user to:  
- Mask (ignore) or unmask (allow) interrupts for each device  
- Stop the simulation by typing `exit`

## How It Works
1. Each device is represented by a DeviceInterrupt class implementing Runnable.
2. An AtomicBoolean flag is used to control whether a device is **masked** or if the simulation is running.
3. The main class InterruptSimulation starts threads for all devices.
4. The user interacts via the console to mask/unmask devices or exit the simulation.
5. When the simulation stops, all threads are joined, and a message confirms the simulation ended.

## How to Run
1. Compile the Java file:
```bash
javac InterruptSimulation.java
