package com.leap.training.gateway.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * The Employee entity.
 */
@ApiModel(description = "The Employee entity.")
@Table("employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    /**
     * The firstname attribute.
     */
    @ApiModelProperty(value = "The firstname attribute.")
    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("email")
    private String email;

    @Column("phone_number")
    private String phoneNumber;

    @Column("hire_date")
    private Instant hireDate;

    @Column("salary")
    private Long salary;

    @Column("commission_pct")
    private Long commissionPct;

    @Transient
    @JsonIgnoreProperties(
        value = { "subEmployees", "jobHistorys", "managedDepartments", "job", "manager", "department" },
        allowSetters = true
    )
    private Set<Employee> subEmployees = new HashSet<>();

    @Transient
    @JsonIgnoreProperties(value = { "job", "department", "employee" }, allowSetters = true)
    private Set<JobHistory> jobHistorys = new HashSet<>();

    @Transient
    @JsonIgnoreProperties(value = { "employees", "jobHistories", "manager", "location" }, allowSetters = true)
    private Set<Department> managedDepartments = new HashSet<>();

    @Transient
    @JsonIgnoreProperties(value = { "employees", "jobHistories" }, allowSetters = true)
    private Job job;

    @Transient
    @JsonIgnoreProperties(
        value = { "subEmployees", "jobHistorys", "managedDepartments", "job", "manager", "department" },
        allowSetters = true
    )
    private Employee manager;

    @Transient
    @JsonIgnoreProperties(value = { "employees", "jobHistories", "manager", "location" }, allowSetters = true)
    private Department department;

    @Column("job_id")
    private Long jobId;

    @Column("manager_id")
    private Long managerId;

    @Column("department_id")
    private Long departmentId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Employee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Employee firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Employee lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public Employee email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Employee phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Instant getHireDate() {
        return this.hireDate;
    }

    public Employee hireDate(Instant hireDate) {
        this.setHireDate(hireDate);
        return this;
    }

    public void setHireDate(Instant hireDate) {
        this.hireDate = hireDate;
    }

    public Long getSalary() {
        return this.salary;
    }

    public Employee salary(Long salary) {
        this.setSalary(salary);
        return this;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Long getCommissionPct() {
        return this.commissionPct;
    }

    public Employee commissionPct(Long commissionPct) {
        this.setCommissionPct(commissionPct);
        return this;
    }

    public void setCommissionPct(Long commissionPct) {
        this.commissionPct = commissionPct;
    }

    public Set<Employee> getSubEmployees() {
        return this.subEmployees;
    }

    public void setSubEmployees(Set<Employee> employees) {
        if (this.subEmployees != null) {
            this.subEmployees.forEach(i -> i.setManager(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setManager(this));
        }
        this.subEmployees = employees;
    }

    public Employee subEmployees(Set<Employee> employees) {
        this.setSubEmployees(employees);
        return this;
    }

    public Employee addSubEmployees(Employee employee) {
        this.subEmployees.add(employee);
        employee.setManager(this);
        return this;
    }

    public Employee removeSubEmployees(Employee employee) {
        this.subEmployees.remove(employee);
        employee.setManager(null);
        return this;
    }

    public Set<JobHistory> getJobHistorys() {
        return this.jobHistorys;
    }

    public void setJobHistorys(Set<JobHistory> jobHistories) {
        if (this.jobHistorys != null) {
            this.jobHistorys.forEach(i -> i.setEmployee(null));
        }
        if (jobHistories != null) {
            jobHistories.forEach(i -> i.setEmployee(this));
        }
        this.jobHistorys = jobHistories;
    }

    public Employee jobHistorys(Set<JobHistory> jobHistories) {
        this.setJobHistorys(jobHistories);
        return this;
    }

    public Employee addJobHistorys(JobHistory jobHistory) {
        this.jobHistorys.add(jobHistory);
        jobHistory.setEmployee(this);
        return this;
    }

    public Employee removeJobHistorys(JobHistory jobHistory) {
        this.jobHistorys.remove(jobHistory);
        jobHistory.setEmployee(null);
        return this;
    }

    public Set<Department> getManagedDepartments() {
        return this.managedDepartments;
    }

    public void setManagedDepartments(Set<Department> departments) {
        if (this.managedDepartments != null) {
            this.managedDepartments.forEach(i -> i.setManager(null));
        }
        if (departments != null) {
            departments.forEach(i -> i.setManager(this));
        }
        this.managedDepartments = departments;
    }

    public Employee managedDepartments(Set<Department> departments) {
        this.setManagedDepartments(departments);
        return this;
    }

    public Employee addManagedDepartments(Department department) {
        this.managedDepartments.add(department);
        department.setManager(this);
        return this;
    }

    public Employee removeManagedDepartments(Department department) {
        this.managedDepartments.remove(department);
        department.setManager(null);
        return this;
    }

    public Job getJob() {
        return this.job;
    }

    public void setJob(Job job) {
        this.job = job;
        this.jobId = job != null ? job.getId() : null;
    }

    public Employee job(Job job) {
        this.setJob(job);
        return this;
    }

    public Employee getManager() {
        return this.manager;
    }

    public void setManager(Employee employee) {
        this.manager = employee;
        this.managerId = employee != null ? employee.getId() : null;
    }

    public Employee manager(Employee employee) {
        this.setManager(employee);
        return this;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
        this.departmentId = department != null ? department.getId() : null;
    }

    public Employee department(Department department) {
        this.setDepartment(department);
        return this;
    }

    public Long getJobId() {
        return this.jobId;
    }

    public void setJobId(Long job) {
        this.jobId = job;
    }

    public Long getManagerId() {
        return this.managerId;
    }

    public void setManagerId(Long employee) {
        this.managerId = employee;
    }

    public Long getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(Long department) {
        this.departmentId = department;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", hireDate='" + getHireDate() + "'" +
            ", salary=" + getSalary() +
            ", commissionPct=" + getCommissionPct() +
            "}";
    }
}
