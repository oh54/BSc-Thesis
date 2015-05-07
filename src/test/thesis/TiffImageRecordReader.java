package test.thesis;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RecordReader;

import cluster.hdfs.TiffImageWritable;

public class TiffImageRecordReader implements RecordReader<Text, TiffImageWritable> {

	public TiffImageRecordReader(JobConf job, FileSplit input)
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
	public TiffImageWritable createValue() {
		return new TiffImageWritable();
	}

	@Override
	public long getPos() throws IOException {
		return 0;
	}

	@Override
	public boolean next(Text arg0, TiffImageWritable arg1) throws IOException {
		return false;
	}

	@Override
	public float getProgress() throws IOException {
		return 0;
	}

}
