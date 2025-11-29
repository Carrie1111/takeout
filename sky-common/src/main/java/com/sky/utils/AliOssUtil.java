package com.sky.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.io.ByteArrayInputStream;

/**
 * 文件上传工具类：负责实际的文件上传操作，是OSS功能的核心实现
 */
@Data
@AllArgsConstructor
@Slf4j
public class AliOssUtil {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    /**
     * 文件上传
     *
     * @param bytes
     * @param objectName
     * @return
     */
//    public String upload(byte[] bytes, String objectName) {
//
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//
//        try {
//            // 创建PutObject请求。
//            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));
//        } catch (OSSException oe) {
//            System.out.println("Caught an OSSException, which means your request made it to OSS, "
//                    + "but was rejected with an error response for some reason.");
//            System.out.println("Error Message:" + oe.getErrorMessage());
//            System.out.println("Error Code:" + oe.getErrorCode());
//            System.out.println("Request ID:" + oe.getRequestId());
//            System.out.println("Host ID:" + oe.getHostId());
//        } catch (ClientException ce) {
//            System.out.println("Caught an ClientException, which means the client encountered "
//                    + "a serious internal problem while trying to communicate with OSS, "
//                    + "such as not being able to access the network.");
//            System.out.println("Error Message:" + ce.getMessage());
//        } finally {
//            if (ossClient != null) {
//                ossClient.shutdown();
//            }
//        }
//
//        // 动态拼接生成文件访问路径，规则： https://BucketName.Endpoint/ObjectName
//        StringBuilder stringBuilder = new StringBuilder("https://");
//        stringBuilder
//                .append(bucketName)
//                .append(".")
//                .append(endpoint.split("//")[1])
//                .append("/")
//                .append(objectName);
//
//        log.info("文件上传到:{}", stringBuilder.toString());
//
//        return stringBuilder.toString();
//    }
    public String upload(byte[] bytes, String objectName) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            log.info("开始上传文件到OSS，文件名：{}，文件大小：{}字节", objectName, bytes.length);
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));
            log.info("OSS文件上传成功");

            // 只有上传成功才返回URL
            String fileUrl = "https://" + bucketName + "." + endpoint + "/" + objectName;
            log.info("文件访问地址：{}", fileUrl);
            return fileUrl;

        } catch (OSSException oe) {
            log.error("OSS服务异常 - 错误消息: {}, 错误代码: {}, 请求ID: {}, Host ID: {}",
                    oe.getErrorMessage(), oe.getErrorCode(), oe.getRequestId(), oe.getHostId());
            throw new RuntimeException("OSS服务异常: " + oe.getErrorMessage(), oe);
        } catch (ClientException ce) {
            log.error("OSS客户端异常 - 错误消息: {}", ce.getMessage());
            throw new RuntimeException("OSS客户端异常: " + ce.getMessage(), ce);
        } catch (Exception e) {
            log.error("文件上传未知异常", e);
            throw new RuntimeException("文件上传失败", e);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
