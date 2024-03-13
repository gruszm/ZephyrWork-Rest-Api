package pl.gruszm.ZephyrWork.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "work_sessions")
public class WorkSession
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "workSession")
    private List<Location> locations;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public LocalDateTime getStartTime()
    {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime)
    {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime()
    {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime)
    {
        this.endTime = endTime;
    }

    public List<Location> getLocations()
    {
        return locations;
    }

    public void addLocation(Location location)
    {
        if (locations == null)
        {
            locations = new ArrayList<>();
        }

        location.setWorkSession(this);
        locations.add(location);
    }

    public void addLocations(Location ... locations)
    {
        if (this.locations == null)
        {
            this.locations = new ArrayList<>();
        }

        Arrays.stream(locations).forEach(l -> l.setWorkSession(this));
        this.locations.addAll(Arrays.stream(locations).toList());
    }
}
