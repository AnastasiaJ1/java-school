package com.digdes.school.project.specifications;

import com.digdes.school.project.filters.ProjectSearchFilter;
import com.digdes.school.project.model.Project;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ProjectSpecification {
    public static Specification<Project> getSpec(ProjectSearchFilter filter){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(!ObjectUtils.isEmpty(filter.getName()))
                predicates.add(criteriaBuilder.equal(root.get("name"), filter.getName()));
            if(!ObjectUtils.isEmpty(filter.getDescription()))
                predicates.add(criteriaBuilder.like(root.get("description"), filter.getDescription()));
            if(!ObjectUtils.isEmpty(filter.getCode()))
                predicates.add(criteriaBuilder.equal(root.get("code"), filter.getCode()));
            if(!ObjectUtils.isEmpty(filter.getStatus()))
                predicates.add(criteriaBuilder.equal(root.get("code"), filter.getStatus()));
            if(CollectionUtils.isEmpty(predicates))
                return query.where().getRestriction();
            else
                return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();
        });
    }
}
