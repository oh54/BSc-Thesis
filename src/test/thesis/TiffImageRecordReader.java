package test.thesis;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RecordReader;

public class TiffImageRecordReader implements RecordReader<Text, ImageWritable> {

	public TiffImageRecordReader(JobConf job, InputSplit input)
			throws IOException {
	}

	@Override
	public void close() throws IOException {
	}

	@Override
	public Text createKey() {
		return new Text("");
	}

	@Override
	public ImageWritable createValue() {
		return new ImageWritable();
	}

	@Override
	public long getPos() throws IOException {
		return 0;
	}

	@Override
	public boolean next(Text arg0, ImageWritable arg1) throws IOException {
		return false;
	}

	@Override
	public float getProgress() throws IOException {
		return 0;
	}

}
