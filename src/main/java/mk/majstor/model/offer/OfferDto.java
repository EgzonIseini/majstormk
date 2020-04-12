package mk.majstor.model.offer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
public class OfferDto {

    @Setter
    private Long madeBy;

    private final String description;

    private final Boolean wasAccepted;

    private final Long oglasId;

    private Float offeredPrice;

    @JsonCreator
    public OfferDto(
            @JsonProperty("userId") Long madeBy,
            @JsonProperty("description") String description,
            @JsonProperty("wasAccepted") Boolean wasAccepted,
            @JsonProperty("oglasId") Long oglasId,
            @JsonProperty("offeredPrice") Float offeredPrice
    ) {
        this.madeBy = madeBy;
        this.description = description;
        this.wasAccepted = wasAccepted;
        this.oglasId = oglasId;
        this.offeredPrice = offeredPrice;
    }
}
