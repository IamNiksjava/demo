package com.example.demo.tasklet;

import com.example.demo.model.Customer;
import com.example.demo.model.Supplier;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProcessorTasklet implements Tasklet, StepExecutionListener {

    private List<Customer> customerList;
    private List<Supplier> supplierList;


    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("Inside beforestep"+stepExecution.getJobExecution().getExecutionContext().get("custList"));
        this.customerList= (List<Customer>) stepExecution.getJobExecution().getExecutionContext().get("custList");
        System.out.println("This is List"+this.customerList);

    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println("inside Execute"+this.customerList);
        this.supplierList=customerList.stream().map(this::convertToSupplier).collect(Collectors.toList());
        System.out.println("inside Execute"+this.supplierList);
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        stepExecution.getJobExecution().getExecutionContext().put("supplierList",supplierList);
        return ExitStatus.COMPLETED;
    }

    private Supplier convertToSupplier(Customer customer){
        Supplier sup=new Supplier();
        sup.setId(customer.getId());
        sup.setName("Mr."+customer.getName());
        sup.setDesc(customer.getDesc());
        return sup;
    }

}
