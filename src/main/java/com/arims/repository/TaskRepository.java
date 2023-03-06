package com.arims.repository;

import com.arims.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    //Todo false returning empty, pagination, update and delete crud

    @Query(value="SELECT * FROM tasks t WHERE t.assigned= true",nativeQuery = true)
    public Iterable<Task> findAllAssigned();

    @Query(value="SELECT * FROM tasks t WHERE t.completed = true",nativeQuery = true)
    public Iterable<Task> findAllCompleted();


    @Query(value ="SELECT * FROM tasks t WHERE t.assigned = 0",nativeQuery = true)
    public Iterable<Task> findAllnotAssigned();


    @Query(value = " SELECT * FROM tasks t WHERE t.completed = false", nativeQuery = true)
    public Iterable<Task> findAllNotCompleted();












}
