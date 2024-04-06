package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountrySeedDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;


@Service
public class CountryServiceImpl implements CountryService {


    public static final String FILE_PATH_COUNTRIES = "src/main/resources/files/json/countries.json";

    private CountryRepository countryRepository;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;
    private Gson gson;

    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return countryRepository.count()>0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH_COUNTRIES));
    }

    @Override
    public String importCountries() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readCountriesFromFile(), CountrySeedDto[].class))
                .filter(countrySeedDto -> {
                    Optional<Country> country = countryRepository.findAllByName(countrySeedDto.getName());
                    boolean isValid = validationUtil.isValid(countrySeedDto) && country.isEmpty();

                    if (isValid){
                        sb.append(String.format("Successfully imported country %s - %s\n"
                        ,countrySeedDto.getName(),countrySeedDto.getCapital()));
                    }else {
                        sb.append("Invalid country\n");
                    }
                    return isValid;
                }).map(countrySeedDto -> modelMapper.map(countrySeedDto,Country.class))
                .forEach(countryRepository::save);


        return sb.toString();
    }
}
