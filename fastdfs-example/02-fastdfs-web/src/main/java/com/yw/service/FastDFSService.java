package com.yw.service;

import com.yw.model.FastDFSFile;
import org.csource.fastdfs.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * @author yangwei
 * @date 2019-07-12 11:20
 */
public interface FastDFSService {
    /**
     * 文件上传
     */
    Optional<String> uploadFile(MultipartFile file);

    /**
     * 文件上传
     */
    Optional<String> uploadFile(FastDFSFile file);

    /**
     * 文件下载
     */
    void downloadFile(String groupName, String remoteFileName);

    /**
     * 文件删除
     */
    boolean deleteFile(String groupName, String remoteFileName) throws Exception;

    /**
     * 根据 groupName 和文件名获取文件信息
     */
    Optional<FileInfo> getFileInfo(String groupName, String remoteFileName);
}
