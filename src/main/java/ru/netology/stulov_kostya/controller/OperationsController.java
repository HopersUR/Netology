package ru.netology.stulov_kostya.controller;

import org.springframework.web.bind.annotation.*;
import ru.netology.stulov_kostya.controller.dto.OperationsDTO;
import ru.netology.stulov_kostya.controller.dto.OperationsGetResponse;
import ru.netology.stulov_kostya.domain.Customer;
import ru.netology.stulov_kostya.domain.operation.Operation;
import ru.netology.stulov_kostya.service.CustomerService;
import ru.netology.stulov_kostya.service.StatementService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/operations/")
public class OperationsController {
    private final CustomerService customerService;
    private final StatementService statementService;

    public OperationsController(CustomerService customerService, StatementService statementService) {
        this.customerService = customerService;
        this.statementService = statementService;
    }

    @GetMapping("{id}")
    public OperationsGetResponse checkOperationsByCustomerId(@PathVariable("id") int id){
        List<Operation> operations = statementService.getOperationOnId(id);
        List<OperationsDTO> operationsDTOS = operations.stream()
                .map(operation ->
                        new OperationsDTO(operation.getId(), operation.getSum(),operation.getCurrency(), operation.getMerchant())).collect(Collectors.toList());
        return new OperationsGetResponse(operationsDTOS);
    }

    @PostMapping()
    public OperationsDTO addOperation(int id, int sum, String currency, String merchant, Customer customer){
        Operation operation = new Operation(id, sum,currency,merchant,customer);
        statementService.saveOperation(operation);
        return new OperationsDTO(operation.getId(),operation.getSum(),operation.getCurrency(),operation.getMerchant());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteOperation(@PathVariable("id") int id){
        statementService.removeOperationsOnCustomerId(id);
    }
}
