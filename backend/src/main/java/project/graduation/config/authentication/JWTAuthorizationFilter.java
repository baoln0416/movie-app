package project.graduation.config.authentication;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import project.graduation.service.JWTTokenService;
import project.graduation.service.UserService;

@Component
public class JWTAuthorizationFilter extends GenericFilterBean {
	@Autowired
	private UserService userService;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		Authentication authentication = JWTTokenService
				.parseTokenToUserInformation((HttpServletRequest) servletRequest);
		if (authentication == null) {

			SecurityContextHolder.getContext().setAuthentication(authentication);
			filterChain.doFilter(servletRequest, servletResponse);
		} else {
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			String url = request.getRequestURL().toString();
			if (StringUtils.startsWithIgnoreCase(url, "/api/v1/login")) {
				filterChain.doFilter(request, response);
			} else {
				authentication = JWTTokenService.parseTokenToUserInformation(request);
				UserDetails user = userService.loadUserByUsername(authentication.getName());
				UsernamePasswordAuthenticationToken authentication2 = new UsernamePasswordAuthenticationToken(user,
						null, user.getAuthorities());

				SecurityContextHolder.getContext().setAuthentication(authentication2);
				filterChain.doFilter(request, response);
			}

		}

	}

}
