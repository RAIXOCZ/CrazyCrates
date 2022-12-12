package com.badbones69.crazycrates.api.configuration.enums;

public enum Jars {

    LUCKO_HELPER("helper.jar", "lucko", "https://ci.lucko.me/job/helper/lastSuccessfulBuild/artifact/helper/target/helper.jar", "https://github.com/lucko/helper");

    private final String jarName;
    private final String authorName;
    private final String downloadLink;
    private final String githubLink;

    Jars(String jarName, String authorName, String downloadLink, String githubLink) {
        this.jarName = jarName;
        this.authorName = authorName;
        this.downloadLink = downloadLink;
        this.githubLink = githubLink;
    }

    public String getJarName() {
        return jarName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public String getGithubLink() {
        return githubLink;
    }
}