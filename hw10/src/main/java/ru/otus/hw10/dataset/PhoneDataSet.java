package ru.otus.hw10.dataset;

import javax.persistence.*;

/**
 * Created by Stanislav on 14.06.2017
 */
@Entity
@Table(name = "phones")
public class PhoneDataSet extends DataSet {

    @Column(name = "number")
    private String number;

    @Column(name = "code")
    private int code;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserDataSet user;

    public PhoneDataSet() {
    }

    public PhoneDataSet(String number, int code) {
        this.number = number;
        this.code = code;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public UserDataSet getUser() {
        return user;
    }

    public void setUser(UserDataSet user) {
        this.user = user;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PhoneDataSet{id=").append(id).append(", ")
                .append("number='").append(number).append("', ")
                .append("code=").append(code).append("}");
        return sb.toString();
    }
}
