package pl.gruszm.ZephyrWork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.gruszm.ZephyrWork.config.TokenConfig;

@SpringBootApplication
@EnableConfigurationProperties(TokenConfig.class)
public class ZephyrWorkApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ZephyrWorkApplication.class, args);
    }
}
