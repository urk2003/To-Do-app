package com.todo.todoapp.services;

import com.todo.todoapp.models.Task;
import com.todo.todoapp.models.User;
import com.todo.todoapp.repo.TaskRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepo taskRepo;

    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    public List<Task> getAllTasksByUser(User user) {
        return taskRepo.findByUserOrderByTaskDateDesc(user);
    }

    public List<Task> getTasksByUserAndDate(User user, LocalDate date) {
        return taskRepo.findByUserAndTaskDate(user, date);
    }

    public void createTask(String title, User user, LocalDate taskDate) {
        Task task = new Task();
        task.setTitle(title);
        task.setCompleted(false);
        task.setUser(user);
        task.setTaskDate(taskDate != null ? taskDate : LocalDate.now());
        taskRepo.save(task);
    }

    public void deleteTask(Long id, User user) {
        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task ID"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Unauthorized to delete this task");
        }

        taskRepo.deleteById(id);
    }

    public void toggleTask(Long id, User user) {
        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task ID"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Unauthorized to modify this task");
        }

        task.setCompleted(!task.isCompleted());
        taskRepo.save(task);
    }

    public List<LocalDate> getDistinctTaskDatesByUser(User user) {
        return taskRepo.findDistinctTaskDatesByUser(user);
    }

    public List<Task> getTasksByUserAndMonth(User user, int year, int month) {
        return taskRepo.findTasksByUserAndMonth(user, year, month);
    }
}