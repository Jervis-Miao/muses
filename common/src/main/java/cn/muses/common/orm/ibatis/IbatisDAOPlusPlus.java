package cn.muses.common.orm.ibatis;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import cn.muses.common.orm.common.Constants;
import cn.muses.common.orm.page.PageReq;
import cn.muses.common.orm.page.PageRes;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConverterRegistry;

/**
 * IbatisDAO加强版，主要是默认添加入参与出参为DTO
 *
 * @author Jervis
 *
 * @param <Entity>
 * @param <Criteria>
 */
public abstract class IbatisDAOPlusPlus<Entity, Criteria> extends IbatisDAO<Entity, Criteria> {

	protected static final Map<Class<?>, TypeDescriptor>	typeDescriptorCache	= new HashMap<Class<?>, TypeDescriptor>();
	protected TypeDescriptor								entityTypeDescriptor;
	protected TypeDescriptor								entityListTypeDescriptor;

	@Override
	@PostConstruct
	public void postConstruct() {
		super.postConstruct();
		this.addConverter((ConverterRegistry) ormContext.getConversionService());
		Map<TypeVariable, Type> typeVariableMap = GenericTypeResolver.getTypeVariableMap(this.getClass());
		for (TypeVariable typeVariable : typeVariableMap.keySet()) {
			if (Constants.TYPE_VARIABLES_ENTITY.equals(typeVariable.getName())) {
				entityListTypeDescriptor = TypeDescriptor.collection(List.class,
						TypeDescriptor.valueOf((Class<?>) typeVariableMap.get(typeVariable)));
				entityTypeDescriptor = TypeDescriptor.valueOf((Class<?>) typeVariableMap.get(typeVariable));
			}
		}
	}

	protected abstract void addConverter(ConverterRegistry registry);

	public <DTO> int deleteByPrimaryKeyPlus(DTO dto) {
		Entity entity = (Entity) ormContext.getConversionService().convert(dto, entityTypeDescriptor.getType());
		int rows = super.deleteByPrimaryKey(entity);
		return rows;
	}

	public <DTO> void insertPlus(DTO dto) {
		Entity entity = (Entity) ormContext.getConversionService().convert(dto, entityTypeDescriptor.getType());
		super.insert(entity);
	}

	public <DTO> void insertSelectivePlus(DTO dto) {
		Entity entity = (Entity) ormContext.getConversionService().convert(dto, entityTypeDescriptor.getType());
		super.insertSelective(entity);
	}

	public <DTO> List<DTO> selectByCriteriaPlus(Class<DTO> clazz, Criteria criteria) {
		List<Entity> list = super.selectByCriteria(criteria);
		return toList(list, clazz);
	}

	public <DTO> PageRes<DTO> selectByCriteriaWithPageReqPlus(Class<DTO> clazz, Criteria criteria, PageReq pageReq) {
		PageRes<Entity> p1 = super.selectByCriteriaWithPageReq(criteria, pageReq);
		PageRes<DTO> p2 = new PageRes<DTO>(pageReq);
		p2.setTotalCount(p1.getTotalCount());
		p2.setData(toList(p1.getData(), clazz));
		return p2;
	}

	public <DTO> List<DTO> selectByCriteriaWithBLOBsPlus(Class<DTO> clazz, Criteria criteria) {
		List<Entity> list = super.selectByCriteriaWithBLOBs(criteria);
		return toList(list, clazz);
	}

	public <DTO> PageRes<DTO> selectByCriteriaWithBLOBsWithPageReqPlus(Class<DTO> clazz, Criteria criteria,
			PageReq pageReq) {
		PageRes<Entity> p1 = super.selectByCriteriaWithBLOBsWithPageReq(criteria, pageReq);
		PageRes<DTO> p2 = new PageRes<DTO>(pageReq);
		p2.setTotalCount(p1.getTotalCount());
		p2.setData(toList(p1.getData(), clazz));
		return p2;
	}

	public <DTO> DTO selectByPrimaryKeyPlus(Class<DTO> clazz, Entity entity) {
		Entity entity0 = super.selectByPrimaryKey(entity);
		return ormContext.getConversionService().convert(entity0, clazz);
	}

	public <DTO> DTO selectByPrimaryKeyPlus0(DTO dto) {
		Entity entity = (Entity) ormContext.getConversionService().convert(dto, entityTypeDescriptor.getType());
		return (DTO) this.selectByPrimaryKeyPlus(dto.getClass(), entity);
	}

	public <DTO> int updateByCriteriaPlus(DTO dto, Criteria criteria) {
		Entity entity = (Entity) ormContext.getConversionService().convert(dto, entityTypeDescriptor.getType());
		return super.updateByCriteria(entity, criteria);
	}

	public <DTO> int updateByCriteriaSelectivePlus(DTO dto, Criteria criteria) {
		Entity entity = (Entity) ormContext.getConversionService().convert(dto, entityTypeDescriptor.getType());
		return super.updateByCriteriaSelective(entity, criteria);
	}

	public <DTO> int updateByCriteriaWithBLOBsPlus(DTO dto, Criteria criteria) {
		Entity entity = (Entity) ormContext.getConversionService().convert(dto, entityTypeDescriptor.getType());
		return super.updateByCriteriaWithBLOBs(entity, criteria);
	}

	public <DTO> int updateByPrimaryKeyPlus(DTO dto) {
		Entity entity = (Entity) ormContext.getConversionService().convert(dto, entityTypeDescriptor.getType());
		return super.updateByPrimaryKey(entity);
	}

	public <DTO> int updateByPrimaryKeySelectivePlus(DTO dto) {
		Entity entity = (Entity) ormContext.getConversionService().convert(dto, entityTypeDescriptor.getType());
		return super.updateByPrimaryKeySelective(entity);
	}

	public <DTO> int updateByPrimaryKeyWithBLOBsPlus(DTO dto) {
		Entity entity = (Entity) ormContext.getConversionService().convert(dto, entityTypeDescriptor.getType());
		return super.updateByPrimaryKeyWithBLOBs(entity);
	}

	private <DTO> List<DTO> toList(List<Entity> list, Class<DTO> clazz) {
		TypeDescriptor desc = typeDescriptorCache.get(clazz);
		if (desc == null) {
			desc = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(clazz));
			typeDescriptorCache.put(clazz, desc);
		}
		return (List<DTO>) ormContext.getConversionService().convert(list, entityListTypeDescriptor, desc);
	}
}
