package com.dx.test.controller;

import com.dx.test.model.vo.UserOnlineVo;
import com.dx.test.shiro.RedisSessionDao;
import com.dx.test.shiro.ShiroService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/online")
public class OnlineUserController {
    @Autowired
    private RedisSessionDao redisSessionDao;
    @Autowired
    private ShiroService shiroService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("online/list.html");
        List<UserOnlineVo> userOnlineVoList = this.shiroService.getOnlineUserList();
        mv.addObject("list", userOnlineVoList);

        return mv;
    }

    /**
     * 强制踢出用户
     */
    @RequestMapping(value = "/kickout", method = RequestMethod.GET)
    @ResponseBody
    public String kickout(String sessionId, String username) {
        try {
            if (SecurityUtils.getSubject().getSession().getId().equals(sessionId)) {
                return "不能踢出自己";
            }
            shiroService.kickout(sessionId, username);
            return "踢出用户成功";
        } catch (Exception e) {
            return "踢出用户失败";
        }
    }

}
