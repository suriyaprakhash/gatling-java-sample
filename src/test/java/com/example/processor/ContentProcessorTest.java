package com.example.processor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ContentProcessorTest  {
	
	@Mock
	private ContentProcessor contentProcessor;
	
	@Before
	public void setUp() {
	//	contentProcessor=new ContentProcessorImpl();
	}

	@Test
	public void classifyInputTest() {
		contentProcessor.classifyInput("glob is I");
	}
}
