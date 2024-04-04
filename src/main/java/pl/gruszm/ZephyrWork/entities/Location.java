package pl.gruszm.ZephyrWork.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "locations")
public class Location
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "location_time")
    private LocalDateTime locationTime;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "work_session_id", referencedColumnName = "id", nullable = false)
    private WorkSession workSession;

    public Location()
    {
        this.locationTime = LocalDateTime.now();
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public WorkSession getWorkSession()
    {
        return workSession;
    }

    public void setWorkSession(WorkSession workSession)
    {
        this.workSession = workSession;
    }

    public LocalDateTime getLocationTime()
    {
        return locationTime;
    }

    public void setLocationTime(LocalDateTime locationTime)
    {
        this.locationTime = locationTime;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }
}
