package org.dainn.mediaservice.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.dainn.mediaservice.service.IFirebaseService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FirebaseService implements IFirebaseService {
    private final Cloudinary cloudinary;

    @Override
    public void upload(MultipartFile file, String publicId) {
        try {
            cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap(
                            "public_id", publicId,
                            "overwrite", true
                    ));
        } catch (Exception e) {
            throw new RuntimeException("Error generating upload URL", e);
        }
    }
}
