package com.Gastos.controll.entities.DTO;

import java.time.Instant;

import com.Gastos.controll.entities.Category;
import com.Gastos.controll.entities.User;

public record ExpenseRequest(Long id, String description, Double amount, Instant moment, Category category, User user) {

}
