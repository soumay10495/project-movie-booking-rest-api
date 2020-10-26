package project.entity.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SexConverter implements AttributeConverter<Sex, String> {
    @Override
    public String convertToDatabaseColumn(Sex sex) {
        if (sex == null)
            return null;
        else
            return sex.name().toString();
    }

    @Override
    public Sex convertToEntityAttribute(String s) {
        if (s == null)
            return null;
        else
            return Sex.valueOf(s);
    }
}
