package cluster.hdfs;

import java.io.IOException;

import javax.imageio.stream.ImageOutputStream;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.RecordWriter;
import org.apache.hadoop.mapred.Reporter;

public class PngImageRecordWriter implements RecordWriter<Text, PngImageWritable> {

	private ImageOutputStream out;

	public PngImageRecordWriter(ImageOutputStream out) throws IOException {
		this.out = out;
	}

	@Override
	public void close(Reporter arg0) throws IOException {
		out.close();
	}

	@Override
	public void write(Text key, PngImageWritable value) throws IOException {
		javax.imageio.ImageIO.write(value.getImg(), "png", out);
	}

}