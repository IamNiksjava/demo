package com.example.demo.config;


import com.example.demo.jobs.MigrationJob;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class JobConfig {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private MigrationJob migrationJob;

    @Scheduled(fixedRate = 300000)
    void runMigrationJob(){

        JobParameters jobParameter=new JobParametersBuilder().toJobParameters();
         try {
             jobLauncher.run(migrationJob.runMigrationJob(), jobParameter);
         }
         catch (Exception e){
             e.printStackTrace();
         };
    }
}
