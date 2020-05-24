package com.heima.article.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.heima.model.mappers")
public class MysqlConfig {
}
