package com.fgaudin.namescan.domain;


public class PersonResult {
    private String dataSourceName;
    private Person person;

    public String getDataSourceName() {
        return dataSourceName;
    }

    public Person getPerson() {
        return person;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
