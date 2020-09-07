/*
Copyright 2018 All rights reserved.
 */

package cn.muses.common.validator.data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 实体对象.一个实体对象包含多个Field及多个Group
 * @author Jervis
 * @date 2018/11/29.
 */
public class Entity extends ToStringObject {
	private final String				clazz;
	private final Map<String, Field>	fields	= new LinkedHashMap<String, Field>();
	private final Map<String, Group>	groups	= new LinkedHashMap<String, Group>();

	public Entity(String clazz) {
		this.clazz = clazz;
	}

	public String getClazz() {
		return clazz;
	}

	public Map<String, Field> getFields() {
		return fields;
	}

	public Map<String, Group> getGroups() {
		return groups;
	}

	public Field getField(String property) {
		return fields.get(property);
	}

	public Group getGroup(String property) {
		return groups.get(property);
	}

	public void addField(String property, Field field) {
		this.fields.put(property, field);
	}

	public void addGroup(String property, Group group) {
		this.groups.put(property, group);
	}
}
