package ge.temojudo.digitalauction.service.images;

import ge.temojudo.digitalauction.exceptions.resourcenotfound.ResourceNotFoundException;
import ge.temojudo.digitalauction.model.images.download.DownloadImageRequest;
import ge.temojudo.digitalauction.model.images.upload.UploadImageRequest;
import ge.temojudo.digitalauction.model.images.upload.UploadImageResponse;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;


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

    public ResponseEntity<Resource> downloadImage(DownloadImageRequest request) {
        File fileDir = uploadDirPath.resolve(request.getImageId()).toFile();

        File[] listOfFiles = fileDir.listFiles();
        if (listOfFiles == null || listOfFiles.length != 1) {
            throw new ResourceNotFoundException(
                    Map.of(
                            "imageId",
                            String.format("Image not found with imageId=[%s]", request.getImageId())
                    )
            );
        }

        Path filePath = Paths.get(fileDir.toURI()).resolve(listOfFiles[0].getName()).normalize();

        try {
            Resource resource = new UrlResource(filePath.toUri());

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            String.format("attachment; filename=\"%s\"", resource.getFilename())
                    )
                    .body(resource);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
