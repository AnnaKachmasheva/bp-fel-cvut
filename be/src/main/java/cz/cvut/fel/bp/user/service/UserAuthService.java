package cz.cvut.fel.bp.user.service;

import cz.cvut.fel.bp.api.v1.model.LogIn;
import cz.cvut.fel.bp.api.v1.model.LoginToken;
import cz.cvut.fel.bp.api.v1.model.Registration;
import cz.cvut.fel.bp.api.v1.model.User;

/**
 * @author annak
 * @since 2024-03-13
 */
public interface UserAuthService {

    /**
     * login the user
     *
     * @param logIn request for login
     * @return token
     */
    LoginToken login(LogIn logIn);


    /**
     * the user registration
     *
     * @param registration request for registration
     */
    User registration(Registration registration);

}