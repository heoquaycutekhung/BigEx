package com.leap.training.gateway.repository.rowmapper;

import com.leap.training.gateway.domain.JobHistory;
import com.leap.training.gateway.service.ColumnConverter;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link JobHistory}, with proper type conversions.
 */
@Service
public class JobHistoryRowMapper implements BiFunction<Row, String, JobHistory> {

    private final ColumnConverter converter;

    public JobHistoryRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link JobHistory} stored in the database.
     */
    @Override
    public JobHistory apply(Row row, String prefix) {
        JobHistory entity = new JobHistory();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setStartDate(converter.fromRow(row, prefix + "_start_date", Instant.class));
        entity.setEndDate(converter.fromRow(row, prefix + "_end_date", Instant.class));
        entity.setSalary(converter.fromRow(row, prefix + "_salary", Long.class));
        entity.setJobId(converter.fromRow(row, prefix + "_job_id", Long.class));
        entity.setDepartmentId(converter.fromRow(row, prefix + "_department_id", Long.class));
        entity.setEmployeeId(converter.fromRow(row, prefix + "_employee_id", Long.class));
        return entity;
    }
}
