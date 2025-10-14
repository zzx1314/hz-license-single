package org.huazhi.util;

import java.io.*;
import java.nio.file.*;
import java.util.zip.*;

public class ZipUtils {

    /**
     * 将指定目录或文件压缩成 ZIP 文件
     * @param sourcePath 要压缩的文件或目录路径
     * @param zipFilePath 目标 ZIP 文件路径
     * @throws IOException 压缩过程中的 I/O 异常
     */
    public static void zip(String sourcePath, String zipFilePath) throws IOException {
        Path source = Paths.get(sourcePath);
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            if (Files.isDirectory(source)) {
                // 递归压缩目录
                Files.walk(source)
                        .filter(path -> !Files.isDirectory(path))
                        .forEach(path -> {
                            String zipEntryName = source.relativize(path).toString().replace("\\", "/");
                            try (InputStream fis = Files.newInputStream(path)) {
                                ZipEntry zipEntry = new ZipEntry(zipEntryName);
                                zos.putNextEntry(zipEntry);
                                fis.transferTo(zos);
                                zos.closeEntry();
                            } catch (IOException e) {
                                throw new UncheckedIOException(e);
                            }
                        });
            } else {
                // 压缩单个文件
                try (InputStream fis = Files.newInputStream(source)) {
                    ZipEntry zipEntry = new ZipEntry(source.getFileName().toString());
                    zos.putNextEntry(zipEntry);
                    fis.transferTo(zos);
                    zos.closeEntry();
                }
            }
        }
    }

    /**
     * 解压 ZIP 文件到指定目录
     * @param zipFilePath ZIP 文件路径
     * @param destDir 解压目标目录
     * @throws IOException 解压过程中的 I/O 异常
     */
    public static void unzip(String zipFilePath, String destDir) throws IOException {
        Path destPath = Paths.get(destDir);
        if (!Files.exists(destPath)) {
            Files.createDirectories(destPath);
        }

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                Path newFilePath = destPath.resolve(entry.getName()).normalize();

                if (entry.isDirectory()) {
                    Files.createDirectories(newFilePath);
                } else {
                    if (!Files.exists(newFilePath.getParent())) {
                        Files.createDirectories(newFilePath.getParent());
                    }
                    try (OutputStream fos = Files.newOutputStream(newFilePath)) {
                        zis.transferTo(fos);
                    }
                }
                zis.closeEntry();
            }
        }
    }
}

