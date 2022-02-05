package com.example.demo.jobs;

import com.example.demo.tasklet.ProcessorTasklet;
import com.example.demo.tasklet.ReaderTasklet;
import com.example.demo.tasklet.WriterTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class MigrationJob {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ReaderTasklet readerTasklet;
    @Autowired
    private ProcessorTasklet processorTasklet;
    @Autowired
    private WriterTasklet writerTasklet;

    public Job runMigrationJob(){
        return jobBuilderFactory.get("migration Job")
                .start(migrationJobReadStep())
                .next(migrationJobProcessorStep())
                .next(migrationJobWriterStep())
                .build();
    }
     @Bean
     Step migrationJobReadStep(){
        return stepBuilderFactory.get("read step").tasklet(readerTasklet).build();
    }
     @Bean
     Step migrationJobProcessorStep(){
        return stepBuilderFactory.get("processor step").tasklet(processorTasklet).build();
    }
    @Bean
     Step migrationJobWriterStep(){
        return stepBuilderFactory.get("writer step").tasklet(writerTasklet).build();
    }





}
