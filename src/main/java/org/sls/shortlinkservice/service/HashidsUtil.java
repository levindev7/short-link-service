package org.sls.shortlinkservice.service;

import org.hashids.Hashids;
import org.sls.shortlinkservice.model.Url;
import java.util.Date;

public class HashidsUtil {
    public static int count = 1;
    public static Url getHashidsUtil(String originalUrl) {
        Url url = new Url(originalUrl);
        Hashids hashids = new Hashids(originalUrl, 5);
        String hash = hashids.encode(count);
        url.setToken(hash);

        url.setUrlCreationTime(new Date());
        return url;
    }
    public static Url getHashidsUtilWithNewId(String originalUrl) {
        Url url = new Url(originalUrl);
        Hashids hashids = new Hashids(originalUrl, 5);
        String hash = hashids.encode(++count);
        url.setToken(hash);

        url.setUrlCreationTime(new Date());
        return url;
    }
}
