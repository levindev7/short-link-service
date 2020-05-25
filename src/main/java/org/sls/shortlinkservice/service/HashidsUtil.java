package org.sls.shortlinkservice.service;

import org.hashids.Hashids;
import org.sls.shortlinkservice.model.Url;
import java.util.Date;

public class HashidsUtil {
    public static Url getHashidsUtil(String originalUrl) {
        Url url = new Url(originalUrl);
        Hashids hashids = new Hashids(originalUrl, 5);
        String hash = hashids.encode(url.getId());
        url.setToken(hash);

        url.setUrlCreationTime(new Date());
        return url;
    }
    public Url getHashidsUtilWithNewId(String originalUrl, int id) {
        Url url = new Url(originalUrl);
        Hashids hashids = new Hashids(originalUrl, 5);
        String hash = hashids.encode(++id);
        url.setToken(hash);

        url.setUrlCreationTime(new Date());
        return url;
    }
}
