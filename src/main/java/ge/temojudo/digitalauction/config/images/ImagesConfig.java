package ge.temojudo.digitalauction.config.images;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;


@Configuration
@AllArgsConstructor
@NoArgsConstructor
public class ImagesConfig {

    @Value("${image.upload-dir}")
    private String imageUploadDir;

    @Bean
    public Path getUploadDirPath() {
        return Paths.get(imageUploadDir);
    }

}
