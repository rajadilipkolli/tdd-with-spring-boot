package com.example.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public final class FakeDataGenerator {

    public static byte[] getFakeCarsAsJSON() throws IOException {
//        File file = ResourceUtils.getFile("classpath:getCarResponse.json");
        ClassLoader classLoader = FakeDataGenerator.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("getCarResponse.json"))
                .getFile());
        return Files.readAllBytes(file.toPath());

    }
}

