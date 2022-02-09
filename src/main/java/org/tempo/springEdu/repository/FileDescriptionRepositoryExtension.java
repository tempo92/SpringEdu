package org.tempo.springEdu.repository;

import org.tempo.springEdu.entity.FileDescription;
import org.tempo.springEdu.entity.Project;

import java.util.List;

public interface FileDescriptionRepositoryExtension {
    List<FileDescription> find(String filenamePart, String dir);
}
