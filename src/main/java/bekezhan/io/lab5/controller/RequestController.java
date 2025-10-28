package bekezhan.io.lab5.controller;

import bekezhan.io.lab5.entity.Courses;
import bekezhan.io.lab5.entity.Request;
import bekezhan.io.lab5.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping
    public ResponseEntity<List<Request>> getAllRequests() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Request> getRequestById(@PathVariable Long id) {
        Request request = requestService.getRequestById(id);
        if (request == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(request);
    }

    @PostMapping
    public ResponseEntity<Request> createRequest(@RequestBody Map<String, Object> payload) {
        Request request = new Request();
        request.setUserName((String) payload.get("userName"));
        request.setCommentary((String) payload.get("commentary"));
        request.setPhone((String) payload.get("phone"));
        request.setHandled((Boolean) payload.getOrDefault("handled", false));

        Long courseId = Long.valueOf(payload.get("courseId").toString());
        Courses course = requestService.getAllCourses().stream()
                .filter(c -> c.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Course not found"));

        request.setCourse(course);
        Request createdRequest = requestService.createRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Request> updateRequest(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        Request existingRequest = requestService.getRequestById(id);
        if (existingRequest == null) {
            return ResponseEntity.notFound().build();
        }

        if (payload.containsKey("userName")) {
            existingRequest.setUserName((String) payload.get("userName"));
        }
        if (payload.containsKey("commentary")) {
            existingRequest.setCommentary((String) payload.get("commentary"));
        }
        if (payload.containsKey("phone")) {
            existingRequest.setPhone((String) payload.get("phone"));
        }
        if (payload.containsKey("handled")) {
            existingRequest.setHandled((Boolean) payload.get("handled"));
        }
        if (payload.containsKey("courseId")) {
            Long courseId = Long.valueOf(payload.get("courseId").toString());
            Courses course = requestService.getAllCourses().stream()
                    .filter(c -> c.getId().equals(courseId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Course not found"));
            existingRequest.setCourse(course);
        }

        Request updatedRequest = requestService.updateRequest(existingRequest);
        return ResponseEntity.ok(updatedRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteRequest(@PathVariable Long id) {
        Request request = requestService.getRequestById(id);
        if (request == null) {
            return ResponseEntity.notFound().build();
        }

        requestService.deleteRequest(id);
        return ResponseEntity.ok(Map.of("message", "Request deleted successfully"));
    }

    @GetMapping("/new")
    public ResponseEntity<List<Request>> getNewRequests() {
        return ResponseEntity.ok(requestService.getNewRequests());
    }

    @GetMapping("/processed")
    public ResponseEntity<List<Request>> getProcessedRequests() {
        return ResponseEntity.ok(requestService.getProcessedRequests());
    }

    @PutMapping("/{id}/process")
    public ResponseEntity<Request> processRequest(@PathVariable Long id) {
        requestService.processRequest(id);
        Request processedRequest = requestService.getRequestById(id);
        return ResponseEntity.ok(processedRequest);
    }
}