package softuni.exam.models.entity;

import softuni.exam.models.enums.VolcanoType;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "volcanoes")
public class Volcano extends BaseEntity {

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    @Column(nullable = false)
    private Integer elevation;
    @Column(name = "last_eruption")

    private LocalDate lastEruption;
    @Column(nullable = false,unique = true)
    private String name;
    @Column(name = "volcano_type")
    @Enumerated(EnumType.STRING)
    private VolcanoType volcanoType;
    @ManyToOne
    @JoinColumn(name = "country_id",referencedColumnName = "id")
    private Country country;

    public Volcano() {
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getElevation() {
        return elevation;
    }

    public void setElevation(Integer elevation) {
        this.elevation = elevation;
    }

    public LocalDate getLastEruption() {
        return lastEruption;
    }

    public void setLastEruption(LocalDate lastEruption) {
        this.lastEruption = lastEruption;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VolcanoType getVolcanoType() {
        return volcanoType;
    }

    public void setVolcanoType(VolcanoType volcanoType) {
        this.volcanoType = volcanoType;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
