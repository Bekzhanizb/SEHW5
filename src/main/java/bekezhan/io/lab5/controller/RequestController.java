package bekezhan.io.lab5.controller;

import bekezhan.io.lab5.entity.Courses;
import bekezhan.io.lab5.entity.Request;
import bekezhan.io.lab5.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping("/")
    public String getAllRequests(Model model) {
        model.addAttribute("requests", requestService.getAllRequests());
        model.addAttribute("operators", requestService.getAllOperators());
        return "index";
    }

    @GetMapping("/newrequests")
    public String getNewRequests(Model model) {
        model.addAttribute("requests", requestService.getNewRequests());
        model.addAttribute("operators", requestService.getAllOperators());
        return "newrequests";
    }

    @GetMapping("/processed")
    public String getProcessedRequests(Model model) {
        model.addAttribute("requests", requestService.getProcessedRequests());
        return "processed";
    }

    @GetMapping("/add")
    public String addRequestForm(Model model) {
        model.addAttribute("courses", requestService.getAllCourses());
        model.addAttribute("request", new Request());
        return "add";
    }

    @PostMapping("/add")
    public String createRequest(@ModelAttribute Request request, @RequestParam Long courseId) {
        Courses course = requestService.getAllCourses().stream()
                .filter(c -> c.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Course not found"));
        request.setCourse(course);
        requestService.createRequest(request);
        return "redirect:/";
    }

    @PostMapping("/assign")
    public String assignOperators(@RequestParam Long requestId, @RequestParam List<Long> operatorIds) {
        requestService.assignOperators(requestId, operatorIds);
        return "redirect:/";
    }

    @PostMapping("/process")
    public String processRequest(@RequestParam Long requestId) {
        requestService.processRequest(requestId);
        return "redirect:/";
    }

    @PostMapping("/removeOperator")
    public String removeOperator(@RequestParam Long requestId, @RequestParam Long operatorId) {
        requestService.removeOperator(requestId, operatorId);
        return "redirect:/";
    }
}