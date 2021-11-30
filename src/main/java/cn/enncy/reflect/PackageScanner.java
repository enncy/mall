package cn.enncy.reflect;


import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author enncy
 */
public class PackageScanner {
    private String packageName;

    /**
     * 开始扫描指定包下的类，并返回
     * packageName  -包名,例如  xx.xxx.xxx
     */
    public Class<?>[] scan(String packageName) throws URISyntaxException, ClassNotFoundException {
        this.packageName = packageName;
        URL url = this.getClass().getClassLoader().getResource(packageName.replace('.', File.separatorChar));

        assert url != null;
        File file = new File(url.toURI());

        return scanFile(file);
    }

    /**
     * 扫描文件，这里不使用递归，使用出栈入栈方式遍历
     */
    private Class<?>[] scanFile(File file) throws ClassNotFoundException {

        LinkedList<Class<?>> classList = new LinkedList<>();
        LinkedList<File> fileList = new LinkedList<>();
        fileList.push(file);

        while (!fileList.isEmpty()) {
            File targetFile = fileList.pop();
            if (targetFile.isFile() && targetFile.getName().endsWith(".class")) {
                //获取绝对路径
                String path = targetFile.getAbsolutePath();
                //获取包路径
                String packagePath = path.replace(file.getAbsolutePath(), "");
                String className = Arrays.stream(packagePath.split("\\"+File.separator ))
                        .filter(s -> !"".equals(s))
                        .collect(Collectors.joining("."));
                className = className.substring(0, className.lastIndexOf("."));
                //添加class到集合中
                classList.push(Class.forName(className));

            } else if (targetFile.isDirectory()) {
                fileList.addAll(Arrays.asList(Objects.requireNonNull(targetFile.listFiles())));
            }
        }
        return classList.toArray(new Class[0]);
    }

}
