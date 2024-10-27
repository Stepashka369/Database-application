package com.stepashka.bd.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Stock {
    private Long id;
    private String town;
    private String address;
}
