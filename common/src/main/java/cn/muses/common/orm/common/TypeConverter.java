package cn.muses.common.orm.common;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Map;

import org.springframework.core.GenericTypeResolver;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.util.Assert;

public class TypeConverter<Entity, DTO> {
	private final ConversionService	conversionService;
	private TypeDescriptor			entityTypeDescriptor;
	private TypeDescriptor			entityListTypeDescriptor;
	private TypeDescriptor			dtoTypeDescriptor;
	private TypeDescriptor			dtoListTypeDescriptor;

	public TypeConverter(Class clazz, ConversionService conversionService) {
		this.conversionService = conversionService;
		initial(clazz);
	}

	private void initial(Class clazz) {
		Map<TypeVariable, Type> typeVariableMap = GenericTypeResolver.getTypeVariableMap(clazz);
		for (TypeVariable typeVariable : typeVariableMap.keySet()) {
			if (Constants.TYPE_VARIABLES_ENTITY.equals(typeVariable.getName())) {
				entityTypeDescriptor = TypeDescriptor.valueOf((Class<?>) typeVariableMap.get(typeVariable));
				entityListTypeDescriptor = TypeDescriptor.collection(List.class, entityTypeDescriptor);
			} else if (Constants.TYPE_VARIABLES_DTO.equals(typeVariable.getName())) {
				dtoTypeDescriptor = TypeDescriptor.valueOf((Class<?>) typeVariableMap.get(typeVariable));
				dtoListTypeDescriptor = TypeDescriptor.collection(List.class, dtoTypeDescriptor);
			}
		}
	}

	public List<DTO> toDTOList(List<Entity> entities) {
		Assert.notNull(conversionService);
		return (List<DTO>) conversionService.convert(entities, entityListTypeDescriptor, dtoListTypeDescriptor);
	}

	public DTO toDTO(Entity entity) {
		Assert.notNull(conversionService);
		return (DTO) conversionService.convert(entity, entityTypeDescriptor, dtoTypeDescriptor);
	}

	public Entity toEntity(DTO dto) {
		Assert.notNull(conversionService);
		return (Entity) conversionService.convert(dto, dtoTypeDescriptor, entityTypeDescriptor);
	}

}
