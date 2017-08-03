package com.epam.company;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration*/
public class SecurityTest {

    /*@Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .build();
    }

    @Test
    public void authenticationSuccess() throws Exception {
        mvc
                .perform(login())
                .andExpect(authenticated().withUsername("user"));
    }

    @Test
    public void logoutSuccess() throws Exception {
        mvc
                .perform(login())
                .andExpect(authenticated().withUsername("user"));

        mvc.perform(logout()).andExpect(unauthenticated());
    }

    @Test
    public void authenticationFailed() throws Exception {
        mvc
                .perform(login().user("notfound").password("invalid"))
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(unauthenticated());
    }

    private FormLoginRequestBuilder login() {
        return SecurityMockMvcRequestBuilders
                .formLogin("/login")
                .userParameter("user")
                .passwordParam("pass");
    }

    private SecurityMockMvcRequestBuilders.LogoutRequestBuilder logout() {
        return SecurityMockMvcRequestBuilders
                .logout("/logout");
    }


    @Configuration
    @EnableWebMvcSecurity
    @EnableWebMvc
    static class Config extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .usernameParameter("user")
                    .passwordParameter("pass")
                    .loginPage("/login");
        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .inMemoryAuthentication()
                    .withUser("user").password("password").roles("USER");
        }
    }*/
}