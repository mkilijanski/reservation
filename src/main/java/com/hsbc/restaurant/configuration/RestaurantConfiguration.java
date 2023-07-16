package com.hsbc.restaurant.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.hsbc.restaurant.domain.booking.controller.BookingController;
import com.hsbc.restaurant.domain.booking.handler.BookingCreatorHandler;
import com.hsbc.restaurant.domain.booking.handler.BookingFetchHandler;
import com.hsbc.restaurant.domain.booking.service.BookingRepository;
import com.hsbc.restaurant.domain.booking.service.impl.BookingRepositoryImpl;
import io.muserver.*;
import io.muserver.rest.RestHandlerBuilder;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import static com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS;
import static org.codehaus.plexus.util.StringUtils.isBlank;

public class RestaurantConfiguration {

    private static final String PASSWORD_HEADER = "password";
    private static final String SECRET_PASSWORD = "qwerty1234";//TODO to encoded app param or swap to JWT

    public static MuServerBuilder configure() {
        ObjectMapper objectMapper = getObjectMapper();
        BookingRepository bookingRepository = new BookingRepositoryImpl();

        return MuServerBuilder.httpServer()
//                .addHandler(new AuthHandler())
                .addHandler(RestHandlerBuilder.restHandler(
                                new BookingController(new BookingFetchHandler(bookingRepository),
                                        new BookingCreatorHandler(bookingRepository)))
                        .addRequestFilter(RestaurantConfiguration::filterIfRequestIsAllowed)
                        .addCustomWriter(new JacksonJaxbJsonProvider(objectMapper, DEFAULT_ANNOTATIONS))
                        .addCustomReader(new JacksonJaxbJsonProvider(objectMapper, DEFAULT_ANNOTATIONS))
                        .addExceptionMapper(RuntimeException.class,
                                (ExceptionMapper) throwable -> Response.status(Response.Status.BAD_REQUEST).entity(throwable.getMessage()).build()));
    }

    private static void filterIfRequestIsAllowed(ContainerRequestContext requestContext) {
        Request request = requestContext.getRequest();
        String path = requestContext.getUriInfo().getPath();
        if (request.getMethod().equals(Method.GET.name()) && path.equalsIgnoreCase("bookings")) {
            String passHeader = requestContext.getHeaderString(PASSWORD_HEADER);
            if(isBlank(passHeader) || !passHeader.equals(SECRET_PASSWORD)){
                throw new RuntimeException("Unauthorized!");
            }
        }
    }

//    static class AuthHandler implements MuHandler {
//        public boolean handle(MuRequest request, MuResponse response) {
//
//            if (request.method().equals(Method.GET) && request.relativePath().equals("/bookings")) {
//                String password = request.headers().get("password");
//                if(!isBlank(password) && password.equals("qwerty1234")){
//                    return false;
//                }
//                return true; //always return 200 -> but not continue process flow
//            } else {
//                return false;
//            }
//        }
//    }

    public static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
