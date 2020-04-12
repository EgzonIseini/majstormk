package mk.majstor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mk.majstor.model.listing.ListingDto;
import mk.majstor.model.user.CompanyUser;
import mk.majstor.model.user.PrivateUser;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MajstorApplication {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerSubtypes(new NamedType(PrivateUser.class, "PrivateUser"));
        mapper.registerSubtypes(new NamedType(CompanyUser.class, "CompanyUser"));
        mapper.registerSubtypes(new NamedType(ListingDto.class, "OglasPayload"));
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(MajstorApplication.class, args);
    }

}
