package com.stepashka.bd.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Clients {
    private Long id;
    private String name;
    private String surname;
    private String email;
}
