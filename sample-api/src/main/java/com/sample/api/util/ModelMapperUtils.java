package com.sample.api.util;

import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ModelMapperUtils {
	private static final ModelMapper MAPPER = new ModelMapper();

	static {
		MAPPER.getConfiguration()
			.setSkipNullEnabled(true)
			.setPropertyCondition(Conditions.isNotNull())
			.setMatchingStrategy(MatchingStrategies.STRICT)
			.setPropertyCondition(context -> !(context.getSource() instanceof PersistentCollection));
	}

	public static ModelMapper getMapper() {
		return MAPPER;
	}
}