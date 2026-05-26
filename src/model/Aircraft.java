package model;

public class Aircraft {

    private String buno;
    private String squadron;

    public Aircraft(String buno, String squadron) {
        this.buno = buno;
        this.squadron = squadron;
    }

    public String getBuno() {
        return buno;
    }

    public String getSquadron() {
        return squadron;
    }

    @Override
    public String toString() {
        return buno + " | " + squadron;
    }
}
