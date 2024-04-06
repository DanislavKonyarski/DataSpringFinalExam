package softuni.exam.models.dto;

import softuni.exam.util.LocalDateAdaptor;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement(name = "volcanologist")
@XmlAccessorType(XmlAccessType.FIELD)
public class VolcanologistSeedDto {

//    <first_name>John</first_name>
//        <last_name>Doe</last_name>
//        <salary>5000.00</salary>
//        <age>55</age>
//        <exploring_from>1987-01-15</exploring_from>
//        <exploring_volcano_id>11</exploring_volcano_id>
    @XmlElement(name = "first_name")
    @Size(min = 2, max = 30)
    @NotNull
    private String firstName;
    @XmlElement(name = "last_name")
    @Size(min = 2, max = 30)
    @NotNull
    private String lastName;
    @XmlElement
    @Positive
    @NotNull
    private Double salary;
    @XmlElement
    @Min(value = 18)
    @Max(value = 80)
    @NotNull
    private Integer age;
    @XmlElement(name = "exploring_from")
    @XmlJavaTypeAdapter(LocalDateAdaptor.class)
    private LocalDate exploringFrom;
    @XmlElement(name = "exploring_volcano_id")
    private Long volcano;

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

    public Long getVolcano() {
        return volcano;
    }

    public void setVolcano(Long volcano) {
        this.volcano = volcano;
    }
}
