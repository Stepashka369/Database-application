package com.stepashka.bd.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Client {
    private Long id;
    private String name;
    private String surname;
    private String email;
}
