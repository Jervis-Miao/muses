/*
Copyright All rights reserved.
 */

package functional;

import com.utils.mybatis.GeneratorLauncher;
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
				.generate("D:\\workspace\\muses\\demo-web\\src\\main\\resources\\mybatis\\generatorConfig.xml");
	}
}
