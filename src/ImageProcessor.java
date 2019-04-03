import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ImageProcessor {

	protected int height;
	protected int width;
	protected ArrayList<ArrayList<RGB>> pixels;
	
	public ImageProcessor(String FName) {
		File file = new File(FName);
			
		try {
			Scanner scan = new Scanner(file);
			
			height = scan.nextInt();
			width = scan.nextInt();
			pixels = new ArrayList<ArrayList<RGB>>();
			
			for (int row=0; row<height; row++) {
				ArrayList<RGB> rowList = new ArrayList<RGB>();
				for (int column=0; column<width; column++) {
					int R = scan.nextInt();
					int G = scan.nextInt();
					int B = scan.nextInt();
					
					RGB rgb = new RGB(R, G, B);
					
					rowList.add(rgb);
				}
				pixels.add(rowList);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ArrayList<Integer>> getImportance(){
		ArrayList<ArrayList<Integer>> importanceList = new ArrayList<ArrayList<Integer>>();
		
		for (int row=0; row<height; row++) {
			ArrayList<Integer> rowImportance = new ArrayList<Integer>();
			
			for (int column=0; column<width; column++) {
				int importance = importance(row, column);
				rowImportance.add(importance);
			}
			
			importanceList.add(rowImportance);
		}
		
		return importanceList;
	}
	
	private void deepCopy(ArrayList<ArrayList<RGB>> pixels, ArrayList<ArrayList<RGB>> pixelsTemp) {
		
		for (ArrayList<RGB> tempRow: pixels) {
			ArrayList<RGB> rowList = new ArrayList<RGB>();
			for (RGB temprgb: tempRow) {
				RGB rgb= new RGB(temprgb.R, temprgb.G, temprgb.B);
				
				rowList.add(rgb);
			}
			pixelsTemp.add(rowList);
		}
	}
	
//	public void writeReduced(int k, String FName) {
//		for (int count=0; count<k; count++) {
//			ArrayList<Integer> S1 = new ArrayList<Integer>();
//			ArrayList<Integer> S2 = new ArrayList<Integer>();
//			
//			for (int i=0; i<width; i++) {
//				S2.add(i);
//				S2.add(height-1);
//			}
//			
//			ArrayList<ArrayList<Integer>> I = getImportance();
//			WGraph wg = new WGraph(I, height, width);
//			
//			ArrayList<Integer> minVC = wg.V2S(width, height, S2);
//			
//			for (int i=0; i<minVC.size(); i++) {
//				int column = minVC.get(i++);
//				int row = minVC.get(i);
//				
//				pixels.get(row).remove(column);
//			}
//			
//			width--;
//		}
//		
//		File file = new File(FName);
//		try {
//			FileWriter fw = new FileWriter(file);
//			
//			fw.write(height + "\r\n" + width + "\r\n");
//			
//			for (int row=0; row<height; row++) {
//				for (int column=0; column<width; column++) {
//					RGB rgb = pixels.get(row).get(column);
//					
//					fw.write(rgb.toString() + " ");
//				}
//				fw.write("\r\n");
//			}
//			fw.flush();
//			fw.close();
//			
////			width = tempWidth;
////			deepCopy(pixelsTemp, pixels);
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	public void writeReduced(int k, String FName) {
		ArrayList<ArrayList<RGB>> pixelsTemp = new ArrayList<ArrayList<RGB>>();
//		ArrayList<ArrayList<RGB>> pixelsTemp = new ArrayList<ArrayList<RGB>>(pixels);
//		deepCopy(pixels, pixelsTemp);
		int tempWidth = width;

		System.out.println("Before size:" + pixels.get(1).size() + " width:" + width);
		for (ArrayList<RGB> tempRow: pixels) {
			ArrayList<RGB> rowList = new ArrayList<RGB>();
			for (RGB temprgb: tempRow) {
				RGB rgb= new RGB(temprgb.R, temprgb.G, temprgb.B);
				
				rowList.add(rgb);
			}
			pixelsTemp.add(rowList);
		}
		
		for (int count=0; count<k; count++) {
		ArrayList<Integer> S1 = new ArrayList<Integer>();
		ArrayList<Integer> S2 = new ArrayList<Integer>();
		
		for (int i=0; i<width; i++) {
			S2.add(i);
			S2.add(height-1);
		}
		
		ArrayList<ArrayList<Integer>> I = getImportance();
		WGraph wg = new WGraph(I, height, width);
		
		ArrayList<Integer> minVC = wg.V2S(width, height, S2);
		
		for (int i=0; i<minVC.size(); i++) {
			int column = minVC.get(i++);
			int row = minVC.get(i);
			
			pixels.get(row).remove(column);
		}
		
		width--;
		}
		
		File file = new File(FName);
		try {
			FileWriter fw = new FileWriter(file);
			
			fw.write(height + "\r\n" + width + "\r\n");
			
			for (int row=0; row<height; row++) {
				for (int column=0; column<width; column++) {
					RGB rgb = pixels.get(row).get(column);
					
					fw.write(rgb.toString() + " ");
				}
				fw.write("\r\n");
			}
			fw.flush();
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		width = tempWidth;
		pixels = new ArrayList<ArrayList<RGB>>();
		for (ArrayList<RGB> tempRow: pixelsTemp) {
			ArrayList<RGB> rowList = new ArrayList<RGB>();
			for (RGB temprgb: tempRow) {
				RGB rgb= new RGB(temprgb.R, temprgb.G, temprgb.B);
				
				rowList.add(rgb);
			}
			pixels.add(rowList);
		}
//		pixels = new ArrayList<ArrayList<RGB>>(pixelsTemp);
		
		System.out.println("After size:" + pixels.get(1).size() + " width:" + width + " temp size:" + pixelsTemp.get(0).size());
	}
	
	
	private int importance(int x, int y) {
		return XImportance(x, y) + YImportance(x, y);
	}
	
	private int YImportance(int i, int j){
		int importance = 0;
		
		if (i == 0) {
			importance = PDist(pixels.get(height-1).get(j), pixels.get(i+1).get(j));
		}
		else if (i == height - 1) {
			importance = PDist(pixels.get(i-1).get(j), pixels.get(0).get(j));
		}
		else {
			importance = PDist(pixels.get(i-1).get(j), pixels.get(i+1).get(j));
		}
		
		return importance;
	}
	
	private int XImportance(int i, int j){
		int importance = 0;
		
		if (j == 0) {
			if (pixels.get(i).size() == 1) importance = PDist(pixels.get(i).get(width-1), pixels.get(i).get(j));
			else importance = PDist(pixels.get(i).get(width-1), pixels.get(i).get(j+1));
		}
		else if (j == width - 1) {
			importance = PDist(pixels.get(i).get(j-1), pixels.get(i).get(0));
		}
		else {
			importance = PDist(pixels.get(i).get(j-1), pixels.get(i).get(j+1));
		}
		
		return importance;
	}
	
	private int PDist(RGB p, RGB q) {
		return (int) Math.round(Math.pow(p.R - q.R, 2) + Math.pow(p.G - q.G, 2) + Math.pow(p.B - q.B, 2));
	} 
	
	final class RGB{
		
		int R;
		int G;
		int B;
		
		public RGB(int R, int G, int B) {
			this.R = R;
			this.G = G;
			this.B = B;
		}
		
		@Override
		public String toString() {
			return R + " " + G + " " + B;
		}
	}
}
