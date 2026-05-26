package model;

public class MaintenanceAction {

    private int actionId;
    private String buno;
    private String jcn;
    private String actionDate;
    private String actionCode;
    private String actionName;
    private double maintenanceHours;
    private String corrosionType;

    public MaintenanceAction(int actionId,
                             String buno,
                             String jcn,
                             String actionDate,
                             String actionCode,
                             String actionName,
                             double maintenanceHours,
                             String corrosionType) {

        this.actionId = actionId;
        this.buno = buno;
        this.jcn = jcn;
        this.actionDate = actionDate;
        this.actionCode = actionCode;
        this.actionName = actionName;
        this.maintenanceHours = maintenanceHours;
        this.corrosionType = corrosionType;
    }

    public int getActionId() {
        return actionId;
    }

    public String getBuno() {
        return buno;
    }

    public String getJcn() {
        return jcn;
    }

    public String getActionDate() {
        return actionDate;
    }

    public String getActionCode() {
        return actionCode;
    }

    public String getActionName() {
        return actionName;
    }

    public double getMaintenanceHours() {
        return maintenanceHours;
    }

    public String getCorrosionType() {
        return corrosionType;
    }

    @Override
    public String toString() {
        return buno + " | " +
               jcn + " | " +
               actionDate + " | " +
               actionName + " | " +
               maintenanceHours + " hrs | " +
               corrosionType;
    }
}
