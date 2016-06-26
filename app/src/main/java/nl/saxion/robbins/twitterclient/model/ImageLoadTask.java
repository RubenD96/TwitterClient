package nl.saxion.robbins.twitterclient.model;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.AsyncTask;

import nl.saxion.robbins.twitterclient.entities.media.Media;

/** Download bitmaps in the background thread based on a given URL. returns bitmap
 *
 * @author Niels Jan */
public class ImageLoadTask extends AsyncTask<String, Void, Bitmap> {

    /**********************************************************************************/
    /*********************** ENCAPSULATED INSTANCE VARIABLES **************************/
    /**********************************************************************************/

    private User user;
    private Media media;

    /**********************************************************************************/
    /********************************* CONSTRUCTORS ***********************************/
    /**********************************************************************************/

    /** Construct this bmp downloader from with a user */
    public ImageLoadTask(User user) {
        this.user = user;
    }

    /** Construct this bmp downloader from with a media object */
    public ImageLoadTask(Media media) {
        this.media = media;
    }

    /**********************************************************************************/
    /*********************************** METHODS **************************************/
    /**********************************************************************************/

    /** Perform the downloading in the background thread */
    protected Bitmap doInBackground(String... urls) {
        String imageURL = urls[0];
        Bitmap image = null;
        try {
            InputStream in = new java.net.URL(imageURL).openStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            image = BitmapFactory.decodeStream(in, null, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addBorder(image, 1);
    }

    /** Helper method, adds a black border to a image and returns the image */
    private Bitmap addBorder(Bitmap bmp, int borderSize) {
        Bitmap bmpWithBorder = null;
        if (bmp != null && bmp.getConfig() != null) {
            bmpWithBorder = Bitmap.createBitmap(bmp.getWidth() + borderSize * 2, bmp.getHeight() + borderSize * 2, bmp.getConfig());
            Canvas canvas = new Canvas(bmpWithBorder);
            canvas.drawColor(Color.BLACK);
            canvas.drawBitmap(bmp, borderSize, borderSize, null);
        }
        return bmpWithBorder;
    }

    /** set the result after the download depending on how this downloader was constructed */
    protected void onPostExecute(Bitmap result) {
        if (user != null) {
            user.setImage(result);
        } else if (media != null) {
            media.setImage(result);
        }
    }
}
