package pl.gruszm.ZephyrWork.entities;

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

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "work_session_id", referencedColumnName = "id")
    private WorkSession workSession;

    @Column(name = "location_time")
    private LocalDateTime locationTime;

    @Column(name = "latitude")
    private float latitude;

    @Column(name = "longitude")
    private float longitude;

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
