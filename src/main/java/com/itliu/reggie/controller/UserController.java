package com.itliu.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itliu.reggie.common.R;
import com.itliu.reggie.entity.User;
import com.itliu.reggie.service.UserService;
import com.itliu.reggie.utils.Identifyingcode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @create 2023-05-27-17:11
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    //发送验证码
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user,HttpSession session){
        //获取手机号
        String phone = user.getPhone();

        if(StringUtils.isNotEmpty(phone)){
            //生成随机的4验证码
            String code = Identifyingcode.idetifyingcode(4);
            log.info("code={}",code);

            //需要将生成的验证码保存到Session
            session.setAttribute(phone,code);
        }
        return R.success("手机验证码短信发送成功");
    }

    //用户登录
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        log.info(map.toString());

        //获取手机号
        String phone = map.get("phone").toString();
        //获取验证码
//        String code = map.get("code").toString();
        //从Session中获取保存的验证码
//        Object codeInSession = session.getAttribute(phone);

        //进行验证码的对比（页面提交的验证码和Session中保存的验证码比对—）
//        if(codeInSession != null && codeInSession.equals(code)){
//            //如果能够比对成功，说明登陆成功
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);

            User user = userService.getOne(queryWrapper);
            if(user == null){
                //判断当前手机号对应的是否为新用户，如果是新用户就自动完成注册
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
                session.setAttribute("user",user.getId());
            }
            if(user != null){
                session.setAttribute("user",user.getId());
                return R.success(user);
            }
//        }

        return R.error("登录失败");
    }
}


//通常情况下，前端通过发送HTTP请求到后端的某个接口（例如/sendMsg），
// 后端接口中的代码会通过某种方式创建或获取HttpSession对象，并对其进行操作。
//
//在传统的Java Web应用程序中，HttpSession对象可以通过HttpServletRequest对象获取。
// 在Spring框架中，你可以使用HttpSession作为控制器方法参数，Spring会自动将当前请求的HttpSession对象注入进来。
// 例如，你的代码中的HttpSession session参数就是通过这种方式获得的。
//
//因此，在前端发送HTTP请求时，你需要确保请求中包含所需的信息，
// 例如在请求头中设置Session ID，以便后端可以通过Session ID获取相应的HttpSession对象。
// 具体的实现方式可能依赖于你所使用的前端框架和后端技术。