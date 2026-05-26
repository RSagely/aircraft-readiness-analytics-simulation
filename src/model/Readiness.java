package model;

public class Readiness {

    private String buno;
    private String month;

    private double fmcHours;
    private double pmcHours;
    private double nmcHours;

    public Readiness(String buno,
                     String month,
                     double fmcHours,
                     double pmcHours,
                     double nmcHours) {

        this.buno = buno;
        this.month = month;
        this.fmcHours = fmcHours;
        this.pmcHours = pmcHours;
        this.nmcHours = nmcHours;
    }

    public String getBuno() {
        return buno;
    }

    public String getMonth() {
        return month;
    }

    public double getFmcHours() {
        return fmcHours;
    }

    public double getPmcHours() {
        return pmcHours;
    }

    public double getNmcHours() {
        return nmcHours;
    }

    // -------------------------
    // CALCULATED METRICS
    // -------------------------

    public double getTotalHours() {
        return fmcHours + pmcHours + nmcHours;
    }

    public double getMcRate() {
        return ((fmcHours + pmcHours) / getTotalHours()) * 100;
    }

    public double getFmcRate() {
        return (fmcHours / getTotalHours()) * 100;
    }

    public double getNmcRate() {
        return (nmcHours / getTotalHours()) * 100;
    }

    @Override
    public String toString() {

        return "\nAircraft: " + buno +
                "\nMonth: " + month +
                "\nFMC Hours: " + fmcHours +
                "\nPMC Hours: " + pmcHours +
                "\nNMC Hours: " + nmcHours +
                "\nMC Rate: " + String.format("%.1f", getMcRate()) + "%" +
                "\nFMC Rate: " + String.format("%.1f", getFmcRate()) + "%" +
                "\nNMC Rate: " + String.format("%.1f", getNmcRate()) + "%";
    }
}
