package com.example.demo.tasklet;

import com.example.demo.model.Customer;
import com.example.demo.model.Supplier;
import com.example.demo.repository.SupplierRepository;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class WriterTasklet implements Tasklet, StepExecutionListener {


    final String INSERT_QUERY = "insert into `supplier` (`id`,`name`, `desc`) values (?,?,?)";



    List<Supplier> suppliers;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.suppliers= (List<Supplier>) stepExecution.getJobExecution().getExecutionContext().get("supplierList");
        System.out.println("inside beforestep"+this.suppliers);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {


        return null;
    }

    public void save(Supplier supp) {
         jdbcTemplate.update(INSERT_QUERY,supp.getId(), supp.getName(), supp.getDesc());
    }




    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println("inside execute"+this.suppliers+"---"+(Objects.nonNull(suppliers) && !suppliers.isEmpty()));
          if (Objects.nonNull(suppliers) && !suppliers.isEmpty())
          {
              //supplierRepository.saveAll(suppliers);
            suppliers.forEach(this::save);
          }
        return RepeatStatus.FINISHED;
    }
}
