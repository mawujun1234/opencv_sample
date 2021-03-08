package opencv;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

//https://github.com/TauBai/a-test-for-triangulation-using-opencv/blob/master/feature-based/Find_Contours.cpp
//首先二值化Canny
//然后findContours
//写个算法，判断哪些是椭圆，一个是构成椭圆的最少点数，一个椭圆的长短轴要在一定范围内，其实还可以再用面积约束下
// Imgproc.fitEllips，看下选的椭圆对不对
//最后Imgproc.ellipse
//现在最重要的是如何判断一个countours是椭圆了
//椭圆算分发1：https://blog.csdn.net/qq_33635860/article/details/78046838  RotatedRect类的概念很重要

public class EllipseTest {

//	private static Mat srcGray = new Mat();
//	private static int threshold = 100;
	
	private static JFrame frame;

	//参考GeneralContours2,HoughCirclesRun
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Random rng = new Random(12345);
		
		// TODO Auto-generated method stub
		//https://blog.csdn.net/qq_33635860/article/details/78046838
		//https://ask.csdn.net/questions/914488
		String filename = args.length > 0 ? args[0] : "src/data/stuff.jpg";
        Mat src = Imgcodecs.imread(filename);
        if (src.empty()) {
            System.err.println("Cannot read image: " + filename);
            System.exit(0);
        }
        
        Mat srcGray = new Mat();
        int threshold = 100;
        
        /// Convert image to gray and blur it
        //必须要有
        Imgproc.cvtColor(src, srcGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.blur(srcGray, srcGray, new Size(3, 3));
			
		Mat cannyOutput = new Mat();
        Imgproc.Canny(srcGray, cannyOutput, threshold, threshold * 2);
        //! [Canny]

        //! [findContours]
        /// Find contours
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(cannyOutput, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        //! [findContours]
        
      //! [draw]//给椭圆描边
        for (int i = 0; i < contours.size(); i++) {
        	MatOfPoint circle = contours.get(i);//new Mat();
        	//double s1=Imgproc.contourArea(circle);//面积
        	
        	//获取矩形
        	RotatedRect box = Imgproc.fitEllipse(new MatOfPoint2f(circle.toArray()));
        	double s3=box.size.area();
        	//
        	double s2=Math.PI*box.size.width*box.size.height/4;
        	System.out.println(s2+"====="+s3+"-----s2/s3="+s2/s3);
        	//面积比例，进行判断
        	if(s2/s3>0.78) {
        		Imgproc.ellipse(src, box, new Scalar(0,0,255));
        	}
        	
        }
        //! [draw]

        
        HighGui.imshow("寻找椭圆", src);
        HighGui.waitKey(0);
        //![display]

        System.exit(0);
	}

}
