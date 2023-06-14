package com.digdes.school.project.specifications;

import com.digdes.school.project.filters.TaskSearchFilter;
import com.digdes.school.project.model.Task;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class TaskSpecification {
    public static Specification<Task> getSpec(TaskSearchFilter filter){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(!ObjectUtils.isEmpty(filter.getName()))
                predicates.add(criteriaBuilder.equal(root.get("name"), filter.getName()));
            if(!ObjectUtils.isEmpty(filter.getDescription()))
                predicates.add(criteriaBuilder.like(root.get("description"), filter.getDescription()));
            if(!ObjectUtils.isEmpty(filter.getExecutor()))
                predicates.add(criteriaBuilder.equal(root.get("executor"), filter.getExecutor()));
            if(!ObjectUtils.isEmpty(filter.getAuthor()))
                predicates.add(criteriaBuilder.equal(root.get("author"), filter.getAuthor()));
            if(!ObjectUtils.isEmpty(filter.getChangeDateStart()) && !ObjectUtils.isEmpty(filter.getChangeDateEnd()))
                predicates.add(criteriaBuilder.between(root.get("creationDate"), filter.getChangeDateStart(), filter.getChangeDateEnd()));
            if(!ObjectUtils.isEmpty(filter.getCreationDateStart()) && !ObjectUtils.isEmpty(filter.getCreationDateEnd()))
                predicates.add(criteriaBuilder.between(root.get("changeDate"), filter.getCreationDateStart(), filter.getCreationDateEnd()));
            if(!ObjectUtils.isEmpty(filter.getStatus()))
                predicates.add(criteriaBuilder.equal(root.get("status"), filter.getStatus()));
            if(CollectionUtils.isEmpty(predicates))
                return query.where().getRestriction();
            else
                return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();
        });
    }
}
