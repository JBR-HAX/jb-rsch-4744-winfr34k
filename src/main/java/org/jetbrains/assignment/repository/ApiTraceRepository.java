package org.jetbrains.assignment.repository;

import org.jetbrains.assignment.entity.ApiTrace;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ApiTraceRepository extends CrudRepository<ApiTrace, Long> {
    @Query("SELECT a FROM ApiTrace a ORDER BY a.id DESC LIMIT 1")
    Optional<ApiTrace> findLast();
}
