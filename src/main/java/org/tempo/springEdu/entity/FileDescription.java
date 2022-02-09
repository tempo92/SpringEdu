package org.tempo.springEdu.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@CompoundIndex(name = "paths", def = "{'originalFilename' : 1, 'directory': 1}", unique = true)
public class FileDescription {

    @Id
    private String id;
    private String originalFilename;
    private Long size;
    private String mimeType;
    private String filename;
    private String directory;

    public String getDirectory()
    {
        if (directory != null)
            return directory;
        else
            return "";
    }
}
