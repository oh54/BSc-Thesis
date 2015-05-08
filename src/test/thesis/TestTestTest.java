package test.thesis;

import java.util.Arrays;

import javax.imageio.ImageIO;

public class TestTestTest {
	
	public static void main(String[] args){
		
		String pathStr = "hdfs://localhost:9000/user/oskar/input/0_0_s1a-iw1-slc-vh-20150501t160356-20150501t160424-005730-0075b6-001.tiff";
		
		String[] tokens = pathStr.split("/");
		
		String[] imgTokens = tokens[tokens.length - 1].split("_");
		

		System.out.println(Arrays.toString(imgTokens));
		
		String loc = imgTokens[0] + "_" + imgTokens[1];
		
		System.out.println(loc);
		
		
	}

}
