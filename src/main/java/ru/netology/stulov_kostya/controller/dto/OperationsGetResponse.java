package ru.netology.stulov_kostya.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OperationsGetResponse {
    private final List<OperationsDTO> operations;

}
