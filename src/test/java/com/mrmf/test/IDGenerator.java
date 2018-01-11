package com.mrmf.test;

import com.osg.entity.Entity;
import com.osg.framework.util.SecurityHelper;

public class IDGenerator {

	public static void main(String[] args) {
		for(int i=0;i<10;i++) {
			System.out.println(Entity.getLongUUID());
			System.out.println(SecurityHelper.digest(Entity.getLongUUID()));
		}
	}
}
