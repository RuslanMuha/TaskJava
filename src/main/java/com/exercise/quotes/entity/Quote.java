package com.exercise.quotes.entity;

import lombok.*;


import javax.persistence.*;

import java.util.Set;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@EqualsAndHashCode(exclude = "items")
@Table(name = "quotes",uniqueConstraints=@UniqueConstraint(columnNames="name"))
@ToString(exclude = "items")
public class Quote  {

    @Id
    private Long id;

    private String name;
    private double price;

    @OneToMany
    @JoinColumn(name = "quote_id")
    private Set<ItemQuote> items;

    @Column(name = "removed", columnDefinition = "boolean default false")
    private boolean removed;


}
