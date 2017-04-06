package com.fgaudin.namescan.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public class Person {

    @Id
    private String id;

    private String lastName;
    private String firstName;
    private LocalDate dateOfBirth;

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("id", this.id).
                append("firstName", firstName).
                append("lastName", lastName).
                append("dateOfBirth", dateOfBirth).
                toString();
    }
}
