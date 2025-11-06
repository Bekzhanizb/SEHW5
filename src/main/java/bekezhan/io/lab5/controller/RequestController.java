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

    @PostMapping("/add")
    public ResponseEntity<Request> createRequest( @RequestParam String userName,
            @RequestParam String commentary,
            @RequestParam String phone,
            @RequestParam Long courseId
    ) {
        Request request = new Request();
        request.setUserName(userName);
        request.setCommentary(commentary);
        request.setPhone(phone);
        request.setHandled(false);

        Courses course = requestService.getAllCourses().stream()
                .filter(c -> c.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Course not found"));

        request.setCourse(course);

        Request createdRequest = requestService.createRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }

    @PutMapping("/update")
    public ResponseEntity<Request> updateRequest( @RequestParam Long id,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String commentary,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) Boolean handled,
            @RequestParam(required = false) Long courseId
    ) {
        Request existingRequest = requestService.getRequestById(id);
        if (existingRequest == null) {
            return ResponseEntity.notFound().build();
        }

        if (userName != null) existingRequest.setUserName(userName);
        if (commentary != null) existingRequest.setCommentary(commentary);
        if (phone != null) existingRequest.setPhone(phone);
        if (handled != null) existingRequest.setHandled(handled);
        if (courseId != null) {
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