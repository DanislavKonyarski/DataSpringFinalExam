package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.VolcanologistSeedRootDto;
import softuni.exam.models.entity.Volcano;
import softuni.exam.models.entity.Volcanologist;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.repository.VolcanologistRepository;
import softuni.exam.service.VolcanologistService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class VolcanologistServiceImpl implements VolcanologistService {

    public static final String FILE_PATH_VOLCANOLOGIST = "src/main/resources/files/xml/volcanologists.xml";
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;
    private VolcanologistRepository volcanologistRepository;
    private XmlParser xmlParser;
    private VolcanoRepository volcanoRepository;

    public VolcanologistServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil, VolcanologistRepository volcanologistRepository, XmlParser xmlParser, VolcanoRepository volcanoRepository) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.volcanologistRepository = volcanologistRepository;
        this.xmlParser = xmlParser;
        this.volcanoRepository = volcanoRepository;
    }

    @Override
    public boolean areImported() {
        return volcanologistRepository.count()>0;
    }

    @Override
    public String readVolcanologistsFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH_VOLCANOLOGIST));
    }

    @Override
    public String importVolcanologists() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        xmlParser.fromFile(FILE_PATH_VOLCANOLOGIST, VolcanologistSeedRootDto.class).getVolcanologist().stream()
                .filter(volcanologistSeedDto -> {
                    Optional<Volcanologist> volcanologist =volcanologistRepository.findAllByFirstNameAndLastName(
                            volcanologistSeedDto.getFirstName(),volcanologistSeedDto.getLastName());

                    Optional<Volcano> volcano = volcanoRepository.findById(volcanologistSeedDto.getVolcano());
                    boolean isValid = validationUtil.isValid(volcanologistSeedDto) && volcanologist.isEmpty()
                            &&volcano.isPresent();
                    if (isValid){
                        sb.append(String.format("Successfully imported volcanologist %s %s\n",
                                volcanologistSeedDto.getFirstName(),volcanologistSeedDto.getLastName()));
                    }else {
                        sb.append("Invalid volcanologist\n");
                    }

                    return isValid;
                }).map(volcanologistSeedDto -> {
                    Volcanologist volcanologist = modelMapper.map(volcanologistSeedDto,Volcanologist.class);
                    volcanologist.setVolcano(volcanoRepository.findById(volcanologistSeedDto.getVolcano()).orElse(null));
                    return volcanologist;
                }).forEach(volcanologistRepository::save);

        return sb.toString();
    }
}
