package cn.enncy.mall.controller.restful;


import cn.enncy.mall.bean.ResultBody;
import cn.enncy.mall.utils.Security;

import cn.enncy.spring.mvc.annotation.Post;
import cn.enncy.spring.mvc.annotation.RestController;
import cn.enncy.spring.mvc.annotation.params.Param;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * //TODO
 * <br/>Created in 12:46 2021/11/26
 *
 * @author enncy
 */

@RestController
public class CommonRestController {

    HttpServletRequest request;
    HttpSession session;
    HttpServletResponse response;


    @Post("image/upload")
    public ResultBody imageUpload(@Param("name") String name){
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List<FileItem> items = upload.parseRequest(request);
            Map<String, FileItem> map = items.stream().collect(Collectors.toMap(FileItem::getFieldName, fileItem -> fileItem));
            FileItem avatarItem = map.remove("image");
            String avatarPath = "/assets/img/" + Security.stringToMD5(name) + ".png";
            long size = avatarItem.getSize();
            if (size > 0) {
                // 获取文件路径

                String filePath = request.getServletContext().getRealPath(avatarPath);
                System.out.println("name "+name);
                System.out.println("filePath "+filePath);
                // 保存文件，如果存在则删除
                File file = new File(filePath);
                if (file.exists()) {
                    if (file.delete()) {
                        avatarItem.write(file);
                    }
                } else {
                    avatarItem.write(file);
                }
            }

            return ResultBody.of(avatarPath);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultBody.error();
        }
    }


}
