package cn.muses.common.orm.mybatis;

import java.util.List;

import javax.annotation.PostConstruct;

import cn.muses.common.orm.common.TypeConverter;
import cn.muses.common.orm.page.PageReq;
import cn.muses.common.orm.page.PageRes;
import org.apache.ibatis.session.RowBounds;
import org.springframework.core.convert.converter.ConverterRegistry;

public abstract class MyBatisDAOPlus<Entity, Criteria, DTO> extends MyBatisDAO<Entity, Criteria> {
	protected TypeConverter<Entity, DTO> typeConverter;

	@Override
	@PostConstruct
	public void postConstruct() {
		super.postConstruct();
		this.addConverter((ConverterRegistry) ormContext.getConversionService());
		typeConverter = new TypeConverter<>(this.getClass(), ormContext.getConversionService());
	}

	protected abstract void addConverter(ConverterRegistry registry);

	public int deleteByPrimaryKeyPlus(DTO dto) {
		return super.deleteByPrimaryKey(typeConverter.toEntity(dto));
	}

	public int insertPlus(DTO dto) {
		return super.insert(typeConverter.toEntity(dto));
	}

	public int insertSelectivePlus(DTO dto) {
		return super.insertSelective(typeConverter.toEntity(dto));
	}

	public List<DTO> selectByCriteriaPlus(Criteria criteria) {
		List<Entity> entities = super.selectByCriteria(criteria);
		return typeConverter.toDTOList(entities);
	}

	@Deprecated
	public PageRes<DTO> selectByCriteriaPlus(Criteria criteria, int pageIndex, int pageSize) {
		return selectByCriteriaWithPageReqPlus(criteria, new PageReq(pageIndex, pageSize));
	}

	public PageRes<DTO> selectByCriteriaWithPageReqPlus(Criteria criteria, PageReq pageReq) {
		PageRes<Entity> p1 = super.selectByCriteriaWithPageReq(criteria, pageReq);
		PageRes<DTO> p2 = new PageRes<DTO>(pageReq);
		p2.setTotalCount(p1.getTotalCount());
		p2.setData(typeConverter.toDTOList(p1.getData()));
		return p2;
	}

	public List<DTO> selectByCriteriaWithRowboundsPlus(Criteria criteria, RowBounds rowBounds) {
		List<Entity> entities = super.selectByCriteriaWithRowbounds(criteria, rowBounds);
		return typeConverter.toDTOList(entities);
	}

	public List<DTO> selectByCriteriaWithBLOBsPlus(Criteria criteria) {
		List<Entity> entities = super.selectByCriteriaWithBLOBs(criteria);
		return typeConverter.toDTOList(entities);
	}

	@Deprecated
	public PageRes<DTO> selectByCriteriaWithBLOBsPlus(Criteria criteria, int pageIndex, int pageSize) {
		return selectByCriteriaWithBLOBsWithPageReqPlus(criteria, new PageReq(pageIndex, pageSize));
	}

	public PageRes<DTO> selectByCriteriaWithBLOBsWithPageReqPlus(Criteria criteria, PageReq pageReq) {
		PageRes<Entity> p1 = super.selectByCriteriaWithBLOBsWithPageReq(criteria, pageReq);
		PageRes<DTO> p2 = new PageRes<DTO>(pageReq);
		p2.setTotalCount(p1.getTotalCount());
		p2.setData(typeConverter.toDTOList(p1.getData()));
		return p2;
	}

	public List<DTO> selectByCriteriaWithBLOBsWithRowboundsPlus(Criteria criteria, RowBounds rowBounds) {
		List<Entity> entities = super.selectByCriteriaWithBLOBsWithRowbounds(criteria, rowBounds);
		return typeConverter.toDTOList(entities);
	}

	public DTO selectByPrimaryKeyPlus(DTO dto) {
		Entity entity = super.selectByPrimaryKey(typeConverter.toEntity(dto));
		return typeConverter.toDTO(entity);
	}

	public int updateByCriteriaPlus(DTO dto, Criteria criteria) {
		return super.updateByCriteria(typeConverter.toEntity(dto), criteria);
	}

	public int updateByCriteriaSelectivePlus(DTO dto, Criteria criteria) {
		return super.updateByCriteriaSelective(typeConverter.toEntity(dto), criteria);
	}

	public int updateByCriteriaWithBLOBsPlus(DTO dto, Criteria criteria) {
		return super.updateByCriteriaWithBLOBs(typeConverter.toEntity(dto), criteria);
	}

	public int updateByPrimaryKeyPlus(DTO dto) {
		return super.updateByPrimaryKey(typeConverter.toEntity(dto));
	}

	public int updateByPrimaryKeySelectivePlus(DTO dto) {
		return super.updateByPrimaryKeySelective(typeConverter.toEntity(dto));
	}

	public int updateByPrimaryKeyWithBLOBsPlus(DTO dto) {
		return super.updateByPrimaryKeyWithBLOBs(typeConverter.toEntity(dto));
	}

}
