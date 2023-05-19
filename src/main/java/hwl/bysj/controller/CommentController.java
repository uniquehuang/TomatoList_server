package hwl.bysj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import hwl.bysj.entity.Comment;
import hwl.bysj.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dengfeng
 * @since 2023-05-06
 */
@RestController
@RequestMapping("/comment")
@Api(tags = "评论接口")
public class CommentController {

    @Autowired
    CommentService commentService;

    @ApiOperation(value = "评论上传接口")
    @PostMapping("/addComment")
    public Result addComment(@RequestBody Comment comment){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        comment.setTime((format.format(date)));
        commentService.save(comment);
        return Result.success("评论成功");
    }

    @ApiOperation(value = "获取评论")
    @GetMapping("/getComment")
    public Result getNoteListByUserPhone(){
        List<Comment> comments=commentService.list();
        return Result.success(comments);
    }

}
