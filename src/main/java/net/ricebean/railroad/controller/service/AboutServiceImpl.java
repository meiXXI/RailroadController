package net.ricebean.railroad.controller.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AboutServiceImpl implements AboutService {

    private static final Logger log = LoggerFactory.getLogger(AboutServiceImpl.class);

    @Value("${app.name}")
    private String appName;

    @Value("${git.build.version}")
    private String version;

    @Value("${git.commit.time}")
    private String releaseTime;

    @Value("${git.commit.id}")
    private String commitId;

    @Value("${git.total.commit.count}")
    private int commitCount;

    @Value("${git.branch}")
    private String branch;

    @Value("${git.commit.id.abbrev}")
    private String commitIdAbbrev;

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getAppName() {
        return appName;
    }

    @Override
    public String getReleaseTime() {
        return releaseTime;
    }

    @Override
    public int getCommitCount() {
        return commitCount;
    }

    @Override
    public String getBranch() {
        return branch;
    }

    @Override
    public String getCommitId() {
        return commitId;
    }

    @Override
    public String getCommitIdAbbrev() {
        return commitIdAbbrev;
    }
}