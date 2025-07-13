package com.todo.todoapp.repo;

import com.todo.todoapp.models.Task;
import com.todo.todoapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {
    List<Task> findByUserAndTaskDate(User user, LocalDate taskDate);
    List<Task> findByUserOrderByTaskDateDesc(User user);

    @Query("SELECT DISTINCT t.taskDate FROM Task t WHERE t.user = :user ORDER BY t.taskDate DESC")
    List<LocalDate> findDistinctTaskDatesByUser(@Param("user") User user);

    @Query("SELECT t FROM Task t WHERE t.user = :user AND YEAR(t.taskDate) = :year AND MONTH(t.taskDate) = :month")
    List<Task> findTasksByUserAndMonth(@Param("user") User user, @Param("year") int year, @Param("month") int month);
}