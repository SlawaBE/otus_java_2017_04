package ru.otus.hw10.dataset;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Stanislav on 14.06.2017
 */
@Entity
@Table(name = "addresses")
public class AddressDataSet extends DataSet {

    @Column(name = "street")
    private String street;

    @Column(name = "post_index")
    private int index;

    public AddressDataSet(String street, int index) {
        this.street = street;
        this.index = index;
    }

    public AddressDataSet() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AddressDataSet{id=").append(id).append(", ")
                .append("street='").append(street).append("', ")
                .append("index=").append(index).append("}");
        return sb.toString();
    }
}
