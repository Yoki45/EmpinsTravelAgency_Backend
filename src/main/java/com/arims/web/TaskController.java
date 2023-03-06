package com.arims.web;

import com.arims.model.Event;
import com.arims.model.Task;
import com.arims.repository.TaskRepository;
import com.arims.service.TaskService;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@RestController
@CrossOrigin(origins ="*")
public class TaskController {


    private TaskService taskService;


    public TaskController(TaskService taskService) {
        this.taskService = taskService;

    }


    @GetMapping("/new/assigned")
    Iterable<Task> t() {
        return taskService.findAssigned();
    }

    @GetMapping("/new/completed")
    Iterable<Task> c() {
        return taskService.findCompleted();
    }


    @GetMapping("/new/unassigned")
    Iterable<Task> tc() {
        return taskService.findNotAssigned();
    }

    @GetMapping("/new/not/completed")
    Iterable<Task> tv() {
        return taskService.findNotCompleted();
    }



    @GetMapping("/all/tasks")
    Iterable<Task> tb() {
        return taskService.findAll();
    }





    @PostMapping("/new/task")
    public ResponseEntity<Task> addChild(@RequestBody Task task) {
        Task newT = taskService.addTask(task);
        return new ResponseEntity<>(newT, HttpStatus.CREATED);
    }














}
