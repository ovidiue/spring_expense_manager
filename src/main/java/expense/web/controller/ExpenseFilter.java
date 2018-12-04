package expense.web.controller;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ovidiu on 28-Nov-18.
 */
@Data
@Component
public class ExpenseFilter {
    private Double amountFrom;
    private Double amountTo;
    private String title;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dueDateFrom;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dueDateTo;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createdFrom;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createdTo;
    private String description;
    private boolean recurrent;
    private Long categoryId;
    private ArrayList<Long> tagIds;

    public boolean isEmpty() {
        return this.amountFrom == null &&
                this.amountTo == null &&
                this.title == null &&
                this.dueDateFrom == null &&
                this.dueDateTo == null &&
                this.createdFrom == null &&
                this.createdTo == null &&
                this.description == null &&
                this.recurrent == false &&
                this.categoryId == null &&
                this.tagIds == null;
    }
}
