package com.example.highthon.global.config.security

import com.example.highthon.global.config.error.handler.ExceptionHandlerFilter
import com.example.highthon.global.config.filter.FilterConfig
import com.example.highthon.global.config.jwt.JwtTokenResolver
import com.example.highthon.global.config.jwt.TokenProvider
import com.example.highthon.global.env.sms.SmsProperty
import mu.KLogger
import mu.KotlinLogging
import net.nurigo.sdk.NurigoApp.initialize
import net.nurigo.sdk.message.service.DefaultMessageService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import java.util.*

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val tokenProvider: TokenProvider,
    private val exceptionHandlerFilter: ExceptionHandlerFilter,
    private val tokenResolver: JwtTokenResolver,
    private val property: SmsProperty
) {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf().disable()
            .formLogin().disable()
            .cors()

            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeRequests()

            .antMatchers(HttpMethod.POST, "/auth/sms").permitAll()
            .antMatchers(HttpMethod.GET, "/auth/sms/check").permitAll()
            .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
            .antMatchers(HttpMethod.POST, "/auth").permitAll()
            .anyRequest().authenticated()
            .and()

            .apply(FilterConfig(tokenProvider, tokenResolver, exceptionHandlerFilter))
            .and().build()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun bankEncoder(): Base64.Encoder = Base64.getEncoder()

    @Bean
    fun messageService(): DefaultMessageService = initialize(property.apiKey, property.apiSecret, "https://api.coolsms.co.kr")

    @Bean
    fun logger(): KLogger = KotlinLogging.logger {}
}