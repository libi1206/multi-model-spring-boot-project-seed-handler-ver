package com.company.project.configurer.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author libi@hyperchain.cn
 * @date 2022/8/29 5:13 PM
 * 从application.yml中加载的配置
 */
@ConfigurationProperties(prefix = "common")
@Configuration
@Data
public class CommonConfig {
}
