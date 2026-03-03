package com.Gastos.controll.entities.DTO;

import java.time.Instant;

import com.Gastos.controll.entities.Category;

public record ExpenseResponse(Long id, String description, Double amount, Instant moment, Category category) {

}
