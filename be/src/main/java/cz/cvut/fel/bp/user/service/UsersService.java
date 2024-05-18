package cz.cvut.fel.bp.user.service;

import cz.cvut.fel.bp.api.v1.model.NewUser;
import cz.cvut.fel.bp.api.v1.model.UserPage;
import org.springframework.data.domain.Pageable;

/**
 * @author annak
 * @since 2024-03-22
 */
public interface UsersService {

    /**
     * @param newUser request body for creating new user
     */
    void createUser(NewUser newUser);

    /**
     * @param pageable count elements on one page
     * @return list Users
     */
    UserPage getUsers(Pageable pageable);

}
