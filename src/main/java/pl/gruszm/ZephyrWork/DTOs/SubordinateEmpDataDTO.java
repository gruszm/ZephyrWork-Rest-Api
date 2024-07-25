package pl.gruszm.ZephyrWork.DTOs;

public class SubordinateEmpDataDTO
{
    private int startingHour;
    private int startingMinute;
    private int endingHour;
    private int endingMinute;
    private int locationRegistrationInterval;
    private boolean forceStartWorkSession;

    public int getStartingHour()
    {
        return startingHour;
    }

    public SubordinateEmpDataDTO setStartingHour(int startingHour)
    {
        this.startingHour = startingHour;

        return this;
    }

    public int getStartingMinute()
    {
        return startingMinute;
    }

    public SubordinateEmpDataDTO setStartingMinute(int startingMinute)
    {
        this.startingMinute = startingMinute;

        return this;
    }

    public int getEndingHour()
    {
        return endingHour;
    }

    public SubordinateEmpDataDTO setEndingHour(int endingHour)
    {
        this.endingHour = endingHour;

        return this;
    }

    public int getEndingMinute()
    {
        return endingMinute;
    }

    public SubordinateEmpDataDTO setEndingMinute(int endingMinute)
    {
        this.endingMinute = endingMinute;

        return this;
    }

    public int getLocationRegistrationInterval()
    {
        return locationRegistrationInterval;
    }

    public SubordinateEmpDataDTO setLocationRegistrationInterval(int locationRegistrationInterval)
    {
        this.locationRegistrationInterval = locationRegistrationInterval;

        return this;
    }

    public boolean isForceStartWorkSession()
    {
        return forceStartWorkSession;
    }

    public SubordinateEmpDataDTO setForceStartWorkSession(boolean forceStartWorkSession)
    {
        this.forceStartWorkSession = forceStartWorkSession;

        return this;
    }
}
