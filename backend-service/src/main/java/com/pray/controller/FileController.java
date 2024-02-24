package com.pray.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.pray.utils.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * FileController
 *
 * @author 春江花朝秋月夜
 * @since 2023/11/2 19:13
 */
@RestController
public class FileController {
    @Value("${server.port}")
    private String port;

    @Value("${file.ip}")
    private String ip;
    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws IOException{
        //获取源文件名称
        String filename = file.getOriginalFilename();
        String flag = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir") + "/files/" + flag + "_" + filename;

        File rootFile = new File(rootFilePath);

        if (rootFile.getParentFile().exists()){
            rootFile.getParentFile().mkdir();
        }
        FileUtil.writeBytes(file.getBytes(), rootFilePath);
        return Result.ok("http://" + ip + ":" + port + "/files/" + flag);
    }
}
