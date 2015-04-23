package test.thesis;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class ImageWritable implements Writable {

	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		//javax.imageio.ImageIO.read(in);
	}

	@Override
	public void write(DataOutput arg0) throws IOException {
		// TODO Auto-generated method stub

	}

}
