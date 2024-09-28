package Application.ConfigFilters;

import App.Filters.Auth.AuthJWTFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigFilter {

    private final AuthJWTFilter authJWTFilter;

    public ConfigFilter(AuthJWTFilter authJWTFilter) {
        this.authJWTFilter = authJWTFilter;
    }

    @Bean
    public FilterRegistrationBean<AuthJWTFilter> jwtFilter() {
        FilterRegistrationBean<AuthJWTFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(authJWTFilter);

        registrationBean.addUrlPatterns("/api/*", "/auth/*");

        return registrationBean;
    }
}

