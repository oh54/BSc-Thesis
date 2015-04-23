package test.thesis;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.InputFormat;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.Reporter;

public class TiffImageInputFormat<K, V> implements InputFormat<Text, ImageWritable> {

	@Override
	public RecordReader<Text, ImageWritable> getRecordReader(InputSplit arg0, JobConf arg1,
			Reporter arg2) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputSplit[] getSplits(JobConf arg0, int arg1) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}

/*
public class ImageInputFormat extends FileInputFormat<Text, ImageWritable> {

    @Override
    public ImageRecordReader createRecordReader(InputSplit split, 
                  TaskAttemptContext context) throws IOException, InterruptedException {
        return new ImageRecordReader();
    }

    @Override
    protected boolean isSplitable(JobContext context, Path filename) {
        return false;
    }
}  
*/