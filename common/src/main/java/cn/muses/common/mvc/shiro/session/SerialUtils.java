package cn.muses.common.mvc.shiro.session;

import java.io.ObjectStreamConstants;
import java.util.Arrays;

import org.springframework.util.SerializationUtils;

/**
 * 序列化：对String类型数据直接调用getBytes()，其它情况使用jdk序列化 Created by mengfanjun on 14/11/18 018.
 */
public abstract class SerialUtils {
    // 序列化固定头
    private static final byte[] STREAM_MAGIC_BYTES = new byte[] { (byte) (ObjectStreamConstants.STREAM_MAGIC >>> 8),
            (byte) (ObjectStreamConstants.STREAM_MAGIC) };

    public static byte[] serialize(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return ((String) object).getBytes();
        } else {
            return SerializationUtils.serialize(object);
        }
    }

    public static Object deserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        byte[] header = Arrays.copyOf(bytes, Math.min(bytes.length, 2));
        if (Arrays.equals(header, STREAM_MAGIC_BYTES)) {
            return SerializationUtils.deserialize(bytes);
        } else {
            return new String(bytes);
        }
    }

    /**
     * 根据key和 prefix 计算外部缓存的key（redis key）
     *
     * @param key key
     * @return 计算后结果
     */
    public static byte[] computeKey(Object key, byte[] prefix) {
        if (key instanceof byte[]) {
            return (byte[]) key;
        }

        byte[] k;
        if (key instanceof String) {
            k = ((String) key).getBytes();
        } else {
            k = SerializationUtils.serialize(key);
        }

        if (prefix == null || prefix.length == 0) {
            return k;
        }

        byte[] result = Arrays.copyOf(prefix, prefix.length + k.length);
        System.arraycopy(k, 0, result, prefix.length, k.length);
        return result;
    }

}
