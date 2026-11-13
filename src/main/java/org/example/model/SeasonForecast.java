package org.example.model;

public class SeasonForecast {
    private String locationId;
    private String locationName;
    private String season;
    private String referenceMonth;
    private String periodLabel;
    private String summary;
    private String detail;
    private String sourceName;
    private String sourceUrl;
    private String sourceDate;
    private boolean exactDatesAvailable;

    public String getLocationId() { return locationId; }
    public String getLocationName() { return locationName; }
    public String getSeason() { return season; }
    public String getReferenceMonth() { return referenceMonth; }
    public String getPeriodLabel() { return periodLabel; }
    public String getSummary() { return summary; }
    public String getDetail() { return detail; }
    public String getSourceName() { return sourceName; }
    public String getSourceUrl() { return sourceUrl; }
    public String getSourceDate() { return sourceDate; }
    public boolean isExactDatesAvailable() { return exactDatesAvailable; }
}
