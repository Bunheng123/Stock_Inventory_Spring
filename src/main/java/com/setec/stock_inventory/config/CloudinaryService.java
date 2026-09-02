package com.setec.stock_inventory.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.setec.stock_inventory.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    // Upload
    public Map uploadFile(MultipartFile file, String folderName) throws Exception {
        if(file.isEmpty() || file == null){
            throw new BadRequestException("File is empty, or missing file");
        }
        try{
            return cloudinary.uploader()
                    .upload(
                            file.getBytes(),
                            ObjectUtils.asMap("folder", "stock_inventory/image")                    );
        }catch(IOException e){
            throw new BadRequestException("Error while uploading image to cloudinary"+ e.getMessage());

        }
    }

    //delete
    public Map deleteFile(String publicId){
        if(publicId == null || publicId.isEmpty()){
            return Map.of();
        }
        try{
            return cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        }catch(IOException e){
            throw new BadRequestException("Error while deleting image from cloudinary"+ e.getMessage());
        }
    }

}
