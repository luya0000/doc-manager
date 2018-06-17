package com.manage.source.service;

import com.manage.common.Constants;
import com.manage.source.bean.FileDetail;
import com.manage.source.controller.FileController;
import com.manage.system.bean.UserBean;
import com.manage.system.model.UserRoleGroupDto;
import com.manage.system.service.UserRoleService;
import com.manage.system.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by luya on 2018/6/16.
 */
@Service
public class FileService {
    private Log logger = LogFactory.getLog(FileService.class);
    // 文件上传根目录
    private String uploadedPath;
    // 用户文件根目录
    private String userBasePath;
    // 小组根目录
    private String groupPath;

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserService userService;

    /*过滤目标地址下的文件列表*/
    public List<FileDetail> listAdminFiles(File currentFile, List<String> ownerDirs) {
        List<FileDetail> results = new LinkedList<>();
        File[] files = currentFile.listFiles();
        if (files == null || ownerDirs == null) {
            return Collections.emptyList();
        }
        for (String dir : ownerDirs) {
            File ownerFile = new File(dir);
            for (File file : files) {
                if (file.getPath().contains(ownerFile.getPath())) {
                    FileDetail fileDetail = new FileDetail();
                    fileDetail.setName(file.getName());
                    fileDetail.setSize(file.length());
                    fileDetail.setPath(file.getPath());
                    fileDetail.setDate(new SimpleDateFormat("yyyy/MM/dd HH:mm")
                            .format(new Date(file.lastModified())));
                    if (file.isFile()) {
                        long size = file.length() / 1024;
                        fileDetail.setUnit("KB");
                        if (size > 1000) {
                            size = size / 1024;
                            fileDetail.setUnit("MB");
                        }
                        size = size == 0 ? 1 : size;
                        fileDetail.setSize(size);// MB
                        String fileName = file.getName();
                        String pic = "";
                        String tmp = fileName.substring(fileName.lastIndexOf('.') + 1);
                        if ("gz".equals(tmp) || "tar".equals(tmp) || "zip".equals(tmp)) {
                            pic = "gz.png";
                        } else if ("gif".equals(tmp) || "png".equals(tmp) || "jpg".equals(tmp) || "jpeg".equals(tmp)) {
                            pic = "img.png";
                        } else if ("xls".equals(tmp) || "xlsx".equals(tmp)) {
                            pic = "excel.png";
                        } else {
                            pic = "other.png";
                        }
                        fileDetail.setPic(pic);
                        fileDetail.setType("file");
                    } else {
                        fileDetail.setType("path");
                        fileDetail.setPic("file.png");
                    }
                    results.add(fileDetail);
                }
            }

        }
        return results;
    }


    /*获取系统管理员以外用户可视的文件夹*/
    public List<String> getAdminUserDirs(Integer userId, String rootPath) {
        List<String> ownDirs = new ArrayList<>();
        try {
            List<UserRoleGroupDto> roleGroupDtoList = userRoleService.getRolesGroupByUserId(userId);
            for (UserRoleGroupDto dto : roleGroupDtoList) {
                // 拼接用户目录列表
                StringBuilder hasPath = new StringBuilder();
                // 系统管理员基本目录 等于根目录
                if (Constants.ADMIN_TYPE.equals(dto.getRoleType()) || Constants.NOMAL_TYPE.equals(dto.getRoleType())) {
                    // 高级管理员 拥有小组的路径
                    hasPath.append(rootPath).append(Constants.FILE_SPLITOR)
                            .append("GROUP").append(dto.getGroupId()).append("_").toString();
                } else {
                    hasPath.append(rootPath).append(Constants.FILE_SPLITOR)
                            .append("DEFAULT").append(Constants.FILE_SPLITOR).append(dto.getAccount()).toString();
                }
                ownDirs.add(hasPath.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ownDirs;
    }

    public void uploadFile(HttpServletRequest request, String path, String filename) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        File fileTmp = null;
        try {
            File savePath = new File(path);
            if (!savePath.exists() && savePath.mkdirs()) {
                logger.info("修改目录名称成功！");
            }
            File file = new File(path + File.separator + filename);
            fileTmp = new File(path + File.separator + filename + ".tmp");

            inputStream = request.getInputStream();
            outputStream = new FileOutputStream(fileTmp);
            int byteCount = 0;
            byte[] bytes = new byte[1024 * 1024];
            while ((byteCount = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, byteCount);
            }
            if (file.delete()) {
                logger.info("删除成功");
            }
            inputStream.close();
            outputStream.close();
            if (fileTmp.renameTo(file)) {
                logger.info("修改文件名称成功");
            }
        } catch (IOException ioe) {
            logger.error(ioe);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    logger.error(ex);
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ex) {
                    logger.error(ex);
                }
            }
            if (fileTmp != null && fileTmp.delete()) {
                logger.info("删除成功！");
            }
        }
    }




    /*public List<FileDetail> listFiles(File dir) {
        List<FileDetail> results = new LinkedList<>();
        File[] files = dir.listFiles();

        if (files == null) {
            return Collections.emptyList();
        }
        for (File file : files) {
            FileDetail fileDetail = new FileDetail();
            fileDetail.setName(file.getName());
            fileDetail.setSize(file.length());
            fileDetail.setPath(file.getPath());
            fileDetail.setDate(new SimpleDateFormat("yyyy/MM/dd HH:mm")
                    .format(new Date(file.lastModified())));
            if (file.isFile()) {
                long size = file.length() / 1024;
                size = size == 0 ? 1 : size;
                fileDetail.setSize(size);// KB
                String fileName = file.getName();
                String pic = "";
                String tmp = fileName.substring(fileName.lastIndexOf('.') + 1);
                switch (tmp) {
                    case "gz":
                        pic = "gz.png";
                        break;
                    case "img":
                        pic = "img.png";
                        break;
                    case "png":
                        pic = "pic.png";
                        break;
                    case "jpg":
                        pic = "pic.png";
                        break;
                    default:
                        pic = "other.png";
                        break;
                }
                fileDetail.setPic(pic);
                fileDetail.setType("file");
            } else {
                fileDetail.setType("path");
                fileDetail.setPic("file.png");
            }
            results.add(fileDetail);
        }
        return results;
    }*/


    private void makeDirs(String path) {
        File rootFile = new File(path);
        logger.info("make sure dirs exist " + rootFile.getAbsolutePath());
        if (!rootFile.exists() && rootFile.mkdirs()) {
            logger.info("创建目录成功：" + path);
        }
    }


    /*获取用户角色*/
    private String getUserRoleType() {

        return "";
    }
}
