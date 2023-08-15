package ge.temojudo.digitalauction.controller.images;

import ge.temojudo.digitalauction.model.images.UploadImageRequest;
import ge.temojudo.digitalauction.model.images.UploadImageResponse;
import ge.temojudo.digitalauction.model.users.loginuser.LoginUserRequest;
import ge.temojudo.digitalauction.model.users.loginuser.LoginUserResponse;
import ge.temojudo.digitalauction.model.users.registeruser.RegisterUserRequest;
import ge.temojudo.digitalauction.model.users.registeruser.RegisterUserResponse;
import ge.temojudo.digitalauction.service.images.ImagesService;
import ge.temojudo.digitalauction.service.users.LoginService;
import ge.temojudo.digitalauction.service.users.RegistrationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


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

}
