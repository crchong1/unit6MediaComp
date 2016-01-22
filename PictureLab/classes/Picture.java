import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(0);
      }
    }
  }
  
  public void negate()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
         int blue = pixelObj.getBlue();
         int red = pixelObj.getRed();
         int green = pixelObj.getGreen();
         pixelObj.setBlue(255 - blue);
         pixelObj.setGreen(255-green);
         pixelObj.setRed(255-red);
      }
    }
  }
  



public void diagonalNegate(int rowStartLocation, int rowEnd, int colStartLocation, int colEnd)
{
    Pixel[][] pixels = this.getPixels2D();
    for(int row= rowStartLocation; row < rowEnd; row = row+2)
    {
        for (int col = colStartLocation; col < colEnd; col = col + 2)
        {
            int blue = pixels[row][col].getBlue();
            int red = pixels[row][col].getRed();
            int green = pixels[row][col].getGreen();
            pixels[row][col].setBlue(255 - blue);
            pixels[row][col].setGreen(255-green);
            pixels[row][col].setRed(255-red);
        }
    }
}

public void diagonalNegate2(int rowStart, int colStart)
{
    Pixel[][] pixels = this.getPixels2D();
    int loopCounter = 0;
    int increment = colStart;
    boolean escape= false;
    while(rowStart < pixels.length && escape ==false)
    {
        increment = colStart;
        for(int row= rowStart; row < pixels.length&& escape == false; row++)
        {
            int col = increment;
            if(col >= pixels[row].length || row >= pixels.length)
            {
                escape = true;
            }
            else
            {
                col = increment;
                int blue = pixels[row][col].getBlue();
                int red = pixels[row][col].getRed();
                int green = pixels[row][col].getGreen();
                pixels[row][col].setBlue(255 - blue);
                pixels[row][col].setGreen(255-green);
                pixels[row][col].setRed(255-red);
                increment++;
            }
        }
        rowStart = rowStart + 5;
    }
}

  
  
  public void grayscale()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        int blue = pixelObj.getBlue();
        int red = pixelObj.getRed();
        int green = pixelObj.getGreen();
        int total = (int)(blue + red + green)/3;
        pixelObj.setBlue(total);
        pixelObj.setGreen(total);
        pixelObj.setRed(total);
      }
    }
  }
  
  public void fixUnderwater()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        int blue = pixelObj.getBlue();
        int red = pixelObj.getRed();
        int green = pixelObj.getGreen();
        if (blue < 163 && red >= 18)
        {
              pixelObj.setBlue(blue - 80);
              pixelObj.setGreen(green -80);
              pixelObj.setRed(red-100);
        }
      }
    }
  }
  
  
  
  public void keepOnlyBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setGreen(0);
        pixelObj.setRed(0);
      }
    }
  }
  
  public void cropAndCopy( Picture sourcePicture, int startSourceRow, int endSourceRow, int startSourceCol, int endSourceCol,
         int startDestRow, int startDestCol )
  {
      Pixel[][] intPixels = sourcePicture.getPixels2D();
      Pixel[][] finalPixels = this.getPixels2D();
      for (int row1 = startSourceRow; row1 < endSourceRow+1; row1++)
      {
          int reset = startDestCol;
          for (int col1 = startSourceCol; col1 < endSourceCol+1; col1++)
          {
               finalPixels[startDestRow][reset].setColor(intPixels[row1][col1].getColor());
               reset++;
          }
          startDestRow++;
          
      }
    }
  
  
  public void scaleShrink(Picture sourcePicture, int x)
  {
      Pixel[][] intPixels = sourcePicture.getPixels2D();
      Pixel[][] finalPixels = this.getPixels2D();
      for (int row = 0; row*x < intPixels.length; row++)
      {
          for (int col = 0; col*x < intPixels[row].length; col++)
          {
              finalPixels[row][col].setColor(intPixels[row*x][col*x].getColor());
          }

          
      }
  } 
  
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
   public void mirrorVerticalRightToLeft()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        leftPixel.setColor(rightPixel.getColor());
      }
    } 
  }
  
  /** Mirror just part of a picture of a temple */
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 27; row < 97; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++)
      {
        
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }
  
  
  public void mirrorArms()
  {
      int mirrorPoint = 195;
      Pixel topPixel = null;
      Pixel botPixel = null;
      Pixel[][] pixels = this.getPixels2D();
    
      // loop through the rows
      for (int row = 161; row < 195; row++)
      {
          // loop from 13 to just before the mirror point
          for (int col = 94; col < 294; col++)
          {
           topPixel = pixels[row][col];      
           botPixel = pixels[(mirrorPoint-row) +94 + 90][col];
           botPixel.setColor(topPixel.getColor());
           }
      }
      
      
  }
  
  
  public void mirrorSeagull()
  {
      int mirrorPoint = 244;
      Pixel leftPixel = null;
      Pixel rightPixel = null;
      Pixel[][] pixels = this.getPixels2D();
    
      // loop through the rows
      for (int row = 232; row < 320; row++)
      {
          for (int col = mirrorPoint; col < 345; col++)
          {
           rightPixel = pixels[row][col];      
           leftPixel = pixels[row][(mirrorPoint-col)+mirrorPoint];
           leftPixel.setColor(rightPixel.getColor());
           }
      }
      
      
      
  }
  
  
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, 
                 int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }

/** Method to create a collage of several pictures */
public void createCollage()
{
      
    Picture hummingbird1 = new Picture("hummingbird.jpg");
    Picture hummingbird2 = new Picture("hummingbird.jpg");
    this.mirrorVertical();
    hummingbird1.scaleShrink(hummingbird1,10);
    cropAndCopy(hummingbird1, 0, 59, 0, 179, 200, 500 );
    hummingbird2.scaleShrink(hummingbird1,5);
    hummingbird2.negate();
    cropAndCopy(hummingbird2, 0, 119, 0, 359, 300, 900 );
    hummingbird1.grayscale();
    cropAndCopy(hummingbird1, 0, 59, 0, 179, 50, 1200 );
    for(int i = 0; i < 80; i++)
    {
        int randomSquare = (int) (Math.random()*(531));
        int randomSquare2 = (int) (Math.random()*(1531));
        this.diagonalNegate(randomSquare,randomSquare+25, randomSquare2, randomSquare2+25); //creates randomly placed negative grids
    }
    this.diagonalNegate2(1,1); // creates diagonal negative lines from start position
    
    
    
    this.write("collage.jpg");
}
  
  /*
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    */


  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; 
           col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > 
            edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
      }
    }
  }
  
  
  /* Main method for testing - each class in Java can have a main 
   * method 
   */
  public static void main(String[] args) 
  {
    Picture beach = new Picture("water.jpg");
    beach.explore();
    beach.cropAndCopy( beach, 20,50,10,30,140,140);
    beach.explore();
  }
  
} // this } is the end of class Picture, put all new methods before this
