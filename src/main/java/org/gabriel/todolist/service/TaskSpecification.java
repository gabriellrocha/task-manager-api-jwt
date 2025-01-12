package org.gabriel.todolist.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.gabriel.todolist.model.Task;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class TaskSpecification implements Specification<Task> {

    private final String priority;
    private final String status;
    private final String email;


    @Override
    public Predicate toPredicate(@NonNull Root<Task> root, CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>(); // TODO: COntém todas as condições de filtro

        if (priority != null) {
            predicates.add(criteriaBuilder.equal(root.get("priority"), priority));
        }

        if (status != null) { // TODO: Se 'null' a condição de filtro não é adicionada na consulta ao BD
            predicates.add(criteriaBuilder.equal(root.get("status"), status));
        }

        if (email != null) {
            predicates.add(criteriaBuilder.equal(root.get("user").get("email"), email));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

    }
}
