package com.manage.source.controller;

import com.manage.common.APIResponse;
import com.manage.common.BaseController;
import com.manage.common.Constants;
import com.manage.common.UrlConstants;
import com.manage.exception.impl.BizExceptionStatusEnum;
import com.manage.exception.impl.SysExceptionStatusEnum;
import com.manage.source.bean.FileDetail;
import com.manage.source.service.FileService;
import com.manage.system.bean.DepartBean;
import com.manage.system.model.SysPermissionDto;
import com.manage.system.service.DepartService;
import com.manage.system.service.RoleMenuService;
import com.manage.system.service.RolePermService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luya on 2018/6/11.
 */
@RestController
@RequestMapping(value = UrlConstants.URL_FILE_MODEL)
public class FileController extends BaseController {
    private Log logger = LogFactory.getLog(FileController.class);
    // 系统文件根目录
    @Value("${file.fileRootPath}")
    private String fileRootPath;

    @Autowired
    private DepartService departService;

    @Autowired
    private RolePermService rolePermService;

    @Autowired
    private FileService fileService;

    /*根据菜单id和用户角色获取用户在当前部门内的根目录*/
    @RequestMapping(value = "/getBasePath", method = RequestMethod.GET)
    public APIResponse getRootpath(int menuId) {
        // 获取部门编码
        DepartBean departBean = departService.getDepartByMenuId(menuId);

        String departPath = fileRootPath + File.separator + departBean.getCode();
        // 创建路径
        File rootPath = new File(departPath);
        if (!rootPath.exists()) {
            fileService.makeDirs(departPath);
        }
        return APIResponse.toOkResponse(rootPath.getPath());
    }

