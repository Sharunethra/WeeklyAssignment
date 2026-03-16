import java.util.*;

class ParkingSpot {
    String licensePlate;
    long entryTime;

    ParkingSpot(String licensePlate) {
        this.licensePlate = licensePlate;
        this.entryTime = System.currentTimeMillis();
    }
}

public class ParkingLot {

    int SIZE = 500;
    ParkingSpot[] table = new ParkingSpot[SIZE];

    int totalProbes = 0;
    int totalParks = 0;

    // hash function
    int hash(String plate) {
        return Math.abs(plate.hashCode()) % SIZE;
    }

    // park vehicle
    void parkVehicle(String plate) {

        int index = hash(plate);
        int probes = 0;

        while (table[index] != null) {
            index = (index + 1) % SIZE;
            probes++;
        }

        table[index] = new ParkingSpot(plate);

        totalProbes += probes;
        totalParks++;

        System.out.println("Assigned spot #" + index +
                " (" + probes + " probes)");
    }

    // exit vehicle
    void exitVehicle(String plate) {

        for (int i = 0; i < SIZE; i++) {

            if (table[i] != null && table[i].licensePlate.equals(plate)) {

                long duration =
                        System.currentTimeMillis() - table[i].entryTime;

                double hours = duration / 3600000.0;

                double fee = hours * 5; // $5 per hour

                table[i] = null;

                System.out.println("Spot #" + i + " freed");
                System.out.println("Duration: " +
                        String.format("%.2f", hours) + " hours");
                System.out.println("Fee: $" +
                        String.format("%.2f", fee));
                return;
            }
        }

        System.out.println("Vehicle not found.");
    }

    // statistics
    void getStatistics() {

        int occupied = 0;

        for (ParkingSpot s : table) {
            if (s != null) occupied++;
        }

        double occupancy = (occupied * 100.0) / SIZE;

        double avgProbes =
                totalParks == 0 ? 0 : (double) totalProbes / totalParks;

        System.out.println("Occupancy: " +
                String.format("%.2f", occupancy) + "%");

        System.out.println("Avg Probes: " +
                String.format("%.2f", avgProbes));
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ParkingLot lot = new ParkingLot();

        while (true) {

            System.out.println("\n1.Park Vehicle");
            System.out.println("2.Exit Vehicle");
            System.out.println("3.Show Statistics");
            System.out.println("4.Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter license plate: ");
                    String plate = sc.nextLine();
                    lot.parkVehicle(plate);
                    break;

                case 2:
                    System.out.print("Enter license plate: ");
                    String exitPlate = sc.nextLine();
                    lot.exitVehicle(exitPlate);
                    break;

                case 3:
                    lot.getStatistics();
                    break;

                case 4:
                    return;
            }
        }
    }
}