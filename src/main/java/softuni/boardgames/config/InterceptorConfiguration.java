package softuni.boardgames.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import softuni.boardgames.interceptor.HelloInterceptor;
import softuni.boardgames.interceptor.LoggingInterceptor;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    private final LoggingInterceptor loggingInterceptor;
    private final HelloInterceptor helloInterceptor;

    public InterceptorConfiguration(LoggingInterceptor loggingInterceptor,
                                    HelloInterceptor helloInterceptor) {
        this.loggingInterceptor = loggingInterceptor;
        this.helloInterceptor = helloInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor).addPathPatterns("/games/add");
        registry.addInterceptor(helloInterceptor);
    }
}
