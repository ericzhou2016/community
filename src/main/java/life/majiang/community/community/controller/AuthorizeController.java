package life.majiang.community.community.controller;


import life.majiang.community.community.dto.AcesstokenDTO;
import life.majiang.community.community.dto.GithubUser;
import life.majiang.community.community.mapper.UserMapper;
import life.majiang.community.community.model.User;
import life.majiang.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController{
    @Autowired
    private GithubProvider githubProvider;



    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.url}")
    private String redirect;

    @Autowired UserMapper userMapper;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){



        AcesstokenDTO acesstokenDTO = new AcesstokenDTO();
        acesstokenDTO.setCode(code);
        acesstokenDTO.setRedirect_url(redirect);
        acesstokenDTO.setClient_id(clientId);
        acesstokenDTO.setState(state);
        acesstokenDTO.setClient_secret(clientSecret);

        String accesstoken = githubProvider.getAcesstoken(acesstokenDTO);

        System.out.println(accesstoken);
        GithubUser githubUser =  githubProvider.getUser(accesstoken);

        System.out.println(githubUser.toString());

        if (githubUser.getName()!=null && githubUser.getId() != null) {
            System.out.println(githubUser.getName());
            System.out.println(githubUser.getBio());
        }else
        {
            System.out.println("get nothing !%");
        }



        if(githubUser !=null && githubUser.getId() !=null)
        {
            //登录成功,写cookie 和  session


            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountID(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatar_url());
            userMapper.insert(user);

            response.addCookie(new Cookie("token",token));



            request.getSession().setAttribute("user",githubUser);


            return "redirect:/";
        }else
        {
            //登录失败,重新登录
            return "redirect:/";
        }
    }
}
