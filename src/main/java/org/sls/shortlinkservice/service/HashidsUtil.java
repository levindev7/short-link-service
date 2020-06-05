package org.sls.shortlinkservice.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hashids.Hashids;
import org.sls.shortlinkservice.model.Url;

import java.util.Date;

@Data
@Slf4j
public class HashidsUtil {
    private static int count = 1;

    public static Url getHashidsUtil(String originalUrl) {
        Url url = new Url(originalUrl);
        Hashids hashids = new Hashids(originalUrl, 5);
        String hash = hashids.encode(count);
        url.setToken(hash);

        url.setUrlCreationTime(new Date());
        log.info("Creating a token: " + url + " from the original URL:" + originalUrl + " and its start time " + url.getUrlCreationTime());
        return url;
        //creating a token from the original link and the time of its creation
    }

    public static Url getHashidsUtilWithNewId(String originalUrl) {
        Url url = new Url(originalUrl);
        Hashids hashids = new Hashids(originalUrl, 5);
        String hash = hashids.encode(++count);
        url.setToken(hash);

        url.setUrlCreationTime(new Date());
        log.info("Creating a token: " + url + " to add the original URL (" + originalUrl + ") with the expired token to the database");
        return url;
    }
}
