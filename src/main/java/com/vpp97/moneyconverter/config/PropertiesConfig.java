package com.vpp97.moneyconverter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource(value = "classpath:configs/mysql_config.properties")
})
public class PropertiesConfig {
}
