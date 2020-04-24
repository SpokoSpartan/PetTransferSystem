package com.pwr.it.app.data.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class CountObjects {

    @Id
    private Long count;

}
