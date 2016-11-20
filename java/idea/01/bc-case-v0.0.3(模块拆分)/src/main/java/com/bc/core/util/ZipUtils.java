package com.bc.core.util;




import java.io.*;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 基于JDK的Zip压缩工具类
 * <p>
 * <pre>
 * 存在问题：压缩时如果目录或文件名含有中文，压缩后会变成乱码
 * </pre>
 *
 */
public class ZipUtils {

    public static final int BUFFER_SIZE_DIFAULT = 128;

    public static void makeZip(String[] inFilePaths, String zipFilePath)
            throws Exception {
        File[] inFiles = new File[inFilePaths.length];
        for (int i = 0; i < inFilePaths.length; i++) {
            inFiles[i] = new File(inFilePaths[i]);
        }
        makeZip(inFiles, zipFilePath);
    }

    public static void makeZip(File[] inFiles, String zipFilePath) throws Exception {
        ZipOutputStream zipOut = new ZipOutputStream(new BufferedOutputStream(
                new FileOutputStream(zipFilePath)));
        for (int i = 0; i < inFiles.length; i++) {
            doZipFile(zipOut, inFiles[i], inFiles[i].getParent());
        }
        zipOut.close();
    }

    private static void doZipFile(ZipOutputStream zipOut, File file,
                                  String dirPath) throws FileNotFoundException, IOException {
        if (file.isFile()) {
            BufferedInputStream bis = new BufferedInputStream(
                    new FileInputStream(file));
            String zipName = file.getPath().substring(dirPath.length());
            while (zipName.charAt(0) == '\\' || zipName.charAt(0) == '/') {
                zipName = zipName.substring(1);
            }
            ZipEntry entry = new ZipEntry(zipName);
            zipOut.putNextEntry(entry);
            byte[] buff = new byte[BUFFER_SIZE_DIFAULT];
            int size;
            while ((size = bis.read(buff, 0, buff.length)) != -1) {
                zipOut.write(buff, 0, size);
            }
            zipOut.closeEntry();
            bis.close();
        } else {
            File[] subFiles = file.listFiles();
            for (File subFile : subFiles) {
                doZipFile(zipOut, subFile, dirPath);
            }
        }
    }

    public static void unZip(String zipFilePath, String storePath)
            throws IOException {
        unZip(new File(zipFilePath), storePath);
    }

    public static void unZip(File zipFile, String storePath) throws IOException {
        if (new File(storePath).exists()) {
            new File(storePath).delete();
        }
        new File(storePath).mkdirs();

        ZipFile zip = new ZipFile(zipFile);
        Enumeration<? extends ZipEntry> entries = zip.entries();
        while (entries.hasMoreElements()) {
            ZipEntry zipEntry = entries.nextElement();

            if (zipEntry.isDirectory()) {
                // TODO
            } else {
                String zipEntryName = zipEntry.getName();
                if (zipEntryName.indexOf(File.separator) > 0) {
                    String zipEntryDir = zipEntryName.substring(0, zipEntryName
                            .lastIndexOf(File.separator) + 1);
                    String unzipFileDir = storePath + File.separator
                            + zipEntryDir;
                    File unzipFileDirFile = new File(unzipFileDir);
                    if (!unzipFileDirFile.exists()) {
                        unzipFileDirFile.mkdirs();
                    }
                }

                InputStream is = zip.getInputStream(zipEntry);
                FileOutputStream fos = new FileOutputStream(new File(storePath
                        + File.separator + zipEntryName));
                byte[] buff = new byte[BUFFER_SIZE_DIFAULT];
                int size;
                while ((size = is.read(buff)) > 0) {
                    fos.write(buff, 0, size);
                }
                fos.flush();
                fos.close();
                is.close();
            }
        }
    }

    //========================================================================================
    public void unzip(File zipFile, File unzipFolder) throws IOException {
        if (!unzipFolder.exists()) {
            unzipFolder.mkdir();
        }
        ZipFile zip = new ZipFile(zipFile);
        unzip(zip, unzipFolder.getAbsolutePath());
        zip.close();
    }

    private void unzip(ZipFile zipFile, String parentDir) throws IOException {
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        byte[] buffer = new byte[128];
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            String entryName = entry.getName();
            File f = new File(parentDir + File.separator + entryName);
            System.out.println(f.getAbsolutePath());
            if (entry.isDirectory()||entryName.endsWith("\\")||entryName.endsWith(File.separator)) {
                if (!f.exists()) {
                    f.mkdir();
                }
            } else {
                if (!f.exists())
                    f.createNewFile();
                OutputStream os = new BufferedOutputStream(new FileOutputStream(f));
                InputStream is = zipFile.getInputStream(entry);
                int count = -1;
                while ((count=is.read(buffer)) != -1) {
                    os.write(buffer, 0, count);
                }
                os.close();
                is.close();
            }
        }
    }

    public void zip(File sourceFile, File zipFile) throws IOException {
        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(fos));
        zip(sourceFile, out, "");
        out.close();
    }

    private void zip(File sourceFile, ZipOutputStream out, String parentDir) throws IOException {
        if (sourceFile == null || !sourceFile.exists())
            throw new FileNotFoundException();
        String currentPath = parentDir + sourceFile.getName();
        if (sourceFile.isDirectory()) {
            ZipEntry entry = new ZipEntry(currentPath + File.separator);
            out.putNextEntry(entry);
            for (File temp : sourceFile.listFiles()) {
                zip(temp, out, currentPath + File.separator);
            }
        } else {
            ZipEntry entry = new ZipEntry(currentPath);
            out.putNextEntry(entry);
            byte[] buffer = new byte[128];
            FileInputStream fis = new FileInputStream(sourceFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            int count = 0;
            while ((count = bis.read(buffer)) != -1) {
                out.write(buffer, 0, count);
            }
            bis.close();
        }
    }


    //======================================================================================


    //我的压缩
    public static void myZip2(List<InputStream> inputStreamList, OutputStream outputStream) throws Exception {
        InputStream input = null;    // 定义文件输入流
        ZipOutputStream zipOut = null;    // 声明压缩流对象
        zipOut = new ZipOutputStream(outputStream);
        zipOut.setComment("注释") ;	// 设置注释
        int temp = 0;
        for (InputStream inputStream : inputStreamList) {
            input = inputStream;
            zipOut.putNextEntry(new ZipEntry("fileName.zip"));    // 设置ZipEntry对象
            while ((temp = input.read()) != -1) {    // 读取内容
                zipOut.write(temp);    // 压缩输出
            }
            input.close();    // 关闭输入流
        }
        zipOut.close();    // 关闭输出流
    }

}