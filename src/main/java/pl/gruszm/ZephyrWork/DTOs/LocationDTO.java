package pl.gruszm.ZephyrWork.DTOs;

import java.time.LocalDateTime;

public class LocationDTO
{
    private LocalDateTime locationTime;
    private float latitude;
    private float longitude;

    public LocationDTO(LocalDateTime locationTime, float latitude, float longitude)
    {
        this.locationTime = locationTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocalDateTime getLocationTime()
    {
        return locationTime;
    }

    public void setLocationTime(LocalDateTime locationTime)
    {
        this.locationTime = locationTime;
    }

    public float getLatitude()
    {
        return latitude;
    }

    public void setLatitude(float latitude)
    {
        this.latitude = latitude;
    }

    public float getLongitude()
    {
        return longitude;
    }

    public void setLongitude(float longitude)
    {
        this.longitude = longitude;
    }
}
