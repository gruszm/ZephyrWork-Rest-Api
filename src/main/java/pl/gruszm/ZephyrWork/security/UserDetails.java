package pl.gruszm.ZephyrWork.security;

public class UserDetails
{
    private String email;

    public UserDetails(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
