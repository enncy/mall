package cn.enncy.spring.mvc;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * //TODO
 * <br/>Created in 20:58 2021/11/26
 *
 * @author enncy
 */
public class PathUtils {


    /**
     * 转换路径
     *
     * @param path
     * @return java.util.List<java.lang.String>
     */
    public static List<String> splitPath(String path) {
        return Arrays.stream(path.split("/")).filter(s -> !"".equals(s)).collect(Collectors.toList());
    }
}
