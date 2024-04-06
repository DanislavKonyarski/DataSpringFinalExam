package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.VolcanoesSeedDto;
import softuni.exam.models.entity.Volcano;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.service.VolcanoService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class VolcanoServiceImpl implements VolcanoService {

    public static final String FILE_PATH_VOLCANO = "src/main/resources/files/json/volcanoes.json";
    private VolcanoRepository volcanoRepository;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;
    private Gson gson;
    private CountryRepository countryRepository;

    public VolcanoServiceImpl(VolcanoRepository volcanoRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson, CountryRepository countryRepository) {
        this.volcanoRepository = volcanoRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.countryRepository = countryRepository;
    }

    @Override
    public boolean areImported() {
        return volcanoRepository.count()>0;
    }

    @Override
    public String readVolcanoesFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH_VOLCANO));
    }

    @Override
    public String importVolcanoes() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readVolcanoesFileContent(),VolcanoesSeedDto[].class))
                .filter(volcanoesSeedDto -> {
                    Optional<Volcano> volcano = volcanoRepository.findAllByName(volcanoesSeedDto.getName());
                    boolean isValid = validationUtil.isValid(volcanoesSeedDto)&&volcano.isEmpty();

                    if(isValid){
                        sb.append(String.format("Successfully imported volcano %s of type %s\n",
                                volcanoesSeedDto.getName(),volcanoesSeedDto.getVolcanoType()));
                    }else {
                        sb.append("Invalid volcano\n");
                    }

                    return isValid;
                }).map(volcanoesSeedDto -> {
                    Volcano volcano = modelMapper.map(volcanoesSeedDto,Volcano.class);
                    volcano.setCountry(countryRepository.getById(volcanoesSeedDto.getCountry()));

                    return volcano;
                }).forEach(volcanoRepository::save);

        return sb.toString();
    }


    @Override
    public String exportVolcanoes() {
        StringBuilder sb = new StringBuilder();

       volcanoRepository
                .findAllByExport()
               .forEach(volcano -> {
                   if (volcano.getLastEruption() != null) {
                       sb.append(String.format("Volcano: %s%n", volcano.getName()));
                       sb.append(String.format("   *Located in: %s%n", volcano.getCountry().getName()));
                       sb.append(String.format("   **Elevation: %d%n", volcano.getElevation()));
                       sb.append(String.format("   ***Last eruption on: %s%n", volcano.getLastEruption()));
                   }
               });
        return sb.toString();
    }
}

