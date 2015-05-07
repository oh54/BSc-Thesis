package test.thesis;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.lib.CombineFileInputFormat;

import cluster.hdfs.TiffImageWritable;


public class TiffImageInputFormat<K, V> extends CombineFileInputFormat<Text, TiffImageWritable> {

	@Override
	public RecordReader<Text, TiffImageWritable> getRecordReader(InputSplit arg0, JobConf arg1, Reporter arg2) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}




}

