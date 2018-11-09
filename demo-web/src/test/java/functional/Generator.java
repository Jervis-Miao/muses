/*
Copyright All rights reserved.
 */

package functional;

import com.utils.GeneratorLauncher;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author miaoqiang
 * @date 2018/11/8.
 */
public class Generator {
	public static void main(String[] args) throws InterruptedException, SQLException, InvalidConfigurationException,
			XMLParserException, IOException {
		GeneratorLauncher
				.generate("D:\\workspace\\xyzjt\\xyzjt-app\\src\\main\\resources\\mybatis\\generatorConfig.xml");
	}
}
