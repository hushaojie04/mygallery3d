package sj.android.mygallery3d;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by Administrator on 2015/6/18.
 */
public class BufferUtil {
    public static FloatBuffer mBuffer;

    public static FloatBuffer floatToBuffer(float[] a) {
        ByteBuffer mbb = ByteBuffer.allocateDirect(a.length * 4);
        mbb.order(ByteOrder.nativeOrder());
        mBuffer = mbb.asFloatBuffer();
        mBuffer.put(a);
        mBuffer.position(0);
        return mBuffer;
    }

    public static IntBuffer intToBuffer(int[] a) {
        IntBuffer intBuffer;
        ByteBuffer mbb = ByteBuffer.allocateDirect(a.length * 4);
        mbb.order(ByteOrder.nativeOrder());
        intBuffer = mbb.asIntBuffer();
        intBuffer.put(a);
        intBuffer.position(0);
        return intBuffer;
    }

    public static ByteBuffer byteToBuffer(byte[] a) {
        ByteBuffer buffer = null;
        buffer = ByteBuffer.allocateDirect(a.length);
        buffer.put(a);
        buffer.position(0);
        return buffer;
    }
}
