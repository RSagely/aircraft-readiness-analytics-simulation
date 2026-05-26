package main;

import data.SimulatedData;

import model.Aircraft;
import model.FlightHour;
import model.MaintenanceAction;
import model.Readiness;

import service.DashboardMetrics;
import service.HtmlDashboardExporter;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // =====================================================
        // LOAD SIMULATED DATA
        // =====================================================

        List<Aircraft> aircraftList =
                SimulatedData.getAircraft();

        List<FlightHour> flightHours =
                SimulatedData.getFlightHours();

        List<Readiness> readinessList =
                SimulatedData.getReadiness();

        List<MaintenanceAction> maintenanceActions =
                SimulatedData.getMaintenanceActions();

        // =====================================================
        // CALCULATE KPI METRICS
        // =====================================================

        double fleetMcRate =
                DashboardMetrics.calculateFleetMcRate(readinessList);

        double fleetNmcRate =
                DashboardMetrics.calculateFleetNmcRate(readinessList);

        double totalMaintenanceHours =
                DashboardMetrics.calculateTotalMaintenanceHours(
                        maintenanceActions);

        double treatmentHours =
                DashboardMetrics.calculateTreatmentHours(
                        maintenanceActions);

        double preventionHours =
                DashboardMetrics.calculatePreventionHours(
                        maintenanceActions);

        // =====================================================
        // DASHBOARD OUTPUT
        // =====================================================

        System.out.println("=================================================");
        System.out.println(" AIRCRAFT READINESS ANALYTICS DASHBOARD");
        System.out.println("=================================================");

        // =====================================================
        // FLEET KPI SUMMARY
        // =====================================================

        System.out.println("\nFLEET KPI SUMMARY");
        System.out.println("-------------------------------------------------");

        System.out.println("Fleet MC Rate: "
                + String.format("%.1f", fleetMcRate) + "%");

        System.out.println("Fleet NMC Rate: "
                + String.format("%.1f", fleetNmcRate) + "%");

        System.out.println("Total Maintenance Hours: "
                + String.format("%.1f", totalMaintenanceHours));

        System.out.println("Treatment Maintenance Hours: "
                + String.format("%.1f", treatmentHours));

        System.out.println("Prevention Maintenance Hours: "
                + String.format("%.1f", preventionHours));

        // =====================================================
        // AIRCRAFT
        // =====================================================

        System.out.println("\nAIRCRAFT");
        System.out.println("-------------------------------------------------");

        for (Aircraft aircraft : aircraftList) {
            System.out.println(aircraft);
        }

        // =====================================================
        // FLIGHT HOURS
        // =====================================================

        System.out.println("\nFLIGHT HOURS");
        System.out.println("-------------------------------------------------");

        for (FlightHour hour : flightHours) {
            System.out.println(hour);
        }

        // =====================================================
        // READINESS DETAIL
        // =====================================================

        System.out.println("\nREADINESS DETAIL");
        System.out.println("-------------------------------------------------");

        for (Readiness readiness : readinessList) {
            System.out.println(readiness);
        }

        // =====================================================
        // MAINTENANCE ACTIONS
        // =====================================================

        System.out.println("\nMAINTENANCE ACTIONS");
        System.out.println("-------------------------------------------------");

        for (MaintenanceAction action : maintenanceActions) {
            System.out.println(action);
        }

        // =====================================================
        
        HtmlDashboardExporter.exportDashboard(
                flightHours,
                maintenanceActions,
                readinessList
        );

        System.out.println("\n=================================================");
        System.out.println(" SIMULATION COMPLETE");
        System.out.println("=================================================");
    }
}