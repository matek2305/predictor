package models.dto;

import models.Predictor;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public class AuthenticateUserResponse {

    public String name;
    public String authenticationToken;

    public AuthenticateUserResponse(Predictor predictor) {
        this.name = predictor.login;
        this.authenticationToken = predictor.authenticationToken;
    }
}
