package br.com.javaspringrocketintro.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.javaspringrocketintro.todolist.users.IUsersRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterAuth extends OncePerRequestFilter {

   @Autowired
   private IUsersRepository usersRepository;

   @Override
   protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
   ) throws ServletException, IOException {

      var servletPath = request.getServletPath();

      if(servletPath.startsWith("/tasks/")) {

         var authorization = request.getHeader("Authorization");
   
         var userAuthorization = authorization.substring("Basic".length()).trim();
   
         byte[] userAuthorizationDecode = Base64.getDecoder().decode(userAuthorization);
   
         var userAuthorizationString = new String(userAuthorizationDecode);
   
         String[] userCredentials = userAuthorizationString.split(":");
   
         // System.out.println("------------------------");
         // System.out.println(authorization);
         // System.out.println(userAuthorization);
         // System.out.println(userAuthorizationString);
         // System.out.println(userCredentials[0]);
         // System.out.println(userCredentials[1]);
         // System.out.println("------------------------");
   
         var user = this.usersRepository.findByUserName(userCredentials[0]);
   
         if (user == null) {
            response.sendError(401);
         } else {
            var userPasswordCheck = BCrypt.verifyer().verify(userCredentials[1].toCharArray(), user.getPassword());
   
            if (userPasswordCheck.verified) {
               request.setAttribute("userId", user.getId());
               filterChain.doFilter(request, response);
            } else {
               response.sendError(401);
            }
   
         }
         
      } else {
         filterChain.doFilter(request, response);
      }

   }

}
