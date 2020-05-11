package mk.majstor.model.deal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DealDto {

    private Long listingId;

    private Long userId;

    private Long madeById;

    private Float price;

}
