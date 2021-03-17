package pl.bookstore.restapi.commons;


import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;


@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@PreAuthorize("hasRole('CUSTOMER')")
public @interface IsCustomer {
}
