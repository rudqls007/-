package toy.project.config;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /* application-properties에 설정한 uploadPath 프로퍼티 값을 읽어 옴. */
    @Value("${uploadPath}")
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        /* 웹 브라우저에 입력하는 url에 /images로 시작하는 경우 uploadPath에 설정한 폴더 기준으로 파일을 읽어오도록 설정 */
        registry.addResourceHandler("/images/**")
                /* 로컬 컴퓨터에 저장된 파일을 읽어 올 root 경로 설정 */
                .addResourceLocations(uploadPath);
    }

}