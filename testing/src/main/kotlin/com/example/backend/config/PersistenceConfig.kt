package com.example.backend.config

import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan("com.example.backend.entity")
@EnableJpaRepositories("com.example.backend.repository")
class PersistenceConfig