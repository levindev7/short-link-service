package org.sls.shortlinkservice.db;

import org.sls.shortlinkservice.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, String> {
    Url findByOriginalUrl(String originalUrl);
    Url findByToken(String token);
}
