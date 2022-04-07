package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/product")
public class FileUploadController {


    //  获取文件上传对应的地址
    @Value("${minio.endpointUrl}")
    public String endpointUrl;

    @Value("${minio.accessKey}")
    public String accessKey;

    @Value("${minio.secreKey}")
    public String secreKey;

    @Value("${minio.bucketName}")
    public String bucketName;




    /**
     * 文件上传
     * admin/product/fileUpload
     * @param file
     * @return
     */
    @PostMapping("/fileUpload")
    public Result fileLoad(MultipartFile file){
        String url= null;

        try {
            // 创建MinIO的客户端
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(endpointUrl)
                            .credentials(accessKey, secreKey)
                            .build();

            // 判断指定名称的桶是否存在
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                // 没有指定的桶，就创建
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            } else {
                System.out.println("Bucket 'asiatrip' already exists.");
            }


            //不能使用这个API: 因为他要知道文件的位置在哪？
//            // Upload '/home/user/Photos/asiaphotos.zip' as object name 'asiaphotos-2015.zip' to bucket
//            // 'asiatrip'.
//            minioClient.uploadObject(
//                    UploadObjectArgs.builder()
//                            .bucket("asiatrip")
//                            .object("asiaphotos-2015.zip")
//                            .filename("/home/user/Photos/asiaphotos.zip")
//                            .build());
//            System.out.println(
//                    "'/home/user/Photos/asiaphotos.zip' is successfully uploaded as "
//                            + "object 'asiaphotos-2015.zip' to bucket 'asiatrip'.");


            String fileName=System.currentTimeMillis()+ UUID.randomUUID().toString().replace("-","")+file.getOriginalFilename();

            //上传文件
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(
                                    file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());

            //拼接文件url
            url = endpointUrl+"/"+bucketName+"/"+fileName;
            //http://192.168.254.150:9000/gmall/fileName
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result.ok(url);

    }
}
