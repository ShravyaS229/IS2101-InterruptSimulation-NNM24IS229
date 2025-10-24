import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.time.LocalTime;

// Class representing a device that can generate interrupts
class DeviceInterrupt implements Runnable {
    private String deviceName;
    private AtomicBoolean masked;    // Whether this device is masked (interrupt ignored)
    private AtomicBoolean running;    // Shared flag to stop simulation

    public DeviceInterrupt(String name, AtomicBoolean running) {
        this.deviceName = name;
        this.masked = new AtomicBoolean(false);
        this.running = running;
    }

    public void mask(boolean status) {
        masked.set(status); // Set mask status (true = masked)
    }

    @Override
    public void run() {
        while (running.get()) { // Keep running while simulation is on
            try {
                Thread.sleep((int)(Math.random() * 2000 + 500)); // Random delay 0.5-2.5s
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            if (!masked.get()) { // Only handle interrupt if not masked
                LocalTime now = LocalTime.now();
                System.out.println(deviceName + " Interrupt Triggered ? Handling ISR ? Completed at " + now);
            }
        }
        System.out.println(deviceName + " thread stopped."); // Inform that thread has ended
    }
}

public class InterruptSimulation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AtomicBoolean running = new AtomicBoolean(true); // Shared flag to stop all threads

        // Create devices
        DeviceInterrupt keyboard = new DeviceInterrupt("Keyboard", running);
        DeviceInterrupt mouse = new DeviceInterrupt("Mouse", running);
        DeviceInterrupt printer = new DeviceInterrupt("Printer", running);

        // Start threads for each device
        Thread t1 = new Thread(keyboard);
        Thread t2 = new Thread(mouse);
        Thread t3 = new Thread(printer);

        t1.start();
        t2.start();
        t3.start();

        System.out.println("Starting Interrupt Simulation...");
        System.out.println("Type 'exit' to stop, or 'Keyboard/Mouse/Printer' to mask/unmask devices.");

        while (running.get()) {
            System.out.print("Enter device to mask/unmask or 'exit': ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                running.set(false); // Stop all threads
                break;
            } else if (input.equalsIgnoreCase("Keyboard")) {
                System.out.print("Enter true to mask, false to unmask: ");
                keyboard.mask(Boolean.parseBoolean(scanner.nextLine().trim()));
            } else if (input.equalsIgnoreCase("Mouse")) {
                System.out.print("Enter true to mask, false to unmask: ");
                mouse.mask(Boolean.parseBoolean(scanner.nextLine().trim()));
            } else if (input.equalsIgnoreCase("Printer")) {
                System.out.print("Enter true to mask, false to unmask: ");
                printer.mask(Boolean.parseBoolean(scanner.nextLine().trim()));
            } else {
                System.out.println("Invalid device name!");
            }
        }

        // Wait for threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Simulation stopped successfully.");
        scanner.close();
    }
}

