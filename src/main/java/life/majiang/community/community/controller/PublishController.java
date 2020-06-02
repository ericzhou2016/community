package life.majiang.community.community.controller;

import life.majiang.community.community.mapper.QuestionMapper;
import life.majiang.community.community.model.Question;
import life.majiang.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired QuestionMapper questionMapper;


    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required = false) String tag,
            HttpServletRequest request,
            Model model) {

        if (title == null ||title == "")
        {
            model.addAttribute("error","標題不能爲空");
            return "publish";
        }

        if (description == null ||description == "")
        {
            model.addAttribute("error","內容不能爲空");
            return "publish";
        }

        if (tag == null ||tag == "")
        {
            model.addAttribute("error","標籤不能爲空");
            return "publish";
        }

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);


        System.out.println(request);
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error","用户未登录");
            System.out.println("用戶未登錄");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreater(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreater());

        questionMapper.create(question);

        return "publish";
    }
}
