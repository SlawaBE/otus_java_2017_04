package ru.otus.hw10.dataset;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by Stanislav on 14.06.2017
 */
@Entity
@Table(name = "users")
public class UserDataSet extends DataSet {

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<PhoneDataSet> phones;

    @OneToOne(cascade = CascadeType.ALL)
    private AddressDataSet address;

    public UserDataSet() {
    }

    public UserDataSet(String name, Set<PhoneDataSet> phones, AddressDataSet address) {
        this.name = name;
        this.phones = phones;
        this.address = address;
        for (PhoneDataSet phone : phones) {
            phone.setUser(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PhoneDataSet> getPhones() {
        return phones;
    }

    public void setPhones(Set<PhoneDataSet> phones) {
        this.phones = phones;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UserDataSet{id=").append(id).append(", ")
                .append("name='").append(name).append("', ")
                .append("address=").append(address.toString()).append(", ")
                .append("phones=").append(phones.toString())
                .append("}");

        return sb.toString();
    }
}
