package com.vosmann.jis.photo;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public class PhotoMetadata {

    private final String id;
    private final String user;
    private final String description;
    private final String url;

    private PhotoMetadata(Builder builder) {
        id = checkNotNull(builder.id, "Id can't be null.");
        user = checkNotNull(builder.user, "User can't be null.");
        description = checkNotNull(builder.description, "Description can't be null.");
        url = checkNotNull(builder.url, "URL can't be null.");
    }

    public static final class Builder {

        private String id;
        private String user;
        private String description;
        private String url;

        public Builder id(final String id) {
            this.id = id;
            return this;
        }

        public Builder user(final String user) {
            this.user = user;
            return this;
        }

        public Builder description(final String description) {
            this.description = description;
            return this;
        }

        public Builder url(final String url) {
            this.url = url;
            return this;
        }

        public static Builder of(final String user, final String description) {
            // UUID conflict possible.
            return new Builder().user(user).description(description).id(UUID.randomUUID().toString());
        }

        public String getId() {
            return id;
        }

        public PhotoMetadata build() {
            return new PhotoMetadata(this);
        }
    }

    public String getId() {
        return id;
    }

}
