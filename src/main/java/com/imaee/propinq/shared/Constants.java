package com.imaee.propinq.shared;

import java.util.List;

import static java.util.List.of;

public class Constants {
    public static final Long MAXIMUM_FILE_SIZE = 5_000_000L;
    public static final List<String> ALLOWED_IMAGE_FORMATS = of(
            "image/jpg",
            "image/jpeg",
            "image/png",
            "image/webp"
    );
}