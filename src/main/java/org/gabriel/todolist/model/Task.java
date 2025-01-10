package org.gabriel.todolist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gabriel.todolist.interfaces.Authorizable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "tasks")
public class Task implements Authorizable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Override
    public String getOwnerEmail() {
        return user.getEmail();
    }
}
