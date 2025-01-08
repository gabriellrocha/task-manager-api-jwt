package org.gabriel.todolist.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserRequestRegister {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

}
