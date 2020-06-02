package life.majiang.community.community.provider;
import com.alibaba.fastjson.JSON;
import life.majiang.community.community.dto.AcesstokenDTO;
import life.majiang.community.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


@Component
public class GithubProvider {
    public String getAcesstoken(AcesstokenDTO acesstokenDTO)
    {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(acesstokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {

            String string = response.body().string();
            System.out.println(string);
            String  token = string.split("&")[0].split("=")[1];
            return  token;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();

        try {
            System.out.println(request);
            Response response;//出错代码
            response = client.newCall(request).execute();//超時 獲取不到數據
            System.out.println(response);
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);

            return githubUser;
        }catch (IOException e){

        }
        return null;
    }

}
