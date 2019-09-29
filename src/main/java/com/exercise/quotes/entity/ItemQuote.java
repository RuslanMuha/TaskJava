package com.exercise.quotes.entity;

import lombok.*;

import javax.persistence.*;


@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "items")
public class ItemQuote  {


    @Id
    private long id;
    private String name;
    @Column(name = "removed", columnDefinition = "boolean default false")
    private boolean removed;

}
