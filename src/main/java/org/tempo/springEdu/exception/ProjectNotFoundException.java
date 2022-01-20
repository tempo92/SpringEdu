package org.tempo.springEdu.exception;

public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException(String id) {
        super(String.format("Project with id=%s not found", id));
    }

}
