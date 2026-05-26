package service;

import model.MaintenanceAction;
import model.Readiness;

import java.util.List;

public class DashboardMetrics {

    // =====================================================
    // READINESS METRICS
    // =====================================================

    public static double calculateFleetMcRate(List<Readiness> readinessList) {

        double totalFmc = 0;
        double totalPmc = 0;
        double totalNmc = 0;

        for (Readiness readiness : readinessList) {

            totalFmc += readiness.getFmcHours();
            totalPmc += readiness.getPmcHours();
            totalNmc += readiness.getNmcHours();
        }

        double totalHours = totalFmc + totalPmc + totalNmc;

        if (totalHours == 0) {
            return 0;
        }

        return ((totalFmc + totalPmc) / totalHours) * 100;
    }

    public static double calculateFleetNmcRate(List<Readiness> readinessList) {

        double totalNmc = 0;
        double totalHours = 0;

        for (Readiness readiness : readinessList) {

            totalNmc += readiness.getNmcHours();
            totalHours += readiness.getTotalHours();
        }

        if (totalHours == 0) {
            return 0;
        }

        return (totalNmc / totalHours) * 100;
    }

    // =====================================================
    // MAINTENANCE METRICS
    // =====================================================

    public static double calculateTotalMaintenanceHours(
            List<MaintenanceAction> actions) {

        double total = 0;

        for (MaintenanceAction action : actions) {

            total += action.getMaintenanceHours();
        }

        return total;
    }

    public static double calculateTreatmentHours(
            List<MaintenanceAction> actions) {

        double total = 0;

        for (MaintenanceAction action : actions) {

            if (action.getCorrosionType()
                    .equalsIgnoreCase("Treatment")) {

                total += action.getMaintenanceHours();
            }
        }

        return total;
    }

    public static double calculatePreventionHours(
            List<MaintenanceAction> actions) {

        double total = 0;

        for (MaintenanceAction action : actions) {

            if (action.getCorrosionType()
                    .equalsIgnoreCase("Prevention")) {

                total += action.getMaintenanceHours();
            }
        }

        return total;
    }
}