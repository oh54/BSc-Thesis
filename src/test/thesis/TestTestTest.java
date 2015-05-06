package test.thesis;

import java.util.Arrays;

import javax.imageio.ImageIO;

public class TestTestTest {
	
	public static void main(String[] args){
		
		String writerNames[] = ImageIO.getWriterFormatNames();	
		System.out.println(Arrays.toString(writerNames));
	}

}
