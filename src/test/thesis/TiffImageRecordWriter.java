package test.thesis;


import java.io.IOException;

import javax.imageio.stream.ImageOutputStream;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.RecordWriter;
import org.apache.hadoop.mapred.Reporter;

import cluster.hdfs.TiffImageWritable;

public class TiffImageRecordWriter implements RecordWriter<Text, TiffImageWritable> {

	private ImageOutputStream out;

	public TiffImageRecordWriter(ImageOutputStream out) throws IOException {
		this.out = out;
	}

	@Override
	public void close(Reporter arg0) throws IOException {
		out.close();
	}

	@Override
	public void write(Text key, TiffImageWritable value) throws IOException {
		javax.imageio.ImageIO.write(value.getImg(), "tif", out);
	}

}