import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotBlank { }

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength { int value(); }

class SignupForm {
    @NotBlank @MaxLength(10)
    String username;

    @NotBlank
    String email;

    @MaxLength(8)
    String password;

    SignupForm(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}

public class FormValidator {

    // reads each field's annotations by reflection and collects the errors
    static List<String> validate(Object obj) throws IllegalAccessException {
        List<String> errors = new ArrayList<>();
        for (Field f : obj.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            String value = (String) f.get(obj);

            if (f.isAnnotationPresent(NotBlank.class) && (value == null || value.isBlank())) {
                errors.add(f.getName() + " must not be blank");
            }
            if (f.isAnnotationPresent(MaxLength.class) && value != null) {
                int max = f.getAnnotation(MaxLength.class).value();
                if (value.length() > max) {
                    errors.add(f.getName() + " must be at most " + max + " characters (found " + value.length() + ")");
                }
            }
        }
        return errors;
    }

    public static void main(String[] args) throws Exception {
        SignupForm good = new SignupForm("priyansh", "p@example.com", "secret12");
        SignupForm bad  = new SignupForm("  ", "", "averyverylongpassword");

        for (SignupForm form : new SignupForm[] { good, bad }) {
            List<String> errors = validate(form);
            System.out.println("Form for '" + form.username.trim() + "': "
                    + (errors.isEmpty() ? "VALID" : errors.size() + " error(s)"));
            for (String e : errors) System.out.println("   - " + e);
        }
    }
}
