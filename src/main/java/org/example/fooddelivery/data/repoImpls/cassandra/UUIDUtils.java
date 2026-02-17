package org.example.fooddelivery.data.repoImpls.cassandra;

import java.util.Random;
import java.util.UUID;

public class UUIDUtils {
    public static UUID getUUIDFromLong(Long value) {
        if (value == null) return new UUID(new Random().nextLong(), 0);
        return new UUID(value, 0);
    }

    public static Long getLongFromUUID(UUID value) {
        return value.getMostSignificantBits();
    }
}
