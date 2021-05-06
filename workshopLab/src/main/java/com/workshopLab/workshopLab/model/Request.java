package com.workshopLab.workshopLab.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "request")
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "text")
    private String text;
    @Column(name = "date")
    private Date date;
    @Column(name = "price")
    private Double price;
    @ManyToOne
    private  User user;
    @ManyToOne
    private  User master;
    @ManyToOne
    private  Status status;
}
