package bekezhan.io.lab5.service;

import bekezhan.io.lab5.entity.Courses;
import bekezhan.io.lab5.entity.Operators;
import bekezhan.io.lab5.entity.Request;
import bekezhan.io.lab5.repository.CourseRepository;
import bekezhan.io.lab5.repository.OperatorRepository;
import bekezhan.io.lab5.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private CourseRepository courseRepository;

    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    public List<Request> getNewRequests() {
        return requestRepository.findAll().stream()
                .filter(request -> !request.isHandled())
                .toList();
    }

    public List<Request> getProcessedRequests() {
        return requestRepository.findAll().stream()
                .filter(Request::isHandled)
                .toList();
    }

    public List<Courses> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Operators> getAllOperators() {
        return operatorRepository.findAll();
    }

    public Request createRequest(Request request) {
        request.setHandled(false);
        requestRepository.save(request);
        return request;
    }

    public void assignOperators(Long requestId, List<Long> operatorIds) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        if (request.isHandled()) {
            throw new RuntimeException("Cannot assign operators to a handled request");
        }
        Set<Operators> operators = new HashSet<>(operatorRepository.findAllById(operatorIds));
        request.getOperators().addAll(operators);
        requestRepository.save(request);
    }

    public void processRequest(Long requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setHandled(true);
        requestRepository.save(request);
    }

    public void removeOperator(Long requestId, Long operatorId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        if (request.isHandled()) {
            throw new RuntimeException("Cannot remove operators from a handled request");
        }
        Operators operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new RuntimeException("Operator not found"));
        request.getOperators().remove(operator);
        requestRepository.save(request);
    }
    public Request getRequestById(Long id) {
        return requestRepository.findById(id).orElse(null);
    }

    public Request updateRequest(Request request) {
        return requestRepository.save(request);
    }

    public void deleteRequest(Long id) {
        requestRepository.deleteById(id);
    }

    public Courses createCourse(Courses course) {
        return courseRepository.save(course);
    }

    public Courses updateCourse(Courses course) {
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public Operators createOperator(Operators operator) {
        return operatorRepository.save(operator);
    }

    public Operators updateOperator(Operators operator) {
        return operatorRepository.save(operator);
    }

    public void deleteOperator(Long id) {
        operatorRepository.deleteById(id);
    }
}