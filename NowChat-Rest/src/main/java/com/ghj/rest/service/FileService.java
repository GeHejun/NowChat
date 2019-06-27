package com.ghj.rest.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author gehj
 * @date 2019/6/2718:19
 */
public interface FileService {

    String uploadFile(String content, String fileExtension);

    String uploadFile(MultipartFile file);

    void deleteFile(String fileUrl);
}
