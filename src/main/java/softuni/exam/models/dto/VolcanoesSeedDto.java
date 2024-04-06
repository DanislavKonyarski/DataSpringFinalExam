package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;

import javax.persistence.Convert;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class VolcanoesSeedDto {
    @Size(min = 2, max = 30)
    @Expose
    @NotNull
    private String name;
    @Expose
    @Positive
    @NotNull
    private Integer elevation;
    @Expose
    private String volcanoType;
    @Expose
    @NotNull
    private Boolean isActive;
    @Expose
@Convert
    private String lastEruption;
    @Expose
    @NotNull
    private Long country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getElevation() {
        return elevation;
    }

    public void setElevation(Integer elevation) {
        this.elevation = elevation;
    }

    public String getVolcanoType() {
        return volcanoType;
    }

    public void setVolcanoType(String volcanoType) {
        this.volcanoType = volcanoType;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }


    public String getLastEruption() {
        return lastEruption;
    }


    public void setLastEruption(String lastEruption) {
        this.lastEruption = lastEruption;
    }

    public Long getCountry() {
        return country;
    }

    public void setCountry(Long country) {
        this.country = country;
    }
}
