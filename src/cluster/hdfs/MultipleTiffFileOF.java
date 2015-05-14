package cluster.hdfs;

import java.io.IOException;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RecordWriter;
import org.apache.hadoop.mapred.lib.MultipleOutputFormat;
import org.apache.hadoop.util.Progressable;

public class MultipleTiffFileOF extends MultipleOutputFormat<Text, TiffImageWritable> {

	@Override
	public RecordWriter<Text, TiffImageWritable> getRecordWriter(FileSystem fs, JobConf job, String name, Progressable progress)
			throws IOException {
		return new TiffImageOutputFormat().getRecordWriter(fs, job, name, progress);
	}

	@Override
	protected RecordWriter<Text, TiffImageWritable> getBaseRecordWriter(FileSystem fs, JobConf job, String name, Progressable progress)
			throws IOException {
		return new TiffImageOutputFormat().getRecordWriter(fs, job, name, progress);
	}

	@Override
	protected String generateFileNameForKeyValue(Text key, TiffImageWritable value, String name) {
		// TODO Auto-generated method stub
		return key.toString();
		// return super.generateFileNameForKeyValue(key, value, name);
	}

}
