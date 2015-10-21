package com.util;

import java.util.UUID;



public class Id {
	public static String getId() {
		return UUID.randomUUID().toString();
	}

	public static void main(String[] args) {
		 System.out.println(getId());
	}
}