    /*根据菜单id和用户角色获取用户在当前部门内的根目录*/
    private List<SysPermissionDto> getUserPermistion(int menuId) {
        try {
            // 获取权限
            if (StringUtils.isEmpty(menuId)) {
                // 参数不完整
                throw new Exception(SysExceptionStatusEnum.BAD_REQUEST.getMessage());
            }
            String account = super.getAccount();
            // 获取用户当前部门权限列表
            List<SysPermissionDto> permissionDtoList = rolePermService.getPermByRoleIdMenuId(menuId, account);
            return permissionDtoList;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
        return null;
    }

    @RequestMapping(value = "/fileList")
    public APIResponse fileList(@RequestParam("menuId") Integer menuId, @RequestParam("path") String path) {

        // 如果是根目录不进行操作
        if (fileRootPath.contains(path)) {
            return APIResponse.toExceptionResponse(BizExceptionStatusEnum.FILE_NOT_EXEC_ERROR);
        }
        // 获取角色对应部门权限
        List<SysPermissionDto> permissionDtoList = this.getUserPermistion(menuId);
        if (permissionDtoList != null) {
            // 查看权限
            boolean searchFlg = false;

            for (SysPermissionDto dto : permissionDtoList) {
                // 拥有查看权限
                if (Constants.PERM_TYPE_SELECT.equals(dto.getName())) {
                    searchFlg = true;
                }
            }
            // 获取列表
            if (searchFlg) {
                // 获取文件列表
                File listPath = new File(path);
                // TODO 如果是文件想办法打开它
                if (listPath.isFile()) {
                    String erro = path + " is a file";
                    logger.error(erro);
                    return APIResponse.toExceptionResponse(BizExceptionStatusEnum.FILE_NOT_DIR_ERROR);
                }
                if (!listPath.exists()) {
                    String erro = path + " no exist";
                    logger.error(erro);
                    return APIResponse.toExceptionResponse(BizExceptionStatusEnum.FILE_NOT_EXIST_ERROR);
                }

                List<FileDetail> reFiles = fileService.listAdminFiles(listPath);
                return APIResponse.toOkResponse(reFiles);
            }
        }

        return APIResponse.toExceptionResponse(BizExceptionStatusEnum.FILE_NOT_EXEC_ERROR);
    }

    /*按类型查找文件*/
    public void fileListByType() {

    }

    /**
     * 上传镜像文件
     *
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/upload-file")
    public APIResponse uploadFile(HttpServletRequest request) throws Exception {
        String filename = request.getParameter("name");
        String path = request.getParameter("path");
        String menuIdStr = request.getParameter("menuId");
        Integer menuId = 0;
        //  转换menuid
        try {
            menuId = Integer.parseInt(menuIdStr);
        } catch (Exception e) {
            throw new Exception(SysExceptionStatusEnum.BAD_REQUEST.getMessage());
        }

        // 如果是根目录不进行操作
        if (fileRootPath.contains(path)) {
            throw new Exception(BizExceptionStatusEnum.FILE_NOT_EXEC_ERROR.getMessage());
        }

        // 获取角色对应部门权限
        List<SysPermissionDto> permissionDtoList = getUserPermistion(menuId);
        if (permissionDtoList != null) {
            for (SysPermissionDto dto : permissionDtoList) {
                if (Constants.PERM_TYPE_UPLOAD.equals(dto.getName())) {
                    fileService.uploadFile(request, path, filename);
                    return APIResponse.toOkResponse();
                }
            }
        }
        throw new Exception(BizExceptionStatusEnum.FILE_NOT_EXEC_ERROR.getMessage());
    }

    public void viewFile() {

    }

    // 文件下载,表示/upload后面接的任何路径都会进入到这里
    @RequestMapping("/download/**")
    public APIResponse downloadFile(HttpServletResponse response, HttpServletRequest request,
                                    @RequestParam("paths") String paths, @RequestParam("menuId") Integer menuId) throws Exception{

        // 获取角色对应部门权限
        List<SysPermissionDto> permissionDtoList = getUserPermistion(menuId);
        if (permissionDtoList != null) {
            for (SysPermissionDto dto : permissionDtoList) {
                if (Constants.PERM_TYPE_DOWNLOAD.equals(dto.getName())) {
                    String[] path = paths.split(":");
                    for (String filePath : path) {
                        File file = new File(filePath);
                        try {
                            if (file.exists()) {
                                String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
                                String userAgent = request.getHeader("User-Agent");
                                /* IE 8 至 IE 10 *//* IE 11 */
                                if (userAgent.toUpperCase().contains("MSIE") || userAgent.contains("Trident/7.0")) {
                                    fileName = URLEncoder.encode(fileName, "UTF-8");
                                } else if (userAgent.toUpperCase().contains("MOZILLA") || userAgent.toUpperCase().contains("CHROME")) {
                                    fileName = new String(fileName.getBytes(), "ISO-8859-1");
                                } else {
                                    fileName = URLEncoder.encode(fileName, "UTF-8");
                                }
                                response.setHeader("Content-type", "text/html;charset=UTF-8");
                                response.setCharacterEncoding("UTF-8");
                                // 设置下载文件的名称,如果想直接在想查看就注释掉，因为要是文件原名才能下载，不然就只能在浏览器直接浏览无法下载
                                response.setHeader("content-disposition", "attachment;filename=" + fileName);

                                // 把文件输出到浏览器
                                OutputStream os = response.getOutputStream();
                                FileInputStream fs = new FileInputStream(file);
                                byte[] b = new byte[1024];
                                int len = 0;
                                while ((len = fs.read(b)) > 0) {
                                    os.write(b, 0, len);
                                }
                                fs.close();
                                os.flush();
                            } else {
                                response.sendError(404);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new Exception(SysExceptionStatusEnum.SERVER_ERROR.getMessage());
                        }
                    }
                    return APIResponse.toOkResponse();
                }
            }
        }
        // 没有权限返回
        return APIResponse.toExceptionResponse(BizExceptionStatusEnum.FILE_NOT_EXEC_ERROR);
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public APIResponse deleteFile(@RequestParam("paths") String paths, @RequestParam("menuId") Integer menuId) {
        // 空文件删除
        if (StringUtils.isEmpty(paths)) {
            return APIResponse.toOkResponse();
        }

        List<SysPermissionDto> permissionDtoList = getUserPermistion(menuId);
        if (permissionDtoList != null) {
            for (SysPermissionDto dto : permissionDtoList) {
                if (Constants.PERM_TYPE_DELETE.equals(dto.getName())) {
                    String[] dirs = paths.split(":");
                    List<String> errorFileList = new ArrayList<>();
                    for (String dir : dirs) {
                        if (!fileService.deleteFile(dir)) {
                            errorFileList.add(dir.substring(dir.lastIndexOf(File.separator) + 1));
                        }
                    }
                    return APIResponse.toOkResponse(errorFileList);
                }
            }
        }
        return APIResponse.toExceptionResponse(BizExceptionStatusEnum.FILE_NOT_EXEC_ERROR);
    }

    //TODO
    public void changeFolder() {
    }

    @RequestMapping(value = "/newdir")
    public APIResponse newFolder(@RequestParam("path") String path, @RequestParam("dir") String dir,
                                 @RequestParam("menuId") Integer menuId) {
        try {
            // 获取上传权限
            List<SysPermissionDto> permissionDtoList = getUserPermistion(menuId);
            if (permissionDtoList != null) {
                for (SysPermissionDto dto : permissionDtoList) {
                    // 有权限可以创建文件夹
                    if (Constants.PERM_TYPE_UPLOAD.equals(dto.getName())) {
                        if (!StringUtils.isEmpty(path) && !StringUtils.isEmpty(dir)) {
                            fileService.makeDirs(path + File.separator + dir);
                            return APIResponse.toOkResponse();
                        }
                    }
                }
            }
            return APIResponse.toExceptionResponse(BizExceptionStatusEnum.FILE_NOT_EXEC_ERROR);
        } catch (Exception e) {
            return APIResponse.toExceptionResponse(BizExceptionStatusEnum.FILE_CREATE_ERROR);
        }
    }

}
