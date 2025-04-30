package com.policymanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

//@EnableElasticsearchRepositories(basePackages = "com.policymanagement.repository")
@SpringBootApplication
public class PolicymanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(PolicymanagementApplication.class, args);
	}

}
