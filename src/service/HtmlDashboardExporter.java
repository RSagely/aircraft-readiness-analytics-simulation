package service;

import model.FlightHour;
import model.MaintenanceAction;
import model.Readiness;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HtmlDashboardExporter {

    public static void exportDashboard(
            List<FlightHour> flightHours,
            List<MaintenanceAction> actions,
            List<Readiness> readinessList) {

        Map<String, Double> flightMap = new LinkedHashMap<>();
        Map<String, Double> maintMap = new LinkedHashMap<>();
        Map<String, Double> nmcMap = new LinkedHashMap<>();

        for (FlightHour fh : flightHours) {
            flightMap.put(fh.getBuno(), fh.getFlightHours());
        }

        for (MaintenanceAction action : actions) {
            maintMap.put(
                    action.getBuno(),
                    maintMap.getOrDefault(action.getBuno(), 0.0)
                            + action.getMaintenanceHours()
            );
        }

        for (Readiness readiness : readinessList) {
            nmcMap.put(readiness.getBuno(), readiness.getNmcHours());
        }

        double totalFlightHours = 0;
        for (double value : flightMap.values()) {
            totalFlightHours += value;
        }

        double totalMaintenanceHours = 0;
        for (double value : maintMap.values()) {
            totalMaintenanceHours += value;
        }

        double totalNmcHours = 0;
        for (double value : nmcMap.values()) {
            totalNmcHours += value;
        }

        double totalFmc = 0;
        double totalPmc = 0;
        double totalNmc = 0;

        for (Readiness r : readinessList) {
            totalFmc += r.getFmcHours();
            totalPmc += r.getPmcHours();
            totalNmc += r.getNmcHours();
        }

        double mcRate = ((totalFmc + totalPmc) / (totalFmc + totalPmc + totalNmc)) * 100;

        try (PrintWriter writer = new PrintWriter(new FileWriter("dashboard.html"))) {

            writer.println("<!DOCTYPE html>");
            writer.println("<html>");
            writer.println("<head>");
            writer.println("<h1>Aircraft Readiness Analytics Simulation</h1>");
            writer.println("<script src='https://cdn.jsdelivr.net/npm/chart.js'></script>");
            writer.println("<script src='https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@2'></script>");

            writer.println("<style>");
            writer.println("body { font-family: Arial, sans-serif; margin: 30px; background: #f3f4f6; color: #111827; }");
            writer.println("h1 { margin-bottom: 5px; }");
            writer.println(".note { margin-bottom: 25px; color: #374151; }");
            writer.println(".kpi-row { display: flex; gap: 20px; margin-bottom: 25px; }");
            writer.println(".kpi { background: white; padding: 20px; border-radius: 12px; box-shadow: 0 2px 8px rgba(0,0,0,0.15); flex: 1; }");
            writer.println(".kpi-title { color: #4b5563; font-size: 14px; }");
            writer.println(".kpi-value { font-size: 34px; font-weight: bold; color: #1d4ed8; margin-top: 8px; }");
            writer.println(".grid { display: grid; grid-template-columns: 1fr 1fr; gap: 25px; }");
            writer.println(".card { background: white; padding: 20px; border-radius: 12px; box-shadow: 0 2px 8px rgba(0,0,0,0.15); }");
            writer.println("canvas { width: 100% !important; height: 360px !important; }");
            writer.println("</style>");

            writer.println("</head>");
            writer.println("<body>");

            writer.println("<p class='note'><b>Portfolio Demonstration:</b> This dashboard uses a fully synthetic dataset. Aircraft identifiers, readiness values, maintenance actions, "
            		+ "and trends are fictional and do not represent operational systems or real aircraft data.</p>");

            writer.println("<div class='kpi-row'>");

            writer.println("<div class='kpi'>");
            writer.println("<div class='kpi-title'>Mission Capable %</div>");
            writer.println("<div class='kpi-value'>" + String.format("%.1f", mcRate) + "%</div>");
            writer.println("</div>");

            writer.println("<div class='kpi'>");
            writer.println("<div class='kpi-title'>Total Maintenance Hours</div>");
            writer.println("<div class='kpi-value'>" + String.format("%.1f", totalMaintenanceHours) + "</div>");
            writer.println("</div>");

            writer.println("<div class='kpi'>");
            writer.println("<div class='kpi-title'>Total Flight Hours</div>");
            writer.println("<div class='kpi-value'>" + String.format("%.1f", totalFlightHours) + "</div>");
            writer.println("</div>");

            writer.println("<div class='kpi'>");
            writer.println("<div class='kpi-title'>Total NMC Hours</div>");
            writer.println("<div class='kpi-value'>" + String.format("%.1f", totalNmcHours) + "</div>");
            writer.println("</div>");

            writer.println("</div>");

            writer.println("<div class='grid'>");

            writer.println("<div class='card'>");
            writer.println("<h2>Maintenance Hours by Aircraft</h2>");
            writer.println("<canvas id='maintenanceBarChart'></canvas>");
            writer.println("</div>");

            writer.println("<div class='card'>");
            writer.println("<h2>Maintenance Burden vs Flight Hours</h2>");
            writer.println("<canvas id='scatterChart'></canvas>");
            writer.println("</div>");

            writer.println("<div class='card'>");
            writer.println("<h2>Operational Readiness: MC vs NMC Hours</h2>");
            writer.println("<canvas id='readinessStackedChart'></canvas>");
            writer.println("</div>");

            writer.println("<div class='card'>");
            writer.println("<h2>Fleet MC Rate Trend</h2>");
            writer.println("<canvas id='lineChart'></canvas>");
            writer.println("</div>");

            writer.println("</div>");

            writer.println("<script>");

            writer.println("const labels = " + toJsStringArray(flightMap.keySet()) + ";");
            writer.println("const maintenanceHours = " + toJsNumberArrayByLabels(flightMap.keySet(), maintMap) + ";");
            writer.println("const mcHours = " + toReadinessArray(readinessList, "MC") + ";");
            writer.println("const readinessNmcHours = " + toReadinessArray(readinessList, "NMC") + ";");

            writer.println("new Chart(document.getElementById('maintenanceBarChart'), {");
            writer.println("type: 'bar',");
            writer.println("data: { labels: labels, datasets: [{ label: 'Maintenance Hours', data: maintenanceHours, backgroundColor: '#2563eb' }] },");
            writer.println("options: { responsive: true, plugins: { legend: { display: false } }, scales: { y: { beginAtZero: true, title: { display: true, text: 'Hours' } }, x: { title: { display: true, text: 'BUNO' } } } }");
            writer.println("});");
            
            writer.println("Chart.register(ChartDataLabels);");
            writer.println("new Chart(document.getElementById('scatterChart'), {");
            writer.println("type: 'bubble',");
            writer.println("data: { datasets: [{ label: 'Aircraft', data: [");

            int count = 0;
            for (String buno : flightMap.keySet()) {
                double flight = flightMap.getOrDefault(buno, 0.0);
                double maint = maintMap.getOrDefault(buno, 0.0);
                double nmc = nmcMap.getOrDefault(buno, 0.0);
                double radius = Math.max(6, nmc / 20);

                writer.print("{ x: " + flight
                        + ", y: " + maint
                        + ", r: " + radius
                        + ", buno: '" + buno + "' }");

                count++;
                if (count < flightMap.size()) {
                    writer.println(",");
                }
            }

            writer.println("], backgroundColor: '#dc2626' }] },");
            writer.println("options: { responsive: true, plugins: { legend: { display: false }, datalabels: { align: 'right', anchor: 'end', formatter: function(value) { return value.buno; }, font: { weight: 'bold' } }, tooltip: { callbacks: { label: function(ctx) { return ctx.raw.buno + ': Flight Hours=' + ctx.raw.x + ', Maint Hours=' + ctx.raw.y + ', Bubble=NMC Hours'; } } } }, scales: { x: { title: { display: true, text: 'Flight Hours' } }, y: { beginAtZero: true, title: { display: true, text: 'Maintenance Hours' } } } }");
            writer.println("});");

            writer.println("new Chart(document.getElementById('readinessStackedChart'), {");
            writer.println("type: 'bar',");
            writer.println("data: { labels: labels, datasets: [");
            writer.println("{ label: 'MC Hours', data: mcHours, backgroundColor: '#16a34a' },");
            writer.println("{ label: 'NMC Hours', data: readinessNmcHours, backgroundColor: '#dc2626' }");
            writer.println("] },");
            writer.println("options: { responsive: true, scales: { x: { stacked: true }, y: { stacked: true, beginAtZero: true, title: { display: true, text: 'Hours' } } } }");
            writer.println("});");

            writer.println("new Chart(document.getElementById('lineChart'), {");
            writer.println("type: 'line',");
            writer.println("data: {");
            writer.println("labels: " + toMonthLabels(readinessList) + ",");
            writer.println("datasets: [{");
            writer.println("label: 'Fleet MC Rate %',");
            writer.println("data: " + toMonthlyMcRates(readinessList) + ",");
            writer.println("borderColor: '#1d4ed8',");
            writer.println("backgroundColor: '#1d4ed8',");
            writer.println("tension: 0.3,");
            writer.println("pointRadius: 5");
            writer.println("}]");
            writer.println("},");
            writer.println("options: {");
            writer.println("responsive: true,");
            writer.println("scales: {");
            writer.println("y: { min: 0, max: 100, title: { display: true, text: 'MC Rate %' } },");
            writer.println("x: { title: { display: true, text: 'Month' } }");
            writer.println("}");
            writer.println("}");
            writer.println("});");

            writer.println("</script>");

            writer.println("<footer style='margin-top:30px; padding:15px; font-size:12px; color:#4b5563; text-align:center;'>");
            writer.println("Synthetic Dataset | Portfolio Demonstration Only | No Operational Aircraft Data Utilized");
            writer.println("</footer>");

            writer.println("</body>");
            writer.println("</html>");

            System.out.println("HTML dashboard created: dashboard.html");

        } catch (IOException e) {
            System.out.println("Error creating dashboard: " + e.getMessage());
        }
    }

    private static String toJsStringArray(Collection<String> values) {
        StringBuilder sb = new StringBuilder("[");
        int count = 0;

        for (String value : values) {
            sb.append("'").append(value).append("'");

            count++;
            if (count < values.size()) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    private static String toJsNumberArrayByLabels(Collection<String> labels, Map<String, Double> map) {
        StringBuilder sb = new StringBuilder("[");
        int count = 0;

        for (String label : labels) {
            sb.append(String.format("%.2f", map.getOrDefault(label, 0.0)));

            count++;
            if (count < labels.size()) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    private static String toReadinessArray(List<Readiness> readinessList, String type) {
        StringBuilder sb = new StringBuilder("[");

        for (int i = 0; i < readinessList.size(); i++) {
            Readiness r = readinessList.get(i);

            if (type.equals("MC")) {
                sb.append(String.format("%.2f", r.getFmcHours() + r.getPmcHours()));
            } else if (type.equals("FMC")) {
                sb.append(String.format("%.2f", r.getFmcHours()));
            } else if (type.equals("PMC")) {
                sb.append(String.format("%.2f", r.getPmcHours()));
            } else {
                sb.append(String.format("%.2f", r.getNmcHours()));
            }

            if (i < readinessList.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }
    private static String toMonthLabels(List<Readiness> readinessList) {
        StringBuilder sb = new StringBuilder("[");
        Map<String, Boolean> months = new LinkedHashMap<>();

        for (Readiness r : readinessList) {
            months.put(r.getMonth(), true);
        }

        int count = 0;
        for (String month : months.keySet()) {
            sb.append("'").append(month).append("'");

            count++;
            if (count < months.size()) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    private static String toMonthlyMcRates(List<Readiness> readinessList) {
        Map<String, double[]> monthTotals = new LinkedHashMap<>();

        for (Readiness r : readinessList) {
            monthTotals.putIfAbsent(r.getMonth(), new double[3]);

            monthTotals.get(r.getMonth())[0] += r.getFmcHours();
            monthTotals.get(r.getMonth())[1] += r.getPmcHours();
            monthTotals.get(r.getMonth())[2] += r.getNmcHours();
        }

        StringBuilder sb = new StringBuilder("[");
        int count = 0;

        for (double[] totals : monthTotals.values()) {
            double fmc = totals[0];
            double pmc = totals[1];
            double nmc = totals[2];

            double mcRate = ((fmc + pmc) / (fmc + pmc + nmc)) * 100;

            sb.append(String.format("%.1f", mcRate));

            count++;
            if (count < monthTotals.size()) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }
}