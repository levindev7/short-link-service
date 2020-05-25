package org.sls.shortlinkservice.service;

import lombok.extern.slf4j.Slf4j;
import org.hashids.Hashids;
import org.sls.shortlinkservice.model.Url;
import java.util.Date;

@Slf4j
public class HashidsUtil {
    public static int count = 1;
    public static Url getHashidsUtil(String originalUrl) {
        Url url = new Url(originalUrl);
        Hashids hashids = new Hashids(originalUrl, 5);
        String hash = hashids.encode(count);
        url.setToken(hash);

        url.setUrlCreationTime(new Date());
        log.info("Creating a token and its start time");
        return url;
    }
    public static Url getHashidsUtilWithNewId(String originalUrl) {
        Url url = new Url(originalUrl);
        Hashids hashids = new Hashids(originalUrl, 5);
        String hash = hashids.encode(++count);
        url.setToken(hash);

        url.setUrlCreationTime(new Date());
        log.info("Creating a token to add the original URL with the expired token to the database");
        return url;
    }
}
