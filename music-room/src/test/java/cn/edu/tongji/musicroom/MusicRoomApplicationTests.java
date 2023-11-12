package cn.edu.tongji.musicroom;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MusicRoomApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void encode() {
		StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
		EnvironmentStringPBEConfig environmentStringPBEConfig = new EnvironmentStringPBEConfig();

		environmentStringPBEConfig.setAlgorithm("PBEWithMD5AndDES");

		// 随机生成的额外字符串用来增加加密的复杂度
		environmentStringPBEConfig.setPassword("microArch");

		standardPBEStringEncryptor.setConfig(environmentStringPBEConfig);

		System.out.println("username " + standardPBEStringEncryptor.encrypt("music_room"));
		System.out.println("password " + standardPBEStringEncryptor.encrypt("microArch2023"));
	}

}
