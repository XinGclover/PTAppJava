package com.example.backend.utils;

import java.io.File;
import java.io.FilenameFilter;

public class MyFileFilter implements FilenameFilter {

    @Override
    public boolean accept(File directory, String fileName) {

        if (fileName.endsWith(".json")) {
            return true;
        }
        return false;
    }
}
