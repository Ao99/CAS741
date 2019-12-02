import java.awt.Image;
import java.util.*;
import Constants; //from Constants module 
import ImageData //from ImageData Module
public class ImageVerify {

	static int minX = Constants.MINX;
	static int maxX = Constants.MAXX;
	static int minY = Constants.MINY;
	static int maxY = Constants.MAXY;
	
	public static boolean varify1File(ImageData img){
		//image has x,y,p(pixelValue)
		int imageWidth = img.width(null);
		int imageHeight = img.height(null);
		List<Integer>  imagePixelValue = img.pixelValue(null);
		boolean Valid = true;
		if(img.isEmpty()){
			System.out.println("Empty Image"); 
			Valid = false;
		}
		else if(imageWidth<minX || imageWidth> maxX){
			System.out.println("Bad Width");
			Valid = false;
		}
		else if(imageHeight<minY || imageHeight>maxY){
			System.out.println("Bad Height");
			Valid = false;
		}
		else if(imagePixelValue.size()!=0){
			for(Integer element:imagePixelValue){
				if(element<0 || element >255){
					System.out.println("Bad Pixel Data");
					Valid = false;
				}
			}
		}
		return Valid;
	}
	
	public static boolean compare2Files(ImageData img1, ImageData img2){
		int imageWidth1 = img1.width(null);
		int imageWidth2 = img2.width(null);
		int imageHeight1 = img1.height(null);
		int imageHeight2 = img2.height(null);
		List<Integer>  imagePixelValue1 = img1.pixelValue(null);
		List<Integer>  imagePixelValue2 = img2.pixelValue(null);
		boolean compare = true;
		if(img1.isEmpty()){
			System.out.println("Empty Image1"); 
			compare = false;
		}
		else if(img2.isEmpty()){
			System.out.println("Empty Image2"); 
			compare = false;
		} 
		else if(imageWidth1 != imageWidth2){
			System.out.println("Bad Width2");
			compare = false;
		}
		else if(imageHeight1 != imageHeight2){
			System.out.println("Bad Height2");
			compare = false;
		}
		return compare;
	}
	
}
