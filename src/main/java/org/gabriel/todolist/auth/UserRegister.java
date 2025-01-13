package org.gabriel.todolist.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserRegister {

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    private String lastName;

    @Email(message = "E-mail invalid")
    @NotBlank(message = "E-mail is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

}
