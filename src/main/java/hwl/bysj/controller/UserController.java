package hwl.bysj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import hwl.bysj.entity.User;
import hwl.bysj.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dengfeng
 * @since 2023-04-24
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    UserService userService;



    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public Result addUser(@Validated @RequestBody User registerUser){
        Result result = null;
        List<User> list = userService.list();

        for (int i1 = 0; i1 < list.size(); i1++) {
            if (registerUser.getUsername().equals(list.get(i1).getUsername())) {
                result = Result.fail("用户已存在");
                return result;
            }
        }
        userService.save(registerUser);
        return Result.success("注册成功");

    }




    //账号+密码
    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public Result login(@RequestParam(required = true) String password, @RequestParam(required = true)String username){
        User user = userService.getOne(new QueryWrapper<User>()
                .eq("username",username));
        if (user == null){
            return Result.fail("用户不存在！");
        }
        if (!user.getPassword().equals
                (password)) {
            return Result.fail("密码错误！");
        }
        return Result.success(user);
      /*  //通过map的形式返回需要的部分信息
           return Result.success(MapUtil.builder()
                .put("id", user.getId())
                .put("userphone", user.getUserphone())
                .put("password", user.getPassword())
                .put("username", user.getUsername())
                .put("gender",user.getGender())
                .put("code",user.getCode())
                .map()
        );*/

    }


}
