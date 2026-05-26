package data;

import model.Aircraft;
import model.FlightHour;
import model.Readiness;
import model.MaintenanceAction;

import java.util.ArrayList;
import java.util.List;

public class SimulatedData {

    public static List<Aircraft> getAircraft() {

        List<Aircraft> aircraft = new ArrayList<>();

        aircraft.add(new Aircraft("PB-001", "Sqdrn_1"));
        aircraft.add(new Aircraft("PB-002", "Sqdrn_2"));
        aircraft.add(new Aircraft("PB-003", "Sqdrn_1"));
        aircraft.add(new Aircraft("PB-004", "Sqdrn_2"));
        aircraft.add(new Aircraft("PB-005", "Sqdrn_1"));
        aircraft.add(new Aircraft("PB-006", "Sqdrn_2"));
        aircraft.add(new Aircraft("PB-007", "Sqdrn_1"));

        return aircraft;
    }

    public static List<FlightHour> getFlightHours() {

        List<FlightHour> hours = new ArrayList<>();

        hours.add(new FlightHour("PB-001", "2025-04", 18)); // moderate use
        hours.add(new FlightHour("PB-002", "2025-04", 24)); // high use
        hours.add(new FlightHour("PB-003", "2025-04", 14)); // lower use
        hours.add(new FlightHour("PB-004", "2025-04", 11)); // low use, high maint burden
        hours.add(new FlightHour("PB-005", "2025-04", 27)); // high use, low maint burden
        hours.add(new FlightHour("PB-006", "2025-04", 20)); // moderate/high use
        hours.add(new FlightHour("PB-007", "2025-04", 16)); // moderate use

        return hours;
    }

    public static List<Readiness> getReadiness() {

        List<Readiness> readiness = new ArrayList<>();

        // April baseline
        readiness.add(new Readiness("PB-001", "2025-04", 400, 200, 120)); // MC 83.3
        readiness.add(new Readiness("PB-002", "2025-04", 420, 180, 100)); // MC 85.7
        readiness.add(new Readiness("PB-003", "2025-04", 350, 220, 150)); // MC 79.2
        readiness.add(new Readiness("PB-004", "2025-04", 300, 200, 220)); // MC 69.4
        readiness.add(new Readiness("PB-005", "2025-04", 450, 150, 80));  // MC 88.2
        readiness.add(new Readiness("PB-006", "2025-04", 320, 210, 190)); // MC 73.6
        readiness.add(new Readiness("PB-007", "2025-04", 410, 190, 120)); // MC 83.3

        // May - readiness dip due to increased NMC burden
        readiness.add(new Readiness("PB-001", "2025-05", 360, 190, 170));
        readiness.add(new Readiness("PB-002", "2025-05", 390, 160, 170));
        readiness.add(new Readiness("PB-003", "2025-05", 315, 205, 200));
        readiness.add(new Readiness("PB-004", "2025-05", 275, 190, 255));
        readiness.add(new Readiness("PB-005", "2025-05", 425, 145, 150));
        readiness.add(new Readiness("PB-006", "2025-05", 290, 200, 230));
        readiness.add(new Readiness("PB-007", "2025-05", 370, 185, 165));

        // June - partial recovery after maintenance actions
        readiness.add(new Readiness("PB-001", "2025-06", 410, 200, 110));
        readiness.add(new Readiness("PB-002", "2025-06", 430, 180, 110));
        readiness.add(new Readiness("PB-003", "2025-06", 365, 220, 135));
        readiness.add(new Readiness("PB-004", "2025-06", 320, 210, 190));
        readiness.add(new Readiness("PB-005", "2025-06", 460, 160, 100));
        readiness.add(new Readiness("PB-006", "2025-06", 335, 215, 170));
        readiness.add(new Readiness("PB-007", "2025-06", 415, 200, 105));

        return readiness;
    }
    
    public static List<MaintenanceAction> getMaintenanceActions() {

        List<MaintenanceAction> actions = new ArrayList<>();

        actions.add(new MaintenanceAction(1, "PB-001", "WO1001", "2025-04-01", "Z",
                "Corrosion Removal", 2.5, "Treatment"));

        actions.add(new MaintenanceAction(2, "PB-001", "WO1001", "2025-04-02", "O",
                "Wash Aircraft", 1.2, "Prevention"));

        actions.add(new MaintenanceAction(3, "PB-002", "WO1002", "2025-04-05", "Z",
                "Surface Treatment", 3.1, "Treatment"));

        actions.add(new MaintenanceAction(4, "PB-003", "WO1003", "2025-04-07", "O",
                "Preventive Coating", 4.0, "Prevention"));

        actions.add(new MaintenanceAction(5, "PB-004", "WO1004", "2025-04-10", "Z",
                "Corrosion Repair", 6.5, "Treatment"));

        actions.add(new MaintenanceAction(6, "PB-005", "WO1005", "2025-04-12", "O",
                "Inspection Only", 2.0, "Prevention"));

        actions.add(new MaintenanceAction(7, "PB-006", "WO1006", "2025-04-15", "Z",
                "Corrosion Treatment", 5.2, "Treatment"));

        actions.add(new MaintenanceAction(8, "PB-007", "WO1007", "2025-04-18", "O",
                "Wash and Clean", 3.0, "Prevention"));

        return actions;
    }
}
