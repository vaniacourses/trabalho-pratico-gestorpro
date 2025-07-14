package com.gestorpro.admin_service.utils;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public class MultipartFileSaveAdapter extends InputStream {

    private InputStream adaptee;

    public MultipartFileSaveAdapter(MultipartFile file) throws IOException{
        this.adaptee = file.getInputStream();
    }

    @Override
    public int read() throws IOException {
        return adaptee.read();
    }
    
}
