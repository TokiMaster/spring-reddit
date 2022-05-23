package swt.reddit.demo.security;


import org.springframework.stereotype.Component;

//** Komponenta koja moze da obavlja dodatnu proveru zahteva pre nego sto dospe na endpoint.
//Moguce je pristupiti @PathVariable podacima sa URL-a zahteva na endpoint, poput {id}.
//https://docs.spring.io/spring-security/site/docs/5.2.11.RELEASE/reference/html/authorization.html
@Component
public class WebSecurity {

//    @Autowired
//    private UserService userService;
//
//    public boolean checkClubId(Authentication authentication, HttpServletRequest request, int id) {
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        User user = userService.findByUsername(userDetails.getUsername());
//        if(id == user.getId()) {
//            return true;
//        }
//        return false;
//    }
}

