package softuni.boardgames.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import softuni.boardgames.interceptor.LoggingInterceptor;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    private final LoggingInterceptor loggingInterceptor;

    public InterceptorConfiguration(LoggingInterceptor loggingInterceptor) {
        this.loggingInterceptor = loggingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor).addPathPatterns("/games/add");
    }
}