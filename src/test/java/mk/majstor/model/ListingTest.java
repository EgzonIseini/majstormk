package mk.majstor.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mk.majstor.model.listing.Listing;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ListingTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerSubtypes(new NamedType(Listing.class, "Oglas"));
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void serializeOglas() throws IOException {
        //PrivateUser user = new PrivateUser("Egzon", "Iseini", "asdsa", "", "123", LocalDate.of(1998, 10, 3), "", City.Skopje, 1000);
        //Oglas oglas = new Oglas("Krecam soba 5x5m", user, "Nudam krecenje soba hahahah", LocalDateTime.now(), City.Kicevo);

        //System.out.println(mapper.writeValueAsString(oglas));
    }
}