package hu.uniobuda.nik.guideme;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by tothb on 2017. 05. 05..
 */

public class BitmapConvert
{
    public static byte[] fromImageToBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,stream);
        return stream.toByteArray();
    }

    public static Bitmap fromBytesToImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
