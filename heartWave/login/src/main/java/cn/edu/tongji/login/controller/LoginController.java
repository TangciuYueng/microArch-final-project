package cn.edu.tongji.login.controller;

import cn.edu.tongji.login.dto.AddUserRequest;
import cn.edu.tongji.login.dto.UpdateUserRequest;
import cn.edu.tongji.login.model.User;
import cn.edu.tongji.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private final RestTemplate restTemplate;  //需要配置RestTemplate的Bean
    private final DiscoveryClient discoveryClient;

    @Value("${student.name}")
    private String name;
    @Value("${student.id}")
    private Integer id;
    @Value("${student.gender}")
    private String gender;
    @Value("${student.age}")
    private Integer age;
    @Value("${student.major}")
    private String major;

    @Override
    public String toString() {
        return "HelloController{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", major='" + major + '\'' +
                '}';
    }

    @RequestMapping("/hello")
    private String sayHello() {
        return toString();
    }

    @GetMapping("/another-service")
    public ResponseEntity<String> anotherService() {
        //获取所有微服务实例列表
        List<ServiceInstance> instances = discoveryClient.getInstances("music-room-service");
        if (instances.size() == 0)
            return null;

        //手写负载均衡，随机分配一个实例提供服务
        ServiceInstance instance = instances.get(new Random().nextInt(instances.size()));

        //发送http请求并调用其余实例的服务
        //可通过ResponseEntity的类型参数指定返回类型，一定要与对方Controller的返回类型一致

        return restTemplate.exchange(
                instance.getUri() + "/api/musicroom/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
    }

    @RequestMapping("/config-test")
    public String configTest() {
        return toString();
    }
    
    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = loginService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(loginService.getUserById(id));
    }

    @PostMapping("/user/add")
    public int addUser(@RequestBody AddUserRequest addUserRequest) {
        return loginService.addUser(addUserRequest);
    }

    @PutMapping("/user/update")
    public int updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        return loginService.updateUser(updateUserRequest);
    }
}
