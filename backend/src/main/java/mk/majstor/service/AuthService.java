package mk.majstor.service;

import mk.majstor.model.auth.JwtAuthenticationResponse;
import mk.majstor.model.auth.LoginRequest;
import mk.majstor.model.auth.PrivateUserSignUpRequest;
import mk.majstor.model.user.CompanyUser;
import mk.majstor.model.user.PrivateUser;
import mk.majstor.model.user.User;
import mk.majstor.security.UserPrincipal;

public interface AuthService {

    JwtAuthenticationResponse loginUser(LoginRequest loginRequest);

    User registerPrivateUser(PrivateUser privateUser);

    User registerCompanyUser(CompanyUser companyUser);

    User changeUserPassword(UserPrincipal user, String newPassword);

    Boolean checkPassword(UserPrincipal user, String password);

}
