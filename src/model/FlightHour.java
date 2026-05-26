package model;

public class FlightHour {

    private String buno;
    private String month;
    private double flightHours;

    public FlightHour(String buno, String month, double flightHours) {
        this.buno = buno;
        this.month = month;
        this.flightHours = flightHours;
    }

    public String getBuno() {
        return buno;
    }

    public String getMonth() {
        return month;
    }

    public double getFlightHours() {
        return flightHours;
    }

    @Override
    public String toString() {
        return buno + " | " + month + " | " + flightHours;
    }
}
