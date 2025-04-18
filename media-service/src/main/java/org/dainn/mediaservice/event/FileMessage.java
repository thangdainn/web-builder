package org.dainn.mediaservice.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileMessage implements Serializable {
    private String fileName;
    private String contentType;
    private String tempFilePath;
    private String originalFilename;

    @Override
    public String toString() {
        return "FileUploadMessage{" +
                "fileName='" + fileName + '\'' +
                ", contentType='" + contentType + '\'' +
                ", tempFilePath='" + tempFilePath + '\'' +
                ", originalFilename='" + originalFilename + '\'' +
                '}';
    }
}
