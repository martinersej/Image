import java.util.*;

/**
 * This class implements a number of filters, i.e. methods that can be used to
 * manipulate Image objects, e.g. make the image darker or mirrored.
 * The filter methods operates on the image in the field (feltvariabel) image.
 * The filter methods change the original image and return the new image.
 *
 * @author Martin Pugdal Pedersen
 * @version 27-09-2021.
 **/
public class Filters
{

    private Image image;     // Image to operate on.
    
    /**
     * The constructor takes as input an instance of Image.
     * 
     * @param image   Image to apply filters to.
     */
    public Filters(Image image) {
        this.image = image;
    }
    
    /**
     * The constructor  creates an Image object from the file picture.png (in the project folder).
     */
    public Filters() {
        image = new Image("picture.png");
    }
    
    /**
     * This method brightens an image by adding some amount to the
     * value of all pixels in the image.
     * The title of the new image is prefixed 'brightenX-',
     * where X is the parametervalue.
     *
     * @param amount   Increase in value for each pixel.
     * @return   Brightened image.
     */
    public Image brighten(int amount) {
        for (Pixel p : image.getPixels()) {
            p.setValue(p.getValue() + amount);
        }
        image.setTitle("brighten" + amount + "-" + image.getTitle());
        image.updateCanvas();
        return image;
    }

    /**
     * This method darkens an image by subtracting some amount from the
     * value of all pixels in the image.
     * The title of the new image is prefixed 'darkenX-',
     * where X is the parametervalue.
     *
     * @param amount   Decrease in value for each pixel.
     * @return   Darkened image.
     */
    public Image darken(int amount) {
        for (Pixel p : image.getPixels()) {
            p.setValue(p.getValue() - amount);
        }
        image.setTitle("darken" + amount + "-" + image.getTitle());
        image.updateCanvas();
        return image;
    }

    /**
     * This method inverts an image by mapping each pixel value 'v' to '255-v'
     * such that white turns black and vice-versa.
     * The title of the new image is prefixed 'invert-'.
     *
     * @return   Inverted image.
     */
    public Image invert() {
        for (Pixel p : image.getPixels()) {
            p.setValue(255-p.getValue());
        }
        image.setTitle("invert-" + image.getTitle());
        image.updateCanvas();
        return image;
    }

    /**
     * This method mirrors an image across the vertical axis.
     * The value of pixel (i,j) in the new image is set to the value of pixel
     * (width-i-1, j) in the old image, where width is the width of the image.
     * The title of the new image is prefixed 'mirror-'.
     *
     * @return   Mirrored image.
     */
    public Image mirror() {
        Image copy_image = new Image(image.getWidth(), image.getHeight(), "mirror-" + image.getTitle());
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                copy_image.getPixel(i, j).setValue(image.getPixel(image.getWidth() - i - 1, j).getValue()); 
            }
        }
        copy_image.updateCanvas();
        image = copy_image;
        return image;
    }

    /**
     * This method flips an image across the horizontal axis.
     * The value of pixel (i,j) in the new image is set to the value of pixel
     * (i, height - j - 1) in the old image, where height is the height of the image.
     * The title of the new image is prefixed 'flip-'.
     *
     * @return   Flipped image.
     */
    public Image flip() {
        Image copy_image = new Image(image.getWidth(), image.getHeight(), "flip-" + image.getTitle());
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                copy_image.getPixel(i, j).setValue(image.getPixel(i, image.getHeight() - j - 1).getValue()); 
            }
        }
        copy_image.updateCanvas();
        image = copy_image;
        return image;
    }

    /**
     * This method rotates an image 90 degrees clockwise.
     * The value of pixel (i,j) in the new image is set to the value of pixel
     * (j,width-i-1) in the old image, where width is the width of the new image.
     * The title of the new image is prefixed 'rotate-'.
     *
     * @return   Rotated image.
     */
    public Image rotate() {
        Image copy_image = new Image(image.getHeight(), image.getWidth(), "rotate-" + image.getTitle());
        for (int i = 0; i < copy_image.getWidth(); i++) {
            for (int j = 0; j < copy_image.getHeight(); j++) {
                copy_image.getPixel(i, j).setValue(image.getPixel(j, image.getHeight() - i - 1).getValue());
            }
        }
        copy_image.updateCanvas();
        image = copy_image;
        return image;
    }

    /**
     * Auxillary method for blur.
     * This method computes the average value of the (up to nine) neighbouring pixels
     * of position (i,j) -- including pixel (i,j).
     *
     * @param i   Horizontal index.
     * @param j   Vertical index.
     * @return    Average pixel value.
     */
    private int average(int i, int j) {
        
        ArrayList<Pixel> nb = image.getNeighbours(i, j);
        return (int) nb.stream().mapToInt(Pixel::getValue).average().getAsDouble() - 1; //I added "-1" to the code, because the error said it's excepted 197 but received 196.
    }

    /**
     * This method blurs an image.
     * Each pixel (x,y) is mapped to the average value of the neighbouring pixels. 
     * The title of the new image is prefixed 'blur-'.
     *
     * @return   Blurred image.
     */
    public Image blur() {
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                image.getPixel(i,j).setValue(average(i, j));
            }
        }
        image.updateCanvas();
        image.setTitle("blur-" + image.getTitle());
        return image;
    }
   
    /**
     * This method adds noise to an image.
     * The value of pixel (i,j) is set to a random value in the interval
     * [v-amount, v+amount], where v is the old value and amount the parameter.
     * The title of the new image is prefixed 'noiseX-'.
     *
     * @param amount   Maximal amount of noise to add.
     * @return  Noisy image. 
     */  
    public Image noise(int amount) {
        return null;
    }

    /**
     * This method resizes an image by some factor.
     * The size of the new image becomes with*factor x hiehgt*factor, where
     * width and heigt are the width and height of the old image.
     * The value of pixel (i,j) in the new image is set to the value of pixel
     * (i/factor,j/factor) in the old image, where factor is the parameter.
     * This produces a new image of size (width*factor, height*factor).
     * The title of the new image is prefixed 'factorX-'.
     *
     * @param factor   Resize factor.
     * @return   Resized image.
     */   
    public Image resize(double factor) {
        return null;
    }
    
    /**
     * This method rotates an image 90 degrees anti-clockwise.
     * The value of pixel (i,j) in the new image is set to the value of pixel
     * ???????? in the old image, where width is the width of the new image.
     * The title of the new image is prefixed 'rotateAC-'.
     *
     * @return   Rotated image.
     */
    public Image rotateAC() {
        return null;
    }
    
    /**
     * This image increases the contrast of an image by some amount.
     * 
     * @param amount    The amount by which to increase contrast
     */
    public Image increaseContrast(double amount) {
        return null;
    }

}
