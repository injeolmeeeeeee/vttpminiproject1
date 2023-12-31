package vttp.iss.nus.miniproject1vttp.model;

import java.io.Serializable;

public class ResultDetails implements Serializable{
    private String positionText;
    private String driverName;
    private String constructor;
    private String time;
    private String status;

    public ResultDetails(String positionText, String driverName, String constructor, String time, String status) {
        this.positionText = positionText;
        this.driverName = driverName;
        this.constructor = constructor;
        this.time = time;
        this.status = status;
    }

    public String getPositionText() {return positionText;}
    public void setPositionText(String positionText) {this.positionText = positionText;}
    public String getDriverName() {return driverName;}
    public void setDriverName(String driverName) {this.driverName = driverName;}
    public String getConstructor() {return constructor;}
    public void setConstructor(String constructor) {this.constructor = constructor;}
    public String getTime() {return time;}
    public void setTime(String time) {this.time = time;}
    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}

}