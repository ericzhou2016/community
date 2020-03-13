package life.majiang.community.community.controller;


import life.majiang.community.community.dto.AcesstokenDTO;
import life.majiang.community.community.dto.GithubUser;
import life.majiang.community.community.provider.GithubProvider;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;

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



    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){



        AcesstokenDTO acesstokenDTO = new AcesstokenDTO();
        acesstokenDTO.setCode(code);
        acesstokenDTO.setRedirect_url(redirect);
        acesstokenDTO.setClient_id(clientId);
        acesstokenDTO.setState(state);
        acesstokenDTO.setClient_secret(clientSecret);




        String accesstoken = githubProvider.getAcesstoken(acesstokenDTO);

        System.out.println(accesstoken);
        GithubUser user =  githubProvider.getUser(accesstoken);

        System.out.println(user.getName());
        System.out.println(user.getBio());

        return "index";
    }
}
