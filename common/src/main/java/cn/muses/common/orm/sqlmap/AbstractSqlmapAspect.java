package cn.muses.common.orm.sqlmap;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import cn.muses.common.orm.common.OrmContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.util.ClassUtils;

import cn.muses.common.orm.annotations.Sqlmap;
import cn.muses.common.orm.sqlmap.sqlsession.SqlSession;

/**
 * 执行@SqlExecutor注解标记的方法对应的sqlMap.xml对应的sql
 *
 * 1、在Spring配置文件中配置 <bean class="com.muses.common.orm.aspect.SqlExecutorAspect" /> <bean id="ormConversionService"
 * class="org.springframework.context.support.ConversionServiceFactoryBean" />
 * 2、配置xxx_Ext_SqlMap.xml，其中sqlMap的namespace为：实现类名称，select|update|delete|insert的id为方法名
 *
 * @author Jervis
 */
@SuppressWarnings("unused")
public abstract class AbstractSqlmapAspect {
	protected final Logger										logger		= LoggerFactory.getLogger(this.getClass());
	protected final LocalVariableTableParameterNameDiscoverer	local		= new LocalVariableTableParameterNameDiscoverer();
	protected final ConcurrentMap<Object, SqlSession>			sqlSessions	= new ConcurrentHashMap<Object, SqlSession>();
	@Autowired
	protected OrmContext ormContext;

	@Around(value = "@annotation(com.muses.common.orm.annotations.Sqlmap) && @annotation(sqlmap)")
	public Object proceeding(ProceedingJoinPoint joinPoint, Sqlmap sqlmap) throws Throwable {
		MethodSignature ms = (MethodSignature) joinPoint.getSignature();
		Method method = ms.getMethod();
		// 将方法参数封装为k-v的map结构，以便执行框架代码
		Object[] args = joinPoint.getArgs();
		Map<String, Object> parameters = null;
		if ((args != null) && (args.length > 0)) {
			parameters = new HashMap<String, Object>();
			String[] names = local.getParameterNames(method);
			for (int i = 0; i < names.length; i++) {
				parameters.put(names[i], args[i]);
			}
		}
		// 执行原方法
		Object source = execute(createSqlSession(joinPoint), sqlmap, method, parameters);
		joinPoint.proceed();// TODO 这里其实是可以做点事情，譬如类型转换是否可以在这里完成？
		// 如定义返回值类型与实际执行方法类型不一致，则进行类型转换，这里用到了spring里的converter
		Class<?> returnType = method.getReturnType();
		if (ClassUtils.isPrimitiveOrWrapper(returnType)) {
			return ormContext.getConversionService().convert(source, returnType);
		}
		TypeDescriptor targetTypeDescriptor = null;
		if (List.class.isAssignableFrom(returnType)) {
			Class<?> targetType = GenericTypeResolver.resolveReturnTypeArgument(method, List.class);
			targetTypeDescriptor = TypeDescriptor
					.collection(method.getReturnType(), TypeDescriptor.valueOf(targetType));
		} else {
			targetTypeDescriptor = TypeDescriptor.valueOf(returnType);
		}
		return ormContext.getConversionService()
				.convert(source, TypeDescriptor.forObject(source), targetTypeDescriptor);
	}

	protected abstract SqlSession createSqlSession(ProceedingJoinPoint joinPoint);

	private Object execute(SqlSession sqlSession, Sqlmap sqlmap, Method method, Object parameter) {
		Object result = null;
		String name = method.getName();
		String sqlMapId = method.getDeclaringClass().getName().concat(".").concat(name);
		logger.debug("sql map id : " + sqlMapId);
		if (sqlmap.value().equals(Sqlmap.Dml.SELECT) || name.startsWith(Sqlmap.Dml.SELECT.name().toLowerCase())) {
			if (List.class.isAssignableFrom(method.getReturnType())) {
				result = sqlSession.selectList(sqlMapId, parameter);
			} else {
				result = sqlSession.selectOne(sqlMapId, parameter);
			}
		} else if (sqlmap.value().equals(Sqlmap.Dml.INSERT) || name.startsWith(Sqlmap.Dml.INSERT.name().toLowerCase())) {
			result = sqlSession.insert(sqlMapId, parameter);
		} else if (sqlmap.value().equals(Sqlmap.Dml.UPDATE) || name.startsWith(Sqlmap.Dml.UPDATE.name().toLowerCase())) {
			result = sqlSession.update(sqlMapId, parameter);
		} else if (sqlmap.value().equals(Sqlmap.Dml.DELETE) || name.startsWith(Sqlmap.Dml.DELETE.name().toLowerCase())) {
			result = sqlSession.delete(sqlMapId, parameter);
		}
		return result;
	}
}
