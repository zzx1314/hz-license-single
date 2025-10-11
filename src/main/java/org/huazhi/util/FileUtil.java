package org.huazhi.util;

import org.jboss.resteasy.reactive.multipart.FileUpload;

import jakarta.ws.rs.core.Response;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

public class FileUtil {

    /**
     * 保存上传文件
     * @param file      上传的文件对象
     * @param targetDir 保存目录
     * @return          保存后的路径
     */
    public static Path saveFile(FileUpload file, String targetDir) {
        if (file == null || file.uploadedFile() == null) {
            throw new IllegalArgumentException("上传文件不能为空");
        }

        try {
            // 确保目录存在
            Path dir = Paths.get(targetDir);
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }

            // 保存文件
            Path target = dir.resolve(file.fileName());
            Files.copy(file.uploadedFile(), target, StandardCopyOption.REPLACE_EXISTING);

            return target;
        } catch (IOException e) {
            throw new RuntimeException("保存文件失败: " + file.fileName(), e);
        }
    }

    /**
     * 保存文件到指定目录
     *
     * @param dirPath   保存的目录，例如 "knowledge/123"
     * @param fileName  文件名，例如 "test.txt"
     * @param input     输入流
     * @return 文件保存后的路径
     * @throws IOException IO异常
     */
    public static Path saveFile(String dirPath, String fileName, InputStream input) throws IOException {
        // 确保目录存在
        Path dir = Path.of(dirPath);
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }

        // 拼接完整路径
        Path filePath = dir.resolve(fileName);

        // 写入文件
        try (FileOutputStream out = new FileOutputStream(filePath.toFile())) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } finally {
            input.close();
        }

        return filePath;
    }

    /**
     * 下载文件
     * @param filePath 文件路径
     * @return Response (带文件流)
     */
    public static Response downloadFile(String filePath) {
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("文件不存在: " + filePath)
                    .build();
        }
        try {
            String fileName = path.getFileName().toString();
            return Response.ok(path.toFile())
                    .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                    .build();
        } catch (Exception e) {
            return Response.serverError().entity("下载失败: " + e.getMessage()).build();
        }
    }
}

