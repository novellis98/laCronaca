package com.novprod.lacronaca.services;

import com.novprod.lacronaca.models.CareerRequest;
import com.novprod.lacronaca.models.User;

public interface CareerRequestService {
    boolean isRoleAlreadyAssigned(User user, CareerRequest careerRequest);

    void save(CareerRequest careerRequest, User user);

    void careerAccept(Long requestId);

    CareerRequest find(Long id);

}
