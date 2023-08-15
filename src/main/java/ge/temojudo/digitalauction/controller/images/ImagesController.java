package ge.temojudo.digitalauction.controller.images;

import ge.temojudo.digitalauction.model.images.download.DownloadImageRequest;
import ge.temojudo.digitalauction.model.images.upload.UploadImageRequest;
import ge.temojudo.digitalauction.model.images.upload.UploadImageResponse;
import ge.temojudo.digitalauction.service.images.ImagesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@RestController
@RequestMapping(path = "images")
@AllArgsConstructor
@Validated
public class ImagesController {

    private final ImagesService imagesService;

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public UploadImageResponse uploadImage(@RequestParam MultipartFile image) {
        UploadImageRequest request = new UploadImageRequest(image);
        log.info("[uploadImage] called with args {}", request);

        UploadImageResponse response = imagesService.uploadImage(request);
        log.info("[uploadImage] returned response {}", response);

        return response;
    }

    @GetMapping("/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String imageId) {
        DownloadImageRequest request = new DownloadImageRequest(imageId);
        log.info("[downloadImage] called with args {}", request);

        ResponseEntity<Resource> response = imagesService.downloadImage(request);
        log.info("[downloadImage] returned response {}", response);

        return response;
    }

}
