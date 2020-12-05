package com.yw.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yangwei
 * @date 2019-07-11 14:40
 */
@Data
@NoArgsConstructor
public class FastDFSFile {
    private String fileName;
    private byte[] fileContent;
    private String fileExt;
    private String md5;
    private String author;

    public FastDFSFile(String fileName, String fileExt, byte[] fileContent) {
        this.fileName = fileName;
        this.fileExt = fileExt;
        this.fileContent = fileContent;
    }
}
