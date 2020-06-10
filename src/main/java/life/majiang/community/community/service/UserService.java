package life.majiang.community.community.service;


import life.majiang.community.community.mapper.UserMapper;
import life.majiang.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    public void createOrUpdate(User user) {
        User dbuser = userMapper.findByAccountId(user.getAccountID());
        if(dbuser == null){
            //插入操作
        }
        else {
            //更新操作
        }

    }
}
