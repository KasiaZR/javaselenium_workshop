package pl.qalabs.workshops.javaselenium1.support;

import com.google.gson.Gson;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import pl.qalabs.workshops.javaselenium1.support.data.User;

import java.io.InputStreamReader;

public class UserParameterResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.isAnnotated(UserFile.class) && parameterContext.getParameter().getType().equals(User.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        UserFile annotation = parameterContext.getParameter().getAnnotation(UserFile.class);
        String resource = annotation.resource();
        User user = new Gson().fromJson(new InputStreamReader(getClass().getResourceAsStream(resource)), User.class);
        return user;
    }
}
