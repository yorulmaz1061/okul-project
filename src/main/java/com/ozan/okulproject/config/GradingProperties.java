package com.ozan.okulproject.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "grading")
@Getter
@Setter
@AllArgsConstructor
public class GradingProperties {
    private final double midtermWeight;
    private final double finalWeight;
    private final int absenceLimit;
    private final int finalMin;
    private final int termMin;
}
