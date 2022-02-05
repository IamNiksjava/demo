package com.example.demo.tasklet;

import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.SupplierRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
@Slf4j
public class ReaderTasklet  implements Tasklet, StepExecutionListener {

//    Logger log=LoggerFactory.getLogger(ReaderTasklet.class);

    @Autowired
    private CustomerRepository customerRepository;


    private List<Customer>  customerList;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
     System.out.println("Inside RepeatStatus");
        customerList= customerRepository.findAll();
       customerList.forEach(System.out::println);
        return RepeatStatus.FINISHED;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        stepExecution.getJobExecution().getExecutionContext().put("custList",customerList);
                 System.out.println("Inside afterstep"+stepExecution.getJobExecution().getExecutionContext().get("custList"));

        return ExitStatus.COMPLETED;
    }
}
