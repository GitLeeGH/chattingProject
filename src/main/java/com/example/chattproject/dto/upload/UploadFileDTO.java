package com.example.chattproject.dto.upload;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class UploadFileDTO {

    private List<MultipartFile> files;
}
