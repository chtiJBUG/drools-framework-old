package org.chtijbug.drools.runtime.pojotest;

public class UserName {
    private String firstname;
    private String middlename;
    private String lastname;

    public UserName() {
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "UserName [firstname=" + firstname +
                ", lastname=" + lastname +
                ", middlename=" + middlename + "]";
    }

}
