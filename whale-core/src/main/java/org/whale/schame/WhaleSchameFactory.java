package org.whale.schame;

import java.util.Map;

import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaFactory;
import org.apache.calcite.schema.SchemaPlus;

public class WhaleSchameFactory implements SchemaFactory{

	@Override
	public Schema create(SchemaPlus parentSchema, String name, Map<String, Object> operand) {
		return new WhaleSchame(name);
	}

}
