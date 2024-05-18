package cz.cvut.fel.bp.generator;

import cz.cvut.fel.bp.api.v1.model.Registration;

/**
 * @author annak
 * @since 2024-03-16
 */
public class GeneratorRegistrationRequest {

    public static Registration generateRegistration() {
        Registration registration = new Registration();
        registration.setEmail(Common.generateRandomEmail());
        registration.setPassword(Common.generateRandomString(10));
        registration.setFirstName(Common.generateRandomString(6));
        registration.setLastName(Common.generateRandomString(6));
        return registration;
    }

}