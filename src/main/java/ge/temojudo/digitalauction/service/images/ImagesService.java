package ge.temojudo.digitalauction.service.images;

import ge.temojudo.digitalauction.model.images.UploadImageRequest;
import ge.temojudo.digitalauction.model.images.UploadImageResponse;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
@AllArgsConstructor
public class ImagesService {

    private final Path uploadDirPath;

    public UploadImageResponse uploadImage(UploadImageRequest request) {
        MultipartFile image = request.getImage();
        String filename = image.getOriginalFilename() == null ? "" : image.getOriginalFilename();
        String imageId = RandomString.make(16);

        Path targetDirectory = uploadDirPath.resolve(imageId);
        Path targetLocation = targetDirectory.resolve(filename);

        try {
            Files.createDirectories(Paths.get(targetDirectory.toUri()));
            Files.copy(image.getInputStream(), targetLocation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new UploadImageResponse(imageId);
    }

}
