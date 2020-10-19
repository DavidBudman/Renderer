package renderer;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class ImageWriterTest
{
    @Test
    public void writeImageTest()
    {
        ImageWriter imageWriter = new ImageWriter("testGridImage", 500, 500, 500, 500);

        // create black background
        for (int i = 0; i < 500; i++)
        {
            for (int j = 0; j < 500; j++)
            {
                imageWriter.writePixel(i, j, Color.BLACK);
            }
        }

        // draw grid rows
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 500; j++)
            {
                imageWriter.writePixel(i * 50, j, Color.WHITE);
                imageWriter.writePixel(i * 50 + 49, j, Color.WHITE);
            }
        }

        // draw grid columns
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 500; j++)
            {
                imageWriter.writePixel(j, i * 50, Color.WHITE);
                imageWriter.writePixel(j, i * 50 + 49, Color.WHITE);
            }
        }

        imageWriter.writeToImage();
    }
}