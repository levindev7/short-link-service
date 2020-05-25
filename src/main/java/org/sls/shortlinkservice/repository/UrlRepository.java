package org.sls.shortlinkservice.repository;

import com.sun.istack.Nullable;
import org.sls.shortlinkservice.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, String> {
    @Nullable
    Url findByOriginalUrl(String originalUrl);


    @Nullable
    Url findByToken(String token);
}
