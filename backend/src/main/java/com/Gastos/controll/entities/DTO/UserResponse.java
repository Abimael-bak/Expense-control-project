package com.Gastos.controll.entities.DTO;

import java.util.List;

import com.Gastos.controll.entities.Expense;

public record UserResponse(Long id, String name, String email, List<Expense> expenses) {

}
