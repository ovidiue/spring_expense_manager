package expense.web.controller;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Ovidiu on 28-Nov-18.
 */
@Data
@Component
public class ExpenseFilter {
    private Long from;
    private Long to;
    private String title;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date dueDateFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date dueDateTo;
}
