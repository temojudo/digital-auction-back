package ge.temojudo.digitalauction.model.images;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
@AllArgsConstructor
public class UploadImageResponse {
    private String imageId;
}
