package org.sls.shortlinkservice.repository;

import com.sun.istack.Nullable;
import org.sls.shortlinkservice.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UrlRepository extends JpaRepository<Url, String> {
    @Nullable
    @Transactional
    Url findByOriginalUrl(String originalUrl);

    @Nullable
    @Transactional
    Url findByToken(String token);
}
