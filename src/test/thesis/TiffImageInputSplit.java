package test.thesis;

import java.awt.image.BufferedImage;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.mapred.InputSplit;

import cluster.hdfs.TiffImageWritable;

public class TiffImageInputSplit implements InputSplit{

	TiffImageWritable imgSplit;
	
	public TiffImageInputSplit() {
		//new Filespl
	}
	
	@Override
	public void readFields(DataInput in) throws IOException {
		imgSplit = new TiffImageWritable();
		imgSplit.readFields(in);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		imgSplit.write(out);
	}

	@Override
	public long getLength() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String[] getLocations() throws IOException {
		//imgSplit.getImg().get
		return null;
	}
	

}
