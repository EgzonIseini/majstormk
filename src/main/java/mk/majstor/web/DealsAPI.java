package mk.majstor.web;

import mk.majstor.model.deal.Deal;
import mk.majstor.model.deal.DealDto;
import mk.majstor.model.offer.Offer;
import mk.majstor.security.CurrentUser;
import mk.majstor.security.UserPrincipal;
import mk.majstor.service.DealService;
import org.springframework.data.domain.Page;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping(path = "/deals", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class DealsAPI {

    private final DealService service;

    public DealsAPI(DealService dealService) {
        this.service = dealService;
    }

    @GetMapping
    public Page<Deal> getDeals(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "madeBy") String sortBy) {
        return service.getAllDeals(pageNo, pageSize, sortBy);
    }

    @GetMapping(path = "/user")
    public Page<Deal> getDealsByUser(
            @CurrentUser UserPrincipal userPrincipal,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search
    ) {
        return service.getDealsByUser(userPrincipal.getId(), search, pageNo, pageSize);
    }

    @GetMapping(path = "/oglas/{id}")
    public Deal getDealByOglasId(@PathVariable("id") Long id) {
        return service.getDealByOglasId(id);
    }

    @PostMapping
    public Deal createNewDeal(
            @RequestBody DealDto dealDto
    ) {
        return service.createDeal(dealDto);
    }
}