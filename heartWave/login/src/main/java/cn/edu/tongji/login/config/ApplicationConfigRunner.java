package cn.edu.tongji.login.config;

import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfigRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments arguments) {
        IdGeneratorOptions idGeneratorOptions =new IdGeneratorOptions((short) 1);
        YitIdHelper.setIdGenerator(idGeneratorOptions);
        System.out.println("Id生成器初始化配置完成");
    }
}
