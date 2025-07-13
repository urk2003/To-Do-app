package com.todo.todoapp.controller;

import com.todo.todoapp.models.Task;
import com.todo.todoapp.models.User;
import com.todo.todoapp.services.TaskService;
import com.todo.todoapp.services.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByUsername(auth.getName())
                .orElseThrow(() -> new IllegalStateException("User not found"));
    }

    @GetMapping
    public String getTasks(Model model,
                           @RequestParam(value = "date", required = false)
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate selectedDate) {
        User currentUser = getCurrentUser();
        LocalDate targetDate = selectedDate != null ? selectedDate : LocalDate.now();

        List<Task> tasks = taskService.getTasksByUserAndDate(currentUser, targetDate);
        model.addAttribute("tasks", tasks);
        model.addAttribute("selectedDate", targetDate);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("activeTab", "today");

        return "tasks";
    }

    @PostMapping
    public String createTask(@RequestParam String title,
                             @RequestParam(value = "taskDate", required = false)
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate taskDate,
                             @RequestParam(value = "selectedDate", required = false)
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate selectedDate) {
        User currentUser = getCurrentUser();
        LocalDate targetDate = taskDate != null ? taskDate : (selectedDate != null ? selectedDate : LocalDate.now());

        taskService.createTask(title, currentUser, targetDate);

        return "redirect:/?date=" + targetDate;
    }

    @GetMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id,
                             @RequestParam(value = "date", required = false)
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate selectedDate) {
        User currentUser = getCurrentUser();
        taskService.deleteTask(id, currentUser);

        String redirectUrl = selectedDate != null ? "/?date=" + selectedDate : "/";
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/{id}/toggle")
    public String toggleTask(@PathVariable Long id,
                             @RequestParam(value = "date", required = false)
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate selectedDate) {
        User currentUser = getCurrentUser();
        taskService.toggleTask(id, currentUser);

        String redirectUrl = selectedDate != null ? "/?date=" + selectedDate : "/";
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/calendar")
    public String getCalendar(Model model,
                              @RequestParam(value = "year", required = false) Integer year,
                              @RequestParam(value = "month", required = false) Integer month) {
        User currentUser = getCurrentUser();

        LocalDate now = LocalDate.now();
        int targetYear = year != null ? year : now.getYear();
        int targetMonth = month != null ? month : now.getMonthValue();

        YearMonth yearMonth = YearMonth.of(targetYear, targetMonth);
        List<Task> monthTasks = taskService.getTasksByUserAndMonth(currentUser, targetYear, targetMonth);

        // Group tasks by date
        Map<LocalDate, List<Task>> tasksByDate = monthTasks.stream()
                .collect(Collectors.groupingBy(Task::getTaskDate));

        model.addAttribute("currentMonth", yearMonth);
        model.addAttribute("tasksByDate", tasksByDate);
        model.addAttribute("currentUser", currentUser);

        return "calendar";
    }

    @GetMapping("/planner")
    public String getPlanner(Model model) {
        User currentUser = getCurrentUser();
        List<LocalDate> taskDates = taskService.getDistinctTaskDatesByUser(currentUser);

        model.addAttribute("taskDates", taskDates);
        model.addAttribute("currentUser", currentUser);

        return "planner";
    }
}