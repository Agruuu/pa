package com.orhon.pa.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.orhon.pa.modules.opa.dao.OpaPlanTaskDao;

public class Test {

	 public static void main(String[] args) {
		 
		 ApplicationContext xg = new FileSystemXmlApplicationContext("resource/applicationContext.xml");

	 }
}


