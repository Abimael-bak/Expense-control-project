package com.Gastos.controll.entities.DTO;

import java.util.UUID;

public record UserRequest(UUID id, String name, String email, String Password) {

}
