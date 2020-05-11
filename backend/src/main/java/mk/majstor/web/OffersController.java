package mk.majstor.web;

import mk.majstor.model.listing.Listing;
import mk.majstor.model.offer.Offer;
import mk.majstor.model.offer.OfferDto;
import mk.majstor.security.CurrentUser;
import mk.majstor.security.UserPrincipal;
import mk.majstor.service.OffersService;
import org.springframework.data.domain.Page;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/offers", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class OffersController {

    private OffersService offersService;

    public OffersController(OffersService offersService) {
        this.offersService = offersService;
    }

    @PostMapping
    public Offer createOffer(@CurrentUser UserPrincipal userPrincipal, @RequestBody OfferDto offer) {
        offer.setMadeBy(userPrincipal.getId());
        return offersService.createOffer(offer);
    }

    @DeleteMapping(path = "/delete/{id}")
    public Boolean deleteOfferById(
            @CurrentUser UserPrincipal userPrincipal,
            @PathVariable Long id ) {
        return offersService.deleteOffer(userPrincipal.getId(), id);
    }

    @GetMapping(path = "/oglas/{id}")
    public Page<Offer> getOffersByOglasId(
            @PathVariable("id") Long id,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return offersService.getAllOffersByListing(id, pageNo, pageSize);
    }

    @GetMapping(path = "/oglas/{id}/personal")
    public Page<Offer> getOffersByUserByOglasId(
        @PathVariable("id") Long id,
        @RequestParam(defaultValue = "0") Integer pageNo,
        @RequestParam(defaultValue = "10") Integer pageSize,
        @CurrentUser UserPrincipal userPrincipal
    ) {
        return offersService.getAllOffersByListingByUser(id, userPrincipal.getId(), pageNo, pageSize);
    }

    @GetMapping
    public Page<Offer> getOffers(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "madeBy") String sortBy,
            @RequestParam(defaultValue = "") String search) {
        return offersService.getAllOffers(pageNo, pageSize, sortBy, search);
    }

}
