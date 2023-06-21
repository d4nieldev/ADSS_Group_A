package PresentationLayer.GUI.Fields;

import java.awt.Dimension;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JFormattedTextField;

public class DateField extends JFormattedTextField {
    private static final long serialVersionUID = -4070878851012651987L;

    public DateField(DateTimeFormatter dateFormatter) {
        super(dateFormatter.toFormat(LocalDate::from));
        setPreferredSize(new Dimension(100, 26));
    }

    @Override
    public LocalDate getValue() {
        return (LocalDate) super.getValue();
    }
}
