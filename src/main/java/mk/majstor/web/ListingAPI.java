package mk.majstor.web;

import mk.majstor.model.listing.Listing;
import mk.majstor.model.listing.ListingDto;
import mk.majstor.security.CurrentUser;
import mk.majstor.security.UserPrincipal;
import mk.majstor.service.ListingService;
import org.springframework.data.domain.Page;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/oglasi", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class ListingAPI {

    private final ListingService service;

    public ListingAPI(ListingService listingService) {
        this.service = listingService;
    }


    @GetMapping
    public Page<Listing> getListings(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "") String search) {
        return service.getAllOglasi(pageNo, pageSize, sortBy, search);
    }

    @GetMapping(path = "/{id}")
    public Listing getOglasById(@PathVariable("id") Long id) {
        return service.getOglasById(id);
    }

    @PostMapping
    public Listing createOglas(@CurrentUser UserPrincipal user, @RequestBody ListingDto oglas) {
        oglas.setPostedBy(user.getId());
        return service.saveOglas(oglas);
    }

    @GetMapping(path = "/user")
    public Page<Listing> getOglasiByUser(
            @CurrentUser UserPrincipal userPrincipal,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search
    ) {
        return service.getOglasiByUser(userPrincipal.getId(), search, pageNo, pageSize);
    }

    @GetMapping(path = "/user/active")
    public Page<Listing> getActiveOglasiByUser(
            @CurrentUser UserPrincipal userPrincipal,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search
    ) {
        return service.getActiveOglasiByUser(userPrincipal.getId(), search, pageNo, pageSize);
    }

    @GetMapping(path = "/user/ended")
    public Page<Listing> getSucessfulOglasiByUser(
            @CurrentUser UserPrincipal userPrincipal,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search
    ) {
        return service.getAllSuccessfulOglasiByUser(userPrincipal.getId(), pageNo, pageSize, search);
    }

    @GetMapping(path = "/user/unsuccessful")
    public Page<Listing> getUnsuccessfulOglasiByUser(
            @CurrentUser UserPrincipal userPrincipal,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search
    ) {
        return service.getAllUnsuccessfulOglasiByUser(userPrincipal.getId(), pageNo, pageSize, search);
    }

    @PutMapping(path = "/{id}")
    public Listing modifyOglas(@RequestBody ListingDto listingDto, @PathVariable("id") Long id) {
        return service.modifyOglas(id, listingDto);
    }

    @DeleteMapping(path = "/{id}")
    public Listing deleteOglas(@PathVariable("id") Long id) {
        return service.deleteOglas(id);
    }
}
