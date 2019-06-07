package com.app.shipapp.modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SearchModal implements Serializable {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("ship_name")
@Expose
private String shipName;
@SerializedName("imo_number")
@Expose
private String imoNumber;
@SerializedName("call_sign")
@Expose
private String callSign;
@SerializedName("mmsi")
@Expose
private String mmsi;
@SerializedName("gross_tonnage")
@Expose
private String grossTonnage;
@SerializedName("net_tonnage")
@Expose
private String netTonnage;
@SerializedName("length_overall")
@Expose
private String lengthOverall;
@SerializedName("traffic_reports")
@Expose
private String trafficReports;
@SerializedName("incident_reports")
@Expose
private String incidentReports;
@SerializedName("cost")
@Expose
private String cost;
@SerializedName("is_active")
@Expose
private String isActive;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getShipName() {
return shipName;
}

public void setShipName(String shipName) {
this.shipName = shipName;
}

public String getImoNumber() {
return imoNumber;
}

public void setImoNumber(String imoNumber) {
this.imoNumber = imoNumber;
}

public String getCallSign() {
return callSign;
}

public void setCallSign(String callSign) {
this.callSign = callSign;
}

public String getMmsi() {
return mmsi;
}

public void setMmsi(String mmsi) {
this.mmsi = mmsi;
}

public String getGrossTonnage() {
return grossTonnage;
}

public void setGrossTonnage(String grossTonnage) {
this.grossTonnage = grossTonnage;
}

public String getNetTonnage() {
return netTonnage;
}

public void setNetTonnage(String netTonnage) {
this.netTonnage = netTonnage;
}

public String getLengthOverall() {
return lengthOverall;
}

public void setLengthOverall(String lengthOverall) {
this.lengthOverall = lengthOverall;
}

public String getTrafficReports() {
return trafficReports;
}

public void setTrafficReports(String trafficReports) {
this.trafficReports = trafficReports;
}

public String getIncidentReports() {
return incidentReports;
}

public void setIncidentReports(String incidentReports) {
this.incidentReports = incidentReports;
}

public String getCost() {
return cost;
}

public void setCost(String cost) {
this.cost = cost;
}

public String getIsActive() {
return isActive;
}

public void setIsActive(String isActive) {
this.isActive = isActive;
}

}