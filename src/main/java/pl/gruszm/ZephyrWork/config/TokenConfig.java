package pl.gruszm.ZephyrWork.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "token")
public class TokenConfig
{
    private String secret;
    private long expirationTimeSeconds;

    public String getSecret()
    {
        return secret;
    }

    public void setSecret(String secret)
    {
        this.secret = secret;
    }

    public long getExpirationTimeSeconds()
    {
        return expirationTimeSeconds;
    }

    public void setExpirationTimeSeconds(long expirationTimeSeconds)
    {
        this.expirationTimeSeconds = expirationTimeSeconds;
    }
}
