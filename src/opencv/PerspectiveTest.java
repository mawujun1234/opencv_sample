package opencv;

import java.util.Arrays;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;

public class PerspectiveTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    	
	        //! [load]
	       //String filename = ((args.length > 0) ? args[0] : "src/data/noobcvqr.png");
		 	String filename = ((args.length > 0) ? args[0] : "src/data/right.jpg");

	        // Load the image
	        Mat src = Imgcodecs.imread(filename);
	        Mat dst= new Mat();
	        
	        Point src_points[]=new Point[4];
	        Point dst_points[]=new Point[4];
//	        //获得4个角点坐标
//	        src_points[0]=new Point(94.0,374.0);
//	        src_points[1]=new Point(507.0,380.0);
//	        src_points[2]=new Point(1.0,623.0);
//	        src_points[3]=new Point(627.0,627.0);
//	        //期望透视后的4个焦点坐标
//	        dst_points[0]=new Point(0.0,0.0);
//	        dst_points[1]=new Point(627.0,0.0);
//	        dst_points[2]=new Point(0.0,627.0);
//	        dst_points[3]=new Point(627.0,627.0);
//	        
	        //int p1x/y p2x/y p3x/y p4x/y对应左上，右上、左下、右下四个点的x\y坐标
	        //640x480
	        //获得4个角点坐标
	        src_points[0]=new Point(239.0,113.0);
	        src_points[1]=new Point(539.0,137.0);
	        src_points[2]=new Point(212.0,288.0);
	        src_points[3]=new Point(459.0,420.0);
	        
	        //期望透视后的4个焦点坐标
	        dst_points[0]=new Point(0.0,0.0);
	        dst_points[1]=new Point(612.0,0.0);
	        dst_points[2]=new Point(0.0,459.0);
	        dst_points[3]=new Point(612.0,459.0);
	        
	     
	        
	        //计算透视变换矩阵
//	        Mat srcMat = Converters.vector_Point2f_to_Mat(Arrays.asList(src_points));
//	        Mat dstMat = Converters.vector_Point2f_to_Mat(Arrays.asList(dst_points));
	        
	        MatOfPoint2f srcMat = new MatOfPoint2f(src_points[0],src_points[1],src_points[2],src_points[3]);
	        MatOfPoint2f dstMat = new MatOfPoint2f(dst_points[0],dst_points[1],dst_points[2],dst_points[3]);
	        Mat rotation=Imgproc.getPerspectiveTransform(srcMat, dstMat);
	        
	        Size size=src.size();
	        //Size size=new Size(247.0,283.0);
	        
	        Imgproc.warpPerspective(src, dst, rotation, size);
	        
	        HighGui.imshow("透视变换", dst);
	        HighGui.waitKey(0);
	        //![display]

	        System.exit(0);
	        

	}

}
