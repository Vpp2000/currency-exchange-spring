package com.vpp97.moneyconverter;

import com.vpp97.moneyconverter.api.repositories.RoleRepository;
import com.vpp97.moneyconverter.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoneyConverterApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;
    public static void main(String[] args) {
        SpringApplication.run(MoneyConverterApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        long rolesCount = this.roleRepository.count();
        if (rolesCount == 0) {
            this.roleRepository.save(
                    Role.builder()
                            .name("ADMIN")
                            .build()
            );
            this.roleRepository.save(
                    Role.builder()
                            .name("USER")
                            .build()
            );
        }
    }
}
