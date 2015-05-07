package cluster.hdfs;

import java.io.IOException;

import javax.imageio.stream.ImageOutputStream;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RecordWriter;
import org.apache.hadoop.util.Progressable;

import test.thesis.TiffImageRecordWriter;


public class TiffImageOutputFormat  extends FileOutputFormat<Text, TiffImageWritable>{


	@Override
	public RecordWriter<Text, TiffImageWritable> getRecordWriter(FileSystem fs, JobConf job,
			String name, Progressable progress) throws IOException {
		Path file = FileOutputFormat.getTaskOutputPath(job, name);
		FSDataOutputStream fileOut = fs.create(file, progress);
		ImageOutputStream ios = javax.imageio.ImageIO.createImageOutputStream(fileOut);
	    return new TiffImageRecordWriter(ios);
	}

}
