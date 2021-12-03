package cn.enncy.mall.controller;

import cn.enncy.mall.constant.Role;
import cn.enncy.mall.pojo.User;
import cn.enncy.mall.service.UserService;
import cn.enncy.mall.utils.Email;
import cn.enncy.mall.utils.Security;
import cn.enncy.mall.utils.StringUtils;
import cn.enncy.mybatis.core.ServiceFactory;
import cn.enncy.spring.mvc.annotation.Controller;
import cn.enncy.spring.mvc.annotation.Get;
import cn.enncy.spring.mvc.annotation.Post;
import cn.enncy.spring.mvc.annotation.params.Body;
import cn.enncy.spring.mvc.annotation.params.Param;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author enncy
 */

@Controller
public class CommonController {

    UserService userService = ServiceFactory.resolve(UserService.class);
    HttpServletResponse response;
    HttpServletRequest request;
    HttpSession session;

    @Get("/login")
    public String login(HttpSession session) throws IOException {

        // 如果已经登录，则无需登录
        User user = (User) session.getAttribute("user");
        if (user != null) {
            response.sendRedirect("/user");
        }
        String referer = request.getHeader("Referer");
        session.setAttribute("referer",referer);


        return "/login/index";

    }

    @Post("/login")
    public String loginHandler(@Param("account") String account, @Param("password") String password) throws IOException {

        String error = "";
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)) {
            error = "不能留空!";

        } else {
            UserService userService = ServiceFactory.resolve(UserService.class);

            User user = userService.findOneByAccount(account);
            if(user==null ){
                error = "用户不存在！";

            }else if(!user.isActive()){
                error = "用户未激活！";
            }
            else{
                if (  user.getPassword().equals(password)) {
                    request.setAttribute("msg", "登录成功!");
                    // 将用户存入 session
                    session.setAttribute("user", user);

                    String origin = (String) session.getAttribute("origin");
                    String referer = (String) session.getAttribute("referer");
                    System.out.println("origin "+origin);
                    System.out.println("referer "+referer);
                    if (StringUtils.notEmpty(origin)) {
                        response.sendRedirect(origin);
                    }else if(StringUtils.notEmpty(referer)){
                        response.sendRedirect(referer);
                    }
                    else {
                        return "index";
                    }
                } else {
                    error = "登录失败，账号或者密码错误!";

                }
            }

        }
        request.setAttribute("error", error);
        return "/login/index";
    }

    @Get("/register/check")
    public String checkRegister(@Param("token") String token, @Param("account") String account) throws IOException {
        if (checkToken(account, token)) {
            UserService userService = ServiceFactory.resolve(UserService.class);
            User user = userService.findOneByAccount(account);
            if(user==null){
                request.setAttribute("error", "验证已过期，请重新注册！");
                return "/register/index";
            }
            session.setAttribute("user", user);

            // 如果未激活，则激活，并且返回首页
            if ( !user.isActive()) {
                user.setActive(true);
                userService.update(user);
                return "/index";
            } else {
                request.setAttribute("error", "此账号已经被注册！");
                return "/register/index";
            }
        } else {
            request.setAttribute("error", "参数错误，请重新注册！");
            return "/register/index";
        }

    }

    @Get("/register")
    public String checkRegister(){
        return "/register/index";
    }

    @Post("/register")
    public String postRegister(@Body User user, @Param("confirmPassword") String confirmPassword ) {
        String error = "";
        String msg = "";
        if (StringUtils.isEmpty(user.getAccount()) || StringUtils.isEmpty(confirmPassword) || StringUtils.isEmpty(user.getPassword()) || StringUtils.isEmpty(user.getEmail())) {
            error = "不能留空!";
        } else if (!StringUtils.isWord(user.getAccount())){
            error = "用户名存在特殊字符，请删掉空格或者多余的字符！";
        }else if (!StringUtils.isWord(user.getPassword())){
            error = "密码存在特殊字符，请删掉空格或者多余的字符！";
        }else if (user.getAccount().length()>20 || user.getAccount().length()<2){
            error = "账号必须大于2小于20个字符!";
        }else if (user.getPassword().length()>20 || user.getPassword().length()<6){
            error = "密码必须大于6小于20个字符!";
        } else if (!confirmPassword.equals(user.getPassword())) {
            error = "2次输入的密码不一致!";
        } else{
            UserService userService = ServiceFactory.resolve(UserService.class);
            if (userService.findOneByAccount(user.getAccount()) != null) {
                error = "账号已被占用!";
            } else if (userService.findOneByEmail(user.getEmail()) != null) {
                error = "邮箱已被占用!";
            } else {
                try {
                    // 创建激活 秘钥 和 链接0
                    String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/register/check?account=" + user.getAccount() + "&token=" + Security.stringToMD5(user.getAccount());
                    // 发送注册右键
                    Email.register(user.getEmail(), url);
                    user.setRole(Role.USER.value);
                    user.setNickname("无昵称");
                    user.setProfile("无简介");
                    user.setBalance(new BigDecimal("0"));
                    userService.insert(user);
                    msg = "邮箱已发送，请在邮箱中验证您的账号!";
                } catch (Exception e) {
                    e.printStackTrace();
                    if(e.getMessage().contains("Invalid Addresses")){
                        error = "邮箱不存在";
                    }else{
                        error = "服务器错误";
                    }

                }
            }
        }
        request.setAttribute("user", user);
        request.setAttribute("error", error);
        request.setAttribute("msg", msg);
        return "/register/index";
    }


    @Get("/forget")
    public String foget() {
        return "/forget/index";
    }

    @Post("/forget")
    public String fogetPost(@Param("email") String email) {

        if (StringUtils.isEmpty(email)) {
            request.setAttribute("error", "邮箱不能为空");
        } else {
            // 创建激活 秘钥 和 链接
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/forget/reset?email=" + email + "&token=" + Security.stringToMD5(email);
            // 发送注册右键
            try {
                Email.reset(email, url);
                request.setAttribute("msg", "邮箱发送成功，请在邮箱里面点击链接找回密码。");
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "服务器错误");
            }
        }
        return "/forget/index";
    }


    @Get("/forget/reset")
    public String reset(@Param("email") String email, @Param("token") String token) throws IOException {
        if (checkToken(email, token)) {
            User user = userService.findOneByEmail(email);
            request.setAttribute("msg", user.getAccount() + " 请重置您的密码");
        } else {
            response.sendRedirect("/error?code=400");
        }
        return "/forget/reset/index";
    }

    // 验证密匙
    public boolean checkToken(String str, String token) {
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(str)) {
            return false;
        } else {
            return token.equals(Security.stringToMD5(str));
        }
    }

    @Post("/forget/reset")
    public String resetPost(@Param("email") String email, @Param("token") String token, @Param("password") String password, @Param("confirmPassword") String confirmPassword) throws IOException {
        if (checkToken(email, token)) {
            if (StringUtils.isEmpty(password) || StringUtils.isEmpty(confirmPassword)) {
                request.setAttribute("error", "不能留空!");
            } else if (!confirmPassword.equals(password)) {
                request.setAttribute("error", "2次输入的密码不一致!");
            } else {
                User user = userService.findOneByEmail(email);
                user.setPassword(password);
                userService.update(user);
                request.setAttribute("msg", "重置密码成功");
                response.sendRedirect("/login");
            }
        } else {
            response.sendRedirect("/error?code=400");
        }
        return "/forget/reset/index";
    }

    @Get("/logout")
    public void logout() throws IOException {
        session.removeAttribute("user");
        response.sendRedirect("/");
    }




}
