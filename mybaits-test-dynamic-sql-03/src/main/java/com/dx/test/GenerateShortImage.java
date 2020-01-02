package com.dx.test;

import java.awt.Graphics2D;  
import java.awt.RenderingHints;  
import java.awt.geom.AffineTransform;  
import java.awt.image.BufferedImage;  
import java.awt.image.ColorModel;  
import java.awt.image.WritableRaster;  
import java.io.File;  
import java.io.IOException;  

import javax.imageio.ImageIO;  

public class GenerateShortImage {
	/** 
     * 为图片文件生成缩略图 
     * @param args 
     */  
    public static void main(String[] args) {  
        try {  
            //参数1（源图片路径），参数2 （缩略图路径），参数3（缩略图宽）参数4（缩略图高）  
            saveImageAsJpg("/Users/dz/Documents/images4.jpg", "/Users/dz/Documents/images4-do.jpg",((Double)(1440*1.2d)).intValue(), ((Double)(332*1.2d)).intValue());  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     * 生成缩略图 
     * @param fromFileStr 源图片路径 
     * @param saveToFileStr 缩略图路径 
     * @param width 缩略图的宽 
     * @param hight 缩略图的高 
     * @throws IOException 
     */  
    public static void saveImageAsJpg(String fromFileStr,String saveToFileStr,int width,int hight) throws IOException  
    {  
        BufferedImage srcImage;  
        String imgType="JPEG";  
        if(fromFileStr.toLowerCase().endsWith(".png")){  
            imgType="PNG";  
        }  
        File saveFile=new File(saveToFileStr);  
        File fromFile=new File(fromFileStr);  
        srcImage=ImageIO.read(fromFile);  
        if(width>0||hight>0){  
            srcImage=resize(srcImage,width,hight);  
        }  
        ImageIO.write(srcImage, imgType, saveFile);  
    }  
  
    /** 
     * 将源图片的BufferedImage对象生成缩略图 
     * @param source 源图片的BufferedImage对象 
     * @param targetW 缩略图的宽 
     * @param targetH 缩略图的高 
     * @return 
     */  
    private static BufferedImage resize(BufferedImage source, int targetW,  
            int targetH) {  
        int type=source.getType();  
        BufferedImage target=null;  
        double sx=(double)targetW/source.getWidth();  
        double sy=(double)targetH/source.getHeight();  
        if(sx>sy)  
        {  
            sx=sy;  
            targetW=(int)(sx*source.getWidth());  
        }else{  
            sy=sx;  
            targetH=(int)(sy*source.getHeight());  
        }  
          
        if(type==BufferedImage.TYPE_CUSTOM){  
            ColorModel cm=source.getColorModel();  
                WritableRaster raster=cm.createCompatibleWritableRaster(targetW, targetH);  
                boolean alphaPremultiplied=cm.isAlphaPremultiplied();  
                target=new BufferedImage(cm,raster,alphaPremultiplied,null);  
        }else{  
            target=new BufferedImage(targetW, targetH,type);  
        }  
        Graphics2D g=target.createGraphics();  
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);  
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));  
        g.dispose();  
        return target;  
    }  
}
