package com.digdes.school.project.specifications;

import com.digdes.school.project.input.EmployeeDTO;
import com.digdes.school.project.model.Employee;
import com.digdes.school.project.enums.EmployeeStatus;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification {
    public static Specification<Employee> getSpec(EmployeeDTO employeeDTO){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(!ObjectUtils.isEmpty(employeeDTO.getLastname()))
                predicates.add(criteriaBuilder.equal(root.get("lastname"), employeeDTO.getLastname()));
            if(!ObjectUtils.isEmpty(employeeDTO.getFirstname()))
                predicates.add(criteriaBuilder.equal(root.get("firstname"), employeeDTO.getFirstname()));
            if(!ObjectUtils.isEmpty(employeeDTO.getSurname()))
                predicates.add(criteriaBuilder.equal(root.get("surname"), employeeDTO.getSurname()));
            if(!ObjectUtils.isEmpty(employeeDTO.getJobTitle()))
                predicates.add(criteriaBuilder.equal(root.get("jobTitle"), employeeDTO.getJobTitle()));
            if(!ObjectUtils.isEmpty(employeeDTO.getAccount()))
                predicates.add(criteriaBuilder.equal(root.get("account"), employeeDTO.getAccount()));
            if(!ObjectUtils.isEmpty(employeeDTO.getEmail()))
                predicates.add(criteriaBuilder.equal(root.get("email"), employeeDTO.getEmail()));
            predicates.add(criteriaBuilder.equal(root.get("status"), EmployeeStatus.ACTIVE));
            if(CollectionUtils.isEmpty(predicates)){
                return query.where().getRestriction();
            } else {
                return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();
            }

        });
    }
}
