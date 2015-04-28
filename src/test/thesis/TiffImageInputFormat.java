package test.thesis;

import org.apache.avro.mapred.SequenceFileInputFormat;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;

public class TiffImageInputFormat<K, V> extends
		SequenceFileInputFormat<Text, ImageWritable> {


	@Override
	protected boolean isSplitable(FileSystem fs, Path filename) {
		// TODO Auto-generated method stub
		return false;
	}
}

/*
 * public class ImageInputFormat extends FileInputFormat<Text, ImageWritable> {
 * 
 * @Override public ImageRecordReader createRecordReader(InputSplit split,
 * TaskAttemptContext context) throws IOException, InterruptedException { return
 * new ImageRecordReader(); }
 * 
 * @Override protected boolean isSplitable(JobContext context, Path filename) {
 * return false; } }
 */