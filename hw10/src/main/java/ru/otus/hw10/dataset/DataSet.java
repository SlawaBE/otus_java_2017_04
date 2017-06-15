package ru.otus.hw10.dataset;

import javax.persistence.*;

/**
 * Created by Stanislav on 14.06.2017
 */
@MappedSuperclass
public class DataSet {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public DataSet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
