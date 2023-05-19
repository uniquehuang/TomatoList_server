package hwl.bysj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import hwl.bysj.entity.Forum;
import hwl.bysj.entity.User;
import hwl.bysj.service.ForumService;
import hwl.bysj.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dengfeng
 * @since 2023-05-02
 */
@RestController
@RequestMapping(value = "/forum")
@Api(tags = "论坛上传接口")
public class ForumController {
    @Autowired
    ForumService forumService;

    @Autowired
    UserService userService;

    @ResponseBody
    @PostMapping(value = "/upload")
    @ApiOperation(value = "单个图片论坛上传接口")
    public Object upload(@RequestParam(required = false) MultipartFile file,
                         @RequestParam("username")String username,
                         @ApiParam(required = false,value = "标题") @RequestParam(required = false,value = "title")String title,
                         @ApiParam(required = false,value = "内容")@RequestParam(required = false,value = "article")String atricle)throws IOException {
        Forum forum=new Forum();

        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        Assert.notNull(user,"用户不存在");
        forum.setUsername(user.getUsername());
        if(atricle!=null)
            forum.setArticle(atricle);
        if(title!=null)
            forum.setTitle(title);
        if (file!=null)
            forum.setImgurl1(saveFile(file));
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        forum.setPosttime((format.format(date)));
        forum.setUsername(username);
        forum.setUserimg(user.getUserimg());
        forumService.save(forum);
        return Result.success(forum);
    }

    @ResponseBody
    @PostMapping(value = "/multiUpload")
    @ApiOperation(value = "多个最多三张图片论坛上传接口")
    public Object multiUpload(@RequestParam(required = false)MultipartFile file1,
                              @RequestParam(required = false)MultipartFile file2,
                              @RequestParam(required = false)MultipartFile file3,
                              @RequestParam("username")String username,
                              @ApiParam("标题") @RequestParam(required = false,value = "title")String title,
                              @ApiParam("内容")@RequestParam(required = false,value = "article")String atricle)throws IOException{
        Forum forum=new Forum();
        if(atricle!=null)
            forum.setArticle(atricle);
        if(title!=null)
            forum.setTitle(title);
        if (file1!=null)
            forum.setImgurl1(saveFile(file1));
        if (file2!=null)
            forum.setImgurl2(saveFile(file2));
        if (file3!=null)
            forum.setImgurl3(saveFile(file3));
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        Assert.notNull(user,"用户不存在");
        forum.setUsername(user.getUsername());
        forum.setUserimg(user.getUserimg());
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        forum.setPosttime((format.format(date)));
        forumService.save(forum);
        return Result.success(forum);
    }


    private String saveFile(MultipartFile file){

        if (file.isEmpty()){
            return "未选择文件";
        }
        String filename = file.getOriginalFilename(); //获取上传文件原来的名称

        String filePath = "C:\\BtSoft\\tomcat\\tomcat8\\webapps\\resource\\upload\\";
        File temp = new File(filePath);
        if (!temp.exists()){
            temp.mkdirs();
        }
        String url =("http://47.120.41.185:8082/resource/upload/"+filename);
        File localFile = new File(filePath+filename);
        System.out.println(localFile.getAbsolutePath());
        try {
            file.transferTo(localFile); //把上传的文件保存至本地
            System.out.println(file.getOriginalFilename()+" 上传成功");
        }catch (IOException e){
            e.printStackTrace();
            return "上传失败";
        }

        return url;
    }

    @ApiOperation(value = "获取论坛所有数据")
    @GetMapping("/getForumItemList")
    public Result getForumItemList(){
        List<Forum> forumList =forumService.getBaseMapper().selectList(new QueryWrapper<Forum>()
                .orderByDesc("id"));
        return Result.success(forumList);
    }

    @ApiOperation(value = "根据username获取论坛数据")
    @GetMapping("/getForumItemListByUserName")
    public Result getForumItemListByUserName(@RequestParam("username") String username){
        List<Forum> forumList =forumService.getBaseMapper().selectList(new QueryWrapper<Forum>()
                .eq("username",username)
                .orderByDesc("id"));
        return Result.success(forumList);
    }

    @ApiOperation(value = "根据Id更新论坛的点赞数量")
    @PostMapping("/updateForumByUserName")
    public Result updateForumByUserName(@RequestParam(value = "dzflag") int dzflag,@RequestParam(value = "username")String username,@RequestParam(value="id")int id){
        Forum forum = forumService.getOne(new QueryWrapper<Forum>()
                .eq("username", username).eq("id",id));
        if (dzflag==1)
            forum.setDzcount(forum.getDzcount()+1);
        if (dzflag==0&&forum.getDzcount()>0)
            forum.setDzcount(forum.getDzcount()-1);
        forum.setDzflag(dzflag);
        forumService.updateById(forum);
        return Result.success(forum);
    }

}
