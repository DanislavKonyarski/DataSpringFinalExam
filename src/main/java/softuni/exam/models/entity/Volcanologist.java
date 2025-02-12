package softuni.exam.models.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Optional;

@Entity
@Table(name = "volcanologists")
public class Volcanologist extends BaseEntity{

    @Column(nullable = false)
    private Integer age;
    @Column(name = "exploring_from")
    private LocalDate exploringFrom;
    @Column(name = "first_name",nullable = false,unique = true)
    private String firstName;
    @Column(name = "last_name",nullable = false,unique = true)
    private String lastName;
    @Column(nullable = false)
    private Double salary;

    @ManyToOne
    @JoinColumn(name = "exploring_volcano_id", referencedColumnName = "id")
    private Volcano volcano;

    public Volcanologist() {
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getExploringFrom() {
        return exploringFrom;
    }

    public void setExploringFrom(LocalDate exploringFrom) {
        this.exploringFrom = exploringFrom;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Volcano getVolcano() {
        return volcano;
    }

    public void setVolcano(Volcano volcano) {
        this.volcano = volcano;
    }
}
