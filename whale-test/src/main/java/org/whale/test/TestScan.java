package org.whale.test;

import org.apache.calcite.plan.RelOptUtil;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.tools.FrameworkConfig;
import org.apache.calcite.tools.Frameworks;
import org.apache.calcite.tools.RelBuilder;

public class TestScan {

	
	public static void main(String[] args) {
		
		
		final FrameworkConfig config = Frameworks.newConfigBuilder().build();
		final RelBuilder builder = RelBuilder.create(config);
		
		RelNode build = builder.build();
		
		System.out.println(build);
		
		final RelNode node = builder
		  .scan("EMP")
		  .build();
		System.out.println(RelOptUtil.toString(node));
		
	}
}
