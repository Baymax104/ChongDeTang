package com.cdtde.chongdetang.service.impl.user;
import com.cdtde.chongdetang.pojo.User;
import com.cdtde.chongdetang.service.impl.utils.UserDetailsImpl;
import com.cdtde.chongdetang.service.user.LoginService;
import com.cdtde.chongdetang.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Map<String, String> getToken(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(username,password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);    //登陆失败会自动处理

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticate.getPrincipal();
        User user = loginUser.getUser();
        String jwt = JwtUtil.createJWT(user.getId().toString());
        Map<String,String> map = new HashMap<>();
        map.put("error_message","success");
        map.put("token",jwt);
        return map;
    }
}
