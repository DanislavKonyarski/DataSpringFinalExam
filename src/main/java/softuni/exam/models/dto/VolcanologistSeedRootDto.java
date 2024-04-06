package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "volcanologists")
@XmlAccessorType(XmlAccessType.FIELD)
public class VolcanologistSeedRootDto implements Serializable {

    @XmlElement(name = "volcanologist")
    private List<VolcanologistSeedDto> volcanologist;

    public List<VolcanologistSeedDto> getVolcanologist() {
        return volcanologist;
    }

    public void setVolcanologist(List<VolcanologistSeedDto> volcanologist) {
        this.volcanologist = volcanologist;
    }
}
