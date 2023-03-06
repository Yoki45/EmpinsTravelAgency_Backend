package com.arims.service;

import com.arims.model.Task;
import com.arims.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;


    public Task addTask(Task task){
        return taskRepository.save(task);
    }


    public Iterable<Task> findAssigned(){
        return  taskRepository.findAllAssigned();
    }



    public Iterable<Task>findCompleted(){
        return taskRepository.findAllCompleted();
    }



    public Iterable<Task>findNotAssigned(){
        return taskRepository.findAllnotAssigned();
    }


    public Iterable<Task>findNotCompleted(){
        return taskRepository.findAllNotCompleted();
    }

    public Iterable <Task> findAll(){
        return  taskRepository.findAll();
    }










    //todo implement sort, filter of tasks
}
