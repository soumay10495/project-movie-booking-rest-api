package project.entity.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SeatTypeConverter implements AttributeConverter<SeatType, String> {
    @Override
    public String convertToDatabaseColumn(SeatType seatType) {
        if (seatType == null)
            return null;
        else
            return seatType.name().toString();
    }

    @Override
    public SeatType convertToEntityAttribute(String s) {
        if (s == null)
            return null;
        else
            return SeatType.valueOf(s);
    }
}
