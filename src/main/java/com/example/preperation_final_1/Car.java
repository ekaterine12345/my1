package com.example.preperation_final_1;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Car {
    private Long ID;
    private String mark;
    private String model;
    private int year;
    private int speed;
    private float engine;

    public Car(String mark, String model, int year, int speed, float engine) {
        this.mark = mark;
        this.model = model;
        this.year = year;
        this.speed = speed;
        this.engine = engine;
    }
}
