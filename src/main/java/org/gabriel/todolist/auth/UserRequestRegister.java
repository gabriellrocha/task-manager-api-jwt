package org.gabriel.todolist.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestRegister {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

}
