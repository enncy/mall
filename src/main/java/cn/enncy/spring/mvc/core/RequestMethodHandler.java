package cn.enncy.spring.mvc.core;


import cn.enncy.spring.mvc.annotation.Get;
import cn.enncy.spring.mvc.annotation.Post;
import cn.enncy.spring.mvc.constant.HttpMethods;
import cn.enncy.spring.mvc.entity.RequestMethod;

/**
 * //TODO
 * <br/>Created in 17:47 2021/11/20
 *
 * @author enncy
 */
public class RequestMethodHandler {

    @MethodHandler(target = Post.class,type = HttpMethods.POST)
    public void post(Post post){

        System.out.println("RequestMethodHandler "+post);
    }

    @MethodHandler(target = Get.class,type = HttpMethods.GET)
    public void get(Get get){
        System.out.println("RequestMethodHandler "+get);
    }



}
