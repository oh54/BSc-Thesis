package test.thesis;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.lib.CombineFileInputFormat;


public class TiffImageInputFormat<K, V> extends CombineFileInputFormat<Text, ImageWritable> {

	@Override
	public RecordReader<Text, ImageWritable> getRecordReader(InputSplit arg0, JobConf arg1, Reporter arg2) throws IOException {
		// TODO Auto-generated method stub
		return null;
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