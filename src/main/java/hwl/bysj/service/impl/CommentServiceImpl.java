package hwl.bysj.service.impl;

import hwl.bysj.entity.Comment;
import hwl.bysj.mapper.CommentMapper;
import hwl.bysj.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dengfeng
 * @since 2023-05-06
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
