package org.tempo.springEdu.entity;

public enum RoleName {
    USER
    , ADMIN;

    public String longName()
    {
        return "ROLE_" + name();
    }
}
