package mk.majstor.web;

import mk.majstor.model.listing.UserListingDetail;
import mk.majstor.model.user.CompanyUser;
import mk.majstor.model.user.PrivateUser;
import mk.majstor.model.user.User;
import mk.majstor.security.CurrentUser;
import mk.majstor.security.UserPrincipal;
import mk.majstor.service.UserService;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/users", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/all")
    public List<User> getAllUsers(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "name") String sortBy) {
        return this.userService.getAllUsers(pageNo, pageSize, sortBy);
    }

    @GetMapping()
    public User getUserById(@CurrentUser UserPrincipal user) {
        return this.userService.getUserById(user.getId());
    }

    @GetMapping(path = "/email")
    public User getUserByEmail(@RequestParam("q") String email) {
        return this.userService.getUserByEmail(email);
    }


    @PutMapping(path = "/private")
    public User modifyPrivateUser(@RequestBody PrivateUser user, @CurrentUser UserPrincipal userPrincipal) {
        return userService.modifyUser(userPrincipal.getId(), user);
    }

    @PutMapping(path = "/company")
    public User modifyCompanyUser(@RequestBody CompanyUser user, @CurrentUser UserPrincipal userPrincipal) {
        return userService.modifyUser(userPrincipal.getId(), user);
    }

    @PatchMapping(path = "/follow/{to}")
    public User followUser(@CurrentUser UserPrincipal userPrincipal, @PathVariable("to") Long toFollow)
    {
        return userService.followUser(userPrincipal.getId(), toFollow);
    }

    @GetMapping(path = "/followers")
    public List<User> getUserFollowers(@CurrentUser UserPrincipal userPrincipal) {
        return userService.getUserFollowers(userPrincipal.getId());
    }

    @GetMapping(path = "/following")
    public List<User> getUserFollowing(@CurrentUser UserPrincipal userPrincipal) {
        return userService.getUserFollowing(userPrincipal.getId());
    }

    @GetMapping(path = "/oglasi/details")
    public UserListingDetail getUserListingDetails(@CurrentUser UserPrincipal userPrincipal) {
        return userService.getUserListingDetails(userPrincipal.getId());
    }

}
