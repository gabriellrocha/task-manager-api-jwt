package org.gabriel.todolist.service;

import org.gabriel.todolist.exception.AccessDeniedException;
import org.gabriel.todolist.interfaces.Authorizable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    public void checkAuthorization(Authorizable authorizable) {  // TODO: check owner

        var userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!authorizable.getOwnerEmail().equals(userEmail)) {
            throw new AccessDeniedException("Unauthorized");
        }

    }
}
