/**
 * Benetech trainning app Copyrights reserved
 */

package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.argSecurity.SpringBootARGSecurity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootARGSecurity.class)
@WebAppConfiguration
public class ARGSecurityApplicationTests {

	@Test
	public void contextLoads() {
	}

}
